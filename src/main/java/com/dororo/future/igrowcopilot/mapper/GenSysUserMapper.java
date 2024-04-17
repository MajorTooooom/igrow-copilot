package com.dororo.future.igrowcopilot.mapper;

import com.dororo.future.igrowcopilot.domain.GenSysUser;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface GenSysUserMapper {
    int getLastInsertId();

    int deleteByPrimaryKey(Integer id);

    int insert(GenSysUser record);

    int insertSelective(GenSysUser record);

    GenSysUser selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(GenSysUser record);

    int updateByPrimaryKey(GenSysUser record);

    List<GenSysUser> findByAll(GenSysUser genSysUser);


}