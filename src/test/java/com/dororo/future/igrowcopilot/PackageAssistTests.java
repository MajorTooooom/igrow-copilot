package com.dororo.future.igrowcopilot;


import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.lang.Console;
import cn.hutool.system.SystemUtil;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.Date;

public class PackageAssistTests {
    @Test
    public void copy() {
        String currentDir = SystemUtil.getUserInfo().getCurrentDir();
        //
        File dist = FileUtil.file(currentDir, "web-ui", "dist");
        Assert.isTrue(FileUtil.exist(dist) && FileUtil.isDirectory(dist), "前端工程下dist目录不存在, 请先执行npm run build");
        //
        // 查询`index.html`文件的时间是否超过10分钟,如果是则发出警告
        if (DateUtil.between(FileUtil.lastModifiedTime(FileUtil.file(dist, "index.html")), new Date(), DateUnit.MINUTE, false) > 10) {
            for (int i = 0; i < 10; i++) {
                Console.error("前端工程下dist目录下的index.html文件最后修改时间超过10分钟,请决定是否需要重新执行`npm run build`命令");
            }
        }
        //
        File staticDir = FileUtil.file(currentDir, "src", "main", "resources", "static");
        // 删除`static`目录下除了`.gitkeep`的所有文件
        FileUtil.loopFiles(FileUtil.file(staticDir)).stream().forEach(file -> {
            if (!file.getName().equals(".gitkeep")) {
                FileUtil.del(file);
            }
        });
        //
        // 拷贝
        FileUtil.copyContent(FileUtil.file(dist), FileUtil.file(staticDir), true);
        //
        // 拷贝数据库文件
        File dbFile = FileUtil.file(currentDir, "attachments", "igrow-copilot.db");
        File dbDist = FileUtil.file(currentDir, "attachments", ".dockercomposeproduction", "attachments", "igrow-copilot.db");
        FileUtil.copy(dbFile, dbDist, true);
        //
        // 拷贝模板文件
        File templateFrom = FileUtil.file(currentDir, "attachments", ".template");
        File templateTo = FileUtil.file(currentDir, "attachments", ".dockercomposeproduction", "attachments", ".template");
        FileUtil.copyContent(templateFrom, templateTo, true);
    }


}
