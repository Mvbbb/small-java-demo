 
public interface Factory {
	public abstract Animal createAnimal();
}
class DogFactory implements Factory {

	@Override
	public Animal createAnimal() {
		return new Dog();
	}

}class CatFactory implements Factory {
    @Override
    public Animal createAnimal() {
        return new Cat();
    }
}

