package com.tis.dbf.service;
import com.jcraft.jsch.*;
import com.tis.dbf.model.Subjects;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


public class ServerConnection {

    private String username;
    // doplnit sem udaje
    private String password;

    private String host;
    private int port;


    public Subjects downloadAndParseSubjects(String faculty) throws JSchException, SftpException, JAXBException {
        String remoteFilePath = getSubjectsFilePath(faculty);

        JSch jsch = new JSch();
        Session session = jsch.getSession(username, host, port);
        session.setPassword(password);
        session.setConfig("StrictHostKeyChecking", "no");
        session.connect();
        ChannelSftp channelSftp = (ChannelSftp) session.openChannel("sftp");
        channelSftp.connect();
        InputStream inputStream = channelSftp.get(remoteFilePath);
        JAXBContext jaxbContext = JAXBContext.newInstance(Subjects.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        Subjects subjects = (Subjects) unmarshaller.unmarshal(inputStream);
        channelSftp.exit();
        session.disconnect();
        return subjects;
    }

    private static String getSubjectsFilePath(String faculty) {

        Properties properties = new Properties();
        try (FileInputStream input = new FileInputStream("config.properties")) {
            properties.load(input);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load configuration file", e);
        }

        String filePathTemplate = properties.getProperty("subjectsFilePath");
        if (filePathTemplate == null || filePathTemplate.isEmpty()) {
            throw new IllegalArgumentException("subjectsFilePath is not configured in the properties file");
        }

        return filePathTemplate.replace("${faculty}", faculty);
    }

}
