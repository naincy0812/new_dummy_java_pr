import java.util.*;

public class StudentManagement {

    private static List<Student> students = new ArrayList<>();

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("===== Student Management System =====");
        System.out.println("1. Add Student");
        System.out.println("2. Display Students");
        System.out.println("3. Find Student By ID");
        System.out.println("4. Exit");

        boolean running = true;
        while (running) {
            System.out.print("\nEnter choice: ");
            int choice = sc.nextInt();  // ⚠️ Bug: will throw InputMismatchException if input is not an integer
            sc.nextLine();

            if (choice == 1) {
                System.out.print("Enter ID: ");
                int id = sc.nextInt();
                sc.nextLine();

                System.out.print("Enter Name: ");
                String name = sc.nextLine();

                System.out.print("Enter Age: ");
                int age = sc.nextInt();

                Student s = new Student(id, name, age);
                students.add(s);
                System.out.println("Student added successfully!");

            } else if (choice == 2) {
                for (Student s : students) {
                    System.out.println(s.toString());
                }

            } else if (choice == 3) {
                System.out.print("Enter ID to search: ");
                int id = sc.nextInt();
                Student found = findStudentById(id);
                // ⚠️ Bug: No null-check, could cause NullPointerException
                System.out.println("Found: " + found.getName() + " (" + found.getAge() + ")");

            } else if (choice == 4) {
                running = false;
            } else {
                System.out.println("Invalid Choice!");
            }
        }

        // ⚠️ Bug: Scanner not closed properly (resource leak)
    }

    private static Student findStudentById(int id) {
        for (Student s : students) {
            if (s.getId() == id) {
                return s;
            }
        }
        return null; // ⚠️ Bug: Calling code doesn’t handle null
    }
}

class Student {
    private int id;
    private String name;
    private int age;

    // ⚠️ Style issue: missing validation (negative age possible)
    public Student(int id, String name, int age) {
        this.id = id;
        this.name = name;
        this.age = age; 
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name; 
    }

    public int getAge() {
        return age; 
    }

    @Override
    public String toString() {
        return "Student[ID=" + id + ", Name=" + name + ", Age=" + age + "]";
    }
}
