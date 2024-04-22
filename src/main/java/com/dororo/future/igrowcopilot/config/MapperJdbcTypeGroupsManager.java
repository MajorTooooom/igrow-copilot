package com.dororo.future.igrowcopilot.config;

import cn.hutool.cache.CacheUtil;
import cn.hutool.cache.impl.TimedCache;
import cn.hutool.core.io.resource.ResourceUtil;
import cn.hutool.json.JSONUtil;
import com.dororo.future.igrowcopilot.dto.MapperJdbcTypeGroups;

/**
 * 对`mapperJdbcTypeGroups.json`进行缓存管理
 */
public class MapperJdbcTypeGroupsManager {
    private static final String resourceName = "mapperJdbcTypeGroups.json";
    private static final String KEY_NAME = "group";
    private static final TimedCache<String, MapperJdbcTypeGroups> timedCache = CacheUtil.newTimedCache(60 * 1000);

    static {
        timedCache.schedulePrune(10 * 1000);
    }

    public static MapperJdbcTypeGroups getOrDefault() {
        MapperJdbcTypeGroups mapperJdbcTypeGroups = timedCache.get(KEY_NAME);
        if (mapperJdbcTypeGroups != null) {
            return mapperJdbcTypeGroups;
        } else {
            String utf8Str = ResourceUtil.readUtf8Str(resourceName);
            MapperJdbcTypeGroups groupsDTO = JSONUtil.toBean(utf8Str, MapperJdbcTypeGroups.class);
            timedCache.put(KEY_NAME, groupsDTO);
            return groupsDTO;
        }
    }
}
