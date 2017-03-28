package com.agoda.downloaders;

import com.agoda.source.FtpSource;
import org.junit.Assert;
import org.junit.Test;


public class FtpDownloaderTest {
    @Test
    public void should_be_able_to_download_files() throws Exception {
        // Arrange
        FtpDownloader ftpDownloader = null;
        String downloadURL = "ftp://other.file.com/other.txt";
        String outputFilePath = "other.txt";
        String host = "localhost";
        String user = "agoda";
        String password = "1234";
        FtpSource ftpSource = new FtpSource(Protocol.FTP, downloadURL, outputFilePath, host, user, password);

        // Act
        ftpDownloader = new FtpDownloader(ftpSource);

        // Assert
        Assert.assertTrue(ftpDownloader instanceof Downloader);
    }

    @Test
    public void download_shouldDownloadUsingURLConnection_ifURLFormatIncludeUserAndPassword() throws Exception {
        // Arrange
        FtpDownloader ftpDownloader = null;
        String downloadURL = "ftp://agoda:1234@localhost/Users/worasitdaimongkol/FTP/captain.mkv";
        String outputFilePath = "/Users/worasitdaimongkol/xxx/captain.mkv";
        String host = "localhost";
        String user = "agoda";
        String password = "1234";
        FtpSource ftpSource = new FtpSource(Protocol.FTP, downloadURL, outputFilePath, host, user, password);

        ftpDownloader = new FtpDownloader(ftpSource);

        // Act
//        ftpDownloader.download();


        // Assert


    }

    @Test
    public void download_shouldDownloadUsingFtpClient_ifURLFormatNotIncludeUserAndPassword() throws Exception {
        // Arrange
        FtpDownloader ftpDownloader = null;
        String downloadURL = "ftp://agoda:1234@localhost/Users/worasitdaimongkol/FTP/captain.mkv";
        String outputFilePath = "/Users/worasitdaimongkol/xxx/captain.mkv";
        String host = "localhost";
        String user = "agoda";
        String password = "1234";
        FtpSource ftpSource = new FtpSource(Protocol.FTP, downloadURL, outputFilePath, host, user, password);

        ftpDownloader = new FtpDownloader(ftpSource);

        // Act
//        ftpDownloader.download();


        // Assert


    }

}