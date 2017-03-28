package com.agoda.downloaders;

import com.agoda.source.FtpSource;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.Session;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.InputStream;
import java.util.Properties;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.internal.verification.VerificationModeFactory.times;
import static org.powermock.api.mockito.PowerMockito.*;

@RunWith(PowerMockRunner.class)
@PrepareForTest(SftpDownloader.class)
public class SftpDownloaderTest {

    @Test
    public void should_be_able_to_download_files() throws Exception {
        // Arrange
        SftpDownloader sftpDownloader;
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
        String downloadURL = "sftp://agoda:1234@localhost/Users/worasitdaimongkol/FTP/captain.mkv";
        String outputFilePath = "/Users/worasitdaimongkol/xxx/captain.mkv";
        String host = "localhost";
        String user = "agoda";
        String password = "1234";
        FtpSource sftpSource = new FtpSource(Protocol.SFTP, downloadURL, outputFilePath, host, user, password);
        SftpDownloader sftpDownloader = spy(new SftpDownloader(sftpSource));

        Properties mockConfig = mock(Properties.class);
        Session mockSession = mock(Session.class);
        ChannelSftp mockChannelSftp = mock(ChannelSftp.class);
        InputStream mockInputStream = mock(InputStream.class);

        doReturn(mockConfig).when(sftpDownloader, "getSessionConfiguration");
        doReturn(mockSession).when(sftpDownloader, "establishSession", any(Properties.class));
        doReturn(mockChannelSftp).when(sftpDownloader, "establishSftpChannel", any(Session.class));
        doReturn(mockInputStream).when(sftpDownloader, "getInputStream", any(ChannelSftp.class));
        doNothing().when(sftpDownloader).writeStreamData(any(InputStream.class), anyLong());

        // Act
        sftpDownloader.download();

        // Assert
        verifyPrivate(sftpDownloader, times(1))
                .invoke("getSessionConfiguration");
        verifyPrivate(sftpDownloader, times(1))
                .invoke("establishSession", mockConfig);
        verifyPrivate(sftpDownloader, times(1))
                .invoke("establishSftpChannel", mockSession);
        verifyPrivate(sftpDownloader, times(1))
                .invoke("getInputStream", mockChannelSftp);
        verifyPrivate(sftpDownloader, times(1))
                .invoke("writeStreamData", mockInputStream, Long.MAX_VALUE);


    }

}