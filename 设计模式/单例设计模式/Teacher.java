  
/**
 * Created by asd on 2016/9/28.
 */
/*
    ¿¡∫∫ Ω
 */
public class Teacher {
    private Teacher() {
    }

    private static Teacher t = null;

    public synchronized Teacher getTeacher() {
        if (t == null) {
            t = new Teacher();
        }
        return t;
    }
}
