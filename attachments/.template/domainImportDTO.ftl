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

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
<#list javaTypes as javaType>
import ${javaType};
</#list>

/**
* 导入`${domainChineseDescription}`DTO
 */
<#if isGenSwagger! == "true">
@ApiModel(description = "导入`${domainChineseDescription}`")
</#if>
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ${domainName}ImportDTO {
<#list columns as column>
    <#if column.columnKey!?has_content && column.columnKey == "PRI">
    // private Integer id;

    <#else>
        <#if isGenSwagger! == "true">
    @ApiModelProperty(value = "${((column.columnSwaggerComment?trim!"")?length > 0)?then(column.columnSwaggerComment, column.javaName)}", required = true)
        </#if>
    @ExcelProperty(value = "${((column.columnSwaggerComment?trim!"")?length > 0)?then(column.columnSwaggerComment, column.javaName)}")
    @${((column.javaType?trim!"") == "java.lang.String")?then("NotBlank","NotNull")}(message = "${((column.columnValidationComment?trim!"")?length > 0)?then(column.columnValidationComment, column.javaName)}不能为空")
    private ${column.javaTypeClassName} ${column.javaName};

    </#if>
</#list>
    // =============================扩展字段=============================
    @ExcelIgnore
    private Integer rowNum;
}