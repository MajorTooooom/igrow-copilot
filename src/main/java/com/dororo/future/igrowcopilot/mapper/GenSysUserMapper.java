package com.dororo.future.igrowcopilot.mapper;

import com.dororo.future.igrowcopilot.domain.GenSysUser;

public interface GenSysUserMapper {
    int getLastInsertId();

    int deleteByPrimaryKey(Integer id);

    int insert(GenSysUser record);

    int insertSelective(GenSysUser record);

    GenSysUser selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(GenSysUser record);

    int updateByPrimaryKey(GenSysUser record);
}