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

@Configuration
public class DymicMybatisSessionFactoryConfig {


    @Autowired
    private Environment env;

    @Bean
    @Autowired
    public SqlSessionFactory sqlSessionFactory(DynamicDataSource ds) throws Exception{
        SqlSessionFactoryBean sqlSFB = new SqlSessionFactoryBean();
        sqlSFB.setDataSource(ds);

        if(env.getProperty("mybatis.disableOrm")
                .equalsIgnoreCase("true")){
            return sqlSFB.getObject();
        }
        //指定自定义的数据源,这个必须用,否则报错
        sqlSFB.setTypeAliasesPackage(env.getProperty("mybatis.typeAliasesPackage"));
        //指定对应的实体包,多个包之间逗号隔开
        Resource configLocationResource =
                new PathMatchingResourcePatternResolver()
                        .getResource(env.getProperty("mybatis.configLocations"));
        sqlSFB.setConfigLocation(configLocationResource);
        //指定mybatis的本地配置文件资源,目的是定义实体等别名,可以不用,如果不用对应配置文件应注释掉
        Resource[] mapperLocations =
                new PathMatchingResourcePatternResolver()
                        .getResources(env.getProperty("mybatis.mapperLocations"));
        sqlSFB.setMapperLocations(mapperLocations);
        //指定mybatis的库表到实体的映射xml文件的mapper资源
        return sqlSFB.getObject();
    }
}
