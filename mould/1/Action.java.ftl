package com.unblocked.upsss.action;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.unblocked.support.web.spring.EntityViewPath;
import com.unblocked.upsss.base.BaseAction;
import com.unblocked.upsss.model.Upsss${entity.type};

/**
 * ${entity.title} - 控制层
 * 
 * @author li (limingwei@mail.com)
 * @version 1 (${now?string('yyyy-MM-dd HH:mm:ss')})
 */
@Controller
@EntityViewPath("view_jsp_back/${entity.url}")
public class ${entity.type}Action extends BaseAction<Upsss${entity.type}> {
	@RequestMapping("${entity.url}_add")
    public String add() {
        return super.add();
    }

    @ResponseBody
    @RequestMapping("${entity.url}_delete")
    public void delete(String id) {
        super.delete(id);
    }

    @RequestMapping("${entity.url}_edit")
    public String edit(Integer id) {
        return super.edit(id);
    }

    @RequestMapping("${entity.url}_list")
    public String list() {
        return super.list();
    }

    @ResponseBody
    @RequestMapping("${entity.url}_list_data")
    public void listData() {
        super.listData();
    }

    @ResponseBody
    @RequestMapping("${entity.url}_save")
    public void save(Upsss${entity.type} entity) {
        super.save(entity);
    }

    @ResponseBody
    @RequestMapping("${entity.url}_update")
    public void update(Upsss${entity.type} entity) {
        super.update(entity);
    }	
}