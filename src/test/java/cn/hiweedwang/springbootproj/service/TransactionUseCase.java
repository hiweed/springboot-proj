package cn.hiweedwang.springbootproj.service;

import cn.hiweedwang.beanprocessor.BeanSelfAware;
import cn.hiweedwang.dao.BaseDao;
import cn.hiweedwang.datasource.DynamicDataSourceContextHolder;
import cn.hiweedwang.springbootproj.TransactionTests;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@Component
public class TransactionUseCase implements BeanSelfAware {

    /** 代表自身对象，用于替换this指针，本身是自己的代理，会被aop处理*/
    private TransactionUseCase proxySelf;

    @Autowired
    private BaseDao baseDao;

    @Override
    public void setSelf(Object proxyBean) {
        this.proxySelf = (TransactionUseCase)proxyBean;
    }

    /**
     * 注意看事务隔离级别对切换结果的影响
     */
    @Transactional(rollbackFor = Exception.class)
    public void testTransation(){
        try{
            Connection conn0 =  baseDao.getConnetion();
            PreparedStatement stmtShow = conn0.prepareStatement("select db_name() AS DB");
            ResultSet rs = stmtShow.executeQuery();
            if(rs.next()){
                System.out.println("DB:"+rs.getString(1));
            }
            //切换到新事务，看是否会被执行事务切换
            DynamicDataSourceContextHolder.setDataSourceId("ZFKJ2005INFO");
            //依然在以往事务里，和当前方法共用了一个连接
            proxySelf.beginTransation();
            //自动开启了新连接，独立的事务管理
            proxySelf.beginNewTransation();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 不指定隔离级别依然复用了以往数据库连接（即在一个事务里）
     */
    @Transactional(rollbackFor = Exception.class)
    public void beginTransation() throws  Exception{
        try{
            Connection conn0 =  baseDao.getConnetion();
            PreparedStatement stmtShow = conn0.prepareStatement("select db_name() AS DB");
            ResultSet rs = stmtShow.executeQuery();
            if(rs.next()){
                System.out.println("依然复用了以往连接:"+rs.getString(1));
            }
            PreparedStatement stmtExec =
                    conn0.prepareStatement("insert into BM1PARM(BMKEY,PARM) values('demo','test')");
            stmtExec.execute();
        }catch (Exception e){
            e.printStackTrace();
            throw  e;
        }
    }

    /**
     * 必须开启新事务
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW,rollbackFor = Exception.class)
    public void beginNewTransation() throws  Exception{
        try{
            Connection conn0 =  baseDao.getConnetion();
            PreparedStatement stmtShow = conn0.prepareStatement("select db_name() AS DB");
            ResultSet rs = stmtShow.executeQuery();
            if(rs.next()){
                System.out.println("DBNew:"+rs.getString(1));
            }
            PreparedStatement stmtExec =
                    conn0.prepareStatement("insert into BM1PARM(BMKEY,CODE,PARM) values('demo',1,'test')");
            stmtExec.execute();
            int a = 1/0;
        }catch (Exception e){
            e.printStackTrace();
            throw  e;
        }
    }
}
