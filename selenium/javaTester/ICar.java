package javaTester;

@FunctionalInterface
public interface ICar {
	public String name = "Test";
	double calculate(int a);

	default double sqrt(int a) {
		return Math.sqrt(a);
	}
	
	default void setName() {
		
	}
}
