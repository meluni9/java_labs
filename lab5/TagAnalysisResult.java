package lab5;

import java.io.Serializable;
import java.util.Map;

class TagAnalysisResult implements Serializable {
    private static final long serialVersionUID = 1L;
    private String url;
    private Map<String, Integer> tagsMap;

    public TagAnalysisResult(String url, Map<String, Integer> tagsMap) {
        this.url = url;
        this.tagsMap = tagsMap;
    }

    @Override
    public String toString() {
        return "Збережений аналіз URL: " + url + "\nКількість унікальних тегів: " + tagsMap.size();
    }
    
    public Map<String, Integer> getTagsMap() {
        return tagsMap;
    }
}
