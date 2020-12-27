import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import entity.Course;
import entity.Table1;
import dto.TableDto;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;
import utils.MyBatisUtils;

import java.io.IOException;
import java.io.Reader;
import java.sql.Connection;
import java.util.ArrayList;
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
    
    @Test
    public void testInsert(){
        SqlSession sqlSession=null;
        try{
            sqlSession = MyBatisUtils.openSession();
            Table1 table1 = new Table1();
            table1.setName("dabei");
            table1.setAge(15);
            table1.setHomeAddress("南旧");
            
            int num = sqlSession.insert("table1.insert", table1);
            //写操作必须提交事务数据
            sqlSession.commit();
            System.out.println(table1.getId());
        }catch (Exception e){
            if(sqlSession != null) {
                sqlSession.rollback();
            }
            throw  e;
        }finally {
            if(sqlSession!=null){
                MyBatisUtils.closeSession(sqlSession);
            }
        }
    }
    @Test
    public void testInsert2(){
        SqlSession sqlSession=null;
        try{
            sqlSession = MyBatisUtils.openSession();
            Table1 table1 = new Table1();
            table1.setName("dabei");
            table1.setAge(15);
            table1.setHomeAddress("南旧");

            int num = sqlSession.insert("table1.insert", table1);
            //写操作必须提交事务数据
            sqlSession.commit();
            System.out.println(table1.getId());
        }catch (Exception e){
            if(sqlSession != null) {
                sqlSession.rollback();
            }
            throw  e;
        }finally {
            if(sqlSession!=null){
                MyBatisUtils.closeSession(sqlSession);
            }
        }
    }
    
    @Test
    public void testUpdate(){
        SqlSession sqlSession=null;
        try{
            sqlSession = MyBatisUtils.openSession();
            Table1 table1 = new Table1();
            table1.setName("nihao");
            table1.setAge(15);
            table1.setHomeAddress("南旧");
            table1.setId(1);
            sqlSession.update("table1.update",table1);
            sqlSession.commit();
        }catch (Exception e){
            if(sqlSession != null) {
                sqlSession.rollback();
            }
            throw  e;
        }finally {
            if(sqlSession!=null){
                MyBatisUtils.closeSession(sqlSession);
            }
        }
    }
    
    @Test
    public void testDelete(){
        SqlSession sqlSession=null;
        try{
            sqlSession = MyBatisUtils.openSession();
            sqlSession.delete("table1.delete",7);
            sqlSession.commit();
        }catch (Exception e){
            if(sqlSession != null) {
                sqlSession.rollback();
            }
        }finally {
            if(sqlSession!=null){
                MyBatisUtils.closeSession(sqlSession);
            }
        }
    }
    
    @Test
    public void testDynamicSQL(){
        SqlSession sqlSession=null;
        try{
            sqlSession = MyBatisUtils.openSession();
            HashMap para=new HashMap();
            //para.put("name","xiaobei");
            para.put("age",20);
            List<Table1> list = sqlSession.selectList("table1.dynamicSQL", para);
            list.forEach(e->{
                System.out.println("学生姓名: "+e.getName()+"  学生ID= "+e.getId());
            });
        }catch (Exception e){
            if(sqlSession != null) {
                sqlSession.rollback();
            }
        }finally {
            if(sqlSession!=null){
                MyBatisUtils.closeSession(sqlSession);
            }
        }
    }

    /**
     * 验证一级缓存
     * 不同的sqlSession不同的内存空间
     */
    @Test
    public void testLv1Cache(){
        SqlSession sqlSession=null;
        try{
            sqlSession = MyBatisUtils.openSession();
            Table1 t = sqlSession.selectOne("table1.selectById",1 );
            Table1 t1 = sqlSession.selectOne("table1.selectById",1 );
            // 两个指向的内存映像相同
            System.out.println("t hashCode: "+t.hashCode());
            System.out.println("t1 hashCode: "+t1.hashCode());
        }catch (Exception e){
            throw  e;
        }finally {
            if(sqlSession!=null){
                MyBatisUtils.closeSession(sqlSession);
            }
        }
    
        try{
            sqlSession = MyBatisUtils.openSession();
            Table1 t3 = sqlSession.selectOne("table1.selectById",1 );
            // 强制清空缓存
            sqlSession.commit();
            Table1 t4 = sqlSession.selectOne("table1.selectById",1 );
            System.out.println("t3 hashCode: "+t3.hashCode());
            System.out.println("t4 hashCode: "+t4.hashCode());
        }catch (Exception e){
            throw  e;
        }finally {
            if(sqlSession!=null){
                MyBatisUtils.closeSession(sqlSession);
            }
        }
    }
    /**
     * 验证二级缓存
     * 开启二级缓存之后两个的内存映像相同
     */
    @Test
    public void testLv2Cache(){
        SqlSession sqlSession=null;
        try{
            sqlSession = MyBatisUtils.openSession();
            Table1 t = sqlSession.selectOne("table1.selectById",1 );
            System.out.println("t hashCode: "+t.hashCode());
        }catch (Exception e){
            throw  e;
        }finally {
            if(sqlSession!=null){
                MyBatisUtils.closeSession(sqlSession);
            }
        }

        try{
            sqlSession = MyBatisUtils.openSession();
            Table1 t3 = sqlSession.selectOne("table1.selectById",1 );
            System.out.println("t3 hashCode: "+t3.hashCode());
        }catch (Exception e){
            throw  e;
        }finally {
            if(sqlSession!=null){
                MyBatisUtils.closeSession(sqlSession);
            }
        }
    }
    
    @Test
    public void testSelectOneToMany(){
        SqlSession sqlSession=null;
        try{
            sqlSession = MyBatisUtils.openSession();
            List<Table1> list = sqlSession.selectList("table1.selectOneToMany", 1);
            list.forEach(e->{
                System.out.println("学生姓名: "+e.getName()+"  学生ID= "+e.getId()+"  ");
                e.getCourses().forEach(c -> {
                    System.out.print(c.getCname()+"  ");
                });
                System.out.println();
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
    public void testSelectManyToOne(){
        SqlSession sqlSession = null;
        try{
            sqlSession=MyBatisUtils.openSession();
            List<Course> list = sqlSession.selectList("course.selectManyToOne");
            list.forEach(c->{
                System.out.print(c.getCname()+": ");
                for (Table1 t : c.getTable1s()) {
                    System.out.print(t.getName()+"  ");
                }
                System.out.println();
            });
        }catch (Exception e){
            throw e;
        }finally {
            MyBatisUtils.closeSession(sqlSession);
        }
    }
    
    @Test
    public void testSelectPage(){
        SqlSession sqlSession=null;
        try{
            sqlSession = MyBatisUtils.openSession();
            /*startPage会自动将下一次查询进行分页*/
            PageHelper.startPage(0,10);
            Page<Table1> page=(Page) sqlSession.selectList("table1.selectPage");
            System.out.println("总页数: "+page.getPages());
            System.out.println("总记录数: " + page.getTotal());
            System.out.println("开始行号: " + page.getStartRow());
            System.out.println("结束行号: " + page.getEndRow());
            System.out.println("当前行号: " + page.getPageNum());
            List<Table1> data = page.getResult();
            data.forEach(d->{
                System.out.println(d.getName());
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
    public void testBatchInsert(){
        SqlSession sqlSession = null;
        try{
            sqlSession=MyBatisUtils.openSession();
            ArrayList<Table1> list = new ArrayList<>();
            Table1 table1 = new Table1();
            table1.setHomeAddress("南门");
            table1.setAge(12);
            table1.setName("xiaohong");
            table1.setId(9);
            list.add(table1);
            sqlSession.insert("table1.batchInsert",list);
            sqlSession.commit();
        }catch (Exception e){
            throw e;
        }finally {
            MyBatisUtils.closeSession(sqlSession);
        }
    }
}
