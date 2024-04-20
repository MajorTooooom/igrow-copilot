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
 * `${domainZnName!tableName}`实体类
 */
</#if>
<#if isGenSwagger>
@ApiModel(description = "`${domainZnName!tableName}`实体类")
</#if>
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "${tableName}")
public class ${domainName} {
<#list columns as column>
    /**
     * 主键ID
     */
    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "JDBC")
    @ApiModelProperty(value = "主键ID")
    private Integer id;

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
