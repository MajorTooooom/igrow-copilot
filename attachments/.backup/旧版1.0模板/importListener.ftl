package ${listenerPackage};

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.exception.ExcelDataConvertException;
import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.metadata.CellExtra;
import com.alibaba.excel.read.listener.ReadListener;
import com.zhien.common.utils.spring.SpringUtils;
import ${dtoPackage}.${domainName}ImportDTO;
import com.zhien.igrow.service.${domainName}Service;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 导入监听器
 * <p>`Listener`不能被spring管理,每次读取excel都要重新new,如果要用到Spring中的Bean,请通过构造方法或者其他方式导入;</p>
 */
@Slf4j
@Data
public class ${domainName}ImportListener implements ReadListener<${domainName}ImportDTO> {
    // 约定单次导入最多尝试获取N行
    private static final int MAX_TRY_COUNT = 10000;
    // 约定单次导入最多处理N行有效数据
    private static final int BATCH_COUNT = 5000;
    // 收集全部的数据行
    private List<${domainName}ImportDTO> rows = new ArrayList<>(BATCH_COUNT);
    // 是否继续读取,当遇到一些行数据不符合要求时,可以设置为false,终止读取
    private boolean continueReading = true;
    // 反馈信息收集
    private String totalMsg;

    @Override
    public void onException(Exception exception, AnalysisContext context) throws Exception {
        // 捕获读取过程中的异常
        if (exception instanceof ExcelDataConvertException) {
            ExcelDataConvertException convertException = (ExcelDataConvertException) exception;
            // 可以获取到具体出错的行和列
            int rowIndex = convertException.getRowIndex();
            int columnIndex = convertException.getColumnIndex();
            // System.err.println("Error converting data at row " + rowIndex + ", column " + columnIndex + ": " + exception.getMessage());
        } else {
            // System.err.println("Unexpected error: " + exception.getMessage());
        }
        throw exception;
    }

    /**
     * 当解析到一个表头行时，会触发此方法。`headMap` 包含了表头的信息，键是列的索引，值是单元格数据。
     */
    @Override
    public void invokeHead(Map<Integer, CellData> headMap, AnalysisContext context) {
        // Integer rowIndex = context.readRowHolder().getRowIndex();
        // System.out.println("表头rowIndex=" + rowIndex);
        // 可选:可以校验是否有表头字段缺失
    }

    /**
     * 每读取到一行数据时，都会调用此方法。`data` 是根据 Excel 行内容转换成的 Java 对象。可以在此方法中处理或存储每行的数据。
     */
    @Override
    public void invoke(${domainName}ImportDTO data, AnalysisContext context) {
        Integer rowIndex = context.readRowHolder().getRowIndex();
        // (1)防止Excel的空行被认为是数据行
        if (rowIndex > MAX_TRY_COUNT) {
            continueReading = false;
            return;
        }
        // (2)如果超过5000行有效数据,则不再继续读取
        if (rows.size() > BATCH_COUNT) {
            continueReading = false;
            return;
        }
        // 两种方式:(1)收集到单条之后马上入库处理;(2)这里先收集,后面统一处理
        // 这里演示第二种方式,在`doAfterAllAnalysed`中调用,或者在当前对象的调用处`listener.getRows()`
        data.setRowNum(rowIndex);
        rows.add(data);
    }

    @Override
    public void extra(CellExtra extra, AnalysisContext context) {
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        // log.info("读取到的数据行数:{}", rows.size());
        SpringUtils.getBean(${domainName}Service.class).importRows(rows);
    }

    @Override
    public boolean hasNext(AnalysisContext context) {
        return continueReading;
    }
}
