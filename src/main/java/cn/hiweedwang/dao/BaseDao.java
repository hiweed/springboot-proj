package cn.hiweedwang.dao;

import cn.hiweedwang.datasource.DynamicDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * 公开给service层的数据库访问对象本质上所有的数据源均来自DynamicDataSource
 * 只是sessionFactory得访问可由事务管理器自动进行事务管理，
 * datasource的只是临时取一个连接，不必干预线程上绑定的连接池id对象
 */
@Component
public class BaseDao {

    @Autowired
    private SqlSessionFactory sessionFactory;

    @Autowired
    private DynamicDataSource dataSource;

    /**
     * 获取数据库连接(带有自动化事务管理)
     * @return 数据库连接
     */
    public Connection getConnetion() {
        return sessionFactory.openSession().getConnection();
    }

    /**
     * 获取数据库连接(与事务管理器无关，需要自己管理)
     * @return 数据库连接
     */
    public Connection getConnetion(String dataBaseName) throws SQLException {
        return dataSource.getConnectionByLookUpKey(dataBaseName);
    }
}
