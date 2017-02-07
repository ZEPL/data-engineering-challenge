package zefl.util;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.UUID;

import com.fasterxml.jackson.databind.ObjectMapper;

public class Util {
    static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("uuuu-MM-dd hh:mm:ss.SSS");
    static ObjectMapper objectMapper = new ObjectMapper();

    public static String getFormattedStringOf(LocalDateTime date) {
        return dateTimeFormatter.format(date);
    }

    public static String getUUIDString() {
        return UUID.randomUUID().toString();
    }

    public static void saveMapObjectToJsonFile(Map toSave, String absolutePath) throws IOException {
        try (FileOutputStream fileOut = new FileOutputStream(absolutePath);
             ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
            out.writeObject(toSave);
        }
    }

    public static Map loadMapObjectFromJsonFile(String absolutePath) throws IOException, ClassNotFoundException {
        try (FileInputStream fileIn = new FileInputStream(absolutePath);
             ObjectInputStream in = new ObjectInputStream(fileIn)) {
            return (Map) in.readObject();
        }
    }
}
