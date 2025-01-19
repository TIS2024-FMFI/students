package com.tis.dbf;

import com.tis.dbf.model.Students;
import com.tis.dbf.model.Subjects;
import com.tis.dbf.service.ServerConnection;
import com.tis.dbf.service.XMLParsingServes;

public class DbfApplication {

    private XMLParsingServes xmlParsingServes;
    private ServerConnection serverConnection;

    public static void main(String[] args) {
        DbfApplication app = new DbfApplication();
        try {
            app.init();
            app.run(args);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void init() {
        xmlParsingServes = new XMLParsingServes();
        //serverConnection = new ServerConnection();
    }

    public void run(String... args) throws Exception {
        Students students = xmlParsingServes.parseStudents("studentsExample.xml");
        System.out.println(students);

        Subjects subjects = serverConnection.downloadAndParseSubjects("fsev");
        System.out.println(subjects);

    }
}
