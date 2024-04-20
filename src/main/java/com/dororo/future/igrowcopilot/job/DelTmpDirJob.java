package com.dororo.future.igrowcopilot.job;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.system.SystemUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
public class DelTmpDirJob {

    @Scheduled(cron = "0 0/1 * * * ?")
    // @Scheduled(cron = "1/3 * * * * ?")// test
    public void delTmpDir() {
        File tmpTop = FileUtil.file(SystemUtil.getUserInfo().getCurrentDir(), "attachments", ".tmp");
        List<File> ls = Arrays.stream(FileUtil.ls(FileUtil.getAbsolutePath(tmpTop))).filter(s -> FileUtil.isDirectory(s)).collect(Collectors.toList());
        if (ls.size() == 0) {
            return;
        }
        for (File dir : ls) {
            File file = FileUtil.file(dir, "createTime.txt");
            if (FileUtil.exist(file)) {
                String readUtf8String = FileUtil.readUtf8String(file);
                DateTime parse = DateUtil.parse(readUtf8String);
                if (DateUtil.between(parse, DateUtil.date(), DateUnit.MINUTE, false) > 5) {
                    FileUtil.del(dir);
                    log.info("删除过期5分钟临时目录: {}", dir.getAbsolutePath());
                }
            }
        }
    }
}
