package dhrim.zeplchallenge.todo;

import lombok.Data;

import javax.xml.bind.annotation.XmlRootElement;

@Data
@XmlRootElement
public class FailedMessage {
    private String message;
}
