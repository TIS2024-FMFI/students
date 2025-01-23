package others.model;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Data;

import java.util.List;

@Data
@XmlRootElement(name = "Predmety")
@XmlAccessorType(XmlAccessType.FIELD)
public class Subjects {

    @XmlElement(name = "Predmet")
    public List<Subject> subjectList;
}
