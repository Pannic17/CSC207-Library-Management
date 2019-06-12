package Main;

import java.util.ArrayList;

class Book {
    String title;
    private String description;
    private int copiesAvl;
    private String location;
    private ArrayList<Member> waitList;
    private ArrayList<Member> checkoutList;

    Book(String title){
        this.title = title;
        this.waitList = new ArrayList<>();
        this.checkoutList = new ArrayList<>();
        this.copiesAvl = 0;
    }

    void setTitle(String title){
        //change the title of the Book
        this.title = title;
    }

    String getTitle(){
        return this.title;
    }

    void setDescription(String description){
        //change the description of the Book
        this.description =  description;
    }

    String getDescription(){
        return this.description;
    }

    void setCopiesAvl(int copies){
        //change the copies of available
        this.copiesAvl = copies;
    }

    int getCopiesAvl(){
        return this.copiesAvl;
    }

    void setLocation(String location){
        //change the location of the Book
        this.location = location;
    }

    String getLocation(){
        return this.location;
    }

    void addWaitList(Member person){
        //add a Member to the waitList
        this.waitList.add(person);
    }

    void removeWaitList(Member person){
        //remove a Member from the waitList
        this.waitList.remove(person);
    }

    void addCheckout(Member person){
        //add a Member to the checkout list
        this.checkoutList.add(person);
    }

    void removeCheckout(Member person){
        //remove a Member from the checkout list
        this.checkoutList.remove(person);
    }

    boolean sitWaitList(){
        //return the waitList situation of the Book
        return (waitList.size()<=0);
    }

    boolean availability(){
        //check whether the Book is available, return true if available
        int checkoutNum = this.checkoutList.size();
        return ((checkoutNum < this.copiesAvl)&&(this.copiesAvl > 0));
    }
}