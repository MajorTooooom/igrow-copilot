package com.dororo.future.igrowcopilot.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.dororo.future.igrowcopilot.controller.common.CopilotBaseController;
import com.dororo.future.igrowcopilot.domain.ConnCfg;
import com.dororo.future.igrowcopilot.enums.YesNoEnum;
import com.dororo.future.igrowcopilot.service.ConnCfgService;
import com.zhien.common.core.domain.R;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/connCfg")
public class ConnCfgController extends CopilotBaseController {
    @Resource
    private ConnCfgService connCfgService;

    @PostMapping("/add")
    public R add(@RequestBody @Validated(ConnCfg.Add.class) ConnCfg connCfg) {
        Assert.isTrue(StrUtil.startWith(connCfg.getUrl(), "jdbc:mysql://"), "当前仅支持MySQL数据库");
        connCfg = appointUserIdByHeader(connCfg);
        connCfg.setIsDeleted(YesNoEnum.NO.value + "");
        connCfg.setCreateTime(DateUtil.now());
        connCfg.setUpdateTime(DateUtil.now());
        connCfgService.insertSelective(connCfg);
        return R.ok("添加成功");
    }

    @PostMapping("/delete")
    public R batchDelete(@RequestBody List<Integer> ids) {
        return R.ok(connCfgService.batchDelete(ids, getCurrentUserIdWithException()));
    }

    @PostMapping("/update")
    public R update(@RequestBody @Validated(ConnCfg.Update.class) ConnCfg connCfg) {
        connCfg = appointUserIdByHeader(connCfg);
        connCfg.setUpdateTime(DateUtil.now());
        connCfgService.updateByPrimaryKeySelective(connCfg);
        return R.ok("修改成功");
    }

    @PostMapping("/page")
    public R page(@RequestBody ConnCfg connCfg) {
        connCfg = appointUserIdByHeader(connCfg);
        connCfg.setIsDeleted(YesNoEnum.NO.value + "");
        startPage();
        List<ConnCfg> list = connCfgService.page(connCfg);
        return R.page(list);
    }


    @PostMapping("/connCfgOptions")
    public R elSelectOptions() {
        List<ConnCfg> connCfgs = connCfgService.findByAll(ConnCfg.builder().userId(getCurrentUserIdWithException()).build());
        List<JSONObject> options = connCfgs.stream().map(cfg -> {
            JSONObject option = JSONUtil.createObj()
                    .putOpt("value", cfg.getId())
                    .putOpt("label", StrUtil.format("（{}）{}", StrUtil.fillBefore(cfg.getId() + "", '0', 3), cfg.getCfgName()))
                    .putOpt("data", cfg);
            return option;
        }).collect(Collectors.toList());
        return R.data(options);
    }
}
