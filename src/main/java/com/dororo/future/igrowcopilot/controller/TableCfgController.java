package com.dororo.future.igrowcopilot.controller;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import com.dororo.future.igrowcopilot.controller.common.CopilotBaseController;
import com.dororo.future.igrowcopilot.domain.ConnCfg;
import com.dororo.future.igrowcopilot.domain.TableCfg;
import com.dororo.future.igrowcopilot.dto.DbColumnSelectRequestDTO;
import com.dororo.future.igrowcopilot.dto.TableCfgAddDTO;
import com.dororo.future.igrowcopilot.dto.TableCfgUpdateDTO;
import com.dororo.future.igrowcopilot.service.TableCfgService;
import com.dororo.future.igrowcopilot.util.IdUtils;
import com.zhien.common.core.domain.R;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/tableCfg")
public class TableCfgController extends CopilotBaseController {
    @Resource
    private TableCfgService tableCfgService;

    @PostMapping("/add")
    public R addTableCfg(@RequestBody @Validated TableCfgAddDTO tableCfgAddDTO) {
        tableCfgService.addTableCfg(tableCfgAddDTO);
        return R.ok();
    }

    @PostMapping("/delete")
    public R deleteTableCfg(@RequestBody List<Integer> ids) {
        Assert.notEmpty(ids, "ids不能为空");
        tableCfgService.batchDelete(ids);
        return R.ok();
    }

    @PostMapping("/update")
    public R updateTableCfg(@RequestBody @Validated TableCfgUpdateDTO tableCfgUpdateDTO) {
        tableCfgService.updateTableCfg(tableCfgUpdateDTO);
        return R.ok();
    }

    @PostMapping("/tableCfgOptions")
    public R elSelectOptions(@RequestBody @Validated(ConnCfg.TableOptions.class) ConnCfg connCfg, @RequestParam(name = "forced", required = false) Boolean forced) {
        String sameRoot = StrUtil.format("[{}][{}][{}]", connCfg.getUrl(), connCfg.getUserName(), connCfg.getPassword());
        String cacheKey = IdUtils.getFixedLengthId(sameRoot);
        if (forced != null && forced) {
            return tableCfgService.tableCfgOptionsCachePut(connCfg, cacheKey);
        } else {
            return tableCfgService.tableCfgOptionsCacheable(connCfg, cacheKey);
        }
    }

    /**
     * 从数据库查询表和字段信息
     */
    @PostMapping("/tableAndColumnFromDs")
    public R getTableAndColumnFromDs(@RequestBody @Validated DbColumnSelectRequestDTO bodyDTO) {
        return R.data(tableCfgService.getTableAndColumnFromDs(bodyDTO));
    }

    @PostMapping("/tableCfgTablePage")
    public R tableCfgPage(@RequestBody TableCfg tableCfg) {
        tableCfg.setUserId(getCurrentUserIdWithException());
        startPage();
        List<TableCfg> list = tableCfgService.findByAll(tableCfg);
        return R.page(list);
    }
}