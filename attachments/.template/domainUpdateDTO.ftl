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
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * 更新学员培训审核DTO
 */
@ApiModel(description = "更新学员培训审核")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AaaaBbbbCcccUpdateDTO {
    @ApiModelProperty(value = "主键ID", required = true)
    @NotNull(message = "主键ID不能为空")
    private Integer id;

    @ApiModelProperty(value = "姓名")
    private String name;

    @ApiModelProperty(value = "年龄")
    private Integer age;

    @ApiModelProperty(value = "余额")
    private Long balance;

    @ApiModelProperty(value = "生日")
    private Date birthday;
}