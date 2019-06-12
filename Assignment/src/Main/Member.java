package Main;

import java.util.ArrayList;

class Member extends User {

    ArrayList<Tuple> checkout;
    ArrayList<Book> waitList;
    private double penalty;

    Member(String username){

        super(username);
        this.checkout = new ArrayList<>();
        this.waitList = new ArrayList<>();
    }

    void setCheckout (Book rentBook, Days date){
        //add Book to the checkout list
        Tuple checkoutInfo = new Tuple(rentBook, date);
        this.checkout.add(checkoutInfo);
    }

    void setWaitList(Book waitListBook){
        //add Book to the waitList list
        this.waitList.add(waitListBook);
    }

    void calPenalty(){
        //calculate penalty
        for (Tuple info : this.checkout){
            int days_past = info.dates.get_days_past();
            int days_checkout =  info.dates.checkout_days;
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


    void removeCheckout(Book book_title){
        //remove Book from checkout list
        Tuple temp = removeCheckoutHelper(book_title);
        this.checkout.remove(temp);
    }

    private Tuple removeCheckoutHelper(Book book_title){
        for (Tuple info : this.checkout){
            if (info.rent_book.equals(book_title)){
                return info;
            }
        }
        return null;
    }

    void removeWaitList(Book book_title){
        //remove Book from waitList
        this.waitList.remove(book_title);
    }

    void pay_penalty (){
        this.penalty = 0;
    }

    ArrayList<String> book_within(){
        ArrayList<String> books = new ArrayList<>();
        for (Tuple info : this.checkout){
            int days_due = info.dates.checkout_days - info.dates.get_days_past();
            if ((days_due <= 2)&&(days_due >=0)){
                String title = info.rent_book.get_title();
                books.add(title);
            }
        }
        return books;
    }

    ArrayList<String> book_due(){
        ArrayList<String> books = new ArrayList<>();
        for (Tuple info : this.checkout){
            int days_due = info.dates.checkout_days - info.dates.get_days_past();
            if (days_due < 0){
                String title = info.rent_book.get_title();
                books.add(title);
            }
        }
        return books;
    }

}