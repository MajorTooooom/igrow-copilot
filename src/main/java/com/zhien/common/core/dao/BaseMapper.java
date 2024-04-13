package com.zhien.common.core.dao;

import tk.mybatis.mapper.common.ConditionMapper;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.special.InsertListMapper;

public interface BaseMapper<T> extends tk.mybatis.mapper.common.BaseMapper<T>, Mapper<T>, InsertListMapper<T>, ConditionMapper<T> {
}