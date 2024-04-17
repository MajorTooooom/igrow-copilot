package com.dororo.future.igrowcopilot.service;

import java.util.List;

import org.springframework.stereotype.Service;

import org.springframework.beans.factory.annotation.Autowired;

import com.dororo.future.igrowcopilot.mapper.ColumnCfgMapper;
import com.dororo.future.igrowcopilot.domain.ColumnCfg;

@Service
public class ColumnCfgService {

    @Autowired
    private ColumnCfgMapper columnCfgMapper;

    public int deleteByPrimaryKey(Integer id) {
        return columnCfgMapper.deleteByPrimaryKey(id);
    }

    public ColumnCfg insert(ColumnCfg record) {
        columnCfgMapper.insert(record);
        record.setId(columnCfgMapper.getLastInsertId());
        return record;
    }

    public ColumnCfg insertSelective(ColumnCfg record) {
        columnCfgMapper.insertSelective(record);
        record.setId(columnCfgMapper.getLastInsertId());
        return record;
    }

    public ColumnCfg selectByPrimaryKey(Integer id) {
        return columnCfgMapper.selectByPrimaryKey(id);
    }

    public int updateByPrimaryKeySelective(ColumnCfg record) {
        return columnCfgMapper.updateByPrimaryKeySelective(record);
    }

    public int updateByPrimaryKey(ColumnCfg record) {
        return columnCfgMapper.updateByPrimaryKey(record);
    }

    public ColumnCfg findOneByAll(ColumnCfg columnCfg) {
        return columnCfgMapper.findOneByAll(columnCfg);
    }

    public List<ColumnCfg> findByAll(ColumnCfg columnCfg) {
        return columnCfgMapper.findByAll(columnCfg);
    }
}

