package com.dororo.future.igrowcopilot.mvn;


import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.lang.Console;
import cn.hutool.core.util.StrUtil;
import cn.hutool.system.SystemUtil;

import java.io.File;
import java.util.Date;

public class PackageBeforeTests {
    public static void main(String[] args) {
        enterLog();
        // 当前项目的根目录
        String currentDir = SystemUtil.getUserInfo().getCurrentDir();
        // 前端工程的dist目录
        File dist = FileUtil.file(currentDir, "web-ui", "dist");
        Assert.isTrue(FileUtil.exist(dist) && FileUtil.isDirectory(dist), "前端工程下dist目录不存在, 请先执行npm run build");
        // 查询`index.html`文件的时间是否超过10分钟,如果是则发出警告
        if (DateUtil.between(FileUtil.lastModifiedTime(FileUtil.file(dist, "index.html")), new Date(), DateUnit.MINUTE, false) > 10) {
            for (int i = 0; i < 10; i++) {
                System.err.println("前端工程下dist目录下的index.html文件最后修改时间超过10分钟,请决定是否需要重新执行`npm run build`命令");
            }
            System.out.flush(); // 强制刷新缓冲
        }
        // 静态资源目录
        File staticDir = FileUtil.file(currentDir, "src", "main", "resources", "static");
        // 删除`static`目录下除了`.gitkeep`的所有旧文件
        FileUtil.loopFiles(FileUtil.file(staticDir)).stream().forEach(file -> {
            if (!file.getName().equals(".gitkeep")) {
                FileUtil.del(file);
            }
        });
        // 目录拷贝
        FileUtil.copyContent(FileUtil.file(dist), FileUtil.file(staticDir), true);
        quitLog();
    }

    private static void enterLog() {
        System.out.println("====================================================================================================");
        System.out.println("开始执行打包前的准备工作");
        System.out.flush(); // 强制刷新缓冲
    }

    private static void quitLog() {
        System.out.println("打包前的准备工作执行完毕");
        System.out.println("====================================================================================================");
        System.out.flush(); // 强制刷新缓冲
    }
}
