package com.agoda.downloaders;


import com.agoda.source.FtpSource;
import com.jcraft.jsch.*;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Properties;

public class SftpDownloader extends FtpDownloader {

    private static final int SFTP_PORT = 22;

    public SftpDownloader(FtpSource source) {
        super(source);
    }


    @Override
    public void download() throws IOException, URISyntaxException, SftpException, JSchException {

        Session session = null;
        ChannelSftp channelSftp = null;

        try {

            Properties config = getSessionConfiguration();
            session = establishSession(config);
            channelSftp = establishSftpChannel(session);
            InputStream inputStream = getInputStream(channelSftp);
            this.writeStreamData(inputStream, Long.MAX_VALUE);

        } finally {
            channelSftp.disconnect();
            session.disconnect();
        }
    }

    private InputStream getInputStream(ChannelSftp channelSftp) throws URISyntaxException, SftpException {
        URI uri = new URI(this.sourceURL);
        return channelSftp.get(uri.getPath());
    }

    private ChannelSftp establishSftpChannel(Session session) throws JSchException {
        Channel channel = session.openChannel("sftp");
        channel.connect();
        return (ChannelSftp) channel;
    }

    private Session establishSession(Properties config) throws JSchException {
        JSch jsch = new JSch();
        Session session = jsch.getSession(this.user, this.host, SFTP_PORT);
        session.setPassword(this.password);
        session.setConfig(config);
        session.connect();
        return session;
    }

    private Properties getSessionConfiguration() {
        Properties config = new Properties();
        config.put("StrictHostKeyChecking", "no");
        return config;
    }


}
