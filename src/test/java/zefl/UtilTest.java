package zefl;

import static org.junit.Assert.assertEquals;

import java.time.LocalDateTime;

import org.junit.Test;

public class UtilTest {
    @Test
    public void getFormattedStringOf() throws Exception {
        String dateStr = Util.getFormattedStringOf(LocalDateTime.of(2010, 10, 20, 10, 20, 10, 102102102));
        assertEquals("2010-10-20 10:20:10.102", dateStr);
    }
}