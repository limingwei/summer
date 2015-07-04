<?xml version="1.0" encoding="utf-8"?>
<!DOCTYPE hibernate-mapping PUBLIC "-//Hibernate/Hibernate Mapping DTD 3.0//EN"
"http://hibernate.sourceforge.net/hibernate-mapping-3.0.dtd">
<!--
	li limingwei@mail.com
	${now?string('yyyy-MM-dd HH:mm:ss')}
-->
<hibernate-mapping>
    <class name="com.unblocked.upsss.model.Upsss${entity.type}" table="${entity.table}" >
    	<id name="${entity.pk.column?lower_case}">
            <generator class="identity" />
        </id>
<#list entity.fields as field>
	<#if !field.isPk>
		<property name="${field.column?lower_case}"/>
	</#if>
</#list>
    </class>
</hibernate-mapping>