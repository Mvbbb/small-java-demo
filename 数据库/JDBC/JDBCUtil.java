import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class JDBCUtil {
    private static DataSource dataSource=null;

    static{
        Properties props = new Properties();
        FileInputStream fileInputStream=null;
        try {
            fileInputStream=new FileInputStream("druid.Properties");
            props.load(fileInputStream);
            dataSource = DruidDataSourceFactory.createDataSource(props);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fileInputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public static Connection getConnection(){
        Connection conn = null;
        try {
            conn= dataSource.getConnection();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return conn;
    }
    public static void closeConection(Connection conn){
        try{
            conn.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
