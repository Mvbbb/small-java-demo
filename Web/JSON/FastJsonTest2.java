import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class FastJsonTest2 {
    public static void main(String[] args) {
        ArrayList<Employee> emplist = new ArrayList<>();
        for(int i=0;i<10;i++){
            Employee employee = new Employee();
            employee.setJob("打灰");
            Calendar c = Calendar.getInstance();
            c.set(2020,2,2,2,2,2);
            employee.setHdate(c.getTime());
            employee.setJob("程序员");
            emplist.add(employee);
        }
        // 对象数组转JSON字符串
        String str= JSON.toJSONString(emplist);
        System.out.println(str);
        
        // Json数组字符串转对象数组
        List<Employee> employees = JSON.parseArray(str, Employee.class);
        employees.forEach(System.out::println);
    }
}
