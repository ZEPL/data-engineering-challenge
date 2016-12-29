package dhrim.zeplchallenge.todo;

import lombok.Data;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.Date;

@Data
@XmlRootElement
public class Todo implements Serializable {
    private String id;
    private String name;
    private Date created;
}
