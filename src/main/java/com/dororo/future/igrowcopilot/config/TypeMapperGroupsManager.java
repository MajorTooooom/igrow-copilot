package com.dororo.future.igrowcopilot.config;

import cn.hutool.cache.CacheUtil;
import cn.hutool.cache.impl.TimedCache;
import cn.hutool.core.io.resource.ResourceUtil;
import cn.hutool.json.JSONUtil;
import com.dororo.future.igrowcopilot.dto.TypeMapperGroupsDTO;
import lombok.extern.slf4j.Slf4j;


/**
 * 对`typeMapperGroups.json`进行缓存管理
 */
@Slf4j
public class TypeMapperGroupsManager {
    private static final String resourceName = "typeMapperGroups.json";
    private static final String KEY_NAME = "group";
    private static final TimedCache<String, TypeMapperGroupsDTO> timedCache = CacheUtil.newTimedCache(60 * 1000);

    static {
        timedCache.schedulePrune(10 * 1000);
    }

    public static TypeMapperGroupsDTO getOrDefault() {
        TypeMapperGroupsDTO typeMapperGroupsDTO = timedCache.get(KEY_NAME);
        if (typeMapperGroupsDTO != null) {
            return typeMapperGroupsDTO;
        } else {
            String utf8Str = ResourceUtil.readUtf8Str(resourceName);
            TypeMapperGroupsDTO groupsDTO = JSONUtil.toBean(utf8Str, TypeMapperGroupsDTO.class);
            timedCache.put(KEY_NAME, groupsDTO);
            return groupsDTO;
        }
    }
}