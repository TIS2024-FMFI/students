package others.service;

import com.jcraft.jsch.*;
import others.model.Students;
import others.model.Studies;
import others.model.Subjects;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


public class ServerConnection {

    private final String username;

    private final String password;

    private final String host;
    private final int port;

    public ServerConnection(String username, String password, String host, int port) {
        this.username = username;
        this.password = password;
        this.host = host;
        this.port = port;
    }


    public Subjects downloadAndParseSubjects(String faculty) throws JSchException, SftpException, JAXBException {
        String remoteFilePath = getFilePath(faculty, "subjects");
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

    public Studies downloadAndParseStudies(String faculty) throws JSchException, SftpException, JAXBException {
        String remoteFilePath = getFilePath(faculty, "studies");
        JSch jsch = new JSch();
        Session session = jsch.getSession(username, host, port);
        session.setPassword(password);
        session.setConfig("StrictHostKeyChecking", "no");
        session.connect();
        ChannelSftp channelSftp = (ChannelSftp) session.openChannel("sftp");
        channelSftp.connect();
        InputStream inputStream = channelSftp.get(remoteFilePath);
        JAXBContext jaxbContext = JAXBContext.newInstance(Studies.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        Studies studies = (Studies) unmarshaller.unmarshal(inputStream);
        channelSftp.exit();
        session.disconnect();
        return studies;
    }

    public Students downloadAndParseStudents(String faculty) throws JSchException, SftpException, JAXBException {
        String remoteFilePath = getFilePath(faculty, "students");
        JSch jsch = new JSch();
        Session session = jsch.getSession(username, host, port);
        session.setPassword(password);
        session.setConfig("StrictHostKeyChecking", "no");
        session.connect();
        ChannelSftp channelSftp = (ChannelSftp) session.openChannel("sftp");
        channelSftp.connect();
        InputStream inputStream = channelSftp.get(remoteFilePath);
        JAXBContext jaxbContext = JAXBContext.newInstance(Students.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        Students students = (Students) unmarshaller.unmarshal(inputStream);
        channelSftp.exit();
        session.disconnect();
        return students;
    }


    private static String getFilePath(String faculty, String document) {

        Properties properties = new Properties();
        try (FileInputStream input = new FileInputStream("src/main/resources/config.properties")) {
            properties.load(input);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load configuration file", e);
        }

        String filePathTemplate = properties.getProperty(document + "FilePath");
        if (filePathTemplate == null || filePathTemplate.isEmpty()) {
            throw new IllegalArgumentException(document + "FilePath is not configured in the properties file");
        }

        return filePathTemplate.replace("${faculty}", faculty);
    }

}
