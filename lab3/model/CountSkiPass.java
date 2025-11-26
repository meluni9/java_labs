package lab3.model;

import java.time.DayOfWeek;
import java.time.LocalDateTime;

public class CountSkiPass extends SkiPass {
    private int ridesLeft;

    public CountSkiPass(SkiPassType type, int rides) {
        super(type);
        this.ridesLeft = rides;
    }

    @Override
    public boolean isValid(LocalDateTime dateTime) {
        if (ridesLeft <= 0) return false;

        DayOfWeek day = dateTime.getDayOfWeek();
        boolean isWeekend = (day == DayOfWeek.SATURDAY || day == DayOfWeek.SUNDAY);

        if (getType() == SkiPassType.WEEKEND) {
            return isWeekend;
        } else if (getType() == SkiPassType.WEEKDAY) {
            return !isWeekend;
        }
        return false;
    }

    @Override
    public void onPass() {
        if (ridesLeft > 0) ridesLeft--;
    }

    @Override
    public String getStatsKey() {
        return getType() + " COUNT-BASED";
    }

    @Override
    public String toString() {
        return "ID: " + getId() + " [" + getType() + " COUNT-BASED (Left: " + ridesLeft + ")] Blocked: " + isBlocked();
    }
}
