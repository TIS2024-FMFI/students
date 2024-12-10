package com.tis.dbf.service;

import com.jcraft.jsch.*;
import org.springframework.beans.factory.annotation.Value;

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
}
