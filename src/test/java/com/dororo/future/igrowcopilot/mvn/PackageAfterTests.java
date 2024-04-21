package com.dororo.future.igrowcopilot.mvn;


import cn.hutool.core.io.FileUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.lang.Console;
import cn.hutool.core.util.StrUtil;
import cn.hutool.system.SystemUtil;

import java.io.File;

public class PackageAfterTests {
    /**
     * 打包后的工作,拷贝文件到docker-compose工作目录
     */
    public static void main(String[] args) {
        Console.error(StrUtil.fill("", '=', 100, true));
        Console.log("开始执行打包后的工作");
        Console.error(StrUtil.fill("", '=', 100, true));
        // 当前项目的根目录
        String currentDir = SystemUtil.getUserInfo().getCurrentDir();
        // 拷贝数据库文件
        File dbFile = FileUtil.file(currentDir, "attachments", "igrow-copilot.db");
        Assert.isTrue(FileUtil.exist(dbFile) && FileUtil.isFile(dbFile), "数据库文件不存在");
        File dbDist = FileUtil.file(currentDir, "attachments", "..docker", "attachments", "igrow-copilot.db");
        FileUtil.copy(dbFile, dbDist, true);
        //
        // 拷贝模板文件
        File templateFrom = FileUtil.file(currentDir, "attachments", ".template");
        File templateTo = FileUtil.file(currentDir, "attachments", "..docker", "attachments", ".template");
        FileUtil.copyContent(templateFrom, templateTo, true);
    }
}
