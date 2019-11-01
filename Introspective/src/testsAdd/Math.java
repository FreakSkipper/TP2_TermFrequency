package testsAdd;

public class Math {
	private Integer a;
	private Integer b;
	
	public Math(int first, int second) {
		a = new Integer(first);
		b = new Integer(second);
	}
	
	public int soma() {
		return a + b;
	}
}
