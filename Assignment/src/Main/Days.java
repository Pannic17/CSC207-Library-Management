package Main;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

class Days {

    private LocalDate localDate;
    private int daysPast;
    int checkoutLimit;
    private LocalDate today;

    Days(){
        this.localDate = LocalDate.now();
        this.checkoutLimit = 14;
    }

    void getToday(LocalDate systemTime){
        //get today's date
        this.today = systemTime;
    }

    private void calDays(){
        //calculate days past
        LocalDate today = this.today;
        long days = ChronoUnit.DAYS.between(this.localDate, today);
        this.daysPast = (int) days;
    }

    boolean extension(boolean waitList){
        //return true and extend the due due time to 28 Days if extension available, otherwise return false.
        this.calDays();
        if (waitList&&this.checkoutLimit <=14&&this.daysPast <=14){
            this.checkoutLimit = 28;
            return true;
        }else{
            return false;
        }
    }

    int getDaysPast(){
        //return days past
        this.calDays();
        return this.daysPast;
    }
}
