package com.dororo.future.igrowcopilot.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import cn.hutool.system.SystemUtil;
import com.dororo.future.igrowcopilot.constant.CacheConstants;
import com.dororo.future.igrowcopilot.domain.ColumnCfg;
import com.dororo.future.igrowcopilot.domain.GenCfg;
import com.dororo.future.igrowcopilot.domain.TableCfg;
import com.dororo.future.igrowcopilot.dto.TemplateWorker;
import com.dororo.future.igrowcopilot.dto.TemplateEnvDTO;
import com.dororo.future.igrowcopilot.enums.TemplateFromEnum;
import com.dororo.future.igrowcopilot.util.FmUtils;
import com.dororo.future.igrowcopilot.util.VmUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class OptService {
    @Resource
    private TableCfgService tableCfgService;
    @Resource
    private GenCfgService genCfgService;
    @Resource
    private ColumnCfgService columnCfgService;
    @Resource
    private GenSysUserService genSysUserService;

    private TemplateEnvDTO getTemplateEnv(Integer tableCfgId, Integer genCfgId) {
        TableCfg tableCfg = tableCfgService.selectByPrimaryKey(tableCfgId);
        GenCfg genCfg = genCfgService.selectByPrimaryKey(genCfgId);
        List<ColumnCfg> columnCfgs = columnCfgService.findByAll(ColumnCfg.builder().userId(tableCfg.getUserId()).tableCfgId(tableCfgId).build());
        //
        TemplateEnvDTO templateEnv = new TemplateEnvDTO();
        BeanUtil.copyProperties(tableCfg, templateEnv);
        BeanUtil.copyProperties(genCfg, templateEnv);
        templateEnv.setColumns(columnCfgs);
        templateEnv.setDateTime(DateUtil.now());
        templateEnv.setAuthor(genSysUserService.selectByPrimaryKey(tableCfg.getUserId()).getUsername());
        //
        return templateEnv;
    }

    /**
     * 内置模板+上传模板,同时渲染,雪花算法生成missionId作为临时目录,生成文件,同时收集字符串结果
     *
     * @param files 上传文件
     */
    public List<TemplateWorker> render(MultipartFile[] files, Integer tableCfgId, Integer genCfgId, String missionId) {
        return null;
    }
}