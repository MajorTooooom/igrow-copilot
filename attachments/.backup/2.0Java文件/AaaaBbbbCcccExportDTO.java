package com.zhien.igrow.dto;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

/**
 * 条件分页查询或导出rowDTO
 */
@ApiModel(description = "学员培训审核查询或导出rowDTO")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AaaaBbbbCcccExportDTO {
    @ApiModelProperty(value = "主键ID")
    @ExcelProperty(value = "主键ID")
    private Integer id;

    @ApiModelProperty(value = "姓名")
    @ExcelProperty(value = "姓名")
    private String name;

    @ApiModelProperty(value = "年龄")
    @ExcelProperty(value = "年龄")
    private Integer age;

    @ApiModelProperty(value = "余额")
    @ExcelProperty(value = "余额")
    private Long balance;

    @ApiModelProperty(value = "生日")
    @ExcelProperty(value = "生日")
    private Date birthday;
}