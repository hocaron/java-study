package src.main.java.com.basestudy;

import java.util.Arrays;
import java.util.Collection;

public class GenericTest {
	public static void main(String[] args) {
		GenericStack<Number> stack = new GenericStack<Number>();
		Collection<Integer> integers = Arrays.asList(1, 2, 3, 4, 5);
		stack.pushAll(integers); // compile error!
	}
}
