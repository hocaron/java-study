package src.main.java.com.basestudy;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class HashCodeTest {
	public static void main(String[] args) {
		// String a = "aa";
		// String b = "aa";
		// System.out.println(System.identityHashCode(a));
		// System.out.println(System.identityHashCode(b));
		// System.out.println(b.hashCode());
		// System.out.println(a == b);

		Face face1 = new Face("red");
		Face face2 = new Face("red");
		Person person1 = new Person("홍길동", face1);
		Person person2 = new Person("홍길동", face2);
		// System.out.println(person1.hashCode() == person2.hashCode()); // == 은 객체타입인경우 주소값을 비교한다. 서로다른 객체는 다른 주소를 가지고 있기 때문에 false가 출력됨

		System.out.println(System.identityHashCode(person1.name));
		System.out.println(System.identityHashCode(person2.name));
		System.out.println(person1);
		System.out.println(person2);
		System.out.println(person2.equals(person1));
		System.out.println(person2.hashCode());

		System.out.println();
		Set<Face> set = new HashSet<>();
		set.add(new Face("1"));
		set.add(new Face("1"));
		System.out.println(face1);
		System.out.println(face2);
		System.out.println(set.size());

		Object o1 = new String("1");
	}

	private static class Person {
		String name;
		Face face;

		public Person(String name, Face face) {
			this.name = name;
			this.face = face;
		}

		public boolean equals(Object o) {
			if (this == o) return true; // 만일 현 객체 this와 매개변수 객체가 같을 경우 true
			if (!(o instanceof Person)) return false; // 만일 매개변수 객체가 Person 타입과 호환되지 않으면 false
			Person person = (Person) o; // 만일 매개변수 객체가 Person 타입과 호환된다면 다운캐스팅(down casting) 진행
			return Objects.equals(this.name, person.name) && Objects.equals(this.face, ((Person)o).face); // this객체 이름과 매개변수 객체 이름이 같을경우 true, 다를 경우 false
		}
	}

	private static class Face {
		String color;

		public Face(String color) {
			this.color = color;
		}

		public boolean equals(Object o) {
			if (this == o) return true; // 만일 현 객체 this와 매개변수 객체가 같을 경우 true
			if (!(o instanceof Face)) return false; // 만일 매개변수 객체가 Person 타입과 호환되지 않으면 false
			Face face = (Face) o; // 만일 매개변수 객체가 Person 타입과 호환된다면 다운캐스팅(down casting) 진행
			return Objects.equals(this.color, face.color); // this객체 이름과 매개변수 객체 이름이 같을경우 true, 다를 경우 false
		}

		public int hashCode() {
			return Objects.hash(color);
		}
	}

}
