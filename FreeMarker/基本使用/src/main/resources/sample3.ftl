<#--首字母大写-->
${name?cap_first}
<#--全部变为大写-->
${brand?upper_case}
<#--字符长度-->
${brand?length}
<#--单词替换-->
${words?replace("blood","****")}
<#--判断包含,出现位置 -1表示不存在-->
${words?index_of("blood")}
${(words?index_of("blood")!=-1)?string("包含敏感词汇","不包含敏感词汇")}
<#--四舍五入-->
${number?round}
<#--下取整-->
${number?floor}
<#--上取整-->
${number?ceiling}

公司共有电脑 ${computers?size}
第一台电脑: ${computers?first.model}
最后一台: ${computers?last.model}

集合排序
<#list computers?sort_by("price")?reverse as c>
${c.sn}-${c.price}
</#list>