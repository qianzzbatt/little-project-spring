package com.ruyuan.little.project.spring.service;

import com.ruyuan.little.project.spring.dto.Teacher;

import java.util.List;

/**
 * @author <a href="mailto:little@163.com">little</a>
 * version: 1.0
 * Description:教师service组件
 **/
public interface TeacherService {


    /**
     * 查询教师列表
     *
     * @param teacher 查询条件
     * @param orderColumn 排序字段
     * @param orderType 排序类型 asc desc
     * @return 教师列表
     */
    List<Teacher> getPage(Teacher teacher, String orderColumn, String orderType);

    /**
     * 根据教师id获取教师详情
     *
     * @param id 教师id
     * @return 教师详情
     */
    Teacher findById(int id);

    /**
     * 所有教师列表
     *
     * @return 返回结果
     */
    List<Teacher> selectAll();

    /**
     * 查询教师列表总数
     *
     * @param teacher 教师信息
     * @return 查询结果
     */
    int getPageTotal(Teacher teacher);

    /**
     * 修改教师信息
     *
     * @param teacherList 教师列表
     * @return 操作结果
     */
    int updateTeacherList(List<Teacher> teacherList);
}