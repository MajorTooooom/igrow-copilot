package com.dororo.future.igrowcopilot;

import cn.hutool.core.io.FileUtil;
import cn.hutool.system.SystemUtil;
import org.junit.Test;

import java.io.File;

public class CopyJarTests {
    @Test
    public void copy() {
        String currentDir = SystemUtil.getUserInfo().getCurrentDir();
        File from = FileUtil.file(currentDir, "target", "igrow-copilot.jar");
        File to = FileUtil.file(currentDir, "attachments", "..docker", "build_image_springboot", "igrow-copilot.jar");
        FileUtil.copy(from, to, true);
    }
}
