package others.model;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Data;

@Data
@XmlRootElement(name = "Predmet")
@XmlAccessorType(XmlAccessType.FIELD)
public class Subject {
    @XmlElement(name = "UIDP")
    private String UIDP;
    @XmlElement(name = "ID.system1")
    private String id1;
    @XmlElement(name = "ID.system2")
    private String id2;
    @XmlElement(name = "ID.system3")
    private String id3;
    @XmlElement(name = "Name.SK")
    private String slovakName;
    @XmlElement(name = "Name.EN")
    private String englishName;
    @XmlElement(name = "Name.Part")
    private String partName;
    @XmlElement(name = "exc")
    private String exc;
    @XmlElement(name = "acc")
    private String acc;

    @Override
    public String toString() {
        return "Subject{" +
                "id=" + UIDP +
                ", name='" + slovakName + '\'' +
                ", id1='" + id1 + '\'' +
                '}';
    }
}
