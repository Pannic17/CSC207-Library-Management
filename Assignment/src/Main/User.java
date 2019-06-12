package Main;

public class User {
    String userName;
    private String password;
    String userType;

    User(String userName){

        this.userName = userName;
    }

    void setKey(String key){
        //set key for Main.User
        this.password = key;
    }

    void setType(String type){
        //set User type for User, can only be "staff" or "Member"
        if (type.equals("staff")||type.equals("Member")){
            this.userType = type;
        }else{
            this.userType = "null";
        }
    }

    boolean checkKey(String key){
        //return true if the input password is matched
        return  (key.equals(this.password));
    }

    public String getType(){
        //return Main.User type for Main.User, can only be "staff" or "Member"
        return this.userType;
    }
}
