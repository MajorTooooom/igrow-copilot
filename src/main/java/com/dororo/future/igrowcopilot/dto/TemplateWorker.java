package com.dororo.future.igrowcopilot.dto;

import com.dororo.future.igrowcopilot.enums.TemplateFromEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TemplateWorker {
    /**
     * 来自网页上传还是系统内置
     *
     * @see TemplateFromEnum
     */
    private Integer from;
    // 文件名
    private String originalFileName;
    // 扩展名
    private String extName;
    // 模板文件内容
    private String templateContent;
    // 错误信息
    private String errorMessage;
    // 文件绝对路径
    private String absolutePath;
    // 渲染结果字符串版本
    private String stringResult;
    // 渲染成功
    private Boolean success;
}
