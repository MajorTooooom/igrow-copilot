package ${domainPackage};

<#if domainGenEasyExcel>
    import com.alibaba.excel.annotation.ExcelProperty;
</#if>
<#if domainGenSwagger>
    import io.swagger.annotations.ApiModel;
    import io.swagger.annotations.ApiModelProperty;
</#if>

<#list fieldTypeList as fieldType>
    <#if fieldType == "java.lang.String">
    <#elseif fieldType == "java.lang.Integer">
    <#else>
        import ${fieldType};
    </#if>
</#list>
import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

<#if domainGenComment>
    /**
    * ${domainZnName!}
    */
</#if>
<#if domainGenSwagger>
    @ApiModel(description = "${domainZnName}-实体类")
</#if>
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "${tableName}")
public class ${domainName} {
<#list columns as column>
    <#if domainGenComment && column.columnComment?has_content>
        /**
        * ${column.columnComment}
        */
    </#if>
    <#if column.columnKey!?has_content && column.columnKey == "PRI">
        @Id
        @Column(name = "${column.columnName}")
        @GeneratedValue(generator = "JDBC")
    <#else>
        @Column(name = "${column.columnName}")
    </#if>
    <#if domainGenSwagger>
        @ApiModelProperty(value = "${((column.columnComment?trim!"")?length > 0)?then(column.columnComment, column.fieldName)}")
    </#if>
<#--    <#if domainGenEasyExcel>@ExcelProperty(value = "${column.columnComment}")</#if>-->
    private ${column.fieldTypeName} ${column.fieldName};

</#list>
}