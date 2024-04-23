<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="${mapperPackage}.${domainName}Mapper">
    <!--分页查询条件映射-->
    <resultMap id="PageConditionResultMap" type="${dtoPackage}.${domainName}ExportDTO">
        <#--暂弃用<!--@mbg.generated generated on ${mchpFormatDate}.&ndash;&gt;-->
        <!--@Table ${tableName}-->
        <#list columns as column>
            <#if column.columnKey!?has_content && column.columnKey == "PRI">
        <id column="${column.columnName}" jdbcType="${column.jdbcType}" property="${column.javaName}"/>
            <#else>
        <result column="${column.columnName}" jdbcType="${column.jdbcType}" property="${column.javaName}"/>
            </#if>
        </#list>
    </resultMap>
    <!--分页查询字段-->
    <sql id="page_condition_column_list">
        <#--暂弃用<!--@mbg.generated generated on ${mchpFormatDate}.&ndash;&gt;-->
        <#list columns as column>
        major.${column.requireBackQuote?then("`","")}${column.columnName}${column.requireBackQuote?then("`","")}${(column?has_next)?then(",","")}
        </#list>
    </sql>
    <!--已知公共用途(1)条件分页查询(2)导出(3)基于条件分页查询的统计;-->
    <select id="findAllByPageCondition" resultMap="PageConditionResultMap">
        select
        <include refid="page_condition_column_list"/>
        from ${tableName} major
        <where>
            <#list columns as column>
            <if test="majorRecord.${column.javaName} != null">
                and major.${column.columnName} = ${r'#{'}majorRecord.${column.javaName},jdbcType=${column.jdbcType}${r'}'}
                <#if column.javaType == "java.lang.String">
                <!--and major.${column.columnName} LIKE CONCAT('%',${r'#{'}majorRecord.${column.javaName},jdbcType=${column.jdbcType}${r'}'},'%')-->
                </#if>
            </if>
            </#list>
        </where>
    </select>
</mapper>