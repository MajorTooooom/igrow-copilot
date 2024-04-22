package com.dororo.future.igrowcopilot.util;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.lang.Console;
import cn.hutool.core.util.StrUtil;
import cn.hutool.system.SystemUtil;
import lombok.SneakyThrows;
import net.lingala.zip4j.ZipFile;

import java.io.File;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class SqliteHelper {

    @SneakyThrows
    public static void init() {
        String currentDir = SystemUtil.getUserInfo().getCurrentDir();
        File db = FileUtil.file(currentDir, "attachments", "igrow-copilot.db");
        if (FileUtil.exist(db) && FileUtil.isFile(db)) {
            Console.log("igrow-copilot.db exists");
            return;
        }
        String dbRepository = FileUtil.getAbsolutePath(FileUtil.file(currentDir, "attachments"));
        File[] ls = FileUtil.ls(dbRepository);
        List<File> zips = Arrays.stream(ls).filter(f -> {
            String extName = FileUtil.extName(f);
            return StrUtil.isNotBlank(extName) && StrUtil.equalsIgnoreCase(extName, "zip");
        }).collect(Collectors.toList());

        if (zips.size() <= 0) {
            Console.log("No zip file found");
            return;
        }

        File file = zips.stream().collect(Collectors.maxBy(Comparator.comparing(s -> {
            String timeStr = StrUtil.subAfter(s.getName(), "igrowcopilot_", false);
            DateTime parse = DateUtil.parse(timeStr, "yyyyMMdd_HHmmss");
            return parse;
        }))).get();

        new ZipFile(FileUtil.getAbsolutePath(file)).extractFile("igrow-copilot.db", dbRepository);
        Console.log("Extracted igrow-copilot.db from {}", file.getName());
    }
}
