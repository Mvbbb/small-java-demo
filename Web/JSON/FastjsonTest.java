import com.alibaba.fastjson.JSON;

import java.util.Calendar;

public class FastjsonTest {
    public static void main(String[] args) {
        Employee employee = new Employee();
        employee.setEname("daming");
        employee.setJob("程序员");
        Calendar c = Calendar.getInstance();
        c.set(2020,2,2,2,2,2);
        employee.setHdate(c.getTime());
        
        // fastjson 中提供的 JSON 对象完成对象到字符串
        String str = JSON.toJSONString(employee);
        System.out.println(str);
        
        // 字符串转对象
        Employee employee1 = JSON.parseObject(str, Employee.class);
        System.out.println(employee1);
        
    }
}
