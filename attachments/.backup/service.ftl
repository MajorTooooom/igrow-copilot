package ${servicePackage};

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.TimeInterval;
import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.PageHelper;
import ${domainPackage}.${domainName};
import ${dtoPackage}.${domainName}AddDTO;
import ${dtoPackage}.${domainName}ImportDTO;
import ${dtoPackage}.${domainName}PageDTO;
import ${dtoPackage}.${domainName}UpdateDTO;
import org.springframework.stereotype.Service;

import org.springframework.beans.factory.annotation.Autowired;

import ${mapperPackage}.${domainName}Mapper;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.List;

@Service
public class ${domainName}Service {

    @Autowired
    private ${domainName}Mapper ${domainName?uncap_first}Mapper;

    public void addByDto(${domainName}AddDTO addDTO) {
        ${domainName} ${domainName?uncap_first} = Convert.convert(${domainName}.class, addDTO);
        ${domainName?uncap_first}Mapper.insert(${domainName?uncap_first});
    }

    public void delete(Integer id) {
        ${domainName?uncap_first}Mapper.deleteByPrimaryKey(id);
    }

    public void updateByDto(${domainName}UpdateDTO updateDTO) {
        ${domainName} ${domainName?uncap_first} = Convert.convert(${domainName}.class, updateDTO);
        ${domainName?uncap_first}Mapper.updateByPrimaryKeySelective(${domainName?uncap_first});
    }

    /**
     * (通用Mapper版本)根据条件可选项查询(`findByAll()`)
     */
    public List<${domainName}> conditionQuery(${domainName}PageDTO conditionVo) {
        if (conditionVo == null) {
            return new ArrayList<>();
        }
        // 可选:只展示未删除的数据
        // conditionVo.setDeleted(YesNoEnum.NO.value + "");
        Example example = new Example(${domainName}.class);
        Example.Criteria criteria = example.createCriteria();

        <#list columns as column>
            <#if column.columnComment??>
               // ${((column.columnComment?trim!"")?length > 0)?then(column.columnComment, column.fieldName)}
            </#if>
            <#if column.fieldType =="java.lang.String">
                if (StrUtil.isNotBlank(conditionVo.get${column.fieldName?cap_first}())) {
                    criteria.andLike("${column.fieldName}", "%" + conditionVo.get${column.fieldName?cap_first}() + "%");
                }
                <#else>
                    if (conditionVo.get${column.fieldName?cap_first}() != null) {
                    criteria.andEqualTo("${column.fieldName}", conditionVo.get${column.fieldName?cap_first}());
                    }
            </#if>
        </#list>

        // 查询
        List<${domainName}> list = ${domainName?uncap_first}Mapper.selectByExample(example);
        return list;
    }

    /**
     * 规避一次性查询数据量多大导致内存溢出的问题
     */
    public List<${domainName}> conditionQueryAllPages(${domainName}PageDTO pageDTO) {
        int pageNum = 1;
        final int pageSize = 1000;
        TimeInterval overtimeProtectionTimer = DateUtil.timer();
        long maxSecond = 30;
        List<${domainName}> result = new ArrayList<>();
        while (true) {
            PageHelper.startPage(pageNum, pageSize);
            List<${domainName}> pageResult = conditionQuery(pageDTO);
            result.addAll(pageResult);
            // 如果结果为空或者结果的大小小于pageSize，表示没有更多数据
            if (CollectionUtil.isEmpty(pageResult) || pageResult.size() < pageSize) {
                break;
            }
            // 防止数据量过大导致内存溢出
            if (overtimeProtectionTimer.intervalSecond() > maxSecond) {
                throw new RuntimeException(StrUtil.format("数据量过大,请缩小查询范围或分批查询({}秒超时)", maxSecond));
            }
            // 下一页
            pageNum++;
        }
        return result;
    }

    public void importRows(List<${domainName}ImportDTO> rows) {
        if (CollectionUtil.isEmpty(rows)) {
            return;
        }
        for (${domainName}ImportDTO row : rows) {
            ${domainName} ${domainName?uncap_first} = Convert.convert(${domainName}.class, row);
            ${domainName?uncap_first}Mapper.insert(${domainName?uncap_first});
        }
    }
}