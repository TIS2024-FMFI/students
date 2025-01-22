package com.tis.dbf.model;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@XmlRootElement(name = "Subjects")
@XmlAccessorType(XmlAccessType.FIELD)
public class StudySubjects {

    @XmlElement(name = "Subject")
    private List<StudySubject.SubjectDetail> studySubjectList = new ArrayList<>();

    /**
     * Returns a list of all SubjectDetail objects.
     */
    public List<StudySubject.SubjectDetail> getSubjects() {
        return studySubjectList == null ? new ArrayList<>() : studySubjectList;
    }
}
