import com.alibaba.fastjson.annotation.JSONField;

import java.util.Date;

public class Employee {
    private String ename;
    private String job;

    /**
     * 格式化日期, 格式化时 hdate 改名为 hiredate
     */
    @JSONField(name="hiredate", format = "yyyy-MM-dd")
    private Date hdate;
    
    public Employee() {
    }

    public Employee(String ename, String job, Date hdate) {
        this.ename = ename;
        this.job = job;
        this.hdate = hdate;
    }

    public String getEname() {
        return ename;
    }

    public void setEname(String ename) {
        this.ename = ename;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public Date getHdate() {
        return hdate;
    }

    public void setHdate(Date hdate) {
        this.hdate = hdate;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "ename='" + ename + '\'' +
                ", job='" + job + '\'' +
                ", hdate=" + hdate +
                '}';
    }
}
