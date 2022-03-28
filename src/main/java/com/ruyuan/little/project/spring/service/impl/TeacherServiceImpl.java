package com.ruyuan.little.project.spring.service.impl;

import com.ruyuan.little.project.spring.dto.Teacher;
import com.ruyuan.little.project.spring.enums.CourseTypeEnum;
import com.ruyuan.little.project.spring.mapper.TeacherMapper;
import com.ruyuan.little.project.spring.service.TeacherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author <a href="mailto:little@163.com">little</a>
 * version: 1.0
 * Description:教师service组件实现类
 **/
@Service
public class TeacherServiceImpl implements TeacherService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TeacherServiceImpl.class);

    /**
     * 教师mapper组件
     */
    @Resource
    private TeacherMapper teacherMapper;

    /**
     * 查询教师列表
     *
     * @param teacher 查询条件
     * @param orderColumn 排序字段
     * @param orderType 排序类型 asc desc  ,
     *             condition = "#teacher.page<=2 && #teacher.teacherName.empty"
     * @return 教师列表
     */
    @Override
    @Cacheable(value = "teacher",keyGenerator = "teacherKeyGenerator")
    public List<Teacher> getPage(Teacher teacher, String orderColumn, String orderType) {
        LOGGER.info("通过数据库查询教师列表");
        List<Teacher> teacherList = teacherMapper.getPage(teacher,orderColumn,orderType);
        if (!CollectionUtils.isEmpty(teacherList)){
            teacherList.forEach(teacher1 -> teacher1.setCourseName(CourseTypeEnum.getNameByCode(teacher1.getCourse())));
        }
        return teacherList;
    }

    @Override
    public Teacher findById(int id) {
        Teacher teacher = teacherMapper.findById(id);
        teacher.setCourseName(CourseTypeEnum.getNameByCode(teacher.getCourse()));
        return teacher;
    }

    @Override
    public List<Teacher> selectAll() {
        List<Teacher> teacherList = teacherMapper.selectAll();
        if (!CollectionUtils.isEmpty(teacherList)){
            teacherList.forEach(teacher -> teacher.setCourseName(CourseTypeEnum.getNameByCode(teacher.getCourse())));
        }
        return teacherList;
    }

    @Override
    public int getPageTotal(Teacher teacher) {
        return teacherMapper.getPageTotal(teacher);
    }

    @Override
    public int updateTeacherList(List<Teacher> teacherList) {
        return teacherMapper.updateTeacherList(teacherList);
    }
}