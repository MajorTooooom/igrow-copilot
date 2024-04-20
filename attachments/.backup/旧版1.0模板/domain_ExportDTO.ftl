package ${dtoPackage};

<#if domainGenEasyExcel>
    import com.alibaba.excel.annotation.ExcelIgnore;
    import com.alibaba.excel.annotation.ExcelProperty;
</#if>
<#--<#if domainGenEasyExcel>import com.alibaba.excel.annotation.ExcelProperty;</#if>-->
<#if domainGenSwagger>
    import io.swagger.annotations.ApiModel;
    import io.swagger.annotations.ApiModelProperty;
</#if>
<#if domainGenJavaxValidation>
    import javax.validation.constraints.NotBlank;
    import javax.validation.constraints.NotNull;
</#if>
<#list fieldTypeList as fieldType>
    <#if fieldType == "java.lang.String">
    <#elseif fieldType == "java.lang.Integer">
    <#else>
        import ${fieldType};
    </#if>
</#list>
<#--import javax.persistence.*;-->

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

<#if domainGenComment>
    /**
    * ${domainZnName}ExportDTO
    */
</#if>
<#if domainGenSwagger>
    @ApiModel(description = "${domainZnName}ExportDTO")
</#if>
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ${domainName}ExportDTO {
<#list columns as column>
    <#if domainGenComment && column.columnComment?has_content>
        /**
        * ${column.columnComment}
        */
    </#if>
    <#if domainGenSwagger>
        @ApiModelProperty(value = "${((column.columnComment?trim!"")?length > 0)?then(column.columnComment, column.fieldName)}")
    </#if>
    <#if domainGenJavaxValidation>
        <#if column.fieldType == "java.lang.String">
            @NotBlank(message = "${((column.columnComment?trim!"")?length > 0)?then(column.columnComment, column.fieldName)}不能为空")
        <#else>
            @NotNull(message = "${((column.columnComment?trim!"")?length > 0)?then(column.columnComment, column.fieldName)}不能为空")
        </#if>
    </#if>
    <#if domainGenEasyExcel>
        @ExcelProperty(value = "${((column.columnComment?trim!"")?length > 0)?then(column.columnComment, column.fieldName)}")
    </#if>
    private ${column.fieldTypeName} ${column.fieldName};
<#--需要间隔行-->

</#list>
}