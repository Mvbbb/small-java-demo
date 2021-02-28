import utils.JDBCUtil;
import com.alibaba.druid.util.JdbcUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;


import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public abstract class BaseDao {
    private QueryRunner queryRunner=new QueryRunner();

    /**
     * @return 如果返回 -1 证明执行失败, 其他表示影响的行数
     */
    public int update(String sql,Object...args){
        Connection conn= JDBCUtil.getConnection();
        try {
            return queryRunner.update(conn,sql,args);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            JdbcUtils.close(conn);
        }
        return -1;
    }

    /**
     * 查询返回一个 JavaBean 的sql语句的Bean对象
     * @param type
     * @param sql
     * @param args
     * @param <T>
     * @return
     */
    public <T>T queryForOne(Class<T> type,String sql,Object...args){
        Connection conn= JDBCUtil.getConnection();
        try {
            return queryRunner.query(conn,sql,new BeanHandler<T>(type),args);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            JdbcUtils.close(conn);
        }
        return null;
    }

    /**
     * 查询返回多个 JavaBean 的sql语句的Bean对象
     * @param type
     * @param sql
     * @param args
     * @param <T>
     * @return
     */
    public <T> List<T> queryForList(Class<T> type, String sql, Object...args){
        Connection conn = JDBCUtil.getConnection();
        try{
            return queryRunner.query(conn,sql,new BeanListHandler<T>(type), args);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            JdbcUtils.close(conn);
        }
        return null;
    }

    /**
     * 查询返回单个值
     * @return
     */
    public Object queryForSingleValue(String sql, Object...args){
        Connection conn = JDBCUtil.getConnection();
        try{
            queryRunner.query(conn, sql, new ScalarHandler(), args);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            JdbcUtils.close(conn);
        }
        return null;
    }
}
