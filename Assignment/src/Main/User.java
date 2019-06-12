package Main;

public class User {
    public String username;
    public String password;
    public String user_type;

    public User(String username){

        this.username = username;
    }

    public void set_key (String key){
        //set key for Main.User
        this.password = key;
    }

    public void set_type (String type){
        //set User type for User, can only be "Staff" or "Member"
        if (type.equals("Staff")||type.equals("Member")){
            this.user_type = type;
        }else{
            this.user_type = "null";
        }
    }

    public boolean check_key (String key_enter){
        //return true if the input password is matched
        return  (key_enter.equals(this.password));
    }

    public String get_type (){
        //return Main.User type for Main.User, can only be "Staff" or "Member"
        return this.user_type;
    }
}
