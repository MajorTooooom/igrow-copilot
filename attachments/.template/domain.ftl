<#include "环境变量辅助.ftl"/>
package ${domainPackage};

<#if isGenSwagger>
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
import lombok.experimental.Accessors;

<#if isGenComment>
/**
 * `${domainChineseDescription}`实体类
 * @author ${author!}
 * @date ${dateTime!}
 */
</#if>
<#if isGenSwagger>
@ApiModel(description = "`${domainChineseDescription}`实体类")
</#if>
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "${tableName}")
public class ${domainName} {
<#list columns as column>
    <#--字段的备注是否非空-->
    <#if (column.columnComment?trim!"")?length > 0>
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
    <#if isGenSwagger>
    @ApiModelProperty(value = "${((column.columnSwaggerComment?trim!"")?length > 0)?then(column.columnSwaggerComment, column.javaName)}")
    </#if>
    private ${column.javaTypeClassName} ${column.javaName};

    /**
     * 姓名
     */
    @Column(name = "`name`")
    @ApiModelProperty(value = "姓名")
    private String name;

    /**
     * 年龄
     */
    @Column(name = "age")
    @ApiModelProperty(value = "年龄")
    private Integer age;

    /**
     * 余额
     */
    @Column(name = "balance")
    @ApiModelProperty(value = "余额")
    private Long balance;

    /**
     * 生日
     */
    @Column(name = "birthday")
    @ApiModelProperty(value = "生日")
    private Date birthday;
</#list>
}
