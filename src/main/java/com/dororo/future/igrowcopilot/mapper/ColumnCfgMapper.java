package com.dororo.future.igrowcopilot.mapper;

import java.util.List;

import com.dororo.future.igrowcopilot.domain.ColumnCfg;
import org.apache.ibatis.annotations.Mapper;

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