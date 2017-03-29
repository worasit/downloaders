package com.agoda.downloaders;

import com.agoda.source.Source;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.mockito.Matchers.anyString;
import static org.mockito.internal.verification.VerificationModeFactory.times;
import static org.powermock.api.mockito.PowerMockito.*;

@RunWith(PowerMockRunner.class)
@PrepareForTest(FtpDownloader.class)
public class FtpDownloaderTest {
    @Test
    public void should_be_able_to_download_files() throws Exception {
        // Arrange
        FtpDownloader ftpDownloader;
        String downloadURL = "ftp://other.file.com/other.txt";
        String outputFilePath = "other.txt";
        String host = "localhost";
        String user = "agoda";
        String password = "1234";
        Source source = new Source(Protocol.FTP, downloadURL, outputFilePath, host, user, password);

        // Act
        ftpDownloader = new FtpDownloader(source);

        // Assert
        Assert.assertTrue(ftpDownloader instanceof Downloader);
    }

    @Test
    public void download_shouldDownloadUsingURLConnection_ifURLFormatIncludeUserAndPassword() throws Exception {
        // Arrange
        String downloadURL = "ftp://agoda:1234@localhost/captain.mkv";
        String outputFilePath = "/Users/worasitdaimongkol/xxx/captain.mkv";
        String host = "localhost";
        String user = "agoda";
        String password = "1234";
        Source source = new Source(Protocol.FTP, downloadURL, outputFilePath, host, user, password);
        FtpDownloader ftpDownloader = spy(new FtpDownloader(source));

        doReturn(true)
                .when(ftpDownloader)
                .isAbleToDownloadViaURLConnection(anyString());
        doNothing()
                .when(ftpDownloader)
                .downloadUsingURLConnection(anyString());

        // Act
        ftpDownloader.download();

        // Assert
        verifyPrivate(ftpDownloader, times(1))
                .invoke("isAbleToDownloadViaURLConnection", downloadURL);
        verifyPrivate(ftpDownloader, times(1))
                .invoke("downloadUsingURLConnection", downloadURL);
    }

    @Test
    public void download_shouldDownloadUsingFtpClient_ifURLFormatNotIncludeUserAndPassword() throws Exception {
        // Arrange
        String downloadURL = "ftp://localhost/captain.mkv";
        String outputFilePath = "/Users/worasitdaimongkol/xxx/captain.mkv";
        String host = "localhost";
        String user = "agoda";
        String password = "1234";
        Source source = new Source(Protocol.FTP, downloadURL, outputFilePath, host, user, password);
        FtpDownloader ftpDownloader = spy(new FtpDownloader(source));

//        doReturn(false)
//                .when(ftpDownloader)
//                .isAbleToDownloadViaURLConnection(anyString());
//        doNothing()
//                .when(ftpDownloader)
//                .downloadUsingURLConnection(anyString());

        // Act
//        ftpDownloader.download();

        // Assert
//        verifyPrivate(ftpDownloader, times(1))
//                .invoke("isAbleToDownloadViaURLConnection", downloadURL);
//        verifyPrivate(ftpDownloader, times(0))
//                .invoke("downloadUsingURLConnection", downloadURL);
    }

}