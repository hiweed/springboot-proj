package cn.hiweedwang.springbootproj;

import cn.hiweedwang.datasource.DynamicDataSource;
import cn.hiweedwang.datasource.DynamicDataSourceContextHolder;
import org.apache.ibatis.jdbc.SQL;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DataSourceTests {

    @Autowired
    private DynamicDataSource dataSource;

    @Autowired
    private SqlSessionFactory session;

    //@Test
    public void testSQL(){
        try{
          Connection conn =  dataSource.getConnection();
          PreparedStatement stmt = conn.prepareStatement("SELECT USERNAME FROM user");
            ResultSet rs = stmt.executeQuery();
            if (rs.next()){
                System.out.println(rs.getString("username"));
            }
            //切换数据源
            DynamicDataSourceContextHolder.setDataSourceId("demo2");
            conn =  dataSource.getConnection();
            stmt = conn.prepareStatement("SELECT USERNAME FROM user");
            rs = stmt.executeQuery();
            if (rs.next()){
                System.out.println(rs.getString("username"));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    //@Test
    public void testSQLSession(){
        try{
            DynamicDataSourceContextHolder.setDataSourceId("demo");
            Connection conn =  session.openSession().getConnection();
            //动态构建sql语句
            SQL sql = new SQL().SELECT("USERNAME,COUNT(*)").FROM("user");
            String strSql = sql.toString();
            PreparedStatement stmt = conn.prepareStatement(strSql);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()){
                System.out.println(rs.getString("username"));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 测试SQLSERVER数据源
     */
    @Test
    public void testMSSQLSession(){
        try{
            DynamicDataSourceContextHolder.setDataSourceId("JZZF2013_XH_2019YEAR");
            Connection conn =  session.openSession().getConnection();
            //动态构建sql语句
            SQL sql = new SQL().SELECT("CODE,NAME").FROM("ZF1USER01");
            String strSql = sql.toString();
            PreparedStatement stmt = conn.prepareStatement(strSql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()){
                System.out.println(rs.getString("CODE")+":"
                        +rs.getString("NAME"));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
