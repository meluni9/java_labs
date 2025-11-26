package lab3.model;

import java.time.LocalDateTime;

public abstract class SkiPass {
    private static int idCounter = 1;
    private int id;
    private boolean blocked;
    private SkiPassType type;

    public SkiPass(SkiPassType type) {
        this.id = idCounter++;
        this.type = type;
        this.blocked = false;
    }

    public int getId() { return id; }
    public boolean isBlocked() { return blocked; }
    public void block() { this.blocked = true; }
    public SkiPassType getType() { return type; }

    public abstract boolean isValid(LocalDateTime dateTime);
    
    public abstract void onPass();

    public abstract String getStatsKey();

    public abstract String toString();
}
