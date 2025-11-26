package lab3.model;

import java.time.LocalDateTime;

public class SeasonSkiPass extends SkiPass {
    public SeasonSkiPass() {
        super(SkiPassType.SEASON);
    }

    @Override
    public boolean isValid(LocalDateTime dateTime) {
        // Припустимо, сезон триває завжди або до певної фіксованої дати
        return true; 
    }

    @Override
    public void onPass() {}

    @Override
    public String getStatsKey() { return "SEASON PASS"; }

    @Override
    public String toString() {
        return "ID: " + getId() + " [SEASON PASS] Blocked: " + isBlocked();
    }
}
