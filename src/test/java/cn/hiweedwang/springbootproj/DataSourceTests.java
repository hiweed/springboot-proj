package cn.hiweedwang.springbootproj;

import cn.hiweedwang.datasource.DynamicDataSource;
import cn.hiweedwang.datasource.DynamicDataSourceContextHolder;
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

    @Test
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
}
