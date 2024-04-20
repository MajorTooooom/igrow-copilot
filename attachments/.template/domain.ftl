<#include "环境变量辅助.ftl"/>
package ${domainPackage};

<#if isGenSwagger! =="true">
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
</#if>

<#list javaTypes as javaType>
import ${javaType};
</#list>
import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

<#if isGenComment! == "true">
/**
 * `${domainChineseDescription}`实体类
 * @author ${author!}
 * @date ${dateTime!}
 */
</#if>
<#if isGenSwagger! == "true">
@ApiModel(description = "`${domainChineseDescription}`实体类")
</#if>
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "${tableName}")
public class ${domainName} {
<#list columns as column>
    <#if ((column.columnComment?trim!"")?length > 0)>
    /**
     * ${column.columnComment}
     */
    </#if>
    <#if column.columnKey!?has_content && column.columnKey == "PRI">
    @Id
    @Column(name = "${column.requireBackQuote?then("`","")}${column.columnName}${column.requireBackQuote?then("`","")}")
    @GeneratedValue(generator = "JDBC")
    <#else>
    @Column(name = "${column.requireBackQuote?then("`","")}${column.columnName}${column.requireBackQuote?then("`","")}")
    </#if>
    <#if isGenSwagger! == "true">
    @ApiModelProperty(value = "${((column.columnSwaggerComment?trim!"")?length > 0)?then(column.columnSwaggerComment, column.javaName)}")
    </#if>
    private ${column.javaTypeClassName} ${column.javaName};
    <#if column?has_next>

    </#if>
</#list>
}
