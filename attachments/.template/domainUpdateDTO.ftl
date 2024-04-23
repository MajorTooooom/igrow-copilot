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

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
<#list javaTypes as javaType>
import ${javaType};
</#list>

/**
 * 更新`${domainChineseDescription}`DTO
 */
<#if isGenSwagger! == "true">
@ApiModel(description = "更新`${domainChineseDescription}`")
</#if>
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ${domainName}UpdateDTO {
<#list columns as column>
    <#if isGenSwagger! == "true">
    @ApiModelProperty(value = "${((column.columnSwaggerComment?trim!"")?length > 0)?then(column.columnSwaggerComment, column.javaName)}"${(column.columnKey!?has_content && column.columnKey == "PRI")?then(", required = true",", required = false")})
    </#if>
    //@${((column.javaType?trim!"") == "java.lang.String")?then("NotBlank","NotNull")}(message = "${((column.columnValidationComment?trim!"")?length > 0)?then(column.columnValidationComment, column.javaName)}不能为空")
    private ${column.javaTypeClassName} ${column.javaName};
    <#if column?has_next>

    </#if>
</#list>
}