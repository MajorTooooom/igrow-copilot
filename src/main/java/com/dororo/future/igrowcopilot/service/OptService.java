package com.dororo.future.igrowcopilot.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.system.SystemUtil;
import com.dororo.future.igrowcopilot.constant.CommonConstants;
import com.dororo.future.igrowcopilot.domain.ColumnCfg;
import com.dororo.future.igrowcopilot.domain.GenCfg;
import com.dororo.future.igrowcopilot.domain.TableCfg;
import com.dororo.future.igrowcopilot.dto.TemplateWorker;
import com.dororo.future.igrowcopilot.dto.TemplateEnvDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
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
    public List<TemplateWorker> renderDispatch(MultipartFile[] files, Integer tableCfgId, Integer genCfgId, String missionId) {
        List<TemplateWorker> templateWorkers = new ArrayList<>();
        // 总输出目录
        File outputDir = FileUtil.file(SystemUtil.getUserInfo().getCurrentDir(), "attachments", ".tmp", missionId);
        FileUtil.mkdir(outputDir);

        // 处理内置模板
        renderBuiltIn(templateWorkers, tableCfgId, genCfgId, outputDir);

        // 处理上传模板 
        renderUpload(files, templateWorkers, tableCfgId, genCfgId, outputDir);

        return templateWorkers;
    }

    private void renderBuiltIn(List<TemplateWorker> templateWorkers, Integer tableCfgId, Integer genCfgId, File outputDir) {
        File file = FileUtil.file(outputDir, CommonConstants.NZMB);
        FileUtil.mkdir(file);
        FileUtil.copyContent(FileUtil.file(SystemUtil.getUserInfo().getCurrentDir(), "attachments", ".template"), file, true);
        // 根据名称,按照配置的包层级创建目录然后渲染     
        
    }

    private void renderUpload(MultipartFile[] files, List<TemplateWorker> templateWorkers, Integer tableCfgId, Integer genCfgId, File outputDir) {
    }
}