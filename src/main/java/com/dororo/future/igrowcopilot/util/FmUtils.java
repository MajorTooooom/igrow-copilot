package com.dororo.future.igrowcopilot.util;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.dororo.future.igrowcopilot.dto.TemplateEnvDTO;
import freemarker.template.Configuration;
import freemarker.template.Template;
import lombok.SneakyThrows;

import java.io.File;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Map;

/**
 * freeMarker工具类
 */
public class FmUtils {
    @SneakyThrows
    public static String renderToString(String templateName, String templateContent, Map<String, Object> envMap) {
        Configuration cfg = new Configuration(Configuration.VERSION_2_3_29);
        cfg.setSharedVariable("DateUtil", new DateUtil());
        cfg.setSharedVariable("StrUtil", new StrUtil());
        Template template = new Template(templateName, new StringReader(templateContent), cfg);
        try (StringWriter writer = new StringWriter()) {
            template.process(envMap, writer);
            return writer.toString();
        }
    }

    public static Boolean renderToFile(String name, String templateContent, Map<String, Object> stringObjectMap, String targetPath) {
        // TODO
        return null;
    }
}
