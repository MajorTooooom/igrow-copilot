package com.dororo.future.igrowcopilot.service;

import java.util.List;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.db.Db;
import cn.hutool.db.Entity;
import cn.hutool.json.JSONObject;
import com.alibaba.druid.pool.DruidDataSource;
import com.dororo.future.igrowcopilot.config.DruidDataSourceManager;
import com.dororo.future.igrowcopilot.config.MapperJavaTypeGroupsManager;
import com.dororo.future.igrowcopilot.config.MapperJdbcTypeGroupsManager;
import com.dororo.future.igrowcopilot.constant.CacheConstants;
import com.dororo.future.igrowcopilot.domain.ColumnCfg;
import com.dororo.future.igrowcopilot.domain.ConnCfg;
import com.dororo.future.igrowcopilot.domain.TableCfg;
import com.dororo.future.igrowcopilot.domain.common.ColumnMetadata;
import com.dororo.future.igrowcopilot.dto.*;
import com.dororo.future.igrowcopilot.mapper.ColumnCfgMapper;
import com.dororo.future.igrowcopilot.mapper.TableCfgMapper;
import com.zhien.common.core.domain.R;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class TableCfgService {
    @Autowired
    private TableCfgService cacheThisVo;
    @Autowired
    private TableCfgMapper tableCfgMapper;
    @Autowired
    private ColumnCfgMapper columnCfgMapper;

    public int deleteByPrimaryKey(Integer id) {
        return tableCfgMapper.deleteByPrimaryKey(id);
    }

    public TableCfg insert(TableCfg record) {
        tableCfgMapper.insert(record);
        record.setId(tableCfgMapper.getLastInsertId());
        return record;
    }

    public TableCfg insertSelective(TableCfg record) {
        int i = tableCfgMapper.insertSelective(record);
        record.setId(tableCfgMapper.getLastInsertId());
        return record;
    }

    public TableCfg selectByPrimaryKey(Integer id) {
        return tableCfgMapper.selectByPrimaryKey(id);
    }

    public int updateByPrimaryKeySelective(TableCfg record) {
        return tableCfgMapper.updateByPrimaryKeySelective(record);
    }

    public int updateByPrimaryKey(TableCfg record) {
        return tableCfgMapper.updateByPrimaryKey(record);
    }

    public List<TableCfg> findByAll(TableCfg tableCfg) {
        return tableCfgMapper.findByAll(tableCfg);
    }

    public R tableCfgOptions(ConnCfg connCfg) {
        DruidDataSource ds = DruidDataSourceManager.getOrDefault(connCfg.getUrl(), connCfg.getUserName(), connCfg.getPassword());
        // 查询表列表
        String sql = StrUtil.format("SELECT TABLE_SCHEMA, TABLE_NAME FROM information_schema.`COLUMNS` GROUP BY TABLE_SCHEMA, TABLE_NAME ORDER BY TABLE_SCHEMA");
        List<Entity> query = null;
        try {
            query = Db.use(ds).query(sql);
        } catch (Exception e) {
            throw new RuntimeException("查询表列表失败(#27)", e);
        }
        // SQL查出的字段带下划线,需要特殊的转换
        List<ColumnCfg> list = query.stream().map(q -> ColumnMetadata.specialConvert(q, ColumnCfg.class)).collect(Collectors.toList());

        // 忽略的库
        ArrayList<String> ignoreDb = CollectionUtil.toList("mysql", "information_schema", "performance_schema", "sys");
        list = list.stream().filter(e -> !ignoreDb.contains(e.getTableSchema())).collect(Collectors.toList());

        // 按照库进行分组
        Map<String, List<ColumnCfg>> map = list.stream().collect(Collectors.groupingBy(ColumnMetadata::getTableSchema));
        List<JSONObject> options = new ArrayList<>();
        map.forEach((tableSchema, columns) -> {
            JSONObject option = new JSONObject();
            option.put("label", tableSchema);
            option.put("value", tableSchema);
            List<JSONObject> children = new ArrayList<>();
            columns.forEach(e -> {
                JSONObject child = new JSONObject();
                child.put("label", e.getTableName());
                child.put("value", e.getTableName());
                children.add(child);
            });
            option.put("children", children);
            options.add(option);
        });

        R res = R.data(options);
        res.put("msg", StrUtil.format("加载完成：库[{}],表[{}]", map.size(), list.size()));
        return res;
    }

    @Cacheable(cacheNames = CacheConstants.TABLE_CFG_OPTIONS, key = "#cacheKey", unless = "#result == null")
    public R tableCfgOptionsCacheable(ConnCfg connCfg, String cacheKey) {
        return cacheThisVo.tableCfgOptions(connCfg);
    }

    @CachePut(cacheNames = CacheConstants.TABLE_CFG_OPTIONS, key = "#cacheKey", unless = "#result == null")
    public R tableCfgOptionsCachePut(ConnCfg connCfg, String cacheKey) {
        return cacheThisVo.tableCfgOptions(connCfg);
    }

    public void batchDelete(List<Integer> ids) {
        tableCfgMapper.deleteByIdIn(ids);
    }

    @SneakyThrows
    public JSONObject getTableAndColumnFromDs(DbColumnSelectRequestDTO dto) {
        DruidDataSource ds = DruidDataSourceManager.getOrDefault(dto.getUrl(), dto.getUserName(), dto.getPassword());
        // 查询表列表
        String sqlForColumn = StrUtil.format("SELECT * FROM information_schema.`COLUMNS` WHERE TABLE_SCHEMA = '{}' AND TABLE_NAME = '{}'", dto.getTableSchema(), dto.getTableName());
        List<Entity> query = Db.use(ds).query(sqlForColumn);
        // 转换
        List<ColumnCfg> list = query.stream().map(e -> ColumnMetadata.specialConvert(e, ColumnCfg.class)).collect(Collectors.toList());
        // 按照数据库顺序排序
        List<ColumnCfg> sortedList = list.stream().sorted(Comparator.comparing(ColumnMetadata::getOrdinalPosition)).collect(Collectors.toList());
        // TODO 填充扩展信息
        for (ColumnCfg columnCfg : sortedList) {
            // 字段名转小写驼峰
            columnCfg.setJavaName(StrUtil.lowerFirst(StrUtil.toCamelCase(columnCfg.getColumnName())));
            // 推荐的java类型
            columnCfg.setJavaType(getRecommendedJavaType(columnCfg.getColumnType()));
            // 推荐的java类型的类名
            columnCfg.setJavaTypeClassName(getRecommendedJavaTypeClassName(columnCfg.getJavaType()));
            // Swagger注释 & validation注释 预设
            columnCfg.setColumnSwaggerComment(Optional.ofNullable(columnCfg.getColumnComment()).filter(StrUtil::isNotBlank).orElse(columnCfg.getJavaName()));
            columnCfg.setColumnValidationComment(Optional.ofNullable(columnCfg.getColumnComment()).filter(StrUtil::isNotBlank).orElse(columnCfg.getJavaName()));
            // jdbcType预设
            columnCfg.setJdbcType(getRecommendedJdbcType(columnCfg.getColumnType()));
        }

        // 同时通过表名计算实体类名
        String tableName = dto.getTableName();
        String domainName = StrUtil.upperFirst(StrUtil.toCamelCase(tableName));
        // 查询表注释作为表名,如果以"表"字结尾则去掉,如果没注释则使用实体类名
        String domainZnName = null;
        String sql = StrUtil.format("SELECT TABLE_SCHEMA AS 'tableSchema', TABLE_NAME AS 'tableName', TABLE_COMMENT AS 'tableComment' FROM information_schema.`TABLES` WHERE TABLE_SCHEMA = '{}' AND TABLE_NAME = '{}';",
                dto.getTableSchema(), dto.getTableName());
        Entity entity = Db.use(ds).queryOne(sql);
        String tableComment = entity.getStr("tableComment");
        if (StrUtil.isBlank(tableComment)) {
            domainZnName = domainName;
        } else {
            domainZnName = StrUtil.removeSuffix(tableComment, "表");
        }


        // 推荐的配置名称
        String recommendedTableCfgName = StrUtil.format("[{}]-[{}]", dto.getTableSchema(), dto.getTableName());

        JSONObject res = new JSONObject();
        res.put("columns", sortedList);
        res.put("domainName", domainName);
        res.put("domainZnName", domainZnName);
        res.put("recommendedTableCfgName", recommendedTableCfgName);
        //
        return res;
    }

    private String getRecommendedJdbcType(String columnType) {
        MapperJdbcTypeGroups mapperJdbcTypeGroups = MapperJdbcTypeGroupsManager.getOrDefault();
        // 当前攒支持默认配置规则
        List<MapperJdbcTypeGroups.JdbcTypeMappingDTO.ElementListDTO> mappings = mapperJdbcTypeGroups.getJdbcTypeMapping().getElementList();
        for (MapperJdbcTypeGroups.JdbcTypeMappingDTO.ElementListDTO mapping : mappings) {
            if (Pattern.matches(mapping.getColumnType(), columnType)) {
                return mapping.getJdbcType();
            }
        }
        return null;
    }

    private String getRecommendedJavaType(String columnType) {
        MapperJavaTypeGroups groupsDTO = MapperJavaTypeGroupsManager.getOrDefault();
        // 当前仅支持默认配置规则
        List<MapperJavaTypeGroups.DefaultDTO.ElementListDTO> mappings = groupsDTO.getDefaultX().getElementList();
        for (MapperJavaTypeGroups.DefaultDTO.ElementListDTO mapping : mappings) {
            if (Pattern.matches(mapping.getColumnType(), columnType)) {
                return mapping.getJavaType();
            }
        }
        return null;
    }

    private String getRecommendedJavaTypeClassName(String javaType) {
        if (StrUtil.isBlank(javaType)) {
            return null;
        }
        // 尝试通过全类名获取类的简单名字
        String simpleName = null;
        try {
            Class<?> aClass = Class.forName(javaType);
            simpleName = aClass.getSimpleName();
        } catch (ClassNotFoundException e) {
            // ignore
        }
        return simpleName;
    }

    @Transactional(rollbackFor = Exception.class)
    public void addTableCfg(TableCfgAddDTO tableCfgAddDTO) {
        TableCfg tableAddVo = TableCfg.builder()
                .userId(tableCfgAddDTO.getUserId())
                .tableCfgName(tableCfgAddDTO.getTableCfgName())
                .connUrl(tableCfgAddDTO.getConnUrl())
                .connUserName(tableCfgAddDTO.getConnUserName())
                .connPassword(tableCfgAddDTO.getConnPassword())
                .tableSchema(tableCfgAddDTO.getTableSchema())
                .tableName(tableCfgAddDTO.getTableName())
                .domainName(tableCfgAddDTO.getDomainName())
                .domainZnName(tableCfgAddDTO.getDomainZnName())
                .createTime(DateUtil.now())
                .updateTime(DateUtil.now())
                .build();

        tableCfgMapper.insert(tableAddVo);
        int lastInsertId = tableCfgMapper.getLastInsertId();
        tableAddVo.setId(lastInsertId);

        List<ColumnCfg> columnCfgs = tableCfgAddDTO.getColumnCfgs();
        for (ColumnCfg columnCfg : columnCfgs) {
            columnCfg.setId(null);
            columnCfg.setUserId(tableCfgAddDTO.getUserId());
            columnCfg.setTableCfgId(tableAddVo.getId());
            columnCfgMapper.insert(columnCfg);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public void updateTableCfg(TableCfgUpdateDTO tableCfgUpdateDTO) {
        TableCfg tableUpdateVo = TableCfg.builder()
                .id(tableCfgUpdateDTO.getId())
                .userId(tableCfgUpdateDTO.getUserId())
                .tableCfgName(tableCfgUpdateDTO.getTableCfgName())
                .connUrl(tableCfgUpdateDTO.getConnUrl())
                .connUserName(tableCfgUpdateDTO.getConnUserName())
                .connPassword(tableCfgUpdateDTO.getConnPassword())
                .tableSchema(tableCfgUpdateDTO.getTableSchema())
                .tableName(tableCfgUpdateDTO.getTableName())
                .domainName(tableCfgUpdateDTO.getDomainName())
                .domainZnName(tableCfgUpdateDTO.getDomainZnName())
                .updateTime(DateUtil.now())
                .build();

        tableCfgMapper.updateByPrimaryKey(tableUpdateVo);

        List<ColumnCfg> columnCfgs = tableCfgUpdateDTO.getColumnCfgs();
        for (ColumnCfg columnCfg : columnCfgs) {
            if (columnCfg.getId() == null) {
                columnCfg.setId(null);
                columnCfg.setUserId(tableCfgUpdateDTO.getUserId());
                columnCfg.setTableCfgId(tableCfgUpdateDTO.getId());
                columnCfgMapper.insert(columnCfg);
            } else {
                columnCfgMapper.updateByPrimaryKey(columnCfg);
            }
        }
    }

    public int deleteByIdIn(Collection<Integer> idCollection) {
        return tableCfgMapper.deleteByIdIn(idCollection);
    }


}