package Main;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

public class Operation {

    private ArrayList<Book> bookList;
    private ArrayList<User> userList;
    private Book tempBook;
    private Member tempUser;
    private LocalDate systemTime;

    private Operation(){


        this.bookList = new ArrayList<>();
        this.userList = new ArrayList<>();
        this.systemTime = LocalDate.now();
        login();


    }

    private void login(){
        Scanner scan = new Scanner(System.in);
        loginMenu();
        while(true){
            //read input
            int choice = scan.nextInt();
            if (choice == 3){
                System.out.println("Successful exit");
                break;
            }
            switch (choice){
                case 1: staffLogin(); break;
                case 2: memberLogin(); break;
            }
        }
    }

    private void loginMenu(){
        //print login menu
        System.out.println("Welcome login, please enter your User type");
        System.out.println("Staff...1");
        System.out.println("Member...2");
        System.out.println("Exit...3");
    }

    private void staffLogin(){
        //staff login
        System.out.println("Please enter your userName and password...");
        Scanner scan = new Scanner(System.in);
        System.out.println("userName...");
        String userName = scan.next();
        boolean existence = checkAccount(userName,"staff");
        if (!existence){
            createAccount(userName, "staff");
        }
        login();
    }



    private void memberLogin(){
        //Member login
        System.out.println("Please enter your userName and password...");
        Scanner scan = new Scanner(System.in);
        System.out.println("userName...");
        String userName = scan.next();
        boolean existence = checkAccount(userName, "member");
        if (!existence){
            createAccount(userName, "member");
        }
        login();
    }

    private void createAccount(String userName, String userType) {
        //helper function for User login to create a new account
        Scanner scan = new Scanner(System.in);
        System.out.println("User not found, do you want to create a new account?");
        System.out.println("Yes...1");
        System.out.println("No...2");
        int choice = scan.nextInt();
        if (choice == 1) {
            if (userType.equals("staff")){
                //create a staff account and enter staff menu
                User newUser = new User(userName);
                setPassword(newUser, userType);
                staffMenu();
            }else {
                //create a member account and enter member menu
                User newUser = new Member(userName);
                setPassword(newUser, userType);
                //cast must not be removed as this only run when user types is "member"
                //the cast warning can be ignored
                this.tempUser = (Member) newUser;
                memberMemu();
            }
        }
    }

    private void setPassword(User newUser, String userType){
        //set password, helper function
        Scanner scan = new Scanner(System.in);
        System.out.println("Please set your password...");
        String passwordScan = scan.next();
        newUser.setKey(passwordScan);
        newUser.setType(userType);
        System.out.println("Successfully login...");
        this.userList.add(newUser);
    }

    private boolean checkAccount(String userNameScan, String userType){
        //helper function for check the account, return true if the account exist
        Scanner scan = new Scanner(System.in);
        boolean existence = false;
        for (User users : this.userList){
            if (users.userName.equals(userNameScan)){
                if (!users.userType.equals(userType)){
                    System.out.println("Wrong User type, please re-enter.");
                    login();
                    break;
                }else{
                    System.out.println("password...");
                    String passwordScan = scan.next();
                    if (!users.checkKey(passwordScan)){
                        System.out.println("Wrong password, please re-enter.");
                        login();
                        break;
                    }else{
                        System.out.println("Successfully login...");
                        existence = true;
                        if (userType.equals("staff")){
                            staffMenu();
                            break;
                        } else {
                            this.tempUser = (Member) users;
                            memberMemu();
                            break;
                        }
                    }
                }
            }
        }
        return existence;
    }

    private void staffMenu(){
        //menu for staff
        Scanner scan = new Scanner(System.in);

        System.out.println("Welcome to staff menu...");
        System.out.println("Add Book...1");
        System.out.println("Update Book...2");
        System.out.println("Pay penalties...3");
        System.out.println("Exit...4");
        System.out.println("Set Time...5");

        while (true){
            int choice = scan.nextInt();
            if (choice == 4){
                System.out.println("Successful exit");
                login();
                break;
            }
            switch (choice){
                case 1: addBook(); break;
                case 2: updateBook(); break;
                case 3: payPenalty(); break;
                case 5:
                    setTime();
                    staffMenu();
                    break;
                default: System.out.println("Error"); staffMenu();
            }
        }

    }

    private void memberMemu(){
        //menu for Member
        Scanner scan = new Scanner(System.in);
        System.out.println("Welcome to Member menu...");
        //show member information
        memberInfo();
        //print out member menu
        System.out.println("Request Book...1");
        System.out.println("Return Book...2");
        System.out.println("Extend date...3");
        System.out.println("Remove waitList...4");
        System.out.println("Exit...5");
        System.out.println("Set Time...6");

        while (true){
            int choice = scan.nextInt();
            if (choice == 5){
                System.out.println("Successful exit");
                login();
                break;
            }
            switch (choice){
                case 1: requestBook(); break;
                case 2: returnBook(); break;
                case 3: extendDate(); break;
                case 4: removeWaitList(); break;
                case 6:
                    setTime();
                    memberMemu();
                    break;
                default: System.out.println("Error"); memberMemu();
            }
        }
    }

    private void setTime(){
        Scanner scan = new Scanner(System.in);
        System.out.println("Please set today's date...");
        System.out.println("Year...");
        int year = scan.nextInt();
        System.out.println("Month...");
        int month = scan.nextInt();
        System.out.println("Day...");
        int day = scan.nextInt();
        this.systemTime = LocalDate.of(year, month, day);
    }

    private void addBook(){
        //staff only, add Book to Book list
        System.out.println("Add Book...");
        Scanner scan = new Scanner(System.in);
        scan.useDelimiter("\n");
        System.out.println("Please enter Book title...");
        String bookTitle = scan.next();
        Book newBook = new Book(bookTitle);
        updateDescription(newBook);
        updateCopies(newBook);
        updateLocation(newBook);
        this.bookList.add(newBook);
        //end of add

        //ask for return or add a new one
        System.out.println("Do you want to add another Book?");
        menuBiChoice();
        int choice = scan.nextInt();
        if (choice == 1) {
            addBook();
        }else {
            staffMenu();
        }
    }

    private boolean searchBookList(String bookTitle){
        //search for a certain Book of book_title, return true if exists
        for (Book books : this.bookList){
            String title = books.getTitle();
            if (title.equals(bookTitle)){
                this.tempBook = books;
                return true;
            }
        }
        return false;
    }

    private void updateBook(){
        //staff only, update certain Book information
        System.out.println("Update Book...");
        Scanner scan = new Scanner(System.in);
        scan.useDelimiter("\n");
        System.out.println("Please enter the Book title...");
        String bookTitle = scan.next();
        if (searchBookList(bookTitle)){
            updateInfo(this.tempBook);
        }else {
            System.out.println("No Book of this title found...");
            System.out.println("Return to stuff menu...");
            staffMenu();
        }
    }

    private void updateTitle(Book bookUpdate){
        //staff only, update book title
        Scanner scan = new Scanner(System.in);
        scan.useDelimiter("\n");
        System.out.println("Please enter the Book title...");
        String bookTitle = scan.next();
        bookUpdate.setTitle(bookTitle);
    }

    private void updateDescription(Book bookUpdate){
        //staff only, update book description
        Scanner scan = new Scanner(System.in);
        scan.useDelimiter("\n");
        System.out.println("Please enter the Book description...");
        String bookDescription = scan.next();
        bookUpdate.setDescription(bookDescription);
    }

    private void updateCopies(Book bookUpdate){
        //staff only, update copies
        Scanner scan = new Scanner(System.in);
        System.out.println("Please enter the copies available...");
        System.out.println("Please enter integer");
        int newCopies = scan.nextInt();
        bookUpdate.setCopiesAvl(newCopies);
    }

