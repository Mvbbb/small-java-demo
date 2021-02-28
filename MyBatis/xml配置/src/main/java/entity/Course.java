package entity;

import java.util.List;

public class Course {
    private Integer id;
    private Integer fkT1id;
    private String cname;
    private List<Table1> table1s;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getFkT1id() {
        return fkT1id;
    }

    public void setFkT1id(Integer fkT1id) {
        this.fkT1id = fkT1id;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public List<Table1> getTable1s() {
        return table1s;
    }

    public void setTable1s(List<Table1> table1s) {
        this.table1s = table1s;
    }
}
