package com.dororo.future.igrowcopilot.service;

import java.util.List;

import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.symmetric.AES;
import com.dororo.future.igrowcopilot.constant.CommonConstants;
import com.dororo.future.igrowcopilot.domain.GenSysUser;
import com.dororo.future.igrowcopilot.mapper.GenSysUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

@Service
public class GenSysUserService {
    @Autowired
    private GenSysUserMapper genSysUserMapper;


    public int deleteByPrimaryKey(Integer id) {
        return genSysUserMapper.deleteByPrimaryKey(id);
    }


    public int insert(GenSysUser record) {
        int i = genSysUserMapper.insert(record);
        record.setId(genSysUserMapper.getLastInsertId());
        return i;
    }


    public int insertSelective(GenSysUser record) {
        int i = genSysUserMapper.insertSelective(record);
        record.setId(genSysUserMapper.getLastInsertId());
        return i;
    }


    public GenSysUser selectByPrimaryKey(Integer id) {
        return genSysUserMapper.selectByPrimaryKey(id);
    }


    public int updateByPrimaryKeySelective(GenSysUser record) {
        return genSysUserMapper.updateByPrimaryKeySelective(record);
    }


    public int updateByPrimaryKey(GenSysUser record) {
        return genSysUserMapper.updateByPrimaryKey(record);
    }

    public List<GenSysUser> findByAll(GenSysUser genSysUser) {
        return genSysUserMapper.findByAll(genSysUser);
    }

    /**
     * 定长加密
     * 将用户输入的任意长度的密码转换为固定长度的加密版本
     */
    public String fixedLengthEncryption(String userPassword) throws Exception {
        if (StrUtil.isBlank(userPassword)) {
            return null;
        }
        // 1.0 秘钥派生函数,将用户输入的任意长度的密码转换为固定长度的秘钥
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        PBEKeySpec spec = new PBEKeySpec(userPassword.toCharArray(), CommonConstants.secret, 65536, 256);// 65536是迭代次数,256是期望生成的密钥长度
        SecretKey secretKey = factory.generateSecret(spec);
        // 2.0 生成AES加密算法
        byte[] secretKeyBytes = secretKey.getEncoded();
        AES aes = new AES("CBC", "PKCS7Padding", secretKeyBytes, CommonConstants.secret);
        return aes.encryptHex(userPassword);
    }
}
