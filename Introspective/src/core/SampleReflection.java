package core;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

public class SampleReflection {
	public static int value = 666;
	public String name = "Eduardo eh lindo";
	public String culpado = "Alexandre obviamente";
	
	public static float somaFloat(int a, int b) {
		return a + b;
	}
	
	public static int somaTeste() {
		return 2 + 2;
	}
	public static void main(String[] args) {
		SampleReflection class_test = new SampleReflection();
		// String fmt = "%6S:  %-12s = %s%n";
		
		try {
			Class<?> c = class_test.getClass();
			
			Field field_name = c.getDeclaredField("name");
			System.out.println("Nome do campo: " + field_name.getName() + " | Valor: " + field_name.get(class_test));
			field_name.set(class_test, "Eduardo eh lindo e gostoso!");
			System.out.println("Nome do campo: " + field_name.getName() + " | Valor: " + field_name.get(class_test));
			
			Method[] allMethods = c.getDeclaredMethods();
			for(Method m : allMethods) {
				System.out.println(m.getName());
				
				try {
					if (m.getGenericReturnType() == int.class) {
						System.out.println(m.invoke(class_test));
					}
				// Handle any exceptions thrown by method to be invoked.
				} catch (InvocationTargetException x) {
					x.printStackTrace();
				}
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}