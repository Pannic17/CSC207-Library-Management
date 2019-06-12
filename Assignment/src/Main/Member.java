package Main;

import java.util.ArrayList;

public class Member extends User {

    ArrayList<Tuple> checkout;
    ArrayList<Book> waitlist;
    private double penalty;

    Member(String username){

        super(username);
        this.checkout = new ArrayList<>();
        this.waitlist = new ArrayList<>();
    }

    void setCheckout (Book rent_book, Days date){
        //add Book to the checkout list
        Tuple checkout_info = new Tuple(rent_book, date);
        this.checkout.add(checkout_info);
    }

    void setWaitlist (Book waitlist_book){
        //add Book to the waitlist list
        this.waitlist.add(waitlist_book);
    }

    void cal_penalty (){
        //calculate penalty
        for (Tuple info : this.checkout){
            int days_past = info.dates.getDaysPast();
            int days_checkout =  info.dates.checkoutLimit;
            int days_due = days_past - days_checkout;
            if (days_due >= 0){
                this.penalty = this.penalty + (days_due * 0.5);
            }
        }
    }

    double getPenalty (){
        //return penalty
        return this.penalty;
    }


    void remove_checkout (Book book_title){
        //remove Book from checkout list
        Tuple temp = remove_checkout_helper(book_title);
        this.checkout.remove(temp);
    }

    private Tuple remove_checkout_helper (Book book_title){
        for (Tuple info : this.checkout){
            if (info.book.equals(book_title)){
                return info;
            }
        }
        return null;
    }

    void remove_waitlist (Book book_title){
        //remove Book from waitlist
        this.waitlist.remove(book_title);
    }

    void pay_penalty (){
        this.penalty = 0;
    }

    public ArrayList<String> book_within(){
        ArrayList<String> books = new ArrayList<>();
        for (Tuple info : this.checkout){
            int days_due = info.dates.checkoutLimit - info.dates.getDaysPast();
            if ((days_due <= 2)&&(days_due >=0)){
                String title = info.book.getTitle();
                books.add(title);
            }
        }
        return books;
    }

    public ArrayList<String> book_due(){
        ArrayList<String> books = new ArrayList<>();
        for (Tuple info : this.checkout){
            int days_due = info.dates.checkoutLimit - info.dates.getDaysPast();
            if (days_due < 0){
                String title = info.book.getTitle();
                books.add(title);
            }
        }
        return books;
    }

}