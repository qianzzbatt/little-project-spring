package com.ruyuan.little.project.spring.controller;

import com.ruyuan.little.project.common.dto.CommonResponse;
import com.ruyuan.little.project.common.dto.TableData;
import com.ruyuan.little.project.spring.dto.Teacher;
import com.ruyuan.little.project.spring.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/teacher")
public class TeacherController {

    /**
     * 教师service组件
     */
    @Autowired
    private TeacherService teacherService;

    /**
     * 根据条件查询教师列表
     *
     * @param teacher 教师
     * @return 结果
     */
    @PostMapping("/getPage")
    public CommonResponse getPage(Teacher teacher, String orderColumn, String orderType) {
        // 根据条件查询总条数
        int total = teacherService.getPageTotal(teacher);

        // 封装响应数据表格
        CommonResponse<TableData<Teacher>> commonResponse = new CommonResponse<>();
        TableData<Teacher> tableData = new TableData<>();
        tableData.setTotal((long) total);
        if (total != 0) {
            // 总条数不为空，查询教师列表
            List<Teacher> teacherList = teacherService.getPage(teacher,orderColumn,orderType);
            tableData.setRows(teacherList);
        }
        commonResponse.setData(tableData);
        return commonResponse;
    }

    /**
     * 根据教师id查询教师信息
     *
     * @param id 教师id
     * @return 教师信息
     */
    @GetMapping("/{id}")
    public CommonResponse findById(@PathVariable String id) {
        return CommonResponse.success(teacherService.findById(Integer.parseInt(id)));
    }

}