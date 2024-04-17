package com.dororo.future.igrowcopilot.mapper;

import com.dororo.future.igrowcopilot.domain.GenCfg;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface GenCfgMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(GenCfg record);

    int insertSelective(GenCfg record);

    GenCfg selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(GenCfg record);

    int updateByPrimaryKey(GenCfg record);

    GenCfg findOneByAll(GenCfg genCfg);

    List<GenCfg> findByAll(GenCfg genCfg);

    int getLastInsertId();
}