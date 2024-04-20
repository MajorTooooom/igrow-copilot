package ${controllerPackage};

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.excel.EasyExcel;
import com.zhien.common.core.controller.BaseController;
import com.zhien.common.core.domain.R;
import ${domainPackage}.${domainName};
import ${dtoPackage}.*;
import ${listenerPackage}.${domainName}ImportListener;
import com.zhien.igrow.service.${domainName}Service;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.util.List;

/**
 * ${((domainZnName?trim!"")?length > 0)?then(domainZnName, tableName)}控制器
 */
@Api(tags = "${((domainZnName?trim!"")?length > 0)?then(domainZnName, tableName)}控制器")
@RestController
@RequestMapping("/${domainName?uncap_first}")
public class ${domainName}Controller extends BaseController {
    @Resource
    private ${domainName}Service ${domainName?uncap_first}Service;

    @PostMapping("/add")
    @ApiOperation(value = "新增", notes = "新增")
    public R add(@RequestBody @Validated ${domainName}AddDTO addDTO) {
        ${domainName?uncap_first}Service.addByDto(addDTO);
        return R.ok();
    }

    @GetMapping("/delete")
    @ApiOperation(value = "删除", notes = "删除")
    public R delete(@RequestParam("id") Integer id) {
        ${domainName?uncap_first}Service.delete(id);
        return R.ok();
    }

    @PostMapping("/update")
    @ApiOperation(value = "更新", notes = "更新")
    public R update(@RequestBody @Validated ${domainName}UpdateDTO updateDTO) {
        ${domainName?uncap_first}Service.updateByDto(updateDTO);
        return R.ok();
    }

    @PostMapping("page")
    @ApiOperation(value = "条件分页查询", notes = "条件分页查询")
    public R page(@RequestBody @Validated ${domainName}PageDTO pageDTO) {
        startPage();
        List<${domainName}> list = ${domainName?uncap_first}Service.conditionQuery(pageDTO);
        R result = R.page(list);
        // 可选:传递其他参数,例如options等,result.put("options", options);
        return result;
    }

    @PostMapping("/importData")
    @ApiOperation(value = "导入数据", notes = "导入数据")
    public R importData(MultipartFile file) throws Exception {
        EasyExcel.read(file.getInputStream(), ${domainName}ImportDTO.class, new ${domainName}ImportListener()).sheet().doRead();
        return R.ok();
    }

    @PostMapping("/export")
    @ApiOperation(value = "导出数据", notes = "导出数据")
    public void export(@RequestBody ${domainName}PageDTO pageDTO, HttpServletResponse response) throws Exception {
        List<${domainName}> list = ${domainName?uncap_first}Service.conditionQueryAllPages(pageDTO);
        try {
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setCharacterEncoding("utf-8");
            //
            String name = StrUtil.format("{}_{}", "${((domainZnName?trim!"")?length > 0)?then(domainZnName, tableName)}", DateUtil.format(DateUtil.date(), "yyyyMMdd_HHmmss"));
            String fileName = URLEncoder.encode(name, "UTF-8").replaceAll("\\+", "%20");
            response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");
            // 在此处，需配置以保持流未关闭，以便于实现在Web环境中执行写操作，并在操作失败时返回JSON格式的响应
            EasyExcel.write(response.getOutputStream(), ${domainName}ExportDTO.class).autoCloseStream(Boolean.FALSE).sheet("data").doWrite(list);
        } catch (Exception e) {
            // 重置response
            response.reset();
            response.setContentType("application/json");
            response.setCharacterEncoding("utf-8");
            R error = R.error("下载文件失败" + e.getMessage());
            response.getWriter().println(JSONUtil.toJsonStr(error));
        }
    }
}