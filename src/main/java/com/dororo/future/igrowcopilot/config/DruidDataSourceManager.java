package com.dororo.future.igrowcopilot.config;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import com.alibaba.druid.pool.DruidDataSource;
import com.dororo.future.igrowcopilot.util.IdUtils;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
public class DruidDataSourceManager {
    private static final List<item> DSS_POOL = new ArrayList<>();

    /**
     * 不重复创建相同IP、端口、用户名、密码的数据源
     */
    public static DruidDataSource getOrDefault(String url, String username, String password) {
        DruidDataSource resultDs = null;
        Assert.notBlank(url, "url不能为空");
        Assert.notBlank(username, "username不能为空");
        Assert.notBlank(password, "password不能为空");
        // 将URL中的IP和端口号提取出来
        String[] split = url.split(":");
        String ip = null;
        String port = null;
        try {
            ip = StrUtil.subAfter(split[2], "//", false);
            port = StrUtil.subBefore(split[3], "/", false);
        } catch (Exception e) {
            throw new IllegalArgumentException("无法解析URL中的IP和端口号,请检查URL是否正确");
        }
        String uniqueCombination = StrUtil.format("[{}][{}][{}][{}]", ip, port, username, password);
        String uuid = IdUtils.getFixedLengthId(uniqueCombination);
        synchronized (uuid.intern()) {
            Optional<item> opt = DSS_POOL.stream().filter(e -> e.getUuid().equals(uuid)).findFirst();
            if (opt.isPresent() && opt.get().getDataSource() != null) {
                resultDs = opt.get().getDataSource();
            }
            if (resultDs == null) {
                resultDs = new DruidDataSource();
                resultDs.setUrl(url);
                resultDs.setUsername(username);
                resultDs.setPassword(password);
                DSS_POOL.add(item.builder().uuid(uuid).dataSource(resultDs).build());
            }
            return resultDs;
        }
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Accessors(chain = true)
    public static class item {
        private String uuid;
        private DruidDataSource dataSource;
    }
}