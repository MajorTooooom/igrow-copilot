package com.dororo.future.igrowcopilot.dto;

import com.dororo.future.igrowcopilot.domain.ColumnCfg;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TemplateEnvDTO {
    // from tableCfg
    private String tableName;
    private String domainName;
    private String domainZnName;
    // from genCfg
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
    // from ColumnCfg
    List<ColumnCfg> columns;
    // 扩展
    private String author;
    private String dateTime;
    private List<String> javaTypes;
    //
    private String mchpFormatDate;
}
