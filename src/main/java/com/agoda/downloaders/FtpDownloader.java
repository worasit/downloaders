package com.agoda.downloaders;


import com.agoda.source.FtpSource;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

public class FtpDownloader extends Downloader {


    protected String host;
    protected String user;
    protected String password;

    public FtpDownloader(FtpSource ftpSource) {
        super(ftpSource);
        this.host = ftpSource.host;
        this.user = ftpSource.user;
        this.password = ftpSource.password;
    }

    @Override
    public void download() throws IOException {

        ReadableByteChannel readableByteChannel = null;
        FileOutputStream fileOutputStream = null;

        try {
            URL url = new URL(this.sourceURL);
            URLConnection urlConnection = url.openConnection();
            readableByteChannel = Channels.newChannel(urlConnection.getInputStream());
            fileOutputStream = new FileOutputStream(this.outputFilePath);
            fileOutputStream.getChannel().transferFrom(readableByteChannel, 0, urlConnection.getContentLengthLong());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            readableByteChannel.close();
            fileOutputStream.close();
        }

    }
}
