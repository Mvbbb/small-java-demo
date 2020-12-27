package dao;

import dto.TableDto;
import entity.Table1;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface Table1Dao {
    @Select("select * from table1 where age between #{min} and #{max} order by age  limit 0,#{limit}")
    public List<Table1> selectByAgeRanges(@Param("min")Integer min,@Param("max") Integer max,@Param("limit") Integer limit);
    
    @Insert("insert into table1(name,age,home_address) values(#{name},#{age},#{homeAddress})")
    //<selectKey>
    @SelectKey(statement = "select last_insert_id()",before = false,keyProperty = "id" ,resultType = Integer.class)
    public int insert(Table1 table1);
    
    @Select("select * from table1")
    //@Results对应<resultMap> 
    @Results({
            // column字段名, property属性名
            @Result(column = "id", property = "id",id = true), //主键
            @Result(column = "name",property = "name"),
            @Result(column = "home_address",property = "homeAddress")
    })
    public List<TableDto> selectAll();
}
