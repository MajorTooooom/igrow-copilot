package com.dororo.future.igrowcopilot.controller;

import com.dororo.future.igrowcopilot.domain.GenSysUser;
import com.dororo.future.igrowcopilot.service.GenSysUserService;
import com.zhien.common.core.domain.R;
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

    @PostMapping("/update")
    public R update(@RequestBody GenSysUser genSysUser) {
        int i = genSysUserService.updateByPrimaryKeySelective(genSysUser);
        return R.data(i);
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
