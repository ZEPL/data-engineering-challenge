package dhrim.zeplchallenge.todo;

import com.google.inject.Singleton;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.mapdb.*;

import java.io.*;
import java.util.Map;


@Singleton
@Slf4j
/**
 * TodoRepo using file based MadDb.
 *
 * This class use library MapDb. http://www.mapdb.org/
 *
 * Map is created from file and stored into the file.
 *
 */
class MapDbTodoRepo extends AbstractMapBasedTodoRepo {

    static final String DB_FILE_NAME = "todo_data.db";

    public MapDbTodoRepo() {
        super.initIfNot();
    }

    private static DB db;
    private Serializer serializer = new ObjectSerializer();

    private void initDbIfNot() {
        if(db!=null) { return; }
        db = DBMaker
                .fileDB(DB_FILE_NAME)
                .closeOnJvmShutdownWeakReference()
                .checksumHeaderBypass()
                .make();

        log.info("File DbMap is initialized with file "+DB_FILE_NAME);
    }

    @Override
    protected Map<String, Todo> getTodoMapInstance() {
        initDbIfNot();
        return db.hashMap("todoMap", Serializer.STRING, serializer).createOrOpen();
    }

    @Override
    protected Map<String, Map<String, Task>> getTaskMapMapInstance() {
        initDbIfNot();
        return db.hashMap("tasksMap", Serializer.STRING, serializer).createOrOpen();
    }

    protected void clear() {
        new File(DB_FILE_NAME).delete();
    }

    private class ObjectSerializer implements Serializer, Serializable {

        @Override
        public void serialize(@NotNull DataOutput2 out, @NotNull Object value) throws IOException {
            if(!(value instanceof Serializable)) {
                throw new IOException("value class is not implements Serializable. value="+value);
            }
            try (ByteArrayOutputStream bos = new ByteArrayOutputStream(); ObjectOutput objectOut = new ObjectOutputStream(bos)) {
                objectOut.writeObject(value);
                out.write(bos.toByteArray());
            }
        }

        @Override
        public Object deserialize(@NotNull DataInput2 input, int available) throws IOException {
            try (ByteArrayInputStream bis = new ByteArrayInputStream(input.internalByteArray());
                 ObjectInput objectInput = new ObjectInputStream(bis)) {
                return objectInput.readObject();
            } catch (ClassNotFoundException e) {
                throw new IOException("deserialization failed.", e);
            }
        }

    }

}
