package com.dororo.future.igrowcopilot.service;
import java.util.List;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.NumberUtil;
import com.dororo.future.igrowcopilot.domain.ConnCfg;
import com.dororo.future.igrowcopilot.enums.YesNoEnum;
import com.dororo.future.igrowcopilot.mapper.ConnCfgMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ConnCfgService {

    @Autowired
    private ConnCfgMapper connCfgMapper;


    public int deleteByPrimaryKey(Integer id) {
        return connCfgMapper.deleteByPrimaryKey(id);
    }


    public int insert(ConnCfg record) {
        record.setId(null);
        return connCfgMapper.insert(record);
    }


    public int insertSelective(ConnCfg record) {
        record.setId(null);
        return connCfgMapper.insertSelective(record);
    }

    public ConnCfg selectByPrimaryKey(Integer id) {
        return connCfgMapper.selectByPrimaryKey(id);
    }


    public int updateByPrimaryKeySelective(ConnCfg record) {
        return connCfgMapper.updateByPrimaryKeySelective(record);
    }

    public int updateByPrimaryKey(ConnCfg record) {
        return connCfgMapper.updateByPrimaryKey(record);
    }

    public List<ConnCfg> page(ConnCfg connCfg) {
        List<ConnCfg> connCfgs = Optional.ofNullable(connCfgMapper.findByAll(connCfg)).orElse(new ArrayList<>());
        return connCfgs;
    }

    public List<ConnCfg> findByAll(ConnCfg connCfg) {
        return Optional.ofNullable(connCfgMapper.findByAll(connCfg)).orElse(new ArrayList<>());
    }

    @Transactional(rollbackFor = Exception.class)
    public String batchDelete(List<Integer> ids, Integer currentUserId) {
        // 判断是否属于当前用户的数据
        List<ConnCfg> connCfgs = selectByUserIdAndIdIn(currentUserId, ids);
        Map<Boolean, List<ConnCfg>> map = connCfgs.stream().collect(Collectors.partitioningBy(s -> NumberUtil.equals(Convert.toBigDecimal(s.getUserId()), Convert.toBigDecimal(currentUserId))));
        List<ConnCfg> currentConnCfgs = map.get(Boolean.TRUE);
        List<ConnCfg> otherConnCfgs = map.get(Boolean.FALSE);
        if (currentConnCfgs.size() != ids.size()) {
            return "删除失败,部分数据不属于当前用户";
        }
        StringBuffer sb = new StringBuffer();
        if (!currentConnCfgs.isEmpty()) {
            currentConnCfgs.forEach(s -> {
                s.setIsDeleted(YesNoEnum.YES.value + "");
                s.setUpdateTime(DateUtil.now());
                updateByPrimaryKeySelective(s);
                sb.append(s.getId()).append(",");
            });
        }
        return sb.toString();
    }

    public List<ConnCfg> selectByUserIdAndIdIn(Integer userId, Collection<Integer> idCollection) {
        return connCfgMapper.selectByUserIdAndIdIn(userId, idCollection);
    }

	public List<ConnCfg> selectAll(){
		 return connCfgMapper.selectAll();
	}




}
