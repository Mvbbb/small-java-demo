import dao.Table1Dao;
import dto.TableDto;
import entity.Table1;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;
import utils.MyBatisUtils;

import java.util.List;

public class MyBatisTestor {
    @Test
    public void selectByAgeRanges(){
        SqlSession session=null;
        try{
            session= MyBatisUtils.openSession();
            Table1Dao table1Dao= session.getMapper(Table1Dao.class);
            List<Table1> list = table1Dao.selectByAgeRanges(1, 100, 11);
            list.forEach(System.out::println);
        }catch (Exception e){
            throw e;
        }finally {
            if(session != null){
                MyBatisUtils.closeSession(session);
            }
        }
    }

    @Test
    public void insert(){
        SqlSession session=null;
        try{
            session= MyBatisUtils.openSession();
            Table1Dao table1Dao= session.getMapper(Table1Dao.class);
            Table1 table1 = new Table1();
            table1.setAge(15);
            table1.setName("godse");
            table1.setHomeAddress("leshan");
            // 本次操作的记录数
            int count = table1Dao.insert(table1);
            System.out.println(count);
            session.commit();
        }catch (Exception e){
            throw e;
        }finally {
            if(session != null){
                MyBatisUtils.closeSession(session);
            }
        }
    }
    
    @Test
    public void selectAll(){
        SqlSession session=null;
        try{
            session= MyBatisUtils.openSession();
            Table1Dao table1Dao= session.getMapper(Table1Dao.class);
            List<TableDto> list = table1Dao.selectAll();
            list.forEach(System.out::println);
        }catch (Exception e){
            throw e;
        }finally {
            if(session != null){
                MyBatisUtils.closeSession(session);
            }
        }
    }
}
