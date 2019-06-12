package Main;

import java.util.ArrayList;
import java.util.Scanner;

public class Operation {

    private ArrayList<Book> bookList;
    private ArrayList<User> userList;
    private Book tempBook;
    private Member tempUser;

    private Operation(){


        this.bookList = new ArrayList<>();
        this.userList = new ArrayList<>();

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
        System.out.println("Please enter your username and password...");
        Scanner scan = new Scanner(System.in);
        System.out.println("username...");
        String userName = scan.next();
        boolean existence = checkAccount(userName,"staff");
        if (!existence){
            createAccount(userName, "staff");
        }
        login();
    }



    private void memberLogin(){
        //Member login
        System.out.println("Please enter your username and password...");
        Scanner scan = new Scanner(System.in);
        System.out.println("username...");
        String userName = scan.next();
        boolean existence = checkAccount(userName, "Member");
        if (!existence){
            createAccount(userName, "Member");
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
                User newUser = new User(userName);
                setPassword(newUser, userType);
                staff_menu();
            }else {
                User new_user = new Member(userName);
                setPassword(new_user, userType);
                this.tempUser = (Member) new_user;
                member_memu();
            }
        }
    }

    private void setPassword(User new_user, String user_type){
        //set password, helper function
        Scanner scan = new Scanner(System.in);
        System.out.println("Please set your password...");
        String password_scan = scan.next();
        new_user.set_key(password_scan);
        new_user.set_type(user_type);
        System.out.println("Successfully login...");
        this.userList.add(new_user);
    }

    private boolean checkAccount(String username_scan, String user_type){
        //helper function for check the account, return true if the account exist
        Scanner scan = new Scanner(System.in);
        boolean existence = false;
        for (User user_listed : this.userList){
            if (user_listed.username.equals(username_scan)){
                if (!user_listed.user_type.equals(user_type)){
                    System.out.println("Wrong User type, please re-enter.");
                    login();
                    break;
                }else{
                    System.out.println("password...");
                    String password_scan = scan.next();
                    if (!user_listed.check_key(password_scan)){
                        System.out.println("Wrong password, please re-enter.");
                        login();
                        break;
                    }else{
                        System.out.println("Successfully login...");
                        existence = true;
                        if (user_type.equals("staff")){
                            staff_menu();
                            break;
                        } else {
                            this.tempUser = (Member) user_listed;
                            member_memu();
                            break;
                        }
                    }
                }
            }
        }
        return existence;
    }

    private void staff_menu(){
        //menu for staff
        Scanner scan = new Scanner(System.in);

        System.out.println("Welcome to staff menu...");
        System.out.println("Add Book...1");
        System.out.println("Update Book...2");
        System.out.println("Pay penalties...3");
        System.out.println("Exit...4");

        while (true){
            int choice = scan.nextInt();
            if (choice == 4){
                System.out.println("Successful exit");
                login();
                break;
            }
            switch (choice){
                case 1: add_book(); break;
                case 2: update_book(); break;
                case 3: pay_penalty_s(); break;
                default: System.out.println("Error"); staff_menu();
            }
        }

    }

    private void member_memu(){
        //menu for Member
        Scanner scan = new Scanner(System.in);
        System.out.println("Welcome to Member menu...");
        member_info();
        System.out.println("Request Book...1");
        System.out.println("Return Book...2");
        System.out.println("Extend date...3");
        System.out.println("Remove waitList...4");
        System.out.println("Exit...5");

        while (true){
            int choice = scan.nextInt();
            if (choice == 5){
                System.out.println("Successful exit");
                login();
                break;
            }
            switch (choice){
                case 1: request_book(); break;
                case 2: return_book(); break;
                case 3: extend_date(); break;
                case 4: remove_waitlist(); break;
                default: System.out.println("Error"); staff_menu();
            }
        }
    }

    private void add_book(){
        //staff only, add Book to Book list
        System.out.println("Add Book...");
        Scanner scan = new Scanner(System.in);
        scan.useDelimiter("\n");
        System.out.println("Please enter Book title...");
        String book_title = scan.next();
        Book new_book = new Book(book_title);
        update_description(new_book);
        update_copies(new_book);
        update_location(new_book);
        this.bookList.add(new_book);

        System.out.println("Do you want to add another Book?");
        yes_no();
        int choice = scan.nextInt();
        if (choice == 1) {
            add_book();
        }else {
            staff_menu();
        }
    }

    private boolean search_book(String book_title){
        //search for a certain Book of book_title, return true if exists
        for (Book books : this.bookList){
            String title = books.getTitle();
            if (title.equals(book_title)){
                this.tempBook = books;
                return true;
            }
        }
        return false;
    }

    private void update_book(){
        //staff only, update certain Book information
        System.out.println("Update Book...");
        Scanner scan = new Scanner(System.in);
        scan.useDelimiter("\n");
        System.out.println("Please enter the Book title...");
        String book_title = scan.next();
        if (search_book(book_title)){
            update_info(this.tempBook);
        }else {
            System.out.println("No Book of this title found...");
            System.out.println("Return to stuff menu...");
            staff_menu();
        }
    }

    private void update_title(Book book_u){
        Scanner scan = new Scanner(System.in);
        scan.useDelimiter("\n");
        System.out.println("Please enter the Book title...");
        String book_title = scan.next();
        book_u.setTitle(book_title);
    }

    private void update_description(Book book_u){
        Scanner scan = new Scanner(System.in);
        scan.useDelimiter("\n");
        System.out.println("Please enter the Book description...");
        String book_description = scan.next();
        book_u.setDescription(book_description);
    }

    private void update_copies(Book book_u){
        Scanner scan = new Scanner(System.in);
        System.out.println("Please enter the copies available...");
        System.out.println("Please enter integer");
        int new_copies = scan.nextInt();
        book_u.setCopiesAvl(new_copies);
    }

    private void update_location(Book book_u){
        Scanner scan = new Scanner(System.in);
        scan.useDelimiter("\n");
        System.out.println("Please enter the Book location...");
        String book_location = scan.next();
        book_u.setLocation(book_location);
    }

    private void update_info(Book book_update){
        //staff only, update further information for a Book
        Scanner scan = new Scanner(System.in);
        scan.useDelimiter("\n");
        System.out.println("Update Book "+book_update.title);
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
                System.out.println("Current title : "+book_update.getTitle());
                update_title(book_update);
                update_info(book_update);
                break;
            case 2:
                //update description
                System.out.println("Current description : "+book_update.getDescription());
                update_description(book_update);
                update_info(book_update);
                break;
            case 3:
                //update copies
                System.out.println("Current copies available : "+book_update.getCopiesAvl());
                update_copies(book_update);
                update_info(book_update);
                break;
            case 4:
                //update location
                System.out.println("Current branch location : "+book_update.getLocation());
                update_location(book_update);
                update_info(book_update);
            case 5:
                staff_menu();
            default: System.out.println("Error"); staff_menu();

        }
    }

    private void yes_no(){
        //yes no menu
        System.out.println("Yes...1");
        System.out.println("No...2");
    }

    private void request_book(){
        //Member only, request a Book
        Scanner scan = new Scanner(System.in);
        scan.useDelimiter("\n");
        System.out.println("Search for the Book...");
        String book_title = scan.next();
        if (search_book(book_title)){
            System.out.println("Found Book "+book_title);
            operate_book(this.tempBook);
        }else {
            System.out.println("Cannot find the Book.");
            member_memu();
        }
    }

    private void operate_book(Book book_o){
        //checkout or waitList the Book
        if (book_o.availability()){
            checkout_book(book_o);
        }else {
            waitlist_book(book_o);
        }
    }

    private void checkout_book(Book book_c){
        //checkout the Book
        Scanner scan = new Scanner(System.in);
        scan.useDelimiter("\n");
        Member user_c = this.tempUser;
        System.out.println("The Book is available, do you want to checkout the Book");
        yes_no();
        int choice = scan.nextInt();
        if (choice == 1) {
            book_c.addCheckout(user_c);
            user_c.setCheckout(book_c, new Days());
            System.out.println("Successfully checkout");
            member_memu();
        }else {
            member_memu();
        }

    }

    private void waitlist_book(Book book_w){
        Scanner scan = new Scanner(System.in);
        scan.useDelimiter("\n");
        Member user_w = this.tempUser;
        System.out.println("The Book is unavailable, do you want to join the waitList");
        yes_no();
        int choice = scan.nextInt();
        if (choice == 1) {
            book_w.addWaitList(user_w);
            user_w.setWaitList(book_w);
            System.out.println("Successfully waitList");
            member_memu();
        } else {
            member_memu();
        }
    }

    private void return_book(){
        //return the Book
        Scanner scan = new Scanner(System.in);
        scan.useDelimiter("\n");
        System.out.println("Search for the Book...");
        String book_title = scan.next();
        if (returnSearch(book_title)){
            System.out.println("Found Book "+book_title);
            return_book_helper(this.tempBook);
        }else {
            System.out.println("Cannot find the Book.");
            member_memu();
        }
    }

    private boolean returnSearch(String bookTitle){
        //search checklist
        Member user_s = this.tempUser;
        for (Tuple info : user_s.checkout){
            String title = info.book.getTitle();
            if (title.equals(bookTitle)){
                return true;
            }
        }
        return false;
    }

    private void return_book_helper(Book book_r){
        Member user_r = this.tempUser;
        for (Tuple book_c : this.tempUser.checkout){
            book_c.book.removeCheckout(user_r);
            user_r.removeCheckout(book_r);
            System.out.println("Successfully returned");
            member_memu();
        }
    }

    private void extend_date(){
        Scanner scan = new Scanner(System.in);
        scan.useDelimiter("\n");
        System.out.println("Search for the Book...");
        String book_title = scan.next();
        if (search_book(book_title)){
            System.out.println("Found Book "+book_title);
            extend_date_helper(this.tempBook);
        }else {
            System.out.println("Cannot find the Book.");
            member_memu();
        }
    }

    private void extend_date_helper(Book book_e){
        for (Tuple book_c : this.tempUser.checkout){
            if (book_e.equals(book_c.book)){
                boolean waitlist_sit = book_e.sitWaitList();
                if (book_c.dates.extension(waitlist_sit)){
                    System.out.println("Successfully extended");
                    member_memu();
                }else {
                    System.out.println("Fail to extend date");
                    member_memu();
                }
            }
        }
    }

    private void pay_penalty_s(){
        //pay penalty for a certain Member
        Scanner scan = new Scanner(System.in);
        System.out.println("Pay penalty for members...");
        System.out.println("Please enter the username...");
        String username =  scan.next();
        for (User user_p : this.userList){
            if (user_p.username.equals(username)){
                if (user_p.user_type.equals("staff")){
                    System.out.println("Wrong User type, please re-enter");
                    staff_menu();
                    break;
                }else {
                    Member user_m = (Member) user_p;
                    user_m.payPenalty();
                    System.out.println("Successfully paid the penalty");
                    staff_menu();
                }
            }
        }
    }

    private void member_info(){
        //show Member info when the Member login
        member_waitlist();
        member_penalty();
        member_due();
    }

    private void member_waitlist(){
        //return the waitList availability
        System.out.println("Waitlist Book availability list: ");
        for (Book book_w : this.tempUser.waitList){
            String available = available(book_w);
            System.out.println(book_w.getTitle()+available);
        }
    }

    private String available(Book book_a){
        //return Book availability
        if (book_a.availability()){
            return  "Available";
        }else {
            return  "Unavailable";
        }
    }

    private void member_penalty(){
        //calculate and show penalty of the User
        this.tempUser.calPenalty();
        double penalty = this.tempUser.getPenalty();
        System.out.println("Your penalty is : $"+penalty);
    }

    private void member_due(){
        //show a message of Book due
        System.out.println("These books are due within next 2 Days:");
        ArrayList<String> within = this.tempUser.bookWithin();
        for (String book_title : within){
            System.out.println(book_title);
        }
        System.out.println("These books have already past due:");
        ArrayList<String> due = this.tempUser.bookDue();
        for (String book_title : due){
            System.out.println(book_title);
        }
    }

    private void remove_waitlist(){
        //remove from waitList
        Scanner scan = new Scanner(System.in);
        scan.useDelimiter("\n");
        Member user_w = this.tempUser;
        System.out.println("Please enter the Book you want to remove from waitList");
        String book_title = scan.next();
        if (waitlistSearch(book_title)){
            Book book_w = this.tempBook;
            yes_no();
            int choice = scan.nextInt();
            if (choice == 1) {
                book_w.removeWaitList(user_w);
                user_w.removeWaitList(book_w);
                System.out.println("Successfully removed from waitList");
                member_memu();
            } else {
                member_memu();
            }
        }else {
            System.out.println("Cannot find the Book");
            member_memu();
        }
    }

    private boolean waitlistSearch (String bookTitle){
        Member user_s = this.tempUser;
        for (Book books : user_s.waitList){
            String title = books.getTitle();
            if (title.equals(bookTitle)){
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {

        new Operation();
    }

}
