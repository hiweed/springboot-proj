package cn.hiweedwang.springbootproj.config;

import cn.hiweedwang.datasource.DynamicDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class DymicMybatisSessionFactoryConfig {

    @Autowired
    private Environment env;

    @Bean
    @Autowired
    public SqlSessionFactory sqlSessionFactory(DynamicDataSource ds) throws Exception{
        SqlSessionFactoryBean sqlSFB = new SqlSessionFactoryBean();
        //指定自定义的数据源,这个必须用,否则报错
        sqlSFB.setDataSource(ds);

        //指定mybatis的本地配置文件资源,目的是定义实体等别名,可以不用,如果不用对应配置文件应注释掉
        String strCfgLocation = env.getProperty("mybatis.configLocations");
        if(strCfgLocation!=null&&!strCfgLocation.isEmpty()){
            Resource configLocationResource =
                    new PathMatchingResourcePatternResolver()
                            .getResource(strCfgLocation);
            sqlSFB.setConfigLocation(configLocationResource);
        }

        //指定对应的实体包,多个包之间逗号隔开
        String strTypeAliasesPackage = env.getProperty("mybatis.typeAliasesPackage");
        if(strTypeAliasesPackage!=null&&!strTypeAliasesPackage.isEmpty()){
            sqlSFB.setTypeAliasesPackage(strTypeAliasesPackage);
        }

        //指定mybatis的库表到实体的映射xml文件的mapper资源
        String strmapperLocations = env.getProperty("mybatis.mapperLocations");
        if(strmapperLocations!=null&&!strmapperLocations.isEmpty()){
            Resource[] mapperLocations =
                    new PathMatchingResourcePatternResolver()
                            .getResources(strmapperLocations);
            sqlSFB.setMapperLocations(mapperLocations);
        }
        return sqlSFB.getObject();
    }

    /**
     * 自动事务管理器的配置
     * @param dataSource
     * @return
     */
    @Bean
    @Autowired
    public PlatformTransactionManager bfTransactionManager(DynamicDataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }
}
