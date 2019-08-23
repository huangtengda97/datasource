package com.boot.demo1.datasource;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import java.util.concurrent.atomic.AtomicInteger;

public class MyDynamicDataSource extends AbstractRoutingDataSource {

    //配置的读库数量
    private final int dataSourceNumber;
    private AtomicInteger count = new AtomicInteger(0);
    /**
     * @dataSourceNumber  从库的数量
     **/
    public MyDynamicDataSource(int dataSourceNumber) {
        this.dataSourceNumber = dataSourceNumber;
    }

    @Override
    protected Object determineCurrentLookupKey() {
        String type = DynamicDataSourceHolder.getDataSource();
        if (type.equals(DataSourceType.MASTER.getType())) {
            return DataSourceType.MASTER.getType();
        }
        //从库     读 简单负载均衡
        int number = count.getAndAdd(1);
        int lookupKey = number % dataSourceNumber;
        return new Integer(lookupKey);
    }
}
