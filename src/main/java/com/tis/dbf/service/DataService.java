package com.tis.dbf.service;

import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.SftpException;
import com.tis.dbf.model.Students;
import com.tis.dbf.model.Studies;
import com.tis.dbf.service.ServerConnection;
import com.tis.dbf.model.Subjects;
import jakarta.xml.bind.JAXBException;

public class DataService {
    private String sharedData;
    private String username;
    private static DataService instance;
    private Subjects subjects;
    private Students students;
    private Studies studies;

    public static DataService getInstance() {
        if (instance == null) {
            instance = new DataService();
        }
        return instance;
    }

    public String getSharedData() {
        return sharedData;
    }

    public String getUsername() {
        return username;
    }

    public void setSharedData(String sharedData) {
        this.sharedData = sharedData;
    }

    public void setUsername(String username_input) {
        username = username_input;
    }

    public void setData() throws JSchException, SftpException, JAXBException {
        ServerConnection serverConnection = new ServerConnection();
        subjects = serverConnection.downloadAndParseSubjects(username);
        studies = serverConnection.downloadAndParseStudies(username);
        students = serverConnection.downloadAndParseStudents(username);
    }

    public Subjects getSubjects() {
        return subjects;
    }


    public Studies getStudies() {
        return studies;
    }

    public Students getStudents() {
        return students;
    }
}
