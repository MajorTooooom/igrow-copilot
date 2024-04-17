package com.dororo.future.igrowcopilot.service;

import org.springframework.stereotype.Service;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import com.dororo.future.igrowcopilot.domain.GenCfg;
import com.dororo.future.igrowcopilot.mapper.GenCfgMapper;

@Service
public class GenCfgService {

    @Autowired
    private GenCfgMapper genCfgMapper;

    public int deleteByPrimaryKey(Integer id) {
        return genCfgMapper.deleteByPrimaryKey(id);
    }

    public GenCfg insert(GenCfg record) {
        genCfgMapper.insert(record);
        record.setId(genCfgMapper.getLastInsertId());
        return record;
    }

    public GenCfg insertSelective(GenCfg record) {
        genCfgMapper.insertSelective(record);
        record.setId(genCfgMapper.getLastInsertId());
        return record;
    }

    public GenCfg selectByPrimaryKey(Integer id) {
        return genCfgMapper.selectByPrimaryKey(id);
    }

    public int updateByPrimaryKeySelective(GenCfg record) {
        return genCfgMapper.updateByPrimaryKeySelective(record);
    }

    public int updateByPrimaryKey(GenCfg record) {
        return genCfgMapper.updateByPrimaryKey(record);
    }

    public GenCfg findOneByAll(GenCfg genCfg) {
        return genCfgMapper.findOneByAll(genCfg);
    }

    public List<GenCfg> findByAll(GenCfg genCfg) {
        return genCfgMapper.findByAll(genCfg);
    }

}
