package com.tis.dbf.service;

import com.jcraft.jsch.*;
import com.tis.dbf.model.Subjects;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.InputStream;

@Component
public class ServerConnection {

    @Value("${server.username}")
    private String username;

    @Value("${server.password}")
    private String password;

    @Value("${server.host}")
    private String host;

    @Value("${server.port}")
    private int port;

    public void downloadFilesFromServer() throws JSchException, SftpException {
        String localFilePath = "src/main/resources/predmety.xml";
        String remoteFilePath = "/home/skoruba1/students/fsev/PREDMETY.XML";

        JSch jsch = new JSch();
        Session session = jsch.getSession(username, host, port);
        session.setPassword(password);
        session.setConfig("StrictHostKeyChecking", "no");
        session.connect();

        ChannelSftp channelSftp = (ChannelSftp) session.openChannel("sftp");
        channelSftp.connect();

        channelSftp.get(remoteFilePath, localFilePath);

        channelSftp.exit();

        session.disconnect();

    }

    public Subjects downloadAndParseSubjects() throws JSchException, SftpException, JAXBException {
        String remoteFilePath = "/home/skoruba1/students/fsev/PREDMETY.XML";

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
}
