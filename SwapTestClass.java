public class SwapTestClass {

    public static void main(String[] args) {
        SwapTestClass tempClass = new SwapTestClass();
        Person person1 = new Person(10);
        Person person2 = new Person(20);

        System.out.println("Before swap - person1: " + person1 + ", person2: " + person2);
        tempClass.swapReferences(person1, person2);
        System.out.println("After swapReferences - person1: " + person1 + ", person2: " + person2);

        System.out.println();

        person1 = new Person(10);
        person2 = new Person(20);
        System.out.println("Before swap - person1: " + person1 + ", person2: " + person2);
        tempClass.swapFields(person1, person2);
        System.out.println("After swapFields - person1: " + person1 + ", person2: " + person2);

        System.out.println();

        person1 = new Person(10);
        person2 = new Person(20);
        System.out.println("Before swap - person1: " + person1 + ", person2: " + person2);
        tempClass.swapNewObjects(person1, person2);
        System.out.println("After swapNewObjects - person1: " + person1 + ", person2: " + person2);
    }

    // call-by-value 처럼 동작
    public void swapReferences(Person person1, Person person2) {
        System.out.println("Inside swapReferences - before change - person1: " + person1 + ", person2: " + person2);
        Person temp = person1;
        person1 = person2;
        person2 = temp;
        System.out.println("Inside swapReferences - after change - person1: " + person1 + ", person2: " + person2);
    }

    // call-by-reference 처럼 동작
    public void swapFields(Person person1, Person person2) {
        System.out.println("Inside swapFields - before change - person1: " + person1 + ", person2: " + person2);
        Integer tempAge = person1.getAge();
        person1.setAge(person2.getAge());
        person2.setAge(tempAge);
        System.out.println("Inside swapFields - after change - person1: " + person1 + ", person2: " + person2);
    }

    // call-by-value 처럼 동작
    public void swapNewObjects(Person person1, Person person2) {
        System.out.println("Inside swapNewObjects - before change - person1: " + person1 + ", person2: " + person2);
        person1 = new Person(30);
        person2 = new Person(40);
        System.out.println("Inside swapNewObjects - after change - person1: " + person1 + ", person2: " + person2);
    }

    public static class Person {
        private Integer age;

        public Person(Integer age) {
            this.age = age;
        }

        public Integer getAge() {
            return age;
        }

        public void setAge(Integer age) {
            this.age = age;
        }

        @Override
        public String toString() {
            return "Person{" + "age=" + age + '}';
        }
    }
}
