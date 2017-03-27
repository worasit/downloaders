package com.agoda.utilities;

import com.agoda.downloaders.Protocol;
import com.agoda.models.Source;
import org.junit.Assert;
import org.junit.Test;

import java.nio.file.Paths;
import java.util.List;


public class SourceExtractorTest {

    @Test
    public void extract() throws Exception {
        // Arrange
        String sourcesURLs = "http://my.file.com/file.txt,ftp://other.file.com/other.txt,sftp://and.also.this/ending.txt,https://and.also.this.https/fake.txt";
        String outputDirectoryPath = "/Users/agoda/TestDownload";
        int expectedSourcesSize = 4;

        Source expectedHttpSource = new Source(
                Protocol.HTTP,
                "http://my.file.com/file.txt",
                Paths.get(outputDirectoryPath, "file.txt").toString());

        Source expectedHttpsSource = new Source(
                Protocol.HTTPS,
                "https://and.also.this.https/fake.txt",
                Paths.get(outputDirectoryPath, "fake.txt").toString());

        Source expectedFtpSource = new Source(
                Protocol.FTP,
                "ftp://other.file.com/other.txt",
                Paths.get(outputDirectoryPath, "other.txt").toString());

        Source expectedSftpSource = new Source(
                Protocol.SFTP,
                "sftp://and.also.this/ending.txt",
                Paths.get(outputDirectoryPath, "ending.txt").toString());


        // Act
        List<Source> actualSources = SourceExtractor.extract(sourcesURLs, outputDirectoryPath);

        // Assert
        Assert.assertEquals(expectedSourcesSize, actualSources.size());
        Assert.assertTrue(actualSources.contains(expectedHttpSource));
        Assert.assertTrue(actualSources.contains(expectedHttpsSource));
        Assert.assertTrue(actualSources.contains(expectedFtpSource));
        Assert.assertTrue(actualSources.contains(expectedSftpSource));
    }

    @Test(expected = EnumConstantNotPresentException.class)
    public void extract_throwEnumConstantNotPresentException_ifProtocolIsNotValid() throws Exception {

        // Arrange
        String sourceUrlWithInvalidProtocol = "xxx://my.file.com/file.txt";
        String outputDirectoryPath = "/Users/agoda/TestDownload";

        // Act
        SourceExtractor.extract(sourceUrlWithInvalidProtocol, outputDirectoryPath);
    }
}