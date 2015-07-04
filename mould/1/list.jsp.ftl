<!--
	li limingwei@mail.com
	${now?string('yyyy-MM-dd HH:mm:ss')}
-->

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String contextPath = request.getContextPath();
%>
<!DOCTYPE html>
<html>
<head>
	<title>${entity.title}-结果集页面</title>
	<jsp:include page="/inc_base.jsp"/>
	<script type="text/javascript" src="/js/upsss/${entity.url}/list.js?<%=Math.random()%>"></script>
</head>
<body class="easyui-layout" data-options="fit:true,border:false">
	<div id="toolbar" style="display:none; ">
		<table>
			<tr>
				<td>
					<form id="searchForm">
						<table>
							<tr>
								<td>
									<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'ext-icon-zoom',plain:true" onclick="$grid.datagrid('load',uls.serializeObject($('#searchForm')));">查询</a>
									<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'ext-icon-zoom_out',plain:true" onclick="$('#searchForm input').val('');$grid.datagrid('load',{});">刷新</a>
								</td>
							</tr>
						</table>
					</form>
				</td>
			</tr>
			<tr>
				<td>
					<table>
						<tr>
							<td>
								<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-standard-add',plain:true" title="添加" onclick="upsss.${entity.url}.add();"></a>
							</td>
							<td>
								<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-standard-cancel',plain:true" title="删除" onclick="upsss.${entity.url}.del();"></a>
							</td>
							<td>
								<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-hamburg-pencil',plain:true" title="编辑" onclick="upsss.${entity.url}.edit();"></a>
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
	</div>
	<div data-options="region:'center',fit:true,border:false">
		<table id="grid" data-options="fit:true,border:false"/>
	</div>
</body>
</html>