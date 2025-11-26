// File: Turnstile.java
package lab3.service;

import lab3.model.SkiPass;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.TreeMap; 

public class Turnstile {
    private SkiPassRegistry registry;
    private Map<String, int[]> detailedStats = new TreeMap<>(); 
    
    private int totalAccepted = 0;
    private int totalDenied = 0;
    private int readErrors = 0;

    public Turnstile(SkiPassRegistry registry) {
        this.registry = registry;
    }

    public boolean check(int id, LocalDateTime currentTime) {
        SkiPass pass = registry.getById(id);

        if (pass == null) {
            readErrors++;
            totalDenied++;
            return false;
        }

        String key = pass.getStatsKey(); 
        
        detailedStats.putIfAbsent(key, new int[]{0, 0});

        boolean allowed = !pass.isBlocked() && pass.isValid(currentTime);

        if (allowed) {
            pass.onPass(); 
            totalAccepted++;
            detailedStats.get(key)[0]++;
        } else {
            totalDenied++;
            detailedStats.get(key)[1]++;
        }

        return allowed;
    }

    public String getStatistics() {
        StringBuilder sb = new StringBuilder();
        sb.append("=== GENERAL STATS ===\n");
        sb.append("Total Attempts: ").append(totalAccepted + totalDenied).append("\n");
        sb.append("Total Accepted: ").append(totalAccepted).append("\n");
        sb.append("Total Denied:   ").append(totalDenied).append("\n");
        sb.append("  -> Read Errors (unknown ID): ").append(readErrors).append("\n");
        
        sb.append("\n=== DETAILED STATS (By Pass Type) ===\n");
        if (detailedStats.isEmpty()) {
            sb.append("No data yet.\n");
        }
        for (Map.Entry<String, int[]> entry : detailedStats.entrySet()) {
            sb.append(String.format("%-30s | Accepted: %d | Denied: %d\n", 
                entry.getKey(), entry.getValue()[0], entry.getValue()[1]));
        }
        
        return sb.toString();
    }
}
