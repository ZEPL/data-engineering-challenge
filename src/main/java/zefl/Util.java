package zefl;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Util {
    static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("uuuu-MM-dd hh:mm:ss.SSS");
    public static String getFormattedStringOf(LocalDateTime date) {
        return dateTimeFormatter.format(date);
    }
}
