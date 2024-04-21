package com.dororo.future.igrowcopilot.controller;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import cn.hutool.system.SystemUtil;
import com.dororo.future.igrowcopilot.constant.CommonConstants;
import com.dororo.future.igrowcopilot.controller.common.CopilotBaseController;
import com.dororo.future.igrowcopilot.domain.GenCfg;
import com.dororo.future.igrowcopilot.domain.TableCfg;
import com.dororo.future.igrowcopilot.dto.TemplateWorker;
import com.dororo.future.igrowcopilot.enums.RenderModeEnum;
import com.dororo.future.igrowcopilot.service.GenCfgService;
import com.dororo.future.igrowcopilot.service.OptService;
import com.dororo.future.igrowcopilot.service.TableCfgService;
import com.zhien.common.core.domain.R;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import net.lingala.zip4j.ZipFile;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/optGen")
public class OptGenController extends CopilotBaseController {
    @Resource
    private TableCfgService tableCfgService;
    @Resource
    private GenCfgService genCfgService;
    @Resource
    private OptService optService;

    @Value("${LOCAL_HOST_MODE:false}")
    private Boolean localHostMode;

    @PostMapping("/optTableCfgOptions")
    public R optTableCfgOptions() {
        List<TableCfg> matchList = tableCfgService.findByAll(TableCfg.builder().userId(getCurrentUserIdWithException()).build());
        List<JSONObject> options = matchList.stream().map(rc -> {
            JSONObject option = JSONUtil.createObj()
                    .putOpt("label", StrUtil.format("({}) {}", rc.getId(), rc.getTableCfgName()))
                    .putOpt("value", rc.getId());
            return option;
        }).collect(Collectors.toList());
        return R.data(options);
    }

    @PostMapping("/optGenCfgOptions")
    public R optGenCfgOptions() {
        List<GenCfg> list = genCfgService.findByAll(GenCfg.builder().userId(getCurrentUserIdWithException()).build());
        List<JSONObject> options = list.stream().map(rc -> {
            JSONObject option = JSONUtil.createObj()
                    .putOpt("label", StrUtil.format("({}) {}", rc.getId(), rc.getGenCfgName()))
                    .putOpt("value", rc.getId());
            return option;
        }).collect(Collectors.toList());
        return R.data(options);
    }

    /**
     * formData.append('missionId', nanoid());
     * formData.append('tableCfgId', this.envCfgFormVo.tableCfgId);
     * formData.append('genCfgId', this.envCfgFormVo.genCfgId);
     * formData.append('mode', 'web');
     *
     * @param files
     * @param response
     */
    @PostMapping("/renderToString")
    public R renderToString(@RequestParam(value = "file", required = false) MultipartFile[] files,
                            @RequestParam("tableCfgId") Integer tableCfgId,
                            @RequestParam("genCfgId") Integer genCfgId,
                            HttpServletResponse response) {
        String missionId = getMissionId();
        List<TemplateWorker> templateWorkers = optService.renderDispatch(files, tableCfgId, genCfgId, missionId, RenderModeEnum.STRING_MODE);
        return R.data(templateWorkers);
    }

    @PostMapping("/renderToZip")
    public R renderToZip(@RequestParam(value = "file", required = false) MultipartFile[] files,
                         @RequestParam("tableCfgId") Integer tableCfgId,
                         @RequestParam("genCfgId") Integer genCfgId,
                         HttpServletResponse response) {
        String missionId = getMissionId();
        List<TemplateWorker> templateWorkers = optService.renderDispatch(files, tableCfgId, genCfgId, missionId, RenderModeEnum.ZIP_MODE);
        JSONObject data = JSONUtil.createObj()
                .put("missionId", missionId)
                .putOpt("renderResults", templateWorkers);
        return R.data(data);
    }


    @PostMapping("/renderToDir")
    public R renderToDir(@RequestParam(value = "file", required = false) MultipartFile[] files,
                         @RequestParam("tableCfgId") Integer tableCfgId,
                         @RequestParam("genCfgId") Integer genCfgId,
                         HttpServletResponse response) {
        String missionId = getMissionId();
        List<TemplateWorker> templateWorkers = optService.renderDispatch(files, tableCfgId, genCfgId, missionId, RenderModeEnum.DIR_MODE);
        return R.data(templateWorkers);
    }

    @SneakyThrows
    @PostMapping("/downloadZip")
    public void downloadZip(@RequestParam(value = "missionId", required = true) String missionId, HttpServletResponse response) {
        // 生成 ZIP 文件路径
        File baseDir = FileUtil.file(SystemUtil.getUserInfo().getCurrentDir(), "attachments", ".tmp");
        FileUtil.mkdir(baseDir);
        File zipFile = FileUtil.file(baseDir, missionId, missionId + ".zip");

        // 确保 ZIP 文件已经生成
        ZipFile zip = new ZipFile(zipFile);
        zip.addFolder(FileUtil.file(baseDir, missionId, CommonConstants.NZMB_RESULT));
        zip.addFolder(FileUtil.file(baseDir, missionId, CommonConstants.UPLOAD_RESULT));

        // 设置 HTTP 响应头
        response.setContentType("application/zip");
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + missionId + ".zip");

        // 使用 FileInputStream 将 ZIP 文件流式传输到客户端
        try (FileInputStream fis = new FileInputStream(zipFile)) {
            StreamUtils.copy(fis, response.getOutputStream());
            response.flushBuffer();
        } catch (IOException e) {
            response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Error while sending ZIP file");
        }
    }

    @GetMapping("/getLocalHostMode")
    public R getLocalHostMode() {
        return R.data(localHostMode);
    }

    public String getMissionId() {
        Snowflake snowflake = IdUtil.getSnowflake(1, 1);
        return snowflake.nextId() + "";
    }
}