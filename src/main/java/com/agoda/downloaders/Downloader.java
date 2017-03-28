package com.agoda.downloaders;


import com.agoda.source.Source;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

public abstract class Downloader implements Downloadable {

    protected static final int START_BUFFER_POSITION = 0;
    protected String sourceURL;
    protected String outputFilePath;

    public Downloader(Source source) {
        this.sourceURL = source.sourceURL;
        this.outputFilePath = source.outputFilePath;
    }

    public void writeStreamData(InputStream inputStream, long contentLength) throws IOException {
        ReadableByteChannel readableByteChannel = establishChannel(inputStream);
        writeDataFromChannel(readableByteChannel, this.outputFilePath, contentLength);
    }

    protected void writeDataFromChannel(ReadableByteChannel readableByteChannel, String outputFilePath, long contentLength) throws IOException {
        try (FileOutputStream fileOutputStream = new FileOutputStream(outputFilePath)) {
            fileOutputStream.getChannel().transferFrom(readableByteChannel, START_BUFFER_POSITION, contentLength);
        }
    }

    protected ReadableByteChannel establishChannel(InputStream inputStream) throws IOException {
        return Channels.newChannel(inputStream);
    }
}

