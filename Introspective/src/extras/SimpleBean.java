package extras;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.beans.MethodDescriptor;

public class SimpleBean {
	private final String name = "SimpleBean";
	private int size;

	public String getName() {
		return this.name;
	}

	public int getSize() {
		return this.size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public static void main(String[] args) throws IntrospectionException {
		BeanInfo info = Introspector.getBeanInfo(SimpleBean.class, Object.class);
		
		for (PropertyDescriptor pd : info.getPropertyDescriptors())
			System.out.println(pd.getName());
	
		for (MethodDescriptor md : info.getMethodDescriptors()) {
			System.out.println(md.getName());
		}
	}
}