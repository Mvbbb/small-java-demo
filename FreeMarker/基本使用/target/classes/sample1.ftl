<#--freemarker取值-->
${site}
${url}
${author!"不存在的属性"}
${date?string("yyyy-MM-dd")}
${number}
${number?string("0.00")}
SN: ${computer.sn}
型号: ${computer.model}

<#if computer.status==1>
状态: 正在使用
<#elseif computer.status==2>
状态: 闲置
<#elseif computer.status==3>
状态: 作废
</#if>

<#--??判断对象是否为空, 不为空执行内部语句-->
<#if computer.user??>
用户: ${computer.user}
</#if>

采购时间: ${computer.dop?string("yyyy-MM-dd")}
采购价格: ${computer.price?string("0.00")}
---------
CPU: ${computer.info["CPU"]!"未知"}
内存: ${computer.info["Memory"]!"未知"}

<#--Switch-->
<#switch computer.status>
    <#case 1>
        状态: 正在使用
        <#break >
    <#case 2>
        状态: 闲置
         <#break >
    <#case 3>
        状态: 作废
    <   #break >
    <#default>
        状态: 无效状态
         <#break >
</#switch>