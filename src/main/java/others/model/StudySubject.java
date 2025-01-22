package others.model;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Data;

import java.util.List;

@Data
@XmlRootElement(name = "Subjects")
@XmlAccessorType(XmlAccessType.FIELD)
public class StudySubject {

    @XmlElement(name = "Subject")
    private List<SubjectDetail> subjects;

    @Data
    @XmlAccessorType(XmlAccessType.FIELD)
    public static class SubjectDetail {
        @XmlElement(name = "UIDP")
        private String UIDP;

        @XmlElement(name = "Grade")
        private String grade;

        @XmlElement(name = "Type")
        private String type;

        @XmlElement(name = "Attempt")
        private String attempt;

        @XmlElement(name = "End.Subject")
        private String endSubject;

        @XmlElement(name = "End.Subject.Date")
        private String endSubjectDate;

        @XmlElement(name = "Credits")
        private Integer credits;

        @XmlElement(name = "Semester")
        private String semester;

        @Override
        public String toString() {
            return "SubjectDetail{" +
                    "UIDP='" + UIDP + '\'' +
                    ", grade='" + grade + '\'' +
                    ", type='" + type + '\'' +
                    ", attempt='" + attempt + '\'' +
                    ", endSubject='" + endSubject + '\'' +
                    ", endSubjectDate='" + endSubjectDate + '\'' +
                    ", credits=" + credits +
                    ", semester='" + semester + '\'' +
                    '}';
        }
    }
}
