package com.tis.dbf;

import javafx.application.Application;
import com.tis.dbf.model.Subjects;
import com.tis.dbf.service.ServerConnection;
import com.tis.dbf.service.XMLParsingServes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DbfApplication {

//    @Autowired
//    private XMLParsingServes xmlParsingServes;
//
    private ServerConnection serverConnection;

    public static void main(String[] args) {
        Application.launch(JavaFxApp.class, args);
    }

//    @Override
//    public void run(String... args) throws Exception{

//        Students students = xmlParsingServes.parseStudents("studentsExample.xml");
//        System.out.println(students);

//        serverConnection.downloadFilesFromServer();
//        Subjects subjects = xmlParsingServes.parseSubjectsXml("predmety.xml");
//        System.out.println(subjects);
//    }

}
