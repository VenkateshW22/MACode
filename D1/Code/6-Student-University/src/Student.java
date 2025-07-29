public class Student {
    // Fields are made private to enforce encapsulation (protects the data from outside interference).
    private String name;
    private int studentId;
    private double gpa;

    // Default (no-argument) constructor - sets default "empty" values.
    public Student() {
        this.name = "Unknown";     // Default name.
        this.studentId = 0;        // Default ID.
        this.gpa = 0.0;            // Default GPA.
    }

    // Parameterized constructor - allows creation with initial values.
    public Student(String name, int studentId, double gpa) {
        // 'this' refers to the current object's fields (distinguishes from parameter names).
        this.name = name;
        this.studentId = studentId;
        this.gpa = gpa;
    }

    // Public method to display student info in a readable format.
    public void displayInfo() {
        System.out.println("ID: " + this.studentId + ", Name: " + this.name + ", GPA: " + this.gpa);
    }

    // Getter for name: returns the value of name.
    public String getName() { return this.name; }

    // Setter for name: lets you change name with simple validation.
    public void setName(String name) {
        if (name != null && !name.trim().isEmpty()) this.name = name;
    }

    // Getter for studentId.
    public int getStudentId() { return this.studentId; }

    // Setter for studentId: accepts only positive IDs.
    public void setStudentId(int studentId) {
        if (studentId > 0) this.studentId = studentId;
    }

    // Getter for gpa.
    public double getGpa() { return gpa; }

    // Setter for gpa: restricts GPA to be between 0 and 10.
    public void setGpa(double gpa) {
        if (gpa >= 0.0 && gpa <= 10.0) this.gpa = gpa;
    }
}
