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
import java.util.Date;

/**
 * 条件分页查询或导出时查询条件DTO
 */
@ApiModel(description = "条件分页查询或导出时查询条件")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AaaaBbbbCcccPageDTO {
    @ApiModelProperty(value = "主键ID")
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