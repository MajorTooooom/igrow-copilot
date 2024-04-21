package com.dororo.future.igrowcopilot.controller.common;

import cn.hutool.core.lang.Assert;
import com.dororo.future.igrowcopilot.domain.GenCfg;
import com.dororo.future.igrowcopilot.dto.GenCfgAddDTO;
import com.dororo.future.igrowcopilot.service.GenCfgService;
import com.zhien.common.core.domain.R;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/genCfg")
public class GenCfgController extends CopilotBaseController {
    @Resource
    private GenCfgService genCfgService;

    @PostMapping("/add")
    public R add(@RequestBody @Validated GenCfgAddDTO genCfgAddDTO) {
        genCfgService.add(genCfgAddDTO);
        return R.ok();
    }

    @PostMapping("/delete")
    public R delete(@RequestBody List<Integer> ids) {
        Assert.notEmpty(ids, "ids不能为空");
        genCfgService.deleteByIdIn(ids);
        return R.ok();
    }

    @PostMapping("/update")
    public R update(@RequestBody @Validated GenCfg genCfg) {
        return R.ok();
    }

    @PostMapping("/page")
    public R page(@RequestBody GenCfg genCfg) {
        genCfg.setUserId(getCurrentUserIdWithException());
        startPage();
        List<GenCfg> list = genCfgService.findByAll(genCfg);
        return R.page(list);
    }
}
