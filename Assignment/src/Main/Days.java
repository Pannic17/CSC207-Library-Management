package Main;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

class Days {

    public LocalDate checkout_date;
    public int days_past;
    public int checkout_days;

    public Days(){
        this.checkout_date = LocalDate.now();
        this.checkout_days = 14;
    }

    public void cal_days(){
        LocalDate today = LocalDate.now();
        long days = ChronoUnit.DAYS.between(this.checkout_date, today);
        this.days_past = (int) days;
    }

    public boolean extension(boolean waitlist){
        //return true and extend the due due time to 28 Days if extension available, otherwise return false.
        this.cal_days();
        if (waitlist&&this.checkout_days<=14&&this.days_past<=14){
            this.checkout_days = 28;
            return true;
        }else{
            return false;
        }
    }

    public int get_days_past (){
        this.cal_days();
        return this.days_past;
    }
}
