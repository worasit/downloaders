package com.agoda.downloaders;

import com.agoda.source.FtpSource;
import org.junit.Assert;
import org.junit.Test;

public class SftpDownloaderTest {

    @Test
    public void should_be_able_to_download_files() throws Exception {
        // Arrange
        SftpDownloader sftpDownloader = null;
        String downloadURL = "sftp://and.also.this/ending.txt";
        String outputFilePath = "ending.txt";
        String host = "localhost";
        String user = "agoda";
        String password = "1234";
        FtpSource sftpSource = new FtpSource(Protocol.SFTP, downloadURL, outputFilePath, host, user, password);

        // Act
        sftpDownloader = new SftpDownloader(sftpSource);

        // Assert
        Assert.assertTrue(sftpDownloader instanceof Downloader);
    }

    @Test
    public void download() throws Exception {
        // Arrange
        SftpDownloader sftpDownloader = null;
        String downloadURL = "sftp://agoda:1234@localhost/Users/worasitdaimongkol/FTP/captain.mkv";
        String outputFilePath = "/Users/worasitdaimongkol/xxx/captain.mkv";
        String host = "localhost";
        String user = "agoda";
        String password = "1234";
        FtpSource sftpSource = new FtpSource(Protocol.SFTP, downloadURL, outputFilePath, host, user, password);

        sftpDownloader = new SftpDownloader(sftpSource);

        // Act
//        sftpDownloader.download();


        // Assert


    }

}