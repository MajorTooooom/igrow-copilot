package com.dororo.future.igrowcopilot.mapper;
import java.util.Collection;

import org.apache.ibatis.annotations.Param;

import java.util.List;

import com.dororo.future.igrowcopilot.domain.ConnCfg;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ConnCfgMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ConnCfg record);

    int insertSelective(ConnCfg record);

    ConnCfg selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ConnCfg record);

    int updateByPrimaryKey(ConnCfg record);

    int getLastInsertId();

    List<ConnCfg> findByAll(ConnCfg connCfg);

    List<ConnCfg> selectByUserIdAndIdIn(@Param("userId")Integer userId,@Param("idCollection")Collection<Integer> idCollection);

    List<ConnCfg> selectAll();
}