package com.whsxt.anno;

import java.lang.annotation.*;

/**
 * @Author 武汉尚学堂
 * 做切面
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface Log {

    // 操作的名称
    String operation() default "";

}
