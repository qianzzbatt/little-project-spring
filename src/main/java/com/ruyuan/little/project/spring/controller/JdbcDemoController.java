package com.ruyuan.little.project.spring.controller;

import com.ruyuan.little.project.common.dto.CommonResponse;
import com.ruyuan.little.project.spring.dao.JdbcBaseDao;
import com.ruyuan.little.project.spring.dao.JdbcDruidBaseDao;
import com.ruyuan.little.project.spring.dao.JdbcTemplateBaseDao;
import com.ruyuan.little.project.spring.dto.Teacher;
import com.ruyuan.little.project.spring.expection.BusinessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author huangquan
 * @Description
 * @Date 2022/3/23
 **/
@RestController
public class JdbcDemoController {

    Logger log = LoggerFactory.getLogger(getClass());

    @Resource
    private JdbcBaseDao jdbcBaseDao;

    @Resource
    private JdbcDruidBaseDao jdbcDruidBaseDao;

    @Resource
    private JdbcTemplateBaseDao jdbcTemplateBaseDao;

    @GetMapping("/jdbc/all")
    public CommonResponse GetAll(){
        String sql = "select * from t_teacher";
        Object[] params = null;
        List<Teacher> list = null;
        try {
            list = jdbcBaseDao.queryList(sql, null, Teacher.class);
        } catch (Exception e) {
            throw  new BusinessException("查询教师列表失败");
        }
        return CommonResponse.success(list);
    }

    @GetMapping("/jdbcDruid/all")
    public CommonResponse jdbcDruidGetAll(){
        String sql = "select * from t_teacher";
        Object[] params = null;
        List<Teacher> list = null;
        try {
            list = jdbcDruidBaseDao.queryList(sql, null, Teacher.class);
        } catch (Exception e) {
            throw  new BusinessException("查询教师列表失败");
        }
        return CommonResponse.success(list);
    }

    @GetMapping("/jdbcTemplateBaseDao/all")
    public CommonResponse jdbcTemplateBaseDaoGetAll(){
        String sql = "select * from t_teacher";
        Object[] params = null;
        List list = null;
        try {
            list = jdbcTemplateBaseDao.templateQuery(sql, null);
        } catch (Exception e) {
            throw  new BusinessException("查询教师列表失败");
        }
        return CommonResponse.success(list);
    }


}
