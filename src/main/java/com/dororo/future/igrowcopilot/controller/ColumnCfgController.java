package com.dororo.future.igrowcopilot.controller;

import cn.hutool.core.lang.Assert;
import com.dororo.future.igrowcopilot.domain.ColumnCfg;
import com.dororo.future.igrowcopilot.domain.TableCfg;
import com.dororo.future.igrowcopilot.service.ColumnCfgService;
import com.zhien.common.core.domain.R;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/columnCfg")
public class ColumnCfgController {
    @Resource
    private ColumnCfgService columnCfgService;

    @PostMapping("/getByTableCfg")
    public R getByTableCfg(@RequestBody TableCfg tableCfg) {
        Integer id = tableCfg.getId();
        Assert.notNull(id, "id不能为空");
        Integer userId = tableCfg.getUserId();
        Assert.notNull(userId, "userId不能为空");
        List<ColumnCfg> matchList = columnCfgService.findByAll(ColumnCfg.builder().tableCfgId(id).userId(userId).build());
        return R.data(matchList);
    }
}
