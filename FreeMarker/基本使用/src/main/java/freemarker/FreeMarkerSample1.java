package freemarker;

import entity.Computer;
import freemarker.template.Configuration;
import freemarker.template.Template;

import java.io.OutputStreamWriter;
import java.sql.Date;
import java.util.HashMap;
import java.util.Map;

//  http://freemarker.foofun.cn/

public class FreeMarkerSample1{
    public static void main(String[] args) throws Exception {
        // 1. 加载模板
        // 创建核心配置对象
        Configuration config=new Configuration(Configuration.VERSION_2_3_28);
        // 设置加载的目录
        config.setClassForTemplateLoading(FreeMarkerSample1.class,"../");
        // 得到模板对象
        Template t = config.getTemplate("sample1.ftl");
        // 2. 创建数据
        Map<String,Object> data=new HashMap();
        data.put("site","百度");
        data.put("url","http://www.baidu.com");
        data.put("date",new Date(System.currentTimeMillis()));
        data.put("number",8888329.213);
        HashMap<String, String> info = new HashMap<>();
        info.put("CPU","i5");
        Computer computer = new Computer("133232", "ThinkPad", 1, "daming", new Date(System.currentTimeMillis()), 129.90f, info);
        data.put("computer",computer);

        // 3. 生成输出
        t.process(data,new OutputStreamWriter(System.out));
    }
}
