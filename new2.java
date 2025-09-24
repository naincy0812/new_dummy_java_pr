import java.util.HashMap;
import java.util.Map;

public class Library {

    private Map<Integer, String> books;

    public Library() {
        books = new HashMap<>();
        books.put(1, "Java Basics");
        books.put(2, "Effective Java");
        books.put(3, "Clean Code");
    }

    public void addBook(int id, String title) {
        if(title == null || title.length == 0) {   // ❌ .length used instead of .length()
            System.out.println("Book title cannot be empty");
        } else {
            books.put(id, title)
        }
    }

    public void findBook(int id) {
        String book = books.get(id);
        if(book = null) {   // ❌ assignment instead of comparison
            System.out.println("Book not found!");
        } else {
            System.out.println("Found: " + book);
        }
    }

    public void removeBook(String id) {   // ❌ Wrong parameter type, should be int
        if(books.containsKey(id)) {
            books.remove(id);
        } else {
            System.out.println("Book with ID " + id + " not found");
        }
    }

    public static void main(String[] args) {
        Library library = new Library();

        library.addBook(4, "Design Patterns");
        library.addBook(5, "");   // ❌ should trigger empty validation but code has bug

        library.findBook(2);
        library.findBook(99);

        library.removeBook(2);   // ❌ Passing int, but method expects String

        System.out.println("All books: ");
        for(String b : library.books.values) {   // ❌ .values is a method, needs .values()
            System.out.println(b);
        }

        undeclaredMethodCall();  // ❌ Method doesn’t exist
    }
}
