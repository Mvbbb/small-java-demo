import entity.Table1;
import entity.dto.TableDto;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;
import utils.MyBatisUtils;

import java.io.IOException;
import java.io.Reader;
import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyBatisTestor {
    @Test
    public void testSqlSessionFactory() throws IOException {
        // reader 加载 classPath 下的 mybatis-config.xml 核心配置文件
        Reader reader = Resources.getResourceAsReader("mybatis-config.xml");
        // 生产者模式, 传入 reader 解析xml, 返回工厂类
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
        // 创建一个 sqlSession 对象.
        SqlSession sqlSession =null;
        try {
            sqlSession = sqlSessionFactory.openSession();
            System.out.println(sqlSession);
        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            if(sqlSession != null){
                sqlSession.close();
            }
        }
    }
    @Test
    public void testMyBatisUtils(){
        SqlSession sqlSession = MyBatisUtils.openSession();
        Connection connection = sqlSession.getConnection();
        System.out.println("数据库连接"+connection);
        MyBatisUtils.closeSession(sqlSession);
    }
    @Test
    public void testSelectAll(){
        SqlSession sqlSession=null;
        try{
            sqlSession = MyBatisUtils.openSession();
//            命名空间+id
            List<Table1> list = sqlSession.selectList("table1.selectAll");
            list.forEach(e->{
                System.out.println("id= "+e.getId()+"  name= "+e.getName()+"  age= "+e.getAge()+"   家庭住址= "+e.getHomeAddress());
            });
        }catch (Exception e){
            throw  e;
        }finally {
            if(sqlSession!=null){
                MyBatisUtils.closeSession(sqlSession);
            }
        }
    }
    @Test
    public void testSelectById(){
        SqlSession sqlSession=null;
        try{
            sqlSession = MyBatisUtils.openSession();
            Table1 t = sqlSession.selectOne("table1.selectById",1 );
            System.out.println(t.getName());
        }catch (Exception e){
            throw  e;
        }finally {
            if(sqlSession!=null){
                MyBatisUtils.closeSession(sqlSession);
            }
        }
    }
    
    @Test
    public void selectByAgeRange(){
        SqlSession sqlSession=null;
        try{
            sqlSession = MyBatisUtils.openSession();
            Map map = new HashMap();
            map.put("min",10);
            map.put("max",15);
            map.put("limit",100);
//            命名空间+id
            List<Table1> list = sqlSession.selectList("table1.selectByAgeRange",map);
            list.forEach(e->{
                System.out.println("id= "+e.getId()+"  name= "+e.getName()+"  age= "+e.getAge()+"   家庭住址= "+e.getHomeAddress());
            });
        }catch (Exception e){
            throw  e;
        }finally {
            if(sqlSession!=null){
                MyBatisUtils.closeSession(sqlSession);
            }
        }
    }
    @Test
    public void testSelectTableMap(){
        SqlSession sqlSession=null;
        try{
            sqlSession = MyBatisUtils.openSession();
            List<Map> list = sqlSession.selectList("table1.selectTableMap");
            list.forEach(e->{ 
                System.out.println(e);
            });
        }catch (Exception e){
            throw  e;
        }finally {
            if(sqlSession!=null){
                MyBatisUtils.closeSession(sqlSession);
            }
        }
    }
    
    @Test
    public void testSelectTableDto(){
        SqlSession sqlSession=null;
        try{
            sqlSession = MyBatisUtils.openSession();
            List<TableDto> list = sqlSession.selectList("table1.selectTableDto");
            list.forEach(e->{
                System.out.println("学生姓名: "+e.getTable1().getName()+"  教师姓名: "+e.getTeacherName());
            });
        }catch (Exception e){
            throw  e;
        }finally {
            if(sqlSession!=null){
                MyBatisUtils.closeSession(sqlSession);
            }
        }
    }
}
