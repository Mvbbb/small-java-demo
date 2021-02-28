/**
 * JDK 动态代理
 */
class WineHandler implements InvocationHandler {

    private Object wine;

    public WineHandler(Object wine) {
        this.wine = wine;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("销售开始  柜台是： "+this.getClass().getSimpleName());
        method.invoke(wine, args);
        System.out.println("销售结束");
        return null;
    }
}

interface CigarService {
    void sell();
}
interface WineService {
    void sellWine();
}

class CigarServiceImpl implements CigarService {

    @Override
    public void sell() {
        System.out.println("售卖的是正宗的芙蓉王，可以扫描条形码查证。");
    }
}
class WineServiceImpl implements WineService {

    @Override
    public void sellWine() {
        System.out.println("我卖得是茅台酒。");
    }
}
class WineServiceImpl2 implements WineService {

    @Override
    public void sellWine() {
        System.out.println("我卖得是五粮液。");
    }
}

class DynamicProxyTest {

    public static void main(String[] args) {
        WineService wineService = new WineServiceImpl();

        InvocationHandler handler = new WineHandler(wineService);

        WineService dynamicProxy = (WineService) Proxy.newProxyInstance(WineService.class.getClassLoader(), WineServiceImpl.class.getInterfaces(), handler);

        dynamicProxy.sellWine();
    }
}

class DynamicProxyTest2 {

    public static void main(String[] args) {
        WineService wineService1 = new WineServiceImpl();
        WineService wineService2 = new WineServiceImpl2();

        InvocationHandler handler1 = new WineHandler(wineService1);
        InvocationHandler handler2 = new WineHandler(wineService2);

        ((WineService)Proxy.newProxyInstance(WineService.class.getClassLoader(),
                WineServiceImpl.class.getInterfaces(), handler1)).sellWine();

        ((WineService)Proxy.newProxyInstance(WineService.class.getClassLoader(),
                WineServiceImpl2.class.getInterfaces(), handler2)).sellWine();


    }
}

class DynamicProxyTest3 {

    public static void main(String[] args) {
        WineService wineService1 = new WineServiceImpl();
        WineService wineService2 = new WineServiceImpl2();
        CigarService cigarService = new CigarServiceImpl();

        InvocationHandler handler1 = new WineHandler(wineService1);
        InvocationHandler handler2 = new WineHandler(wineService2);
        InvocationHandler handler3 = new WineHandler(cigarService);

        ((WineService) Proxy.newProxyInstance(WineService.class.getClassLoader(),
                WineServiceImpl.class.getInterfaces(), handler1)).sellWine();

        ((WineService)Proxy.newProxyInstance(WineService.class.getClassLoader(),
                WineServiceImpl2.class.getInterfaces(), handler2)).sellWine();

        ((CigarService)Proxy.newProxyInstance(CigarService.class.getClassLoader(),
                CigarServiceImpl.class.getInterfaces(), handler3)).sell();
    }
}