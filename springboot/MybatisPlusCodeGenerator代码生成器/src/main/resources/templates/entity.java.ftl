package ${package.Entity};

import java.io.Serializable;
import java.util.Date;
import lombok.Data;
import lombok.ToString;
<#list table.importPackages as pkg>
<#if pkg == "java.util.Date">
import ${pkg};
</#if>
</#list>

/**
*@program: ${cfg.PROJECT_NAME}
*@description:  ${table.name} : ${table.comment!}
*@author: Mvbbb
*@create: ${cfg.TIME}
*/
@Data
@ToString
public class ${entity}  implements Serializable {

	private static final long serialVersionUID = 1L;
<#-- ----------  属性私有化  ---------->
<#list table.fields as field>
    <#if field.keyFlag>
        <#assign keyPropertyName="${field.propertyName}"/>
    </#if>

    <#if field.keyFlag>
    <#-- 主键 -->
    /**
     * 主键 : ${field.name},  ${field.comment!}
     */
    <#-- 普通字段 -->
    <#elseif !field.keyFlag>
    /**
     * ${field.name},  ${field.comment!}
     */
    </#if>
<#-- 乐观锁注解 -->
    <#if (versionFieldName!"") == field.name>
    @Version
    </#if>
<#-- 逻辑删除注解 -->
    <#if (logicDeleteFieldName!"") == field.name>
    @TableLogic
    </#if>
    <#if field.propertyType == "LocalDateTime" || field.propertyType == "LocalDate">
    private Date ${field.propertyName};
    </#if>
    <#if field.propertyType != "LocalDateTime" && field.propertyType != "LocalDate">
    private ${field.propertyType} ${field.propertyName};
    </#if>
</#list>
}
