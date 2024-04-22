<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="${mapperPackage}.${domainName}Mapper">
    <resultMap id="BaseResultMap" type="${domainPackage}.${domainName}">
        <!--@mbg.generated generated on ${mchpFormatDate}.-->
        <!--@Table ${tableName}-->
        <#list columns as column>
            <#if column.columnKey!?has_content && column.columnKey == "PRI">
        <id column="${column.columnName}" jdbcType="${column.jdbcType}" property="${column.javaName}"/>
            <#else>
        <result column="${column.columnName}" jdbcType="${column.jdbcType}" property="${column.javaName}"/>
            </#if>
        </#list>
    </resultMap>
    <sql id="Base_Column_List">
        <!--@mbg.generated generated on ${mchpFormatDate}.-->
        <#list columns as column>
            ${column.requireBackQuote?then("`","")}${column.columnName}${column.requireBackQuote?then("`","")}${(column?has_next)?then(",","")}
        </#list>
    </sql>
</mapper>