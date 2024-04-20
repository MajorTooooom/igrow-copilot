package ${domainPackage};

<#if isGenSwagger>
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
</#if>

import java.util.Date;
import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 学员培训审核表
 */
@ApiModel(description = "学员培训审核表")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "aaaa_bbbb_cccc")
public class AaaaBbbbCccc {
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
}