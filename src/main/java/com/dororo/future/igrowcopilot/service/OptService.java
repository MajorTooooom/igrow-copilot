package com.dororo.future.igrowcopilot.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.system.SystemUtil;
import com.dororo.future.igrowcopilot.constant.CommonConstants;
import com.dororo.future.igrowcopilot.domain.ColumnCfg;
import com.dororo.future.igrowcopilot.domain.GenCfg;
import com.dororo.future.igrowcopilot.domain.TableCfg;
import com.dororo.future.igrowcopilot.dto.TemplateWorker;
import com.dororo.future.igrowcopilot.dto.TemplateEnvDTO;
import com.dororo.future.igrowcopilot.enums.RenderModeEnum;
import com.dororo.future.igrowcopilot.enums.TemplateFromEnum;
import com.dororo.future.igrowcopilot.util.FmUtils;
import com.dororo.future.igrowcopilot.util.VmUtils;
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
    public List<TemplateWorker> renderDispatch(MultipartFile[] files, Integer tableCfgId, Integer genCfgId, String missionId, RenderModeEnum modeEnum) {
        List<TemplateWorker> templateWorkers = new ArrayList<>();
        // 总输出目录
        File outputDir = FileUtil.file(SystemUtil.getUserInfo().getCurrentDir(), "attachments", ".tmp", missionId);
        FileUtil.mkdir(outputDir);
        // 处理内置模板
        renderBuiltIn(templateWorkers, tableCfgId, genCfgId, outputDir, modeEnum);
        // 处理上传模板 
        renderUpload(files, templateWorkers, tableCfgId, genCfgId, outputDir, modeEnum);
        return templateWorkers;
    }

    private void renderBuiltIn(List<TemplateWorker> templateWorkers, Integer tableCfgId, Integer genCfgId, File outputDir, RenderModeEnum modeEnum) {
        // 拷贝内置模板到目标目录
        File nzmbDir = FileUtil.file(outputDir, CommonConstants.NZMB);
        FileUtil.mkdir(nzmbDir);
        FileUtil.copyContent(FileUtil.file(SystemUtil.getUserInfo().getCurrentDir(), "attachments", ".template"), nzmbDir, true);
        // 内置模板目录下的所有文件
        List<File> nzmbs = CollectionUtil.toList(FileUtil.ls(FileUtil.getAbsolutePath(nzmbDir)));
        // 约定渲染的模板名称(不含扩展名)     
        List<String> agreedList = CollectionUtil.toList(
                "mapperXml" // mapper.xml
                , "mapper" // mapper.java
                , "service"// service.java
                , "domain"// domain.java
                , "domainAddDTO"// domainAddDTO.java
                , "domainUpdateDTO"// domainUpdateDTO.java
                , "domainPageDTO"// domainPageDTO.java
                , "domainImportDTO"// domainImportDTO.java
                , "domainExportDTO"// domainExportDTO.java
                , "easyExcelListener"// easyExcelListener.java
                , "controller"// controller.java
        );
        // 检查期望的模板,在仓库中是否存在同名模板,比如同时存在"controller.ftl"、"controller.vm",将导致混乱     
        for (String agreed : agreedList) {
            long count = nzmbs.stream().map(s -> FileUtil.mainName(s)).filter(q -> q.equals(agreed)).count();
            if (count == 0) {
                throw new RuntimeException(StrUtil.format("内置模板[{}]缺失,请配置{}.ftl或{}.vm", agreed, agreed, agreed));
            } else if (count > 1) {
                throw new RuntimeException(StrUtil.format("内置模板[{}]重复,请删除或重命名多余的{}.ftl或{}.vm", agreed, agreed, agreed));
            }
        }

        // 模板环境变量
        TemplateEnvDTO templateEnv = getTemplateEnv(tableCfgId, genCfgId);

        for (String agreed : agreedList) {
            TemplateWorker templateWorker = TemplateWorker.builder().from(TemplateFromEnum.BUILT_IN.code).mainName(agreed).extName(null).templateContent(null).errorMessage(null).absolutePath(null).stringResult(null).success(false).build();
            try {
                // 找出对应的文件
                File agreedFile = nzmbs.stream().filter(s -> FileUtil.mainName(s).equals(agreed)).findFirst().get();
                String extName = FileUtil.extName(agreedFile);
                String absolutePath = FileUtil.getAbsolutePath(agreedFile);
                // 
                templateWorker.setAbsolutePath(absolutePath);
                templateWorker.setExtName(extName);
                // 
                boolean isFm = StrUtil.equals(extName, "ftl", true);
                // 全部模式都收集字符串结果
                String stringResult = isFm ? FmUtils.renderToString(absolutePath, templateEnv) : VmUtils.renderToString(absolutePath, templateEnv);
                templateWorker.setStringResult(stringResult);

                // 如果是ZIP模式,渲染为临时文件并打包,然后将路径返回     
                if (modeEnum.equals(RenderModeEnum.ZIP_MODE)) {
                    // TODO     
                }
                // 如果是目录模式,根据配置的目录设置输出路径     
                if (modeEnum.equals(RenderModeEnum.DIR_MODE)) {
                    // TODO     
                }
            } catch (Exception e) {
            }
            templateWorkers.add(templateWorker);
        }
    }

    private void renderUpload(MultipartFile[] files, List<TemplateWorker> templateWorkers, Integer tableCfgId, Integer genCfgId, File outputDir, RenderModeEnum modeEnum) {
    }
}