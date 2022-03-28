package com.ruyuan.little.project.spring.mapper;

import com.ruyuan.little.project.spring.dto.Comment;

/**
 * @author <a href="mailto:little@163.com">little</a>
 * version: 1.0
 * Description:spring实战
 **/
public interface CommentMapper {
    /**
     * 添加评论
     *
     * @param comment 评论信息
     * @return 操作结果
     */
    int add(Comment comment);
}