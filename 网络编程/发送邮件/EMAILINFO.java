import java.io.IOException;
import java.util.Properties;

public  final class EMAILINFO {
    public  String smtpserver;
    public  int smtpport;
    public  String username;
    public  String password;
    public  String from;
    public  String to;
    public  String contentpath;
    {
        Properties properties = new Properties();
        try {
            properties.load(EMAILINFO.class.getClassLoader().getResourceAsStream("EmailInfo.properties"));
            this.smtpserver = properties.getProperty("smtpserver");
            this.smtpport = Integer.parseInt(properties.getProperty("smtpport"));
            this.username = properties.getProperty("username");
            this.password = properties.getProperty("password");
            this.from = properties.getProperty("from");
            this.to= properties.getProperty("to");
            this.contentpath = properties.getProperty("contentpath");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return "EmailInfo{" +
                "smtpserver='" + smtpserver + '\'' +
                ", smtpport=" + smtpport +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", from='" + from + '\'' +
                ", to='" + to + '\'' +
                ", contentpath='" + contentpath + '\'' +
                '}';
    }
}
