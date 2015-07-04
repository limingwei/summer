<!--
	li limingwei@mail.com
	${now?string('yyyy-MM-dd HH:mm:ss')}
-->

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
	<title>${entity.title}-增加编辑公共页面</title>
	<jsp:include page="/inc_base.jsp"/>
</head>
<body>
	<form name="form" method="POST" class="form">
		<input type="hidden" name="${entity.pk.column?lower_case}" value="${'$'}{entity.${entity.pk.column?lower_case}}">
		<fieldset>
			<table class="table" style="width:100%; ">
				<tr>
			<#list entity.fields as field>
				<#if !field.isPk>
					<td>${field.comment?lower_case}:</td>
					<td>
						<input name="${field.column?lower_case}" value="${'$'}{entity.${field.column?lower_case}}" class="easyui-validatebox" data-options="required:true" />
					</td>
			<#if field_index%2 == 0 && field_index lt entity.fields?size-1>
				</tr>
				<tr>
			</#if>
				</#if>
			</#list>
				</tr>
			</table>
		</fieldset>
	</form>
</body>
</html>