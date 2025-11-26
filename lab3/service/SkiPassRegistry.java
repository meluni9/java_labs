package lab3.service;

import lab3.model.SkiPass;
import java.util.HashMap;
import java.util.Map;

public class SkiPassRegistry {
    private Map<Integer, SkiPass> database = new HashMap<>();

    public void register(SkiPass pass) {
        database.put(pass.getId(), pass);
    }

    public SkiPass getById(int id) {
        return database.get(id);
    }

    public void block(int id) {
        if (database.containsKey(id)) {
            database.get(id).block();
        }
    }
}
