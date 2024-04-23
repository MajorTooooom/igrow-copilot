package ${mapperPackage};

import com.zhien.common.core.dao.BaseMapper;
import ${domainPackage}.${domainName};
import ${dtoPackage}.${domainName}ExportDTO;
import ${dtoPackage}.${domainName}PageDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ${domainName}Mapper extends BaseMapper<${domainName}> {
    // (1)条件分页查询(2)导出(3)基于条件分页查询的统计
    List<${domainName}ExportDTO> findAllByPageCondition(@Param("majorRecord") ${domainName}PageDTO ${domainName?uncap_first}PageDTO);
${r'}'}