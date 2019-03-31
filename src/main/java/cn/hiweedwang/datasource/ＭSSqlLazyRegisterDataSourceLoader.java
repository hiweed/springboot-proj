package cn.hiweedwang.datasource;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import javax.sql.DataSource;

@Configuration
@PropertySource(value = "classpath:mssql-datasource.properties")
public class ＭSSqlLazyRegisterDataSourceLoader
                        implements LazyRegisterDataSourceLoader {

    @Value(value="${datasource.url}")
    private String url;

    @Value(value="${datasource.username}")
    private String username;

    @Value(value="${datasource.password}")
    private String password;

    @Value(value="${datasource.driver-class-Name}")
    private String driverClassName;
    /**
     * 数据源里的可替换参数
     */
    @Value(value = "${datasource.params.db}")
    private String defalutDb;

    public void setEvn(Environment evn) {
        this.evn = evn;
    }

    @Autowired
    private Environment evn;

    @Override
    public DataSource registerDefaultDataSource() {
        return this.registerDataSource(this.defalutDb);
    }

    @Override
    public DataSource registerTargetDataSource(Object lookupKey) {
        return this.registerDataSource(lookupKey.toString());
    }

    /**
     *注册数据源连接池
     * @param dataBaseName 连接池的数据库名
     * @return
     */
    private DataSource registerDataSource(String dataBaseName){
        HikariConfig config = new HikariConfig();
        config.setDriverClassName(this.driverClassName);
        config.setUsername(this.username);
        config.setPassword(this.password);
        //在这里替换库
        config.setJdbcUrl(this.url.replace("{db}",dataBaseName));

        /*其余参数依据需求设置
        config.setMaximumPoolSize();
        **/

        /*mysql驱动的优化参数这里设置
        config.addDataSourceProperty();
        **/
        HikariDataSource ds = new HikariDataSource(config);
        return ds;
    }

    /**
     * 在这里生成bean避免了侵入DynamicDataSource
     * @return
     */
    public DynamicDataSource getDataSource(){
        DynamicDataSource ds = new DynamicDataSource();
        ds.setLoader(this);
        return ds;
    }
}
