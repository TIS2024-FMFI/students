package com.tis.dbf;

import com.tis.dbf.model.Students;
import com.tis.dbf.model.Subjects;
import com.tis.dbf.service.XMLParsingServes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DbfApplication implements CommandLineRunner {

    @Autowired
    private XMLParsingServes xmlParsingServes;

    public static void main(String[] args) {
        SpringApplication.run(DbfApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception{
        Subjects subjects = xmlParsingServes.parseSubjectsXml("example.xml");
        Students students = xmlParsingServes.parseStudents("studentsExample.xml");
        System.out.println(subjects);
        System.out.println(students);
    }

}
