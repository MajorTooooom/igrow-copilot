package com.dororo.future.igrowcopilot.mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

import com.dororo.future.igrowcopilot.domain.TableCfg;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TableCfgMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TableCfg record);

    int insertSelective(TableCfg record);

    TableCfg selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TableCfg record);

    int updateByPrimaryKey(TableCfg record);

    int getLastInsertId();

    List<TableCfg> findByAll(TableCfg tableCfg);

    List<TableCfg> pageFindByAll(TableCfg tableCfg);
}