package managers;

import java.time.Duration;
import java.time.LocalDateTime;

public class TimeManager {
    public void TimeTo(){
        LocalDateTime currentDate = LocalDateTime.now();
        LocalDateTime futureDate = LocalDateTime.of(2024, 1, 1, 0, 0); 

        String timeUntil = getTimeUntilDate(currentDate, futureDate);
        System.out.println("Time until sales: " + timeUntil);
    }
    public static String getTimeUntilDate(LocalDateTime currentDateTime, LocalDateTime futureDateTime) {
        Duration duration = Duration.between(currentDateTime, futureDateTime);

        long months = duration.toDays() / 30;
        long days = duration.toDays() % 30;
        long hours = duration.toHours() % 24;
        long minutes = duration.toMinutes() % 60;

        return String.format("%d months, %d days, %d hours, %d minutes", months, days, hours, minutes);
    }
    
}
