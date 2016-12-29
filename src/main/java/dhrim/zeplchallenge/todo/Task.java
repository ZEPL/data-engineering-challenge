package dhrim.zeplchallenge.todo;

import lombok.Data;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.Date;

@Data
@XmlRootElement
public class Task implements Serializable {

    private String id;
    private String name;
    private String description;
    private Status status;
    private Date created;

    public enum Status {
        DONE,
        NOT_DONE,
    }

}
