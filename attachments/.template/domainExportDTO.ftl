<#include "环境变量辅助.ftl"/>
package ${dtoPackage};

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
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
* 导出`${domainChineseDescription}`DTO
 */
<#if isGenSwagger! == "true">
@ApiModel(description = "导出`${domainChineseDescription}`")
</#if>
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ${domainName}ExportDTO {
<#list columns as column>
    <#if isGenSwagger! == "true">
    @ApiModelProperty(value = "${((column.columnSwaggerComment?trim!"")?length > 0)?then(column.columnSwaggerComment, column.javaName)}")
    </#if>
    @ExcelProperty(value = "${((column.columnSwaggerComment?trim!"")?length > 0)?then(column.columnSwaggerComment, column.javaName)}")
    private ${column.javaTypeClassName} ${column.javaName};
    <#if column?has_next>

    </#if>
</#list>
}