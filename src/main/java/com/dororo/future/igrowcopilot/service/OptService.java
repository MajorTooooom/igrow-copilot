package com.dororo.future.igrowcopilot.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
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
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;

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
        // 反引号
        for (ColumnCfg columnCfg : columnCfgs) {
            List<String> mysqlKeywords = CommonConstants.MYSQL_KEYWORDS;
            boolean aCase = StrUtil.equalsAnyIgnoreCase(columnCfg.getColumnName(), mysqlKeywords.toArray(new String[0]));
            columnCfg.setRequireBackQuote(aCase);
        }
        //
        TemplateEnvDTO templateEnv = new TemplateEnvDTO();
        BeanUtil.copyProperties(tableCfg, templateEnv);
        BeanUtil.copyProperties(genCfg, templateEnv);
        templateEnv.setColumns(columnCfgs);
        templateEnv.setDateTime(DateUtil.now());
        templateEnv.setAuthor(genSysUserService.selectByPrimaryKey(tableCfg.getUserId()).getUsername());
        // 需要记录全部的javaType,用于生成import语句
        List<String> javaTypes = new ArrayList<>(columnCfgs.stream().map(c -> c.getJavaType())
                .filter(StrUtil::isNotBlank)
                .filter(jt -> {
                    List<String> list = CollectionUtil.toList("java.lang.String", "java.lang.Integer");
                    boolean equalsAny = StrUtil.equalsAny(jt, list.toArray(new String[0]));
                    return !equalsAny;
                })
                .collect(Collectors.toSet()));
        templateEnv.setJavaTypes(javaTypes);

        // 模拟MCHP的时间类型字段
        SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH);
        // Mon Apr 01 15:23:29 CST 2024
        String formattedDate = sdf.format(DateUtil.date());
        templateEnv.setMchpFormatDate(formattedDate);

        return templateEnv;
    }

    /**
     * 内置模板+上传模板,同时渲染,雪花算法生成missionId作为临时目录,生成文件,同时收集字符串结果
     *
     * @param files 上传文件
     */
    public List<TemplateWorker> renderDispatch(MultipartFile[] files, Integer tableCfgId, Integer genCfgId, String missionId, RenderModeEnum modeEnum) {
        List<TemplateWorker> templateWorkers = new ArrayList<>();
        // 增加一组结果展示环境变量
        templateWorkers.add(TemplateWorker.builder()
                .from(TemplateFromEnum.BUILT_IN.code)
                .mainName("环境变量预览")
                .extName("json")
                .templateContent(null)
                .errorMessage(null)
                .absolutePath(null)
                .stringResult(JSONUtil.toJsonPrettyStr(getTemplateEnv(tableCfgId, genCfgId)))
                .success(true)
                .build());


        // 总输出目录
        File outputDir = FileUtil.file(SystemUtil.getUserInfo().getCurrentDir(), "attachments", ".tmp", missionId);
        FileUtil.mkdir(outputDir);

        // 记录时间
        File createTimeFe = FileUtil.file(outputDir, "createTime.txt");
        FileUtil.writeUtf8String(DateUtil.now(), createTimeFe);

        // 处理上传模板
        renderUpload(files, templateWorkers, tableCfgId, genCfgId, outputDir, modeEnum);

        // 处理内置模板
        renderBuiltIn(templateWorkers, tableCfgId, genCfgId, outputDir, modeEnum);


        // 删除临时文件？
        if (!modeEnum.equals(RenderModeEnum.ZIP_MODE)) {
            FileUtil.del(outputDir);
        } else {
            // TODO 打包zip
        }

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

        // 渲染目录配置
        GenCfg genCfg = genCfgService.selectByPrimaryKey(genCfgId);
        TableCfg tableCfg = tableCfgService.selectByPrimaryKey(tableCfgId);
        // 内置模板渲染结果目录
        File nzmbToTop = FileUtil.file(outputDir, CommonConstants.NZMB_RESULT);
        for (String agreed : agreedList) {
            TemplateWorker templateWorker = TemplateWorker.builder().from(TemplateFromEnum.BUILT_IN.code).mainName(agreed).extName(null).templateContent(null).errorMessage(null).absolutePath(null).stringResult(null).success(false).build();
            try {
                // 找出对应的文件
                File agreedFile = nzmbs.stream().filter(s -> FileUtil.mainName(s).equals(agreed)).findFirst().get();
                String extName = FileUtil.extName(agreedFile);
                String absolutePath = FileUtil.getAbsolutePath(agreedFile);
                String parent = FileUtil.getParent(absolutePath, 1);
                String mainName = FileUtil.mainName(agreedFile);
                // 
                templateWorker.setAbsolutePath(absolutePath);
                templateWorker.setExtName(extName);
                // 
                boolean isFm = StrUtil.equals(extName, "ftl", true);
                // 全部模式都收集字符串结果
                String templateContent = getTemplateContent(absolutePath);
                String stringResult = isFm ?
                        FmUtils.renderToString(parent, FileUtil.getName(absolutePath), templateContent, BeanUtil.beanToMap(templateEnv))
                        : VmUtils.renderToString(parent, FileUtil.getName(absolutePath), templateContent, BeanUtil.beanToMap(templateEnv));
                // 
                templateWorker.setStringResult(stringResult);

                // 如果是ZIP模式,渲染为临时文件并打包,然后将路径返回     
                if (modeEnum.equals(RenderModeEnum.ZIP_MODE)) {
                    String targetPath = getBuiltInTargetAbsPath(agreedFile, genCfg, tableCfg, nzmbToTop);
                    Boolean aBoolean = isFm ?
                            FmUtils.renderToFile(parent, FileUtil.getName(absolutePath), BeanUtil.beanToMap(templateEnv), targetPath)
                            : VmUtils.renderToFile(parent, mainName, BeanUtil.beanToMap(templateEnv), targetPath);
                }
                // 如果是目录模式,根据配置的目录设置输出路径     
                if (modeEnum.equals(RenderModeEnum.DIR_MODE)) {
                    String targetPath = getBuiltInTargetAbsPath(agreedFile, genCfg, tableCfg, null);
                    Boolean aBoolean = isFm ?
                            FmUtils.renderToFile(parent, FileUtil.getName(absolutePath), BeanUtil.beanToMap(templateEnv), targetPath)
                            : VmUtils.renderToFile(parent, mainName, BeanUtil.beanToMap(templateEnv), targetPath);
                }
                templateWorker.setSuccess(true);
            } catch (Exception e) {
                templateWorker.setErrorMessage(e.getMessage());
                templateWorker.setSuccess(false);
                log.error("renderBuiltIn() called with exception => [templateWorkers = {}], [tableCfgId = {}], [genCfgId = {}], [outputDir = {}], [modeEnum = {}]", templateWorkers, tableCfgId, genCfgId, outputDir, modeEnum, e);
            }
            templateWorkers.add(templateWorker);
        }
    }

    private String getBuiltInTargetAbsPath(File agreedFile, GenCfg genCfg, TableCfg tableCfg, File nzmbToTop) {
        // 如果nzmbToTop不为空,说明顶级目录是临时文件夹(zip),否则按照配置项向项目目录输出(dir)
        String toTop = nzmbToTop == null ? genCfg.getSourceCodeAbsPath() : FileUtil.getAbsolutePath(nzmbToTop);
        File targetFile = null;
        String mainName = FileUtil.mainName(agreedFile);
        if (StrUtil.equals("mapperXml", mainName)) {
            // mapperXml的toTop需要特殊处理
            targetFile = FileUtil.file(
                    nzmbToTop == null ? genCfg.getResourceAbsPath() : FileUtil.getAbsolutePath(nzmbToTop),
                    genCfg.getMapperXmlPath(), tableCfg.getDomainName() + "Mapper.xml");
        } else if (StrUtil.equals("mapper", mainName)) {
            String[] pkgs = StrUtil.split(genCfg.getMapperPackage(), StrUtil.DOT);
            targetFile = FileUtil.file(toTop);
            for (int i = 0; i < pkgs.length; i++) {
                targetFile = FileUtil.file(targetFile, pkgs[i]);
            }
            targetFile = FileUtil.file(targetFile, tableCfg.getDomainName() + "Mapper.java");
        } else if (StrUtil.equals("service", mainName)) {
            String[] pkgs = StrUtil.split(genCfg.getServicePackage(), StrUtil.DOT);
            targetFile = FileUtil.file(toTop);
            for (int i = 0; i < pkgs.length; i++) {
                targetFile = FileUtil.file(targetFile, pkgs[i]);
            }
            targetFile = FileUtil.file(targetFile, tableCfg.getDomainName() + "Service.java");
        } else if (StrUtil.equals("domain", mainName)) {
            String[] pkgs = StrUtil.split(genCfg.getDomainPackage(), StrUtil.DOT);
            targetFile = FileUtil.file(toTop);
            for (int i = 0; i < pkgs.length; i++) {
                targetFile = FileUtil.file(targetFile, pkgs[i]);
            }
            targetFile = FileUtil.file(targetFile, tableCfg.getDomainName() + ".java");
        } else if (StrUtil.equals("domainAddDTO", mainName)) {
            String[] pkgs = StrUtil.split(genCfg.getDtoPackage(), StrUtil.DOT);
            targetFile = FileUtil.file(toTop);
            for (int i = 0; i < pkgs.length; i++) {
                targetFile = FileUtil.file(targetFile, pkgs[i]);
            }
            targetFile = FileUtil.file(targetFile, tableCfg.getDomainName() + "AddDTO.java");
        } else if (StrUtil.equals("domainUpdateDTO", mainName)) {
            String[] pkgs = StrUtil.split(genCfg.getDtoPackage(), StrUtil.DOT);
            targetFile = FileUtil.file(toTop);
            for (int i = 0; i < pkgs.length; i++) {
                targetFile = FileUtil.file(targetFile, pkgs[i]);
            }
            targetFile = FileUtil.file(targetFile, tableCfg.getDomainName() + "UpdateDTO.java");
        } else if (StrUtil.equals("domainPageDTO", mainName)) {
            String[] pkgs = StrUtil.split(genCfg.getDtoPackage(), StrUtil.DOT);
            targetFile = FileUtil.file(toTop);
            for (int i = 0; i < pkgs.length; i++) {
                targetFile = FileUtil.file(targetFile, pkgs[i]);
            }
            targetFile = FileUtil.file(targetFile, tableCfg.getDomainName() + "PageDTO.java");
        } else if (StrUtil.equals("domainImportDTO", mainName)) {
            String[] pkgs = StrUtil.split(genCfg.getDtoPackage(), StrUtil.DOT);
            targetFile = FileUtil.file(toTop);
            for (int i = 0; i < pkgs.length; i++) {
                targetFile = FileUtil.file(targetFile, pkgs[i]);
            }
            targetFile = FileUtil.file(targetFile, tableCfg.getDomainName() + "ImportDTO.java");
        } else if (StrUtil.equals("domainExportDTO", mainName)) {
            String[] pkgs = StrUtil.split(genCfg.getDtoPackage(), StrUtil.DOT);
            targetFile = FileUtil.file(toTop);
            for (int i = 0; i < pkgs.length; i++) {
                targetFile = FileUtil.file(targetFile, pkgs[i]);
            }
            targetFile = FileUtil.file(targetFile, tableCfg.getDomainName() + "ExportDTO.java");
        } else if (StrUtil.equals("easyExcelListener", mainName)) {
            String[] pkgs = StrUtil.split(genCfg.getEasyExcelListenerPackage(), StrUtil.DOT);
            targetFile = FileUtil.file(toTop);
            for (int i = 0; i < pkgs.length; i++) {
                targetFile = FileUtil.file(targetFile, pkgs[i]);
            }
            targetFile = FileUtil.file(targetFile, tableCfg.getDomainName() + "ImportListener.java");
        } else if (StrUtil.equals("controller", mainName)) {
            String[] pkgs = StrUtil.split(genCfg.getControllerPackage(), StrUtil.DOT);
            targetFile = FileUtil.file(toTop);
            for (int i = 0; i < pkgs.length; i++) {
                targetFile = FileUtil.file(targetFile, pkgs[i]);
            }
            targetFile = FileUtil.file(targetFile, tableCfg.getDomainName() + "Controller.java");
        }
        return FileUtil.getAbsolutePath(targetFile);
    }


    private String getTemplateContent(String absolutePath) {
        return FileUtil.readUtf8String(absolutePath);
    }

    @SneakyThrows
    private void renderUpload(MultipartFile[] files, List<TemplateWorker> templateWorkers, Integer tableCfgId, Integer genCfgId, File outputDir, RenderModeEnum modeEnum) {
        if (files == null || files.length <= 0) {
            return;
        }
        // 拷贝内置模板到目标目录
        File uploadDir = FileUtil.file(outputDir, CommonConstants.UPLOAD);
        FileUtil.mkdir(uploadDir);
        for (MultipartFile multipartFile : files) {
            File file = FileUtil.file(uploadDir, multipartFile.getOriginalFilename());
            multipartFile.transferTo(file);
        }
        // 模板环境变量
        TemplateEnvDTO templateEnv = getTemplateEnv(tableCfgId, genCfgId);

        // 上传模板渲染结果目录
        File uploadToDir = FileUtil.file(outputDir, CommonConstants.UPLOAD_RESULT);
        FileUtil.mkdir(uploadToDir);

        // (1)upload的模板生成字符串结果;(2)生成一份到临时文件夹
        for (File template : FileUtil.ls(FileUtil.getAbsolutePath(uploadDir))) {
            TemplateWorker templateWorker = TemplateWorker.builder().from(TemplateFromEnum.UPLOAD.code).mainName(FileUtil.mainName(template)).extName(FileUtil.extName(template)).templateContent(null).errorMessage(null).absolutePath(null).stringResult(null).success(false).build();
            boolean isFm = StrUtil.equals(FileUtil.extName(template), "ftl", true);
            String stringResult = isFm ?
                    FmUtils.renderToString(FileUtil.getAbsolutePath(uploadDir), FileUtil.getName(template), getTemplateContent(FileUtil.getAbsolutePath(template)), BeanUtil.beanToMap(templateEnv))
                    : VmUtils.renderToString(FileUtil.getAbsolutePath(uploadDir), FileUtil.getName(template), getTemplateContent(FileUtil.getAbsolutePath(template)), BeanUtil.beanToMap(templateEnv));
            templateWorker.setStringResult(stringResult);
            templateWorker.setSuccess(true);
            templateWorkers.add(templateWorker);
            // (2)
            if (modeEnum.equals(RenderModeEnum.ZIP_MODE)) {
                String targetPath = FileUtil.getAbsolutePath(FileUtil.file(uploadToDir, FileUtil.getName(template) + ".txt"));
                Boolean aBoolean = isFm ?
                        FmUtils.renderToFile(FileUtil.getAbsolutePath(uploadDir), FileUtil.getName(template), BeanUtil.beanToMap(templateEnv), targetPath)
                        : VmUtils.renderToFile(FileUtil.getAbsolutePath(uploadDir), FileUtil.getName(template), BeanUtil.beanToMap(templateEnv), targetPath);
            }
        }


    }
}