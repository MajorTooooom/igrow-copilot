package ${mapperPackage};

import com.zhien.common.core.dao.BaseMapper;
import ${domainPackage}.${domainName};
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ${domainName}Mapper extends BaseMapper<${domainName}> {
}