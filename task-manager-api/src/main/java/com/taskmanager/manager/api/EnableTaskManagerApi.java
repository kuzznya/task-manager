package com.taskmanager.manager.api;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Import(TaskManagerApiConfig.class)
public @interface EnableTaskManagerApi {
}
