package com.agoda.downloaders;


import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

public abstract class Downloader implements Downloadable {

    protected String downloadURL;
    protected String outputFilePath;

    public Downloader(String downloadURL, String outputFilePath) {
        this.downloadURL = downloadURL;
        this.outputFilePath = outputFilePath;
    }

    protected void writeDataFromChannel(ReadableByteChannel readableByteChannel, String outputFilePath, long contentLength) throws IOException {
        try (FileOutputStream fileOutputStream = new FileOutputStream(outputFilePath)) {
            fileOutputStream.getChannel().transferFrom(readableByteChannel, 0, contentLength);
        }
    }

    protected ReadableByteChannel establishChannel(InputStream inputStream) throws IOException {
        return Channels.newChannel(inputStream);
    }
}

