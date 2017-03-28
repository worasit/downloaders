package com.agoda.downloaders;


import com.agoda.source.FtpSource;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.SftpException;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import sun.net.ftp.FtpLoginException;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.text.MessageFormat;

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
    public void download() throws IOException, URISyntaxException, SftpException, JSchException {
        if (isAbleToDownloadViaURLConnection(this.sourceURL)) {
            downloadUsingURLConnection(this.sourceURL);
        } else {

            FTPClient ftpClient = connectToFtpServer(this.host, this.user, this.password);
            URI uri = new URI(this.sourceURL);
            ftpClient.enterLocalPassiveMode();
            ftpClient.changeWorkingDirectory(uri.getPath());
            FTPFile[] ftpFiles = ftpClient.listFiles();
        }

    }

    protected FTPClient connectToFtpServer(String host, String user, String password) throws IOException {
        FTPClient ftpClient = new FTPClient();
        ftpClient.connect(host);
        if (!ftpClient.login(user, password) || !FTPReply.isPositiveCompletion(ftpClient.getReplyCode())) {
            ftpClient.logout();
            ftpClient.disconnect();
            throw new FtpLoginException(MessageFormat.format("Cannot connect to host : {0}", host));
        }
        return ftpClient;
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
