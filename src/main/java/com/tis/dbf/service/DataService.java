package com.tis.dbf.service;

import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.SftpException;
import com.tis.dbf.model.*;
import jakarta.xml.bind.JAXBException;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DataService {
    private String sharedData;
    private String username;
    private static DataService instance;
    private Subjects subjects;
    private Students students;
    private Studies studies;

    private Map<String, List<Study>> studyMap;
    private String serverPassword;

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

    public void setServerPassword(String server_password) {
        this.serverPassword = server_password;
    }

    public String getServerPassword() {
        return this.serverPassword;
    }

    public void startDownload() throws JSchException, SftpException, JAXBException {
        ServerConnection serverConnection = new ServerConnection("java", serverPassword, "student.int.uniba.sk", 22);
        subjects = serverConnection.downloadAndParseSubjects(username);
        studies = serverConnection.downloadAndParseStudies(username);
        students = serverConnection.downloadAndParseStudents(username);

        buildStudyMap();
        //System.out.println(studyMap);
    }

    private void buildStudyMap() {
        if (studies != null) {
            studyMap = studies.getStudies().stream()
                    .filter(study -> study.getUPN() != null)
                    .collect(Collectors.groupingBy(Study::getUPN));
        } else {
            studyMap = new HashMap<>();
        }
    }

    public Map<String, List<Study>> getStudyMap() {
        return studyMap;
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


