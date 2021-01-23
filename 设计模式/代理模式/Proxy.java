/**
 * 静态代理
 */
class Main{
    public static void main(String[] args) {
        UserService userService = new UserServiceProxy(new UserServiceImpl());
        userService.createUser();
        
    }
}

interface UserService{
    void createUser();
}

class UserServiceImpl implements UserService{
    @Override
    public void createUser() {
        System.out.println("创建用户");
    }
}


class UserServiceProxy implements UserService{
    private UserService userService;

    public UserServiceProxy(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void createUser() {
        System.out.println("用户创建的时间："+new Date(System.currentTimeMillis()));
        userService.createUser();
    }
}