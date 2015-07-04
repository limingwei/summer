package com.unblocked.upsss.dao.impl;

import org.springframework.stereotype.Repository;

import com.unblocked.support.dao.hibernate.LikeField;
import com.unblocked.upsss.base.BaseDaoImpl;
import com.unblocked.upsss.dao.I${entity.type}Dao;
import com.unblocked.upsss.model.Upsss${entity.type};

/**
 * ${entity.title} - 持久化层实现
 * 
 * @author li (limingwei@mail.com)
 * @version 1 (${now?string('yyyy-MM-dd HH:mm:ss')})
 */
@Repository
@LikeField({<#list entity.fields as field> "${field.column?lower_case}"<#if field_index lt entity.fields?size-1>,</#if></#list>})
public class ${entity.type}DaoImpl extends BaseDaoImpl<Upsss${entity.type}> implements I${entity.type}Dao {}