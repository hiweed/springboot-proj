package cn.hiweedwang.datasource;

import javax.sql.DataSource;

/**
 * @author hiweed
 * 用于在请求多数据源的连接池时的动态注册
 */
public interface LazyRegisterDataSourceLoader {

    /**
     * 返回默认数据源
     * @return 失败返回 null
     */
   public  DataSource registerDefaultDataSource();

    /**
     * 根据连接池的注册id返回新创建的数据源
     * @param lookupKey 连接池多数据源的数据源id
     * @return 失败返回 null
     */
   public  DataSource registerTargetDataSource(Object lookupKey);
}
