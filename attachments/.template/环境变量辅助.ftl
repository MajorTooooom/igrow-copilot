<#--如果表中文名存在则取中文名否则取表对应的实体类名-->
<#assign domainChineseDescription=((domainZnName?trim!"")?length > 0)?then(domainZnName, domainName)>