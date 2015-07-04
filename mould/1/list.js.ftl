/*
	${entity.title} JS
	li limingwei@mail.com
	${now?string('yyyy-MM-dd HH:mm:ss')}
*/

var $grid,gridconfig = {
        columns : [ [
        {
			field : "ck",
			checkbox : true
		},
		<#list entity.fields as field>
		{
			title : "${field.comment?lower_case}",
			field : "${field.column?lower_case}",
			sortable : true 
		}<#if field_index lt entity.fields?size-1>,</#if>
		</#list>
		] ]
};

bootstrap($grid,"${entity.url}","${entity.title}","${entity.pk.column?lower_case}",gridconfig);

$(function() {
	$grid = upsss[upsss.Base.cfg.module].search();
});