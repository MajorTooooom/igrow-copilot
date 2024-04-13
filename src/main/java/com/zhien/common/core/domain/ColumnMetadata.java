package com.zhien.common.core.domain;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.db.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

import java.util.Map;

/**
 * 存储列的元数据信息
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class ColumnMetadata {
    /**
     * 主键ID
     */
    private Integer id;

    /**
     * 表所属的目录名称，通常为def
     */
    private String tableCatalog;

    /**
     * 表所属的数据库名称
     */
    private String tableSchema;

    /**
     * 表名
     */
    private String tableName;

    /**
     * 列名
     */
    private String columnName;

    /**
     * 列在表中的位置（从1开始）
     */
    private Integer ordinalPosition;

    /**
     * 列的默认值
     */
    private String columnDefault;

    /**
     * 列是否可以包含NULL值（YES 或 NO）
     */
    private String isNullable;

    /**
     * 列的数据类型
     */
    private String dataType;

    /**
     * 字符或文本列的最大长度（以字符为单位）
     */
    private Integer characterMaximumLength;

    /**
     * 字符或文本列的最大长度（以字节为单位）
     */
    private Integer characterOctetLength;

    /**
     * 精确数值类型列的精度（总位数）
     */
    private Integer numericPrecision;

    /**
     * 精确数值类型列的小数位数
     */
    private Integer numericScale;

    /**
     * 日期时间类型列的小数秒精度
     */
    private Integer datetimePrecision;

    /**
     * 列字符集名称
     */
    private String characterSetName;

    /**
     * 列排序规则名称
     */
    private String collationName;

    /**
     * 列的数据类型和长度
     */
    private String columnType;

    /**
     * 列的键类型（PRI表示主键，UNI表示唯一键，MUL表示可被索引的）
     */
    private String columnKey;

    /**
     * 列的额外信息（例如，auto_increment）
     */
    private String extra;

    /**
     * 列的权限（如 select,insert,update 等）
     */
    private String privileges;

    /**
     * 列的注释
     */
    private String columnComment;

    /**
     * 列是否为生成列（ALWAYS为总是生成，NEVER为非生成列）
     */
    private String isGenerated;

    /**
     * 生成列的表达式
     */
    private String generationExpression;

    /**
     * 空间参考系统的ID。仅对空间列有效
     */
    private Integer srsId;

    // ===========================================================================================================
    public static ColumnMetadata specialConvert(Entity entity) {
        ColumnMetadata resultVo = ColumnMetadata.builder().build();
        for (Map.Entry<String, Object> et : entity.entrySet()) {
            // 字段名转小写驼峰
            String camelCase = StrUtil.toCamelCase(et.getKey().toLowerCase());
            // 利用反射对`ColumnMetadata`的字段值赋值
            BeanUtil.setFieldValue(resultVo, camelCase, et.getValue());
        }
        return resultVo;
    }
    // ===========================================================================================================
}