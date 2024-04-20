package com.dororo.future.igrowcopilot.controller;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.symmetric.AES;
import cn.hutool.json.JSONObject;
import com.dororo.future.igrowcopilot.domain.GenSysUser;
import com.dororo.future.igrowcopilot.enums.YesNoEnum;
import com.dororo.future.igrowcopilot.service.GenSysUserService;
import com.zhien.common.core.domain.R;
import lombok.SneakyThrows;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.util.List;

@RestController
@RequestMapping("/login")
public class LoginController {
    private static final byte[] secret = "aaa1234567890bbb".getBytes();
    @Resource
    private GenSysUserService genSysUserService;

    /**
     * 定长加密
     * 将用户输入的任意长度的密码转换为固定长度的加密版本
     */
    private String fixedLengthEncryption(String userPassword) throws Exception {
        if (StrUtil.isBlank(userPassword)) {
            return null;
        }
        // 1.0 秘钥派生函数,将用户输入的任意长度的密码转换为固定长度的秘钥
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        PBEKeySpec spec = new PBEKeySpec(userPassword.toCharArray(), secret, 65536, 256);// 65536是迭代次数,256是期望生成的密钥长度
        SecretKey secretKey = factory.generateSecret(spec);
        // 2.0 生成AES加密算法
        byte[] secretKeyBytes = secretKey.getEncoded();
        AES aes = new AES("CBC", "PKCS7Padding", secretKeyBytes, secret);
        return aes.encryptHex(userPassword);
    }

    @SneakyThrows
    @PostMapping("/submit")
    public R login(@RequestBody JSONObject jsonObject) {
        String username = jsonObject.getStr("username");
        Assert.notBlank(username, "用户名不能为空");
        String password = jsonObject.getStr("password");
        Assert.notBlank(password, "密码不能为空");
        //
        List<GenSysUser> matchList = genSysUserService.findByAll(GenSysUser.builder().username(username).isDeleted(YesNoEnum.NO.value + "").build());
        if (CollectionUtil.isEmpty(matchList)) {
            return R.error("用户不存在");
        } else {
            GenSysUser genSysUser = matchList.get(0);
            String passDb = genSysUser.getPassword();
            String passNow = fixedLengthEncryption(password);
            if (!StrUtil.equals(passDb, passNow)) {
                return R.error("密码错误");
            }
        }
        return R.data(matchList.get(0).getId());
    }

    @SneakyThrows
    @PostMapping("/signUp")
    public R signUp(@RequestBody JSONObject jsonObject) {
        String username = jsonObject.getStr("username");
        Assert.notBlank(username, "用户名不能为空");
        String password = jsonObject.getStr("password");
        Assert.notBlank(password, "密码不能为空");
        //
        List<GenSysUser> matchList = genSysUserService.findByAll(GenSysUser.builder().username(username).isDeleted(YesNoEnum.NO.value + "").build());
        if (CollectionUtil.isNotEmpty(matchList)) {
            return R.error("用户已存在");
        } else {
            GenSysUser genSysUser = GenSysUser.builder().username(username).password(fixedLengthEncryption(password)).isDeleted(YesNoEnum.NO.value + "").createTime(DateUtil.now()).updateTime(DateUtil.now()).build();
            genSysUserService.insert(genSysUser);
        }
        return R.ok();
    }
}
