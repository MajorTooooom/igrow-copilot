package com.zhien.igrow.service;

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
import com.zhien.igrow.domain.AaaaBbbbCccc;
import com.zhien.igrow.dto.*;
import com.zhien.igrow.eelistener.AaaaBbbbCcccImportListener;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import org.springframework.beans.factory.annotation.Autowired;

import com.zhien.igrow.mapper.AaaaBbbbCcccMapper;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AaaaBbbbCcccService {
    @Autowired
    private AaaaBbbbCcccMapper aaaaBbbbCcccMapper;

    public void add(AaaaBbbbCcccAddDTO addDTO) {
        AaaaBbbbCccc aaaaBbbbCccc = Convert.convert(AaaaBbbbCccc.class, addDTO);
        int i = aaaaBbbbCcccMapper.insert(aaaaBbbbCccc);
        Assert.isTrue(i > 0, "新增失败");
    }

    public void deleteByPrimaryKey(Integer id) {
        aaaaBbbbCcccMapper.deleteByPrimaryKey(id);
    }

    public void update(AaaaBbbbCcccUpdateDTO updateDTO) {
        AaaaBbbbCccc aaaaBbbbCccc = Convert.convert(AaaaBbbbCccc.class, updateDTO);
        aaaaBbbbCcccMapper.updateByPrimaryKeySelective(aaaaBbbbCccc);
    }

    public AaaaBbbbCccc selectByPrimaryKey(Integer id) {
        return aaaaBbbbCcccMapper.selectByPrimaryKey(id);
    }

    public List<AaaaBbbbCcccExportDTO> conditionalQueryPage(AaaaBbbbCcccPageDTO pageDTO) {
        // TODO 通常需要改写
        List<AaaaBbbbCccc> select = aaaaBbbbCcccMapper.select(Convert.convert(AaaaBbbbCccc.class, pageDTO));
        List<AaaaBbbbCcccExportDTO> collect = select.stream().map(s -> Convert.convert(AaaaBbbbCcccExportDTO.class, s)).collect(Collectors.toList());
        return collect;
    }

    public List<AaaaBbbbCcccExportDTO> conditionalQueryAllPage(AaaaBbbbCcccPageDTO pageDTO) {
        int pageNum = 1;
        final int pageSize = 1000;
        TimeInterval overtimeProtectionTimer = DateUtil.timer();
        long maxSecond = 30;
        List<AaaaBbbbCcccExportDTO> result = new ArrayList<>();
        while (true) {
            PageHelper.startPage(pageNum, pageSize);
            List<AaaaBbbbCcccExportDTO> pageResult = conditionalQueryPage(pageDTO);
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

    public Map<String, AaaaBbbbCcccImportListener.ImportResultDTO> importExcel(MultipartFile[] files) {
        Map<String, AaaaBbbbCcccImportListener.ImportResultDTO> result = new HashMap<>();
        for (MultipartFile file : files) {
            AaaaBbbbCcccImportListener.ImportResultDTO importResultDTO = new AaaaBbbbCcccImportListener.ImportResultDTO();
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
    public void handleSingleExcel(MultipartFile file, AaaaBbbbCcccImportListener.ImportResultDTO importResultDTO) {
        // 基于EasyExcel监听器
        AaaaBbbbCcccImportListener listener = new AaaaBbbbCcccImportListener(importResultDTO);
        ExcelReaderBuilder read = EasyExcel.read(file.getInputStream(), AaaaBbbbCcccImportDTO.class, listener);
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

    public void handleSingleExcelRow(AaaaBbbbCcccImportDTO row, AaaaBbbbCcccImportListener.ImportResultDTO importResultDTO) {
        // LoginUserDTO currentUser = AuthUserUtils.getCurrentUser();
        // Assert.notNull(currentUser, "当前登录用户不存在");

        // TODO 具体的业务逻辑校验,具体根据业务需求编写
        // TODO 具体的业务逻辑校验,具体根据业务需求编写
        // TODO 具体的业务逻辑校验,具体根据业务需求编写

        // TODO excel行转数据库实体     
        AaaaBbbbCccc aaaaBbbbCccc = Convert.convert(AaaaBbbbCccc.class, row);
        aaaaBbbbCcccMapper.insert(aaaaBbbbCccc);

    }
}
