package com.dororo.future.igrowcopilot.mapper;

import com.dororo.future.igrowcopilot.domain.ColumnCfg;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ColumnCfgMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ColumnCfg record);

    int insertSelective(ColumnCfg record);

    ColumnCfg selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ColumnCfg record);

    int updateByPrimaryKey(ColumnCfg record);

    ColumnCfg findOneByAll(ColumnCfg columnCfg);

    List<ColumnCfg> findByAll(ColumnCfg columnCfg);

    int getLastInsertId();
}