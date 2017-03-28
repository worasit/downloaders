package com.agoda.source;

import com.agoda.downloaders.Protocol;
import org.junit.Assert;
import org.junit.Test;

import java.nio.file.Paths;
import java.util.List;


public class SourceExtractorTest {

    @Test
    public void extract() throws Exception {
        // Arrange
        String sourcesURLs = "http://my.file.com/file.txt,ftp://other.file.com/other.txt,sftp://and.also.this/ending.txt,https://and.also.this.https/fake.txt,ftp://fakeUser:fakePassword@google.com/fake.txt";
        String outputDirectoryPath = "/Users/agoda/TestDownload";
        String ftpHost = "other.file.com";
        String ftpUser = "agoda";
        String sftpHost = "and.also.this";
        String ftpPassword = "1234";
        String sftpUser = "agoda";
        String sftpPassword = "1234";

        int expectedSourcesSize = 5;

        Source expectedHttpSource = new Source(
                Protocol.HTTP,
                "http://my.file.com/file.txt",
                Paths.get(outputDirectoryPath, "file.txt").toString());

        Source expectedHttpsSource = new Source(
                Protocol.HTTPS,
                "https://and.also.this.https/fake.txt",
                Paths.get(outputDirectoryPath, "fake.txt").toString());

        FtpSource expectedFtpSource = new FtpSource(
                Protocol.FTP,
                "ftp://other.file.com/other.txt",
                Paths.get(outputDirectoryPath, "other.txt").toString(),
                ftpHost,
                ftpUser,
                ftpPassword);

        FtpSource expectedFtpSourceWithUserAndPasswordFormat = new FtpSource(
                Protocol.FTP,
                "ftp://fakeUser:fakePassword@google.com/fake.txt",
                Paths.get(outputDirectoryPath, "fake.txt").toString(),
                "google.com",
                "fakeUser",
                "fakePassword");

        FtpSource expectedSftpSource = new FtpSource(
                Protocol.SFTP,
                "sftp://and.also.this/ending.txt",
                Paths.get(outputDirectoryPath, "ending.txt").toString(),
                sftpHost,
                sftpUser,
                sftpPassword);

        // Act
        List<Source> actualSources = SourceExtractor.extract(sourcesURLs, outputDirectoryPath, ftpUser, ftpPassword, sftpUser, sftpPassword);

        // Assert
        Assert.assertEquals(expectedSourcesSize, actualSources.size());
        Assert.assertTrue(actualSources.contains(expectedHttpSource));
        Assert.assertTrue(actualSources.contains(expectedHttpsSource));
        Assert.assertTrue(actualSources.contains(expectedFtpSource));
        Assert.assertTrue(actualSources.contains(expectedFtpSourceWithUserAndPasswordFormat));
        Assert.assertTrue(actualSources.contains(expectedSftpSource));
    }

    @Test(expected = EnumConstantNotPresentException.class)
    public void extract_throwEnumConstantNotPresentException_ifProtocolIsNotValid() throws Exception {

        // Arrange
        String sourceUrlWithInvalidProtocol = "xxx://my.file.com/file.txt";
        String outputDirectoryPath = "/Users/agoda/TestDownload";
        String ftpUser = "agoda";
        String ftpPassword = "1234";
        String sftpUser = "agoda";
        String sftpPassword = "1234";

        // Act
        List<Source> actualSources = SourceExtractor.extract(sourceUrlWithInvalidProtocol, outputDirectoryPath, ftpUser, ftpPassword, sftpUser, sftpPassword);
    }
}