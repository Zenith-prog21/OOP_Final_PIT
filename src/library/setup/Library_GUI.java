package library.setup;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class Library_GUI extends JFrame {

    File_Manager fm = new File_Manager();

    ArrayList<Book_Setup> books = fm.setup_books();
    ArrayList<Student_Log> students = fm.setup_student();

    String bookFile = "Booklist.txt";
    String studentFile = "Studentlist.txt";

    CardLayout cardLayout = new CardLayout();
    JPanel mainPanel = new JPanel(cardLayout);
    DefaultTableModel bookTable;

    public Library_GUI() {
        setTitle("Library Book Manager");
        setSize(850, 500);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        mainPanel.add(homePanel(), "HOME");
        mainPanel.add(availableBooksPanel("HOME"), "AVAILABLE");
        mainPanel.add(borrowPanel(), "BORROW");
        mainPanel.add(returnPanel(), "RETURN");
        mainPanel.add(adminPanel(), "ADMIN");

        add(mainPanel);
        cardLayout.show(mainPanel, "HOME");
    }

    private JTable bookListTable() {
        String[] columns = {"ID", "Title", "Genre", "Author", "Quantity", "Available"};

        bookTable = new DefaultTableModel(columns, 0);

        JTable table = new JTable(bookTable);

        table.getColumnModel().getColumn(0).setPreferredWidth(40);   // ID
        table.getColumnModel().getColumn(1).setPreferredWidth(220);  // Title
        table.getColumnModel().getColumn(2).setPreferredWidth(120);  // Genre
        table.getColumnModel().getColumn(3).setPreferredWidth(180);  // Author
        table.getColumnModel().getColumn(4).setPreferredWidth(80);   // Quantity
        table.getColumnModel().getColumn(5).setPreferredWidth(80);   // Available

        return table;
    }

    private void updateBookTable(String searchText) {
        bookTable.setRowCount(0);

        searchText = searchText.toLowerCase().trim();

        for (Book_Setup book : books) {
            String titleText = book.Book_Name.toLowerCase();
            String genreText = book.Book_Genre.toLowerCase();
            String authorText = book.Author.toLowerCase();

            if (titleText.contains(searchText) ||
                    genreText.contains(searchText) ||
                    authorText.contains(searchText)) {

                bookTable.addRow(new Object[]{
                        book.Book_ID,
                        book.Book_Name,
                        book.Book_Genre,
                        book.Author,
                        book.Book_Quantity,
                        book.Book_Available
                });
            }
        }
    }

    // =========================
    // HOME PAGE
    // =========================
    private JPanel homePanel() {
        JPanel panel = new JPanel(new GridLayout(5, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(40, 150, 40, 150));
        panel.setBackground(Color.WHITE);
        JLabel title = new JLabel("Library Book Manager", SwingConstants.CENTER);
        title.setFont(new Font("Georgia", Font.BOLD, 24));
        title.setForeground(Color.BLACK);

        JButton availableButton = new JButton("Check Available Books");
        JButton borrowButton = new JButton("Borrow Book");
        JButton returnButton = new JButton("Return Book");
        JButton adminButton = new JButton("Admin");

        availableButton.setPreferredSize(new Dimension(150, 30));
        borrowButton.setPreferredSize(new Dimension(150, 30));
        returnButton.setPreferredSize(new Dimension(150, 30));
        adminButton.setPreferredSize(new Dimension(150, 30));

        availableButton.addActionListener(e -> {
            refreshData();
            mainPanel.add(availableBooksPanel("HOME"), "AVAILABLE");
            cardLayout.show(mainPanel, "AVAILABLE");
        });

        borrowButton.addActionListener(e -> {
            refreshData();
            mainPanel.add(borrowPanel(), "BORROW");
            cardLayout.show(mainPanel, "BORROW");
        });

        returnButton.addActionListener(e -> {
            refreshData();
            mainPanel.add(returnPanel(), "RETURN");
            cardLayout.show(mainPanel, "RETURN");
        });

        adminButton.addActionListener(e -> {
            refreshData();
            mainPanel.add(adminPanel(), "ADMIN");
            cardLayout.show(mainPanel, "ADMIN");
        });

        panel.add(title);
        panel.add(availableButton);
        panel.add(borrowButton);
        panel.add(returnButton);
        panel.add(adminButton);

        return panel;
    }

    // =========================
    // AVAILABLE BOOKS PAGE
    // =========================
    private JPanel availableBooksPanel(String status) {
        JPanel panel = new JPanel(new BorderLayout(10,10));
        panel.setBorder(BorderFactory.createEmptyBorder(30, 20, 20, 20));

        JLabel title = new JLabel("Available Books", SwingConstants.CENTER);
        title.setFont(new Font("Georgia", Font.BOLD, 20));

        JTextField searchField = new JTextField();
        JButton searchButton = new JButton("Search");
        JButton backButton = new JButton("Back");

        JTable table = bookListTable();

        updateBookTable(searchField.getText()); // Generates the table

        searchButton.addActionListener(e -> updateBookTable(searchField.getText()));
        searchField.addActionListener(e -> updateBookTable(searchField.getText()));

        JPanel searchPanel = new JPanel(new BorderLayout());
        searchPanel.add(new JLabel("Search Title/Author: "), BorderLayout.WEST);
        searchPanel.add(searchField, BorderLayout.CENTER);
        searchPanel.add(searchButton, BorderLayout.EAST);

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(title, BorderLayout.NORTH);
        topPanel.add(searchPanel, BorderLayout.SOUTH);


            backButton.addActionListener(e -> cardLayout.show(mainPanel, status));

        panel.add(topPanel, BorderLayout.NORTH);
        panel.add(new JScrollPane(table), BorderLayout.CENTER);
        panel.add(backButton, BorderLayout.SOUTH);

        return panel;
    }


    // =========================
    // BORROW BOOK PAGE
    // =========================
    private JPanel borrowPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(30, 20, 20, 20));


        JLabel title = new JLabel("Borrow Book", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 20));

        JPanel form = new JPanel(new GridLayout(5, 2, 10, 10));
        form.setBorder(BorderFactory.createEmptyBorder(40, 100, 40, 100));

        JTextField nameField = new JTextField();
        JTextField idField = new JTextField();
        JTextField bookIdField = new JTextField();

        JButton backButton = new JButton("Back");
        JButton borrowButton = new JButton("Borrow");
        JButton availableButton = new JButton("Check Available Books");
        availableButton.setPreferredSize(new Dimension(200, 40));


        form.add(new JLabel("Student Name:"));
        form.add(nameField);

        form.add(new JLabel("Student ID:"));
        form.add(idField);

        form.add(new JLabel("Book ID:"));
        form.add(bookIdField);

        form.add(backButton);
        form.add(borrowButton);

        borrowButton.addActionListener(e -> {
            String name = nameField.getText().trim();
            String idText = idField.getText().trim();
            String bookIdText = bookIdField.getText().trim();

            if (name.isEmpty() || idText.isEmpty() || bookIdText.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill in all fields.");
                return;
            }

            int studentId;
            int bookId;

            try {
                studentId = Integer.parseInt(idText);
                bookId = Integer.parseInt(bookIdText);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Student ID and Book ID must be numbers.");
                return;
            }

            Book_Setup selectedBook = findBookById(bookId);

            if (selectedBook == null) {
                JOptionPane.showMessageDialog(this, "Book not found.");
                return;
            }

            if (selectedBook.Book_Available <= 0) {
                JOptionPane.showMessageDialog(this, "Book is not available.");
                return;
            }

            selectedBook.Book_Available--;

            students.add(new Student_Log(
                    name,
                    studentId,
                    selectedBook.Book_Name,
                    selectedBook.Book_ID,
                    "BORROW"
            ));

            saveAll();

            JOptionPane.showMessageDialog(this, "Book borrowed successfully.");

            nameField.setText("");
            idField.setText("");
            bookIdField.setText("");
        });

        availableButton.addActionListener(e -> {
            refreshData();
            mainPanel.add(availableBooksPanel("BORROW"), "AVAILABLE");
            cardLayout.show(mainPanel, "AVAILABLE");
        });

        backButton.addActionListener(e -> cardLayout.show(mainPanel, "HOME"));

        panel.add(title, BorderLayout.NORTH);
        panel.add(form, BorderLayout.CENTER);
        panel.add(availableButton, BorderLayout.SOUTH);


        return panel;
    }

    // =========================
    // RETURN BOOK PAGE
    // =========================
    private JPanel returnPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        JLabel title = new JLabel("Return Book", SwingConstants.CENTER);
        title.setFont(new Font("Georgia", Font.BOLD, 20));

        JPanel form = new JPanel(new GridLayout(4, 2, 10, 10));
        form.setBorder(BorderFactory.createEmptyBorder(40, 100, 40, 100));

        JTextField nameField = new JTextField();
        JTextField idField = new JTextField();
        JTextField bookIdField = new JTextField();

        JButton backButton = new JButton("Back");
        JButton returnButton = new JButton("Return");

        form.add(new JLabel("Student Name:"));
        form.add(nameField);

        form.add(new JLabel("Student ID:"));
        form.add(idField);

        form.add(new JLabel("Book ID:"));
        form.add(bookIdField);

        form.add(backButton);
        form.add(returnButton);

        returnButton.addActionListener(e -> {
            String name = nameField.getText().trim();
            String idText = idField.getText().trim();
            String bookIdText = bookIdField.getText().trim();

            if (name.isEmpty() || idText.isEmpty() || bookIdText.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill in all fields.");
                return;
            }

            int studentId;
            int bookId;

            try {
                studentId = Integer.parseInt(idText);
                bookId = Integer.parseInt(bookIdText);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Student ID and Book ID must be numbers.");
                return;
            }

            Book_Setup selectedBook = findBookById(bookId);

            if (selectedBook == null) {
                JOptionPane.showMessageDialog(this, "Book not found.");
                return;
            }

            if (selectedBook.Book_Available >= selectedBook.Book_Quantity) {
                JOptionPane.showMessageDialog(this, "All copies of this book are already returned.");
                return;
            }

            selectedBook.Book_Available++;

            students.add(new Student_Log(
                    name,
                    studentId,
                    selectedBook.Book_Name,
                    selectedBook.Book_ID,
                    "RETURN"
            ));

            saveAll();

            JOptionPane.showMessageDialog(this, "Book returned successfully.");

            nameField.setText("");
            idField.setText("");
            bookIdField.setText("");
        });

        backButton.addActionListener(e -> cardLayout.show(mainPanel, "HOME"));

        panel.add(title, BorderLayout.NORTH);
        panel.add(form, BorderLayout.CENTER);

        return panel;
    }

    // =========================
    // ADMIN PANEL
    // =========================
    private JPanel adminPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        JLabel title = new JLabel("Admin Panel", SwingConstants.CENTER);
        title.setFont(new Font("Georgia", Font.BOLD, 20));

        JTabbedPane tabs = new JTabbedPane();

        tabs.add("Book List", adminBookListPanel());
        tabs.add("Student Logs", adminLogsPanel());
        tabs.add("Add Book", addBookPanel());

        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> cardLayout.show(mainPanel, "HOME"));

        panel.add(title, BorderLayout.NORTH);
        panel.add(tabs, BorderLayout.CENTER);
        panel.add(backButton, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel adminBookListPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        String[] columns = {"ID", "Title", "Author", "Quantity", "Available"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);
        JTable table = new JTable(model);

        for (Book_Setup book : books) {
            model.addRow(new Object[]{
                    book.Book_ID,
                    book.Book_Name,
                    book.Author,
                    book.Book_Quantity,
                    book.Book_Available
            });
        }

        JButton removeButton = new JButton("Remove Selected Book");

        removeButton.addActionListener(e -> {
            int row = table.getSelectedRow();

            if (row == -1) {
                JOptionPane.showMessageDialog(this, "Select a book first.");
                return;
            }

            int bookId = (int) model.getValueAt(row, 0);

            books.removeIf(book -> book.Book_ID == bookId);
            saveAll();

            JOptionPane.showMessageDialog(this, "Book removed.");

            mainPanel.add(adminPanel(), "ADMIN");
            cardLayout.show(mainPanel, "ADMIN");
        });

        panel.add(new JScrollPane(table), BorderLayout.CENTER);
        panel.add(removeButton, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel adminLogsPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        String[] columns = {"Student Name", "Student ID", "Book", "Book ID", "Action"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);
        JTable table = new JTable(model);

        for (Student_Log log : students) {
            model.addRow(new Object[]{
                    log.student_name,
                    log.id_number,
                    log.book_name,
                    log.book_id,
                    log.actionType
            });
        }

        panel.add(new JScrollPane(table), BorderLayout.CENTER);

        return panel;
    }

    private JPanel addBookPanel() {
        JPanel panel = new JPanel(new GridLayout(5, 3, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(40, 100, 40, 100));

        JTextField titleField = new JTextField();
        JTextField genreField = new JTextField();
        JTextField authorField = new JTextField();
        JTextField quantityField = new JTextField();

        JButton addButton = new JButton("Add Book");

        panel.add(new JLabel("Book Title:"));
        panel.add(titleField);

        panel.add(new JLabel("Book Genre:"));
        panel.add(genreField);

        panel.add(new JLabel("Author:"));
        panel.add(authorField);

        panel.add(new JLabel("Quantity:"));
        panel.add(quantityField);

        panel.add(new JLabel(""));
        panel.add(addButton);

        addButton.addActionListener(e -> {
            String author = authorField.getText().trim();
            String genre = genreField.getText().trim();
            String title = titleField.getText().trim();
            String quantityText = quantityField.getText().trim();

            if (title.isEmpty() || genre.isEmpty() || author.isEmpty() || quantityText.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill in all fields.");
                return;
            }

            int quantity;

            try {
                quantity = Integer.parseInt(quantityText);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Quantity must be a number.");
                return;
            }

            if (quantity <= 0) {
                JOptionPane.showMessageDialog(this, "Quantity must be greater than 0.");
                return;
            }

            int newId = getNextBookId();

            books.add(new Book_Setup(
                    newId,
                    title,
                    genre,
                    author,
                    quantity,
                    quantity
            ));

            saveAll();

            JOptionPane.showMessageDialog(this, "Book added.");

            titleField.setText("");
            authorField.setText("");
            quantityField.setText("");
        });

        return panel;
    }

    // Encapsulated Methods
    private Book_Setup findBookById(int id) {
        for (Book_Setup book : books) {
            if (book.Book_ID == id) {
                return book;
            }
        }

        return null;
    }

    private int getNextBookId() {
        int highestId = 0;

        for (Book_Setup book : books) {
            if (book.Book_ID > highestId) {
                highestId = book.Book_ID;
            }
        }

        return highestId + 1;
    }

    private void saveAll() {
        fm.save_changes(books, bookFile, 1);
        fm.save_changes(students, studentFile, 2);
    }

    private void refreshData() {
        books = fm.setup_books();
        students = fm.setup_student();
    }

    public static void main(String[] args) {
        new Library_GUI().setVisible(true);
    }
}