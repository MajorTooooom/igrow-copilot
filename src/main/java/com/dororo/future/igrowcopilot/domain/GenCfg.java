package com.dororo.future.igrowcopilot.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GenCfg {
    private Integer id;

    private Integer userId;

    private String genCfgName;

    private String sourceCodeAbsPath;

    private String domainPackage;

    private String dtoPackage;

    private String mapperPackage;

    private String servicePackage;

    private String controllerPackage;

    private String easyExcelListenerPackage;

    private String resourceAbsPath;

    private String mapperXmlPath;

    private String isExtendTkMapper;

    private String tkMapperPackage;

    private String isGenSwagger;

    private String isGenComment;

    private String isGenEasyExcel;

    private String isGenJavaxValidation;

    private String createTime;

    private String updateTime;
}