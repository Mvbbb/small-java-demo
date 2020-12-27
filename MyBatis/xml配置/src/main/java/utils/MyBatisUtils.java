package utils;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.Reader;

public class MyBatisUtils {
    // 保证全局唯一
    private static SqlSessionFactory sqlSessionFactory=null;
    static{
        try {
            Reader reader = Resources.getResourceAsReader("mybatis-config.xml");
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
            
        } catch (IOException e) {
            e.printStackTrace();
            // 初始化错误, 抛出异常通知调用者
            throw new ExceptionInInitializerError(e);
        }
    }
    public static SqlSession openSession(){
        return sqlSessionFactory.openSession();
    }
    public static void closeSession(SqlSession sqlSession){
        if(sqlSession!=null){
            sqlSession.close();
        }
    }
}
