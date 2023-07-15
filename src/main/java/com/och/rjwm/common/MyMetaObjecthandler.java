package com.och.rjwm.common;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@Component
@Slf4j
public class MyMetaObjecthandler implements MetaObjectHandler {
    @Autowired
    HttpServletRequest request;
    @Override
    public void insertFill(MetaObject metaObject) {
        log.info("公共字段自动填充[INSERT]");
        log.info(metaObject.toString());
        metaObject.setValue("createTime", LocalDateTime.now());
        metaObject.setValue("updateTime", LocalDateTime.now());
        // 如何获取session.
        String employeeID = (String)request.getSession().getAttribute("employee");
        if(null == employeeID|| StringUtils.isBlank(employeeID)){
            employeeID = Long.toString((Long) request.getSession().getAttribute("user"));
        }
        metaObject.setValue("createUser", employeeID);
        metaObject.setValue("updateUser", employeeID);

    }

    @Override
    public void updateFill(MetaObject metaObject) {
        log.info("公共字段自动填充[INSERT]");
        log.info(metaObject.toString());
        metaObject.setValue("updateTime", LocalDateTime.now());
        metaObject.setValue("updateUser", "10");


    }
}
