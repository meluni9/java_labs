package lab5;

import java.io.*;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class FileService {

    public String findLineWithMaxWords(String filePath) throws IOException {
        String maxLine = "";
        int maxCount = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] words = line.trim().split("\\s+");
                if (words.length > maxCount) {
                    maxCount = words.length;
                    maxLine = line;
                }
            }
        }
        return (maxCount > 0) ? "Макс. слів (" + maxCount + "): " + maxLine : "Файл порожній або слів не знайдено.";
    }

    public void encryptFile(String inputFile, String outputFile, int key) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile));
             CipherFilterWriter writer = new CipherFilterWriter(new FileWriter(outputFile), key)) {
            
            int c;
            while ((c = reader.read()) != -1) {
                writer.write(c);
            }
        }
    }

    public void decryptFile(String inputFile, String outputFile, int key) throws IOException {
        try (CipherFilterReader reader = new CipherFilterReader(new FileReader(inputFile), key);
             BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))) {
            
            int c;
            while ((c = reader.read()) != -1) {
                writer.write(c);
            }
        }
    }

    public Map<String, Integer> analyzeTags(String urlString) throws IOException {
        Map<String, Integer> tagCounts = new HashMap<>();
        URL url = new URL(urlString);

        try (BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()))) {
            String inputLine;
            Pattern pattern = Pattern.compile("<([a-zA-Z][a-zA-Z0-9]*)[^>]*>");
            
            while ((inputLine = in.readLine()) != null) {
                Matcher matcher = pattern.matcher(inputLine);
                while (matcher.find()) {
                    String tagName = matcher.group(1).toLowerCase();
                    tagCounts.put(tagName, tagCounts.getOrDefault(tagName, 0) + 1);
                }
            }
        }
        return tagCounts;
    }
    
    public void saveObjectToFile(String filePath, Object object) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
            oos.writeObject(object);
        }
    }

    public Object loadObjectFromFile(String filePath) throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath))) {
            return ois.readObject();
        }
    }
}
