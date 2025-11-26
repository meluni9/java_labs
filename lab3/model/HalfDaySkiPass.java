package lab3.model;

import java.time.DayOfWeek;
import java.time.LocalDateTime;

public class HalfDaySkiPass extends SkiPass {
    private boolean isMorning;
    private LocalDateTime validDate; 

    public HalfDaySkiPass(SkiPassType type, boolean isMorning) {
        super(type);
        this.isMorning = isMorning;
        this.validDate = LocalDateTime.now(); 
    }

    @Override
    public boolean isValid(LocalDateTime dateTime) {
        if (!dateTime.toLocalDate().equals(validDate.toLocalDate())) return false;

        DayOfWeek day = dateTime.getDayOfWeek();
        boolean isWeekend = (day == DayOfWeek.SATURDAY || day == DayOfWeek.SUNDAY);

        if (getType() == SkiPassType.WEEKEND && !isWeekend) return false;
        if (getType() == SkiPassType.WEEKDAY && isWeekend) return false;

        int hour = dateTime.getHour();
        if (isMorning) {
            return hour >= 9 && hour < 13;
        } else {
            return hour >= 13 && hour < 17;
        }
    }

    @Override
    public void onPass() {}

    @Override
    public String getStatsKey() {
        String period = isMorning ? "MORNING" : "AFTERNOON";
        return getType() + " HALF-DAY " + period;
    }

    @Override
    public String toString() {
        return "ID: " + getId() + " [" + getStatsKey() + "] Blocked: " + isBlocked();
    }
}
