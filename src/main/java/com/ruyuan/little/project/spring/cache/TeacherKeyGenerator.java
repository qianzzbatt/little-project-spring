package com.ruyuan.little.project.spring.cache;

import com.ruyuan.little.project.spring.dto.Teacher;
import com.ruyuan.little.project.spring.service.TeacherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;

import static com.ruyuan.little.project.spring.constants.StringPoolConstant.DASH;

/**
 * @author <a href="mailto:little@163.com">little</a>
 * version: 1.0
 * Description:spring实战
 **/
public class TeacherKeyGenerator implements KeyGenerator {

    private static final Logger LOGGER = LoggerFactory.getLogger(TeacherKeyGenerator.class);

    private static final String CACHE_METHOD_NAME = "getPage";
    private static final int CACHE_METHOD_PARAM_LENGTH = 3;

    @Override
    public Object generate(Object o, Method method, Object... objects) {
        // 根据Teacher查询教师列表，objects[0]为teacher参数，objects[1]为orderColumn参数，objects[2]为orderType参数
        if (o instanceof TeacherService && CACHE_METHOD_NAME.equals(method.getName())
                && objects.length == CACHE_METHOD_PARAM_LENGTH && objects[0] instanceof Teacher) {
            LOGGER.info("根据教师类型和状态查询教师列表缓存KeyGenerator");
            String key = o.getClass().getSimpleName() + DASH + method.getName() + DASH +
                    ((Teacher) objects[0]).getTeacherName() + DASH + ((Teacher) objects[0]).getCourse() + DASH +
                    ((Teacher) objects[0]).getStatus() + DASH + ((Teacher) objects[0]).getStartIndex() + DASH +
                    ((Teacher) objects[0]).getEndIndex() + DASH + objects[1] + DASH + objects[2];
            LOGGER.info("生成的缓存key:{}", key);
            return key;
        }
        String key = o.getClass().getSimpleName() + DASH + method.getName() +
                DASH + StringUtils.arrayToDelimitedString(objects, DASH);
        LOGGER.info("生成的缓存key:{}", key);
        return key;
    }
}