public class AnimalFactory {
     public static Dog createDog() {
     return new Dog();
     }

     public static Cat createCat() {
     return new Cat();
     }

     /**
      * 根据用户提供的 type 返回不同的实例对象
      * @param type
      * @return
      */
    public static Animal createAniaml(String type) {
        if ("dog".equals(type)) {
            return new Dog();
        } else if ("cat".equals(type)) {
            return new Cat();
        } else {
            return null;
        }
    }
}

abstract class Animal {
    public abstract void eat();
}

class Cat extends Animal {

	@Override
	public void eat() {
		System.out.println("猫在吃");
	}

}

class Dog extends Animal {

	@Override
	public void eat() {
		System.out.println("狗在吃");
	}
}
