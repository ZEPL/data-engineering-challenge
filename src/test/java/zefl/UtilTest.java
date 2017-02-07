package zefl;

import org.junit.Test;

import java.io.File;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

import zefl.domain.Task;
import zefl.domain.Todo;
import zefl.util.Util;

public class UtilTest {
    @Test
    public void getFormattedStringOf() throws Exception {
        String dateStr = Util.getFormattedStringOf(LocalDateTime.of(2010, 10, 20, 10, 20, 10, 102102102));
        assertEquals("2010-10-20 10:20:10.102", dateStr);
    }

    @Test
    public void testJsonSaveAndLoadToFile() throws Exception {
        File jsonFile = File.createTempFile("to-json", ".tmp");
        Task task = new Task("taskId", "name", "1010", "DONE", "1010");
        Map<String, Task> taskMap = new HashMap<>();
        taskMap.put("taskId", task);

        Todo todo = new Todo("id", "name", "1010");
        todo.setTaskMap(taskMap);
        Map<String, Todo> todoMap = new HashMap<>();
        todoMap.put("id", todo);

        Util.saveMapObjectToJsonFile(todoMap, jsonFile.getAbsolutePath());

        Map loadedMap = Util.loadMapObjectFromJsonFile(jsonFile.getAbsolutePath());

        assertEquals(todoMap, loadedMap);

    }
}