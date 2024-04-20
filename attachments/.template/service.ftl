package ${servicePackage};

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.TimeInterval;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.exception.ExcelCommonException;
import com.alibaba.excel.read.builder.ExcelReaderBuilder;
import com.alibaba.excel.read.builder.ExcelReaderSheetBuilder;
import com.github.pagehelper.PageHelper;
import ${domainPackage}.${domainName};
import ${dtoPackage}.${domainName}AddDTO;
import ${dtoPackage}.${domainName}ExportDTO;
import ${dtoPackage}.${domainName}ImportDTO;
import ${dtoPackage}.${domainName}PageDTO;
import ${dtoPackage}.${domainName}UpdateDTO;
import ${easyExcelListenerPackage}.${domainName}ImportListener;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import org.springframework.beans.factory.annotation.Autowired;

import ${mapperPackage}.${domainName}Mapper;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ${domainName}Service {
    @Autowired
    private ${domainName}Mapper ${domainName?uncap_first}Mapper;

    public void add(${domainName}AddDTO addDTO) {
        ${domainName} ${domainName?uncap_first} = Convert.convert(${domainName}.class, addDTO);
        int i = ${domainName?uncap_first}Mapper.insert(${domainName?uncap_first});
        Assert.isTrue(i > 0, "新增失败");
    }

    public void deleteByPrimaryKey(Integer id) {
        ${domainName?uncap_first}Mapper.deleteByPrimaryKey(id);
    }

    public void update(${domainName}UpdateDTO updateDTO) {
        ${domainName} ${domainName?uncap_first} = Convert.convert(${domainName}.class, updateDTO);
        ${domainName?uncap_first}Mapper.updateByPrimaryKeySelective(${domainName?uncap_first});
    }

    public ${domainName} selectByPrimaryKey(Integer id) {
        return ${domainName?uncap_first}Mapper.selectByPrimaryKey(id);
    }

    public List<${domainName}ExportDTO> conditionalQueryPage(${domainName}PageDTO pageDTO) {
        // TODO 通常需要改写
        List<${domainName}> select = ${domainName?uncap_first}Mapper.select(Convert.convert(${domainName}.class, pageDTO));
        List<${domainName}ExportDTO> collect = select.stream().map(s -> Convert.convert(${domainName}ExportDTO.class, s)).collect(Collectors.toList());
        return collect;
    }

    public List<${domainName}ExportDTO> conditionalQueryAllPage(${domainName}PageDTO pageDTO) {
        int pageNum = 1;
        final int pageSize = 1000;
        TimeInterval overtimeProtectionTimer = DateUtil.timer();
        long maxSecond = 30;
        List<${domainName}ExportDTO> result = new ArrayList<>();
        while (true) {
            PageHelper.startPage(pageNum, pageSize);
            List<${domainName}ExportDTO> pageResult = conditionalQueryPage(pageDTO);
            result.addAll(pageResult);
            if (CollectionUtil.isEmpty(pageResult) || pageResult.size() < pageSize) {
                break;
            }
            if (overtimeProtectionTimer.intervalSecond() > maxSecond) {
                StrUtil.format("查询超时[{}/{}]秒，请检查数据量是否过大", overtimeProtectionTimer.intervalSecond(), maxSecond);
                throw new RuntimeException();
            }
            pageNum++;
        }
        return result;
    }

    public Map<String, ${domainName}ImportListener.ImportResultDTO> importExcel(MultipartFile[] files) {
        Map<String, ${domainName}ImportListener.ImportResultDTO> result = new HashMap<>();
        for (MultipartFile file : files) {
            ${domainName}ImportListener.ImportResultDTO importResultDTO = new ${domainName}ImportListener.ImportResultDTO();
            try {
                importResultDTO.setFileName(file.getOriginalFilename());
                handleSingleExcel(file, importResultDTO);
            } catch (Exception e) {
                importResultDTO.addMessage(e.getMessage());
            }
            result.put(file.getOriginalFilename(), importResultDTO);
        }
        return result;
    }

    @SneakyThrows
    public void handleSingleExcel(MultipartFile file, ${domainName}ImportListener.ImportResultDTO importResultDTO) {
        // 基于EasyExcel监听器
        ${domainName}ImportListener listener = new ${domainName}ImportListener(importResultDTO);
        ExcelReaderBuilder read = EasyExcel.read(file.getInputStream(), ${domainName}ImportDTO.class, listener);
        ExcelReaderSheetBuilder sheet = null;
        try {
            sheet = read.sheet();
        } catch (NullPointerException e) {
            importResultDTO.addMessage(StrUtil.format("[{}]未找到工作表,请检查文件是否损坏,可尝试保存为一个新文件再导入", file.getOriginalFilename()));
            return;
        } catch (ExcelCommonException ee) {
            importResultDTO.addMessage(StrUtil.format("[{}]文件格式错误,请检查文件格式或另存为", file.getOriginalFilename()));
            return;
        }
        // listener.invoke()逐行处理
        sheet.doRead();
    }

    public void handleSingleExcelRow(${domainName}ImportDTO row, ${domainName}ImportListener.ImportResultDTO importResultDTO) {
        // LoginUserDTO currentUser = AuthUserUtils.getCurrentUser();
        // Assert.notNull(currentUser, "当前登录用户不存在");

        // TODO 具体的业务逻辑校验,具体根据业务需求编写
        // TODO 具体的业务逻辑校验,具体根据业务需求编写
        // TODO 具体的业务逻辑校验,具体根据业务需求编写

        // TODO excel行转数据库实体     
        ${domainName} ${domainName?uncap_first} = Convert.convert(${domainName}.class, row);
        ${domainName?uncap_first}Mapper.insert(${domainName?uncap_first});

    }
}