    private void updateLocation(Book bookUpdate){
        //staff only, update location
        Scanner scan = new Scanner(System.in);
        scan.useDelimiter("\n");
        System.out.println("Please enter the Book location...");
        String bookLocation = scan.next();
        bookUpdate.setLocation(bookLocation);
    }

    private void updateInfo(Book bookUpdate){
        //staff only, update further information for a Book
        Scanner scan = new Scanner(System.in);
        scan.useDelimiter("\n");
        System.out.println("Update Book "+bookUpdate.title);
        System.out.println("Please select the field you want to update...");
        System.out.println("Book title...1");
        System.out.println("Book description...2");
        System.out.println("Copies available...3");
        System.out.println("Branch Location...4");
        System.out.println("Exit...5");
        int choice = scan.nextInt();
        switch (choice){
            case 1:
                //update title
                System.out.println("Current title : "+bookUpdate.getTitle());
                updateTitle(bookUpdate);
                updateInfo(bookUpdate);
                break;
            case 2:
                //update description
                System.out.println("Current description : "+bookUpdate.getDescription());
                updateDescription(bookUpdate);
                updateInfo(bookUpdate);
                break;
            case 3:
                //update copies
                System.out.println("Current copies available : "+bookUpdate.getCopiesAvl());
                updateCopies(bookUpdate);
                updateInfo(bookUpdate);
                break;
            case 4:
                //update location
                System.out.println("Current branch location : "+bookUpdate.getLocation());
                updateLocation(bookUpdate);
                updateInfo(bookUpdate);
            case 5:
                staffMenu();
            default: System.out.println("Error"); staffMenu();

        }
    }

    private void menuBiChoice(){
        //yes no menu
        System.out.println("Yes...1");
        System.out.println("No...2");
    }

    private void requestBook(){
        //Member only, request a Book
        Scanner scan = new Scanner(System.in);
        scan.useDelimiter("\n");
        System.out.println("Search for the Book...");
        String bookTitle = scan.next();
        if (searchBookList(bookTitle)){
            System.out.println("Found Book "+bookTitle);
            operateBook(this.tempBook);
        }else {
            System.out.println("Cannot find the Book.");
            memberMemu();
        }
    }

    private void operateBook(Book book){
        //checkout or waitList the Book
        if (book.availability()){
            checkoutBook(book);
        }else {
            waitListBook(book);
        }
    }

    private void checkoutBook(Book book){
        //checkout the Book
        Scanner scan = new Scanner(System.in);
        scan.useDelimiter("\n");
        Member user = this.tempUser;
        System.out.println("The Book is available, do you want to checkout the Book");
        menuBiChoice();
        int choice = scan.nextInt();
        if (choice == 1) {
            book.addCheckout(user);
            user.setCheckout(book, new Days());
            System.out.println("Successfully checkout");
            memberMemu();
        }else {
            memberMemu();
        }

    }

    private void waitListBook(Book book){
        //add a book to wait list
        Scanner scan = new Scanner(System.in);
        scan.useDelimiter("\n");
        Member user = this.tempUser;
        System.out.println("The Book is unavailable, do you want to join the waitList");
        menuBiChoice();
        int choice = scan.nextInt();
        if (choice == 1) {
            book.addWaitList(user);
            user.setWaitList(book);
            System.out.println("Successfully waitList");
            memberMemu();
        } else {
            memberMemu();
        }
    }

    private void returnBook(){
        //return the Book
        Scanner scan = new Scanner(System.in);
        scan.useDelimiter("\n");
        System.out.println("Search for the Book...");
        String bookTitle = scan.next();
        if (returnSearch(bookTitle)){
            System.out.println("Found Book "+bookTitle);
            returnBookHelper(this.tempBook);
        }else {
            System.out.println("Cannot find the Book.");
            memberMemu();
        }
    }

    private boolean returnSearch(String bookTitle){
        //search checklist, return true if found the book in checklist
        Member user = this.tempUser;
        for (Tuple info : user.checkout){
            String title = info.book.getTitle();
            if (title.equals(bookTitle)){
                return true;
            }
        }
        return false;
    }

