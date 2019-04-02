package cn.hiweedwang.springbootproj;


import cn.hiweedwang.beanprocessor.BeanSelfAware;
import cn.hiweedwang.dao.BaseDao;
import cn.hiweedwang.datasource.DynamicDataSourceContextHolder;
import cn.hiweedwang.springbootproj.service.TransactionUseCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TransactionTests{

    @Autowired
    private BaseDao baseDao;

    @Autowired
    private TransactionUseCase other;
    /**
     * 测试SQLSERVER数据源
     */
    @Test
    public void testMSSQLTransation(){
        try{
            //测试事务管理器效果
            DynamicDataSourceContextHolder.setDataSourceId("JZZF2013INFO");
            other.testTransation();

            /**
            Connection conn1 =  baseDao.getConnetion("JZZF2013_XH_2018YEAR");
            Connection conn2 =  baseDao.getConnetion("JZZF2013_XH_2019YEAR");
            //动态构建sql语句
            String strSql = "INSERT INTO BM1KEYINFO(TABLENAME,CODE)VALUES('SFZCTEST',1)";
            PreparedStatement stmt = conn1.prepareStatement(strSql);
            stmt.execute();
            stmt = conn2.prepareStatement(strSql);
            stmt.execute();*/

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
