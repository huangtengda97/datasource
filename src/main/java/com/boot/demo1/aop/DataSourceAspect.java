package com.boot.demo1.aop;

import com.boot.demo1.datasource.DataSource;
import com.boot.demo1.datasource.DynamicDataSourceHolder;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class DataSourceAspect implements Ordered {

    @Before(value = "@annotation(dataSource)")
    public void dataSourcePoint(JoinPoint point, DataSource dataSource) {
//        Class<?> className = point.getTarget().getClass();
//        //获得访问的方法名
//        String methodName = point.getSignature().getName();
//        //得到方法的参数的类型
//        Class[] argClass = ((MethodSignature)point.getSignature()).getParameterTypes();
//        //默认数据源
//        try {
//            // 得到访问的方法对象
//            Method method = className.getMethod(methodName, argClass);
//            // 判断是否存在@注解
//            if (method.isAnnotationPresent(DataSource.class)) {
//                DataSource annotation = method.getAnnotation(DataSource.class);
//                // 取出注解中的数据源名
//                DynamicDataSourceHolder.putDataSource(annotation.value());
//            } else {
//                DynamicDataSourceHolder.putDataSource(DataSourceType.MASTER);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        DynamicDataSourceHolder.putDataSource(dataSource.value());
    }

    @Override
    public int getOrder() {
        return -1;
    }

//    @After(value = "@annotation(DataSource)")
//    public void afterSwitchDS(JoinPoint point) {
//        DynamicDataSourceHolder.clearDB();
//    }
}
