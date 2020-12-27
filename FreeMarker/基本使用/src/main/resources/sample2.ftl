==============================
list结构的遍历
<#list computers as c>
序号: ${c_index}  <#--迭代变量 c_index保存了循环的索引, 从0开始-->
SN:${c.sn}
型号: ${c.model}
<#switch c.status>
<#case 1>
状态: 正在使用
<#break >
<#case 2>
状态: 闲置
<#break >
<#case 3>
状态: 作废
<#break >
<#default>
状态: 无效状态
<#break >
</#switch>
<#if c.user??>
用户: ${c.user}
</#if>
采购日期: ${c.dop?string("dd.MM.yyyy HH:mm:ss")}
采购价格: ${c.price}
    
</#list>

======================
Map 结构的遍历与输出
<#list computerMap?keys as k>
${k}-${computerMap[k].model}
</#list>