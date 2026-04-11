package library.setup;

public class Student_Log {
    User_Input scan = new User_Input();
    String student_name = "", book_name = "", actionType = "";
    int id_number = 0, book_id = 0;

    Student_Log(){
    }

    Student_Log(String name, int id_number, String book_borrowed, int book_id, String action){
        this.student_name = name;
        this.id_number = id_number;
        this.book_id = book_id;
        this.book_name = book_borrowed;
        this.actionType = action;
    }
//
    Student_Log student_borrow(int Book_ID, String Book_name, int amount_of_books, String action_type){
        this.book_id = Book_ID;
        this.book_name = Book_name;
        student_name = scan.input_string("Enter Name: ", 100);
        id_number = scan.input_integer("Enter your ID number: ", 1, amount_of_books);
        this.actionType = action_type;
        return new Student_Log(student_name, id_number, book_name, book_id, actionType);
    }

    @Override
    public String toString() {
        return String.format(" %-30s | %-24d | %-24s | %-13d | %-17s |",
                student_name, id_number, book_name, book_id, actionType);
    }

}
