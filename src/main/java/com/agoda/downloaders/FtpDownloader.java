package com.agoda.downloaders;


import com.agoda.source.Source;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.SftpException;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.text.MessageFormat;

public class FtpDownloader extends Downloader {


    public FtpDownloader(Source source) {
        super(source);
    }

    @Override
    public void download() throws IOException, URISyntaxException, SftpException, JSchException {

        if (!isAbleToDownloadViaURLConnection(this.getSource().getSourceURL())) {

            String newHostWithUserAndPassword = includeUserPasswordToFTPConnection(
                    this.getSource().getUser(),
                    this.getSource().getPassword(),
                    this.getSource().getHost());

            this.getSource().setSourceURL(newHostWithUserAndPassword);
        }

        downloadUsingURLConnection(this.getSource().getSourceURL());
    }

    protected String includeUserPasswordToFTPConnection(String user, String password, String hostname) {
        String hostWithUserAndPassword = MessageFormat.format("{0}:{1}@{2}", user, password, hostname);
        return this.getSource().getSourceURL().replaceAll(this.getSource().getHost(), hostWithUserAndPassword);
    }

    protected boolean isAbleToDownloadViaURLConnection(String sourceURL) throws URISyntaxException {
        return new URI(sourceURL).getUserInfo() != null;
    }

    protected void downloadUsingURLConnection(String sourceURL) throws IOException {
        URL url = new URL(sourceURL);
        URLConnection urlConnection = url.openConnection();
        this.writeStreamData(urlConnection.getInputStream(), urlConnection.getContentLengthLong());
    }
}
