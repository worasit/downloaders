package com.agoda.downloaders;

import com.agoda.source.Source;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.InputStream;
import java.nio.channels.ReadableByteChannel;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyString;


public class DownloaderTest {
    @Test
    public void writeStreamData() throws Exception {
        // Arrange
        long expectedContentLength = Long.MAX_VALUE;
        String expectedOutputFilePath = "/User/path/fake.txt";
        Downloader downloader = Mockito.mock(Downloader.class, Mockito.CALLS_REAL_METHODS);
        Source fakeSource = new Source(Protocol.FTP, "ftp://fakeUser:fakePassword@localhost.com/myfile.txt", expectedOutputFilePath, "localhost.com", "fakeuser", "fakepassword");
        downloader.setSource(fakeSource);

        InputStream inputStream = Mockito.mock(InputStream.class);
        ReadableByteChannel readableByteChannel = Mockito.mock(ReadableByteChannel.class);

        Mockito.doReturn(readableByteChannel)
                .when(downloader)
                .establishChannel(any(InputStream.class));

        Mockito.doNothing()
                .when(downloader)
                .writeDataFromChannel(any(ReadableByteChannel.class), anyString(), anyLong());

        // Act
        downloader.writeStreamData(inputStream, expectedContentLength);

        // Assert
        Mockito.verify(downloader, Mockito.times(1)).establishChannel(inputStream);
        Mockito.verify(downloader, Mockito.times(1)).writeDataFromChannel(readableByteChannel, expectedOutputFilePath, expectedContentLength);
    }

}