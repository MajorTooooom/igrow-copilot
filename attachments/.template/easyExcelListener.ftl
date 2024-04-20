<#include "环境变量辅助.ftl"/>
package ${easyExcelListenerPackage};

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.StrUtil;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.exception.ExcelDataConvertException;
import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.metadata.CellExtra;
import com.alibaba.excel.read.listener.ReadListener;
import com.zhien.common.utils.spring.SpringUtils;
import ${dtoPackage}.${domainName}ImportDTO;
import ${servicePackage}.${domainName}Service;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * ${domainName}导入监听器,不能被Spring管理,需要手动new,每次处理新excel需要new一个新的对象
 */
@Slf4j
@Getter
@Setter
public class ${domainName}ImportListener implements ReadListener<${domainName}ImportDTO> {
    private static final int MAX_TRY_COUNT = 3000;
    private static final int BATCH_COUNT = 1000;
    private List<${domainName}ImportDTO> rows = new ArrayList<>(BATCH_COUNT);
    // 是否继续收集,超出的行数不再业务处理但是EasyExcel会继续读行,才能最后触发`doAfterAllAnalysed`;如果设置`hasNext()`返回false,则不会最终到达`doAfterAllAnalysed`
    private boolean handleThisRow = true;

    private ImportResultDTO importResultDTO;

    public ${domainName}ImportListener(ImportResultDTO importResultDTO) {
        this.importResultDTO = importResultDTO;
    }

    /**
     * 出现异常时不中断,仅记录异常信息,不会进入当前行的invoke方法
     */
    @Override
    public void onException(Exception exception, AnalysisContext context) throws Exception {
        importResultDTO.incrementAndGetFailNum();
        // 如果是格式转换异常
        if (exception instanceof ExcelDataConvertException) {
            ExcelDataConvertException convertException = (ExcelDataConvertException) exception;
            int rowIndex = convertException.getRowIndex();
            int columnIndex = convertException.getColumnIndex();
            String msg = StrUtil.format("第{}行第{}列单元格格式错误", rowIndex + 1, columnIndex + 1);
            importResultDTO.addMessage(msg);
            return;
        }
        // 其他异常
        Integer rowIndex = context.readRowHolder().getRowIndex();
        String msg = StrUtil.format("第{}行数据转换异常:{}", rowIndex, exception.getMessage());
        importResultDTO.addMessage(msg);
    }

    @Override
    public void invokeHead(Map<Integer, CellData> headMap, AnalysisContext context) {
        // log.debug("invokeHead:{}", headMap);
    }

    @Override
    public void invoke(${domainName}ImportDTO data, AnalysisContext context) {
        Integer rowIndex = context.readRowHolder().getRowIndex();
        try {
            // (1)如果前三个空格都是空,则认为是空行(EasyExcel不认为是空行,所以需要自己判断)
            // TODO 需要改写为自己的业务逻辑
            if (StrUtil.isBlank(data.getName()) && StrUtil.isBlank(data.getName()) && data.getName() == null) {
                handleThisRow = false;
            } else {
                // 恢复上一行可能设置的不处理标记
                handleThisRow = true;
            }
            // (2)超出最大尝试读取行数,则后面的数据不再进行业务处理
            if (rowIndex + 1 > MAX_TRY_COUNT) {
                handleThisRow = false;
            }
            // (3)如果已经处理的行数超过了规定的批量处理行数,则后面的数据不再进行业务处理
            if (rows.size() > BATCH_COUNT) {
                handleThisRow = false;
            }

            if (!handleThisRow) {
                importResultDTO.incrementAndGetIgnoreNum();
                // 当前行不进行业务处理,会继续读行
                return;
            }
            // 计数用
            rows.add(data);

            // 如果字段类型是String,则需要自己处理空字符串的情况
            for (Field declaredField : data.getClass().getDeclaredFields()) {
                declaredField.setAccessible(true);
                Object value = declaredField.get(data);
                if (value instanceof String) {
                    String newVal = Optional.ofNullable(value)
                            .map(Convert::toStr)
                            .filter(StrUtil::isNotBlank)
                            .map(StrUtil::trim)
                            .filter(StrUtil::isNotBlank)
                            .map(tmp -> StrUtil.removeAll(tmp, ' '))
                            .filter(StrUtil::isNotBlank)
                            .orElse(null);
                    declaredField.set(data, newVal);
                }
            }

            // 行号
            data.setRowNum(rowIndex + 1);

            // javaxValidation校验
            javaxValidationVerifyWithThrow(data);

            // 业务处理
            SpringUtils.getBean(${domainName}Service.class).handleSingleExcelRow(data, importResultDTO);
            importResultDTO.incrementAndGetSuccessNum();
        } catch (Exception e) {
            importResultDTO.incrementAndGetFailNum();
            String msg = StrUtil.format("第{}行数据:{}", rowIndex + 1, e.getMessage());
            importResultDTO.addMessage(msg);
        }
    }

    @Override
    public void extra(CellExtra extra, AnalysisContext context) {
        // log.debug("extra:{}", extra);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        importResultDTO.calculateTotalNum();
    }

    @Override
    public boolean hasNext(AnalysisContext context) {
        return true;
    }

    private void javaxValidationVerifyWithThrow(${domainName}ImportDTO data) {
        Set<ConstraintViolation<${domainName}ImportDTO>> violations = SpringUtils.getBean(Validator.class).validate(data);
        if (!violations.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            for (ConstraintViolation<${domainName}ImportDTO> violation : violations) {
                sb.append(violation.getMessage()).append(";");
            }
            throw new RuntimeException(sb.toString());
        }
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ImportResultDTO {
        private String fileName;
        @Builder.Default
        private Integer successNum = 0;
        @Builder.Default
        private Integer failNum = 0;
        @Builder.Default
        private Integer ignoreNum = 0;
        @Builder.Default
        private Integer totalNum = 0;
        @Builder.Default
        private List<String> messages = new ArrayList<>();

        public Integer incrementAndGetSuccessNum() {
            return ++successNum;
        }

        public Integer incrementAndGetFailNum() {
            return ++failNum;
        }

        public Integer incrementAndGetIgnoreNum() {
            return ++ignoreNum;
        }

        public Integer calculateTotalNum() {
            totalNum = successNum + failNum + ignoreNum;
            return totalNum;
        }

        public void addMessage(String message) {
            this.messages.add(message);
        }
    }
}
