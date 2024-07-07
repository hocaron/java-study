package src.main.java.com.basestudy;

import java.util.Optional;
import java.util.function.Consumer;

@FunctionalInterface
interface CustomInterface<T> {
	T myCall();
}

public class FunctionalInterfaceExample {

	public static void main(String[] args) {
		CustomInterface<String> customInterface = () -> "Hello, world!";

		// abstract method
		String s = customInterface.myCall();
		System.out.println(s);

		Optional<String> optionalValue = Optional.ofNullable("test");
		optionalValue.orElseGet(() -> {
			System.out.println("Value is null. Generating a new value...");
			return "default";
		});

		Consumer<String> consumer = value -> System.out.println("Consumed value: " + value);
		optionalValue.ifPresent(consumer);
	}
}
