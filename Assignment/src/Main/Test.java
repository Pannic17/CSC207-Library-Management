package Main;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

public class Test {
    public static void main(String[] args) {
        LocalDate today = LocalDate.now();
        System.out.println(today);
        LocalDate future = LocalDate.of(2019, 12, 9);

        Period day = Period.between(today, future);
        long days = ChronoUnit.DAYS.between(today, future);
        int days2 = (int) days;
        System.out.println(day);
        System.out.println(days2);

        System.out.println("plz enter");
        Scanner scan = new Scanner(System.in);
        scan.useDelimiter("\n");
        System.out.println("plz enter 2");
        String scan2 = scan.next();
        System.out.println(scan2);

    }
}
