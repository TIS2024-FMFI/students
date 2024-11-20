package com.tis.dbf.service;

import com.tis.dbf.model.Subject;
import com.tis.dbf.model.Subjects;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

@Service
public class XMLParsingServes {
    public Subjects parseXml(String fileName) throws JAXBException {
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
}
