package library.setup;


import java.io.*;
import java.util.ArrayList;

public class File_Manager {
    String Book_file = "Booklist.txt";
    String Student_file = "Studentlist.txt";

    public ArrayList<Book_Setup> setup_books(){
        ArrayList<Book_Setup> books = new ArrayList<Book_Setup>();
        try (BufferedReader Book_log = new BufferedReader(new FileReader(Book_file))) {
                String buffer;
                Book_log.readLine();
                while ((buffer = Book_log.readLine()) != null) {
                    if (buffer.trim().isEmpty()) {
                        continue;
                    }
                    String[] part = buffer.split("\\|");
                    int Book_ID = Integer.parseInt(part[0].trim());
                    String Book_name = part[1].trim();
                    String Author = part[2].trim();
                    int Book_Quantity = Integer.parseInt(part[3].trim());
                    int Book_Available = Integer.parseInt(part[4].trim());
                    Book_Setup book = new Book_Setup(Book_ID, Book_name, Author, Book_Quantity, Book_Available);
                    books.add(book);
                }
        } catch (
                FileNotFoundException e) {
            System.out.println("File Not Found!");
        } catch (
                IOException e) {
            System.out.println("Error Opening File!");
        }
        return books;
    }

    public ArrayList<Student_Log> setup_student(){
        ArrayList<Student_Log> student = new ArrayList<Student_Log>();
        try (BufferedReader Student_log = new BufferedReader(new FileReader(Student_file))) {
            String buffer;
            Student_log.readLine();
            while ((buffer = Student_log.readLine()) != null) {
                if (buffer.trim().isEmpty()) {
                    continue;
                }
                String[] part = buffer.split("\\|");
                String student_name = part[0].trim();
                int id_number = Integer.parseInt(part[1].trim());
                String book_name = part[2].trim();
                int book_id = Integer.parseInt(part[3].trim());
                String actionType = part[4].trim();
                Student_Log Student_logger = new Student_Log(student_name, id_number, book_name, book_id, actionType);
                student.add(Student_logger);
            }
        } catch (
                FileNotFoundException e) {
            System.out.println("File Not Found!");
        } catch (
                IOException e) {
            System.out.println("Error Opening File!");
        }
        return student;
    }

//

    <T> void save_changes(ArrayList<T> changes, String filePath, int choice) {
        try (BufferedWriter file = new BufferedWriter(new FileWriter(filePath))) {
            System.out.println("Saving file");
            if(choice == 1){
                file.write("ID:   | Title:                                            | Author:                  | Quantity  | Available");
                file.newLine();
            } else if (choice == 2) {
                file.write("Name:                           | Student ID:              | Book Borrowed/Returned:  | Book ID:      | Action:           |");
                file.newLine();
            }
            //Need else for Student
            for (T item : changes) {
                file.write(item.toString());
                file.newLine();
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
