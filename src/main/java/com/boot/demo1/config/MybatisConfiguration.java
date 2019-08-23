package com.boot.demo1.config;

import com.boot.demo1.datasource.DataSourceType;
import com.boot.demo1.datasource.MyDynamicDataSource;
import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Configuration
@ConditionalOnClass({EnableTransactionManagement.class})
public class MybatisConfiguration {
    @Value("${spring.readSize}")
    private String dataSourceSize;
    @Resource(name = "masterDateSource")
    private HikariDataSource dataSource;
    @Resource(name = "slave1DataSource")
    private HikariDataSource slave1DataSource;
    @Resource(name = "slave2DataSource")
    private HikariDataSource slave2DataSource;
    List<HikariDataSource> readDataSources;

    @Bean
    @ConditionalOnMissingBean
    public SqlSessionFactory sqlSessionFactory() throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(roundRobinDataSouceProxy());
        return sqlSessionFactoryBean.getObject();
    }
    /**
     * 设置默认数据库,其他数据源
     * @return
     */
    @Bean(name = "dynamicDataSource")
    public AbstractRoutingDataSource roundRobinDataSouceProxy() {
        int size = Integer.parseInt(dataSourceSize);
        MyDynamicDataSource proxy = new MyDynamicDataSource(size);
        Map<Object, Object> targetDataSources = new HashMap<Object, Object>();
        // DataSource writeDataSource = SpringContextHolder.getBean("writeDataSource");
        // 写
        targetDataSources.put(DataSourceType.MASTER.getType(),dataSource);
        // targetDataSources.put(DataSourceType.read.getType(),readDataSource);
        //多个读数据库时
        readDataSources=new ArrayList<HikariDataSource>();
        readDataSources.add(slave1DataSource);
        readDataSources.add(slave2DataSource);
        for (int i = 0; i < size; i++) {
            targetDataSources.put(i, readDataSources.get(i));
        }
        proxy.setDefaultTargetDataSource(dataSource);
        proxy.setTargetDataSources(targetDataSources);
        return proxy;
    }
}