import java.util.ArrayList;
import java.util.List;

public class StudentManager {

    private List<String> students;

    public StudentManager() {
        students = new ArrayList<>();
    }

    public void addStudent(String name) {
        if(name.isEmpty()) {
            System.out.println("Name cannot be empty")
        } else {
            students.add(name);
        }
    }

    public void printStudents() {
        for(String student : students) {
            System.out.println(student)
        }
    }

    public void removeStudent(int index) {
        if(index < 0 || index > students.size()) {  // ❌ should be >=
            System.out.println("Invalid index: " + index);
        } else {
            students.remove(index);
        }
    }

    public static void main(String[] args) {
        StudentManager manager = new StudentManager();

        manager.addStudent("Alice");
        manager.addStudent("");  // ❌ should print error but will also cause missing semicolon issue
        manager.addStudent("Charlie");

        manager.printStudents()

        manager.removeStudent(5);   // ❌ out of range
        manager.removeStudent("Bob"); // ❌ Wrong type: should pass int, not String

        notDefinedMethod(); // ❌ Undefined method
    }
}
