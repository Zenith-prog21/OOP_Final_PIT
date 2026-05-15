package library.setup;

public class Book_Setup {
    User_Input scan = new User_Input();
    int Book_ID = -134;
    int Book_Quantity = 0;
    int Book_Available = 0;
    String Book_Name = "";
    String Author = "";
    String Book_Genre = "";

    Book_Setup(){
        //
    }

    public Book_Setup(int ID, String Name, String Book_Genre, String Author, int Quantity, int Available) {
        this.Book_ID = ID;
        this.Book_Quantity = Quantity;
        this.Book_Available = Available;
        this.Book_Genre = Book_Genre;
        this.Book_Name = Name;
        this.Author = Author;
    }

    Book_Setup input_book(int ID) {
        Book_Name = scan.input_string("Enter the name of the book: ", 100);
        Book_Genre = scan.input_string("Enter the genre of the book: ", 100);
        Author = scan.input_string("Enter the Author of the book: ", 100);
        Book_Quantity = scan.input_integer("Enter amount of Books: ", 1, 100); // 100 is the max amount
        Book_Available = Book_Quantity;
        Book_ID = ID;
        return new Book_Setup(Book_ID, Book_Name, Book_Genre, Author, Book_Quantity, Book_Available);
    }


    // Check Availability of the Book
    Boolean check_availability(){
        if (Book_Available <= Book_Quantity && Book_Available > 0){
            return Boolean.TRUE;
        } else {
            return Boolean.FALSE;
        }
    }

    @Override
    public String toString() {
        return String.format("%-5d | %-50s| %-25s| %-25s| %-10d| %-4d",
                Book_ID, Book_Name, Book_Genre,  Author, Book_Quantity, Book_Available);
    }
}
