package com.dororo.future.igrowcopilot.service;

import com.dororo.future.igrowcopilot.domain.GenSysUser;
import com.dororo.future.igrowcopilot.mapper.GenSysUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GenSysUserService {

    @Autowired
    private GenSysUserMapper genSysUserMapper;


    public int deleteByPrimaryKey(Integer id) {
        return genSysUserMapper.deleteByPrimaryKey(id);
    }


    public int insert(GenSysUser record) {
        int i = genSysUserMapper.insert(record);
        record.setId(genSysUserMapper.getLastInsertId());
        return i;
    }


    public int insertSelective(GenSysUser record) {
        int i = genSysUserMapper.insertSelective(record);
        record.setId(genSysUserMapper.getLastInsertId());
        return i;
    }


    public GenSysUser selectByPrimaryKey(Integer id) {
        return genSysUserMapper.selectByPrimaryKey(id);
    }


    public int updateByPrimaryKeySelective(GenSysUser record) {
        return genSysUserMapper.updateByPrimaryKeySelective(record);
    }


    public int updateByPrimaryKey(GenSysUser record) {
        return genSysUserMapper.updateByPrimaryKey(record);
    }

}
