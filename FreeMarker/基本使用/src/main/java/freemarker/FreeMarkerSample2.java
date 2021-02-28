package freemarker;

import entity.Computer;
import freemarker.template.Configuration;
import freemarker.template.Template;

import java.io.OutputStreamWriter;
import java.util.*;

public class FreeMarkerSample2 {
    public static void main(String[] args) throws Exception {
        // 1. 加载模板
        // 创建核心配置对象
        Configuration config=new Configuration(Configuration.VERSION_2_3_28);
        // 设置加载的目录
        config.setClassForTemplateLoading(FreeMarkerSample2.class,"../");
        // 得到模板对象
        Template t = config.getTemplate("sample2.ftl");
        // 2. 创建数据
       Map<String,Object>data=new HashMap<>();
       List<Computer> computers=new ArrayList<>();
       computers.add(new Computer("123243","MacBook",2,null,new Date(),3299.9f,data));
        computers.add(new Computer("43554","AUSC",1,"小明",new Date(),3234.9f,data));
        computers.add(new Computer("12653243","ThinkPad",1,"大白",new Date(),4343f,data));
        computers.add(new Computer("877575","MinoteBook",3,"小黑",new Date(),7659.9f,data));
        computers.add(new Computer("122453543","MateBook",2,"小红",new Date(),4599.9f,data));
        data.put("computers",computers);
        // 使用LinkedHashMap可以保证提取时顺序一致
        Map computerMap=new HashMap();
        for(Computer c:computers){
            computerMap.put(c.getSn(),c);
        }
        data.put("computerMap",computerMap);
        // 3. 生成输出
        t.process(data,new OutputStreamWriter(System.out));
    }
}
