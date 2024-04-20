package com.dororo.future.igrowcopilot.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Accessors(chain = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GenCfgAddDTO {
    // private Integer id;
    @NotNull(message = "userId不能为空")
    private Integer userId;
    @NotBlank(message = "渲染配置名称不能为空")
    private String genCfgName;
    @NotBlank(message = "源码绝对路径不能为空")
    private String sourceCodeAbsPath;
    @NotBlank(message = "domain包名不能为空")
    private String domainPackage;
    @NotBlank(message = "dto包名不能为空")
    private String dtoPackage;
    @NotBlank(message = "mapper包名不能为空")
    private String mapperPackage;
    @NotBlank(message = "service包名不能为空")
    private String servicePackage;
    @NotBlank(message = "controller包名不能为空")
    private String controllerPackage;
    @NotBlank(message = "easyExcelListener包名不能为空")
    private String easyExcelListenerPackage;
    @NotBlank(message = "资源目录绝对路径不能为空")
    private String resourceAbsPath;
    @NotBlank(message = "mapperXml路径不能为空")
    private String mapperXmlPath;
    @NotBlank(message = "是否继承tkMapper不能为空")
    private String isExtendTkMapper;
    @NotBlank(message = "tkMapper包名不能为空")
    private String tkMapperPackage;
    @NotBlank(message = "是否生成swagger不能为空")
    private String isGenSwagger;
    @NotBlank(message = "是否生成注释不能为空")
    private String isGenComment;
    @NotBlank(message = "是否生成easyExcel不能为空")
    private String isGenEasyExcel;
    @NotBlank(message = "是否生成javaxValidation不能为空")
    private String isGenJavaxValidation;
    // private String createTime;
    // private String updateTime;
}