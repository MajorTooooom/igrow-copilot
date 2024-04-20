package ${controllerPackage};

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.excel.EasyExcel;
import com.zhien.common.core.controller.BaseController;
import com.zhien.common.core.domain.R;
import ${domainPackage}.${domainName};
import ${dtoPackage}.${domainName}AddDTO;
import ${dtoPackage}.${domainName}ExportDTO;
import ${dtoPackage}.${domainName}ImportDTO;
import ${dtoPackage}.${domainName}PageDTO;
import ${dtoPackage}.${domainName}UpdateDTO;
import ${servicePackage}.${domainName}Service;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

/**
 * 学员培训审核表(aaaa_bbbb_cccc)表控制层
 *
 * @author xxxxx
 */
@RestController
@Api(tags = "学员培训审核表")
@RequestMapping("/aaaa_bbbb_cccc")
public class AaaaBbbbCcccController extends BaseController {
    /**
     * 服务对象
     */
    @Autowired
    private AaaaBbbbCcccService aaaaBbbbCcccService;

    @PostMapping("/add")
    @ApiOperation(value = "新增", notes = "新增")
    public R add(@RequestBody @Validated AaaaBbbbCcccAddDTO addDTO) {
        aaaaBbbbCcccService.add(addDTO);
        return R.ok();
    }

    @GetMapping("/delete/{id}")
    @ApiOperation(value = "删除", notes = "删除")
    public R delete(@PathVariable(value = "id") Integer id) {
        aaaaBbbbCcccService.deleteByPrimaryKey(id);
        return R.ok();
    }

    @PostMapping("/update")
    @ApiOperation(value = "更新", notes = "更新")
    public R update(@RequestBody @Validated AaaaBbbbCcccUpdateDTO updateDTO) {
        aaaaBbbbCcccService.update(updateDTO);
        return R.ok();
    }

    @GetMapping("/get/{id}")
    @ApiOperation(value = "查询", notes = "查询")
    public R get(@PathVariable(value = "id") Integer id) {
        AaaaBbbbCccc aaaaBbbbCccc = aaaaBbbbCcccService.selectByPrimaryKey(id);
        return R.data(aaaaBbbbCccc);
    }

    @PostMapping("/page")
    @ApiOperation(value = "条件分页", notes = "条件分页")
    public R page(@RequestBody @Validated AaaaBbbbCcccPageDTO pageDTO) {
        // pageDTO.setIsDelete(YesNoEnum.NO.value + "");
        startPage();
        List<AaaaBbbbCcccExportDTO> list = aaaaBbbbCcccService.conditionalQueryPage(pageDTO);
        return R.page(list);
    }

    /**
     * 导出失败时反馈JSON
     */
    @PostMapping("/export")
    @ApiOperation(value = "导出", notes = "导出")
    public void export(@RequestBody @Validated AaaaBbbbCcccPageDTO pageDTO, HttpServletResponse response) throws IOException {
        // pageDTO.setIsDelete(YesNoEnum.NO.value + "");
        List<AaaaBbbbCcccExportDTO> list = aaaaBbbbCcccService.conditionalQueryAllPage(pageDTO);
        try {
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setCharacterEncoding("utf-8");
            String businessName = StrUtil.format("{}_{}", "学员培训审核", DateUtil.format(DateUtil.date(), "yyyyMMdd_HHmmss"));
            String fileName = URLEncoder.encode(businessName, "UTF-8").replaceAll("\\+", "%20");
            response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");
            EasyExcel.write(response.getOutputStream(), AaaaBbbbCcccExportDTO.class).autoCloseStream(Boolean.FALSE).sheet("data").doWrite(list);
        } catch (Exception e) {
            response.reset();
            response.setContentType("application/json");
            response.setCharacterEncoding("utf-8");
            R error = R.error("下载文件失败" + e.getMessage());
            response.getWriter().println(JSONUtil.toJsonStr(error));
        }
    }

    @PostMapping("/import")
    @ApiOperation(value = "导入", notes = "导入")
    public R importExcel(@RequestParam("file") MultipartFile[] files) throws Exception {
        return R.data(aaaaBbbbCcccService.importExcel(files));
    }
}
