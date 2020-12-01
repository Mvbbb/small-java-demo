 
public abstract class Animal {
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
