package Main;

import java.util.ArrayList;

public class Book {
    String title;
    private String description;
    private int copies_avl;
    private String location;
    private ArrayList<Member> waitlist;
    private ArrayList<Member> checkout_list;

    Book(String title){
        this.title = title;
        this.waitlist = new ArrayList<>();
        this.checkout_list = new ArrayList<>();
        this.copies_avl = 0;
    }

    void alt_title(String title){
        //change the title of the Book
        this.title = title;
    }

    public String get_title (){
        return this.title;
    }
    
    public void alt_description (String description){
        //change the description of the Book
        this.description =  description;
    }

    public String get_description (){
        return this.description;
    }

    public void alt_copies_avl (int copies){
        //change the copies of available
        this.copies_avl = copies;
    }

    public int get_copies_avl (){
        return this.copies_avl;
    }

    public void alt_location (String location){
        //change the location of the Book
        this.location = location;
    }

    public String get_location (){
        return this.location;
    }

    public void add_waitlist (Member person){
        //add a Member to the waitList
        this.waitlist.add(person);
    }

    public void remove_waitlist (Member person){
        //remove a Member from the waitList
        this.waitlist.remove(person);
    }

    public ArrayList get_waitlist (){
        return this.waitlist;
    }

    public void add_checkout (Member person){
        //add a Member to the checkout list
        this.checkout_list.add(person);
    }

    public void remove_checkout (Member person){
        //remove a Member from the checkout list
        this.checkout_list.remove(person);
    }

    public ArrayList get_checkout (){
        return this.checkout_list;
    }

    public boolean waitlist_sit(){
        //return the waitList situation of the Book
        return (waitlist.size()<=0);
    }

    public boolean availability (){
        //check whether the Book is available, return true if available
        int checkout_num = this.checkout_list.size();
        return ((checkout_num < this.copies_avl)&&(this.copies_avl > 0));
    }
}