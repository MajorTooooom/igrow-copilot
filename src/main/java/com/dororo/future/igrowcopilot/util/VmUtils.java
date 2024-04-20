package com.dororo.future.igrowcopilot.util;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;

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

    public static Boolean renderToFile(String parent, String mainName, Map<String, Object> stringObjectMap, String targetPath) {
        // TODO
        throw new UnsupportedOperationException("Velocity Not implemented yet");
    }
}
