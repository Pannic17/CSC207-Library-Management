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

    void setCheckout (Book book, Days date){
        //add Book to the checkout list
        Tuple checkoutInfo = new Tuple(book, date);
        this.checkout.add(checkoutInfo);
    }

    void setWaitList(Book bookWaitList){
        //add Book to the waitList list
        this.waitList.add(bookWaitList);
    }

    void calPenalty(){
        //calculate penalty
        for (Tuple info : this.checkout){
            int daysPast = info.dates.getDaysPast();
            int checkoutLimit =  info.dates.checkoutLimit;
            int daysDue = daysPast - checkoutLimit;
            if (daysDue >= 0){
                this.penalty = this.penalty + (daysDue * 0.5);
            }
        }
    }

    double getPenalty (){
        //return penalty
        return this.penalty;
    }


    void removeCheckout(Book book){
        //remove Book from checkout list
        Tuple temp = removeCheckoutHelper(book);
        this.checkout.remove(temp);
    }

    private Tuple removeCheckoutHelper(Book book){
        for (Tuple info : this.checkout){
            if (info.book.equals(book)){
                return info;
            }
        }
        return null;
    }

    void removeWaitList(Book book){
        //remove Book from waitList
        this.waitList.remove(book);
    }

    void payPenalty(){
        this.penalty = 0;
    }

    ArrayList<String> bookWithin(){
        //search for book that is 2 days to due
        ArrayList<String> books = new ArrayList<>();
        for (Tuple info : this.checkout){
            int daysDue = info.dates.checkoutLimit - info.dates.getDaysPast();
            if ((daysDue <= 2)&&(daysDue >=0)){
                String title = info.book.getTitle();
                books.add(title);
            }
        }
        return books;
    }

    ArrayList<String> bookDue(){
        //search for book that has already due
        ArrayList<String> books = new ArrayList<>();
        for (Tuple info : this.checkout){
            int daysDue = info.dates.checkoutLimit - info.dates.getDaysPast();
            if (daysDue < 0){
                String title = info.book.getTitle();
                books.add(title);
            }
        }
        return books;
    }

}