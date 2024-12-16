package com.tis.dbf.service;

import com.tis.dbf.model.Student;
import com.tis.dbf.model.Students;
import com.tis.dbf.model.Subject;
import com.tis.dbf.model.Subjects;
import com.tis.dbf.model.Study;
import com.tis.dbf.model.Studies;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;
import org.springframework.stereotype.Service;

import java.io.File;
import java.nio.file.Paths;

@Service
public class XMLParsingServes {
    public Subjects parseSubjectsXml(String fileName) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(Subjects.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();

        String filePath = "src/main/resources/" + fileName;
        File file = new File(Paths.get(filePath).toUri());

        Subjects subjects = (Subjects) unmarshaller.unmarshal(file);

        for (Subject subject : subjects.getSubjectList()) {
            System.out.println(subject);
        }
        return subjects;
    }

    public Students parseStudents(String fileName) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(Students.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();

        String filePath = "src/main/resources/" + fileName;
        File file = new File(Paths.get(filePath).toUri());

        Students students = (Students) unmarshaller.unmarshal(file);

        for (Student student : students.getStudents()) {
            System.out.println(student);
        }
        return students;
    }

    public Studies parseStudiesXml(String fileName) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(Studies.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();

        String filePath = "src/main/resources/" + fileName;
        File file = new File(Paths.get(filePath).toUri());

        Studies studies = (Studies) unmarshaller.unmarshal(file);

        for (Study study : studies.getStudies()) {
            System.out.println(study);
        }
        return studies;
    }
}
