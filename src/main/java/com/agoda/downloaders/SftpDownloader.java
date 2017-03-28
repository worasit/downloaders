package com.agoda.downloaders;


import com.jcraft.jsch.*;

import java.io.*;
import java.net.URI;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

public class SftpDownloader extends Downloader {

    public SftpDownloader(String downloadURL, String outputFilePath) {
        super(downloadURL, outputFilePath);
    }

    @Override
    public void download() throws IOException {

        String SFTPHOST = "localhost";
        int SFTPPORT = 22;
        String SFTPUSER = "agoda";
        String SFTPPASS = "1234";

        Session session = null;
        Channel channel = null;
        ChannelSftp channelSftp = null;
        ReadableByteChannel readableByteChannel = null;
        FileOutputStream fileOutputStream = null;

        try {

            URI uri = new URI(this.downloadURL);

            JSch jsch = new JSch();
            session = jsch.getSession(SFTPUSER, SFTPHOST, SFTPPORT);
            session.setPassword(SFTPPASS);
            java.util.Properties config = new java.util.Properties();
            config.put("StrictHostKeyChecking", "no");
            session.setConfig(config);
            session.connect();
            channel = session.openChannel("sftp");
            channel.connect();
            channelSftp = (ChannelSftp) channel;



            InputStream inputStream = channelSftp.get(uri.getPath());

            readableByteChannel = Channels.newChannel(inputStream);
            fileOutputStream = new FileOutputStream(this.outputFilePath);
            fileOutputStream.getChannel().transferFrom(readableByteChannel, 0, Long.MAX_VALUE);


        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            readableByteChannel.close();
            fileOutputStream.close();
            channelSftp.disconnect();
            channel.disconnect();
            session.disconnect();
        }

    }


}
