package com.dororo.future.igrowcopilot.config;

import cn.hutool.cache.CacheUtil;
import cn.hutool.cache.impl.TimedCache;
import cn.hutool.core.io.resource.ResourceUtil;
import cn.hutool.json.JSONUtil;
import com.dororo.future.igrowcopilot.dto.MapperJavaTypeGroups;
import lombok.extern.slf4j.Slf4j;


/**
 * 对`mapperJavaTypeGroups.json`进行缓存管理
 */
@Slf4j
public class MapperJavaTypeGroupsManager {
    private static final String resourceName = "mapperJavaTypeGroups.json";
    private static final String KEY_NAME = "group";
    private static final TimedCache<String, MapperJavaTypeGroups> timedCache = CacheUtil.newTimedCache(60 * 1000);

    static {
        timedCache.schedulePrune(10 * 1000);
    }

    public static MapperJavaTypeGroups getOrDefault() {
        MapperJavaTypeGroups mapperJavaTypeGroups = timedCache.get(KEY_NAME);
        if (mapperJavaTypeGroups != null) {
            return mapperJavaTypeGroups;
        } else {
            String utf8Str = ResourceUtil.readUtf8Str(resourceName);
            MapperJavaTypeGroups groupsDTO = JSONUtil.toBean(utf8Str, MapperJavaTypeGroups.class);
            timedCache.put(KEY_NAME, groupsDTO);
            return groupsDTO;
        }
    }
}