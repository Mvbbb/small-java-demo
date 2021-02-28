package entity;

import java.util.Date;
import java.util.Map;

public class Computer {
    private String sn;
    private String model;
    private int status;
    private String user;
    private Date dop;
    private float price;
    private Map info;

    public Computer() {
    }

    public Computer(String sn, String model, int status, String user, Date dop, float price, Map info) {
        this.sn = sn;
        this.model = model;
        this.status = status;
        this.user = user;
        this.dop = dop;
        this.price = price;
        this.info = info;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public Date getDop() {
        return dop;
    }

    public void setDop(Date dop) {
        this.dop = dop;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public Map getInfo() {
        return info;
    }

    public void setInfo(Map info) {
        this.info = info;
    }
}
