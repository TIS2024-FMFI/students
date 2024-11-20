package com.tis.dbf;

import com.tis.dbf.model.Subject;
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
        Subjects subjects = xmlParsingServes.parseXml("example.xml");
        System.out.println(subjects);
    }

}
