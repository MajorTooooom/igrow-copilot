package com.zhien.igrow.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * 新增学员培训审核DTO
 */
@ApiModel(description = "新增学员培训审核")
@Data
@Accessors(chain = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AaaaBbbbCcccAddDTO {
    // @ApiModelProperty(value = "主键ID")
    // private Integer id;

    @ApiModelProperty(value = "姓名", required = true)
    @NotBlank(message = "姓名不能为空")
    private String name;

    @ApiModelProperty(value = "年龄", required = true)
    @NotNull(message = "年龄不能为空")
    private Integer age;

    @ApiModelProperty(value = "余额", required = true)
    @NotNull(message = "余额不能为空")
    private Long balance;

    @ApiModelProperty(value = "生日", required = true)
    @NotNull(message = "生日不能为空")
    private Date birthday;
}