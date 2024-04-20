<#include "环境变量辅助.ftl"/>
package ${dtoPackage};

<#if isGenSwagger! =="true">
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
</#if>
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

<#list javaTypes as javaType>
import ${javaType};
</#list>

/**
 * 条件分页查询或导出-查询条件DTO
 */
@ApiModel(description = "条件分页查询或导出-查询条件")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ${domainName}PageDTO {
<#list columns as column>
    <#if isGenSwagger! == "true">
    @ApiModelProperty(value = "${((column.columnSwaggerComment?trim!"")?length > 0)?then(column.columnSwaggerComment, column.javaName)}")
    </#if>
    private ${column.javaTypeClassName} ${column.javaName};
    <#if column?has_next>

    </#if>
</#list>
}