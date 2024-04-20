package com.dororo.future.igrowcopilot.util;

import cn.hutool.core.io.FileUtil;
import lombok.SneakyThrows;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;

import java.io.FileWriter;
import java.io.StringWriter;
import java.util.Map;

/**
 * velocity工具类
 */
public class VmUtils {
    public static String renderToString(String parent, String templateName, String templateContent, Map<String, Object> envMap) {
        VelocityEngine velocityEngine = new VelocityEngine();
        velocityEngine.setProperty(RuntimeConstants.RESOURCE_LOADERS, "string");
        velocityEngine.addProperty("resource.loader.string.class", "org.apache.velocity.runtime.resource.loader.StringResourceLoader");
        // TODO 待考究
        velocityEngine.setProperty(Velocity.FILE_RESOURCE_LOADER_PATH, parent);
        velocityEngine.init();
        //     
        VelocityContext context = new VelocityContext(envMap);
        StringWriter writer = new StringWriter();
        velocityEngine.evaluate(context, writer, templateName, templateContent);
        return writer.toString();
    }

    @SneakyThrows
    public static Boolean renderToFile(String parent, String templateName, Map<String, Object> envMap, String outputFilePath) {
        FileUtil.mkdir(FileUtil.getParent(outputFilePath, 1));
        //
        VelocityEngine velocityEngine = new VelocityEngine();
        velocityEngine.setProperty(Velocity.FILE_RESOURCE_LOADER_PATH, parent);
        velocityEngine.setProperty(Velocity.INPUT_ENCODING, "UTF-8");
        velocityEngine.init();
        // 加载模板
        Template template = velocityEngine.getTemplate(templateName);
        // 使用数据模型创建 Velocity 上下文
        VelocityContext context = new VelocityContext(envMap);

        // 使用 FileWriter 将渲染结果写入文件
        try (FileWriter writer = new FileWriter(outputFilePath)) {
            template.merge(context, writer);
        }
        return true;
    }
}
