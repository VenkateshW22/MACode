// MemoryExample - Stack and Heap Memory Example
public class MemoryExample {

    public static void main(String[] args) {
        int id = 101; // Stored on the Stack (main's frame)
        String name = "Alice"; // The object "Alice" is on the Heap
        // The reference 'name' is on the Stack

        Person person = createPerson("Bob", 30); // 'person' reference on Stack
        // The Person object it points to is on the Heap
    }

    public static Person createPerson(String personName, int personAge) {
        // 'personName' and 'personAge' are local to this method, on the Stack.
        Person p = new Person(personName, personAge); // 'p' reference is on the Stack
        // The new Person object is on the HEAP.
        return p; // The reference 'p' is returned and assigned to 'person' in main.
    }
}
class Person {
    String name;
    int age;

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }
}