    private void returnBookHelper(Book book){
        //helper function for return book
        Member user = this.tempUser;
        for (Tuple bookCheckout : this.tempUser.checkout){
            bookCheckout.book.removeCheckout(user);
            user.removeCheckout(book);
            System.out.println("Successfully returned");
            memberMemu();
        }
    }

    private void extendDate(){
        //member only, extend the date 14 more days
        Scanner scan = new Scanner(System.in);
        scan.useDelimiter("\n");
        System.out.println("Search for the Book...");
        String bookTitle = scan.next();
        if (searchBookList(bookTitle)){
            System.out.println("Found Book "+bookTitle);
            extendDateHelper(this.tempBook);
        }else {
            System.out.println("Cannot find the Book.");
            memberMemu();
        }
    }

    private void extendDateHelper(Book book){
        //helper function for extend date
        for (Tuple bookCheckout : this.tempUser.checkout){
            if (book.equals(bookCheckout.book)){
                boolean sitWaitList = book.sitWaitList();
                if (bookCheckout.dates.extension(sitWaitList)){
                    System.out.println("Successfully extended");
                    memberMemu();
                }else {
                    System.out.println("Fail to extend date");
                    memberMemu();
                }
            }
        }
    }

    private void payPenalty(){
        //pay penalty for a certain Member
        Scanner scan = new Scanner(System.in);
        System.out.println("Pay penalty for members...");
        System.out.println("Please enter the userName...");
        String username =  scan.next();
        for (User userListed : this.userList){
            if (userListed.userName.equals(username)){
                if (userListed.userType.equals("staff")){
                    System.out.println("Wrong User type, please re-enter");
                    staffMenu();
                    break;
                }else {
                    Member userMember = (Member) userListed;
                    userMember.payPenalty();
                    System.out.println("Successfully paid the penalty");
                    staffMenu();
                }
            }
        }
    }

    private void memberInfo(){
        //show Member info when the Member login
        memberWaitList();
        memberPenalty();
        memberDue();
    }

    private void memberWaitList(){
        //return the waitList availability
        System.out.println("Wait list Book availability list: ");
        for (Book book : this.tempUser.waitList){
            String available = available(book);
            System.out.println(book.getTitle()+available);
        }
    }

    private String available(Book book){
        //return Book availability
        if (book.availability()){
            return  "Available";
        }else {
            return  "Unavailable";
        }
    }

    private void memberPenalty(){
        //calculate and show penalty of the User
        this.tempUser.calPenalty(this.systemTime);
        double penalty = this.tempUser.getPenalty();
        System.out.println("Your penalty is : $"+penalty);
    }

    private void memberDue(){
        //show a message of Book due
        System.out.println("These books are due within next 2 Days:");
        ArrayList<String> within = this.tempUser.bookWithin();
        for (String bookTitle : within){
            System.out.println(bookTitle);
        }
        System.out.println("These books have already past due:");
        ArrayList<String> due = this.tempUser.bookDue();
        for (String bookTitle : due){
            System.out.println(bookTitle);
        }
    }

    private void removeWaitList(){
        //remove from waitList
        Scanner scan = new Scanner(System.in);
        scan.useDelimiter("\n");
        Member user = this.tempUser;
        System.out.println("Please enter the Book you want to remove from waitList");
        String book_title = scan.next();
        if (waitListSearch(book_title)){
            Book book = this.tempBook;
            menuBiChoice();
            int choice = scan.nextInt();
            if (choice == 1) {
                book.removeWaitList(user);
                user.removeWaitList(book);
                System.out.println("Successfully removed from waitList");
                memberMemu();
            } else {
                memberMemu();
            }
        }else {
            System.out.println("Cannot find the Book");
            memberMemu();
        }
    }

    private boolean waitListSearch(String bookTitle){
        //search book in the wait list, return true if found the book
        Member user = this.tempUser;
        for (Book books : user.waitList){
            String title = books.getTitle();
            if (title.equals(bookTitle)){
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {

        //run the function
        new Operation();
    }

}
