package com.dororo.future.igrowcopilot.util;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateModelException;
import lombok.SneakyThrows;

import java.io.FileWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Map;

/**
 * freeMarker工具类
 */
public class FmUtils {
    private static Configuration getCommonFmCfg() throws TemplateModelException {
        Configuration cfg = new Configuration(Configuration.VERSION_2_3_29);
        cfg.setSharedVariable("DateUtil", new DateUtil());
        cfg.setSharedVariable("StrUtil", new StrUtil());
        return cfg;
    }

    @SneakyThrows
    public static String renderToString(String parent, String templateName, String templateContent, Map<String, Object> envMap) {
        Configuration cfg = getCommonFmCfg();
        cfg.setDirectoryForTemplateLoading(FileUtil.file(parent));
        Template template = new Template(templateName, new StringReader(templateContent), cfg);
        try (StringWriter writer = new StringWriter()) {
            template.process(envMap, writer);
            return writer.toString();
        }
    }

    @SneakyThrows
    public static Boolean renderToFile(String parent, String name, Map<String, Object> envMap, String targetPath) {
        Configuration cfg = getCommonFmCfg();
        cfg.setDirectoryForTemplateLoading(FileUtil.file(parent));
        Template template = cfg.getTemplate(name);

        FileUtil.mkdir(FileUtil.getParent(targetPath, 1));

        FileWriter writer = new FileWriter(targetPath);
        template.process(envMap, writer);
        writer.close();
        return true;
    }
}
