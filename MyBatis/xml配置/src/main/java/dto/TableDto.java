package dto;

import entity.Table1;

/**
 * 结果映射
 */
public class TableDto {
    private Table1 table1=new Table1();
    private String teacherName;

    public Table1 getTable1() {
        return table1;
    }

    public void setTable1(Table1 table1) {
        this.table1 = table1;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }
}
