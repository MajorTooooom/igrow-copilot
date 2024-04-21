package com.dororo.future.igrowcopilot.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import com.dororo.future.igrowcopilot.domain.GenSysUser;
import com.dororo.future.igrowcopilot.service.GenSysUserService;
import com.zhien.common.core.domain.R;
import lombok.SneakyThrows;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/genSysUser")
public class GenSysUserController {
    private final GenSysUserService genSysUserService;

    public GenSysUserController(GenSysUserService genSysUserService) {
        this.genSysUserService = genSysUserService;
    }

    @PostMapping("/add")
    public R add(@RequestBody GenSysUser genSysUser) {
        int i = genSysUserService.insert(genSysUser);
        return R.data(i);
    }

    @SneakyThrows
    @PostMapping("/update")
    public R update(@RequestBody GenSysUser genSysUser) {
        Assert.notNull(genSysUser.getId(), "id不能为空");
        genSysUser.setUpdateTime(DateUtil.now());
        if (StrUtil.isNotBlank(genSysUser.getPassword())) {
            genSysUser.setPassword(genSysUserService.fixedLengthEncryption(genSysUser.getPassword()));
        }
        int i = genSysUserService.updateByPrimaryKeySelective(genSysUser);
        return R.ok("更新成功");
    }

    @PostMapping("/delete")
    public R delete(@RequestBody GenSysUser genSysUser) {
        int i = genSysUserService.updateByPrimaryKeySelective(GenSysUser.builder().id(genSysUser.getId()).isDeleted("1").build());
        return R.data(i);
    }

    @PostMapping("/get")
    public R get(@RequestBody GenSysUser genSysUser) {
        GenSysUser row = genSysUserService.selectByPrimaryKey(genSysUser.getId());
        return R.data(row);
    }
}
