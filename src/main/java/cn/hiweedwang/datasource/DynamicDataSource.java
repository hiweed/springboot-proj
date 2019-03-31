package cn.hiweedwang.datasource;

public class DynamicDataSource extends AbstractRoutingLazyRegisterDataSource {    @Override
    protected Object determineCurrentLookupKey() {
        return DynamicDataSourceContextHolder.getDataSourceId();
    }
}
