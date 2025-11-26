package lab3.model;

import java.time.DayOfWeek;
import java.time.LocalDateTime;

public class DaySkiPass extends SkiPass {
    private LocalDateTime validUntil;

    public DaySkiPass(SkiPassType type, int days) {
        super(type);
        this.validUntil = LocalDateTime.now().plusDays(days);
    }

    @Override
    public boolean isValid(LocalDateTime dateTime) {
        if (dateTime.isAfter(validUntil)) return false;

        DayOfWeek day = dateTime.getDayOfWeek();
        boolean isWeekend = (day == DayOfWeek.SATURDAY || day == DayOfWeek.SUNDAY);

        if (getType() == SkiPassType.WEEKEND) return isWeekend;
        if (getType() == SkiPassType.WEEKDAY) return !isWeekend;
        
        return false;
    }

    @Override
    public void onPass() {
    }

    @Override
    public String getStatsKey() {
        return getType() + " TIME-BASED";
    }

    @Override
    public String toString() {
        return "ID: " + getId() + " [" + getType() + " TIME-BASED (Until: " + validUntil.toLocalDate() + ")] Blocked: " + isBlocked();
    }
}
