package library.setup;
import java.util.ArrayList;


class Main{
  public static final int exit = 6;
    User_Input scan = new User_Input();
    File_Manager fm = new File_Manager();
    
    ArrayList<Book_Setup> Books  = fm.setup_books();
    ArrayList<Student_Log> student  = fm.setup_student();
    
    Book_Setup book_setup = new Book_Setup();
    Student_Log student_log = new Student_Log();

    
    // Constants
    String Book_file = "Booklist.txt";
    String Student_file = "Studentlist.txt";
    int Student = 2, book = 1;
    
    public static void main(String[] args) {
        Library_GUI.main(args); // swap to GUI entry point
    }

	/*
	 * public static void main(String[] args) { new Main().system_run(); }
	 */

     void system_run(){
        int choice;
//
        do {
        System.out.println("==================================================");
        System.out.println("                   Book Library                   ");
        System.out.println("==================================================");

            System.out.println("\n");
            System.out.println("Select an Action:");
            System.out.println("1. See Available Books");
            System.out.println("2. Borrow a Book");
            System.out.println("3. Return a Book");
            System.out.println("4. See Book Return Log");
            System.out.println("5. Add new Book");
            System.out.println("6. Exit");

            choice = scan.input_integer("Enter Choice: ", 1, 6);

            switch(choice) {
                case 1: See_Available_Books(); break;
                case 2: Borrow_return_Book(1); break;
                case 3: Borrow_return_Book(2); break;
                case 4: Display_Book_Log(); break;
                case 5: Add_book(); break;
                case 6:
                    System.out.println("Thank you for using our app!");
                    return;
                default:
                    System.out.println("Invalid input!! Try Again"); break;
            }
        } while (choice != exit);
    }



      // Borrow and Return Functions

      void Borrow_return_Book(int type){
          See_Available_Books();
          int BORROW = 1, RETURN = 2;
          int Book_ID;
          Book_Setup Selected_Book = null;
          String Book_name = "";

          if (type == BORROW) {
              System.out.println("Borrow Book:");
          } else {
              System.out.println("Return Book:");
          }


          Book_ID = scan.input_integer("Enter Book ID: ",1, Books.size());

          if (Book_ID == 0) {
        	  return;
          }
          for (Book_Setup Book: Books) {
              if(Book_ID == Book.Book_ID) {
                  if (!Book.check_availability() && type == BORROW){
                      System.out.println("Book Unavailable. Sorry!");
                        return;
                  } else if (!Book.check_availability() && type == RETURN) {
                      System.out.println("Error!! Book Quantity is already Full.");
                      return;
                  }

                      Selected_Book = Book;
                      Book_name = Book.Book_Name;

                  if (type == BORROW) {
                      Book.Book_Available--;
                  } else {
                      Book.Book_Available++;
                  }
                  break;
              }
          }

          if (Selected_Book == null && type == BORROW){
              System.out.println("Book not Found!");
              return;
          }

          if (type == 1) {
              student.add(student_log.student_borrow(Book_ID, Book_name, Books.size(), "BORROW"));
          } else {
              student.add(student_log.student_borrow(Book_ID, Book_name, Books.size(), "Return"));
          }
          save_student();
          save_book();

        return;
      }


      // Display Functions

      void Display_Book_Log(){
          System.out.println();
          System.out.println("====================================================================================================");
          for (Student_Log students : student){
              System.out.println(students);
          }
          System.out.println("====================================================================================================");
          System.out.println();
          return;
      }
       void See_Available_Books(){
           System.out.println();
           System.out.println("ID:  | Title:                                            | Genre:                   | Author:                  | Quantity  | Available");
           System.out.println("====================================================================================================");
           for (Book_Setup b : Books){
               System.out.println(b);
           }
           if (Books.isEmpty()){
               System.out.println("                                    There are No Books Yet...                                          ");
           }
           System.out.println("====================================================================================================");
           System.out.println();
           return;
       }



    // Add Book Function

    void Add_book() {
         int lastest_book_index = Books.size() + 1;
         Books.add(book_setup.input_book(lastest_book_index));
         save_book();
         return;
      }


    // Saving Functions

    void save_book() {
        fm.save_changes(Books, Book_file, book);
    }

    void save_student(){
        fm.save_changes(student, Student_file, Student);
    }


}

