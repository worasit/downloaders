package com.agoda.downloaders;


import com.agoda.source.Source;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.text.MessageFormat;
import java.util.concurrent.Callable;
import java.util.logging.Logger;

public abstract class Downloader implements Downloadable, Callable<Boolean> {

    protected static final int START_BUFFER_POSITION = 0;
    protected Logger logger = Logger.getLogger(Downloader.class.getSimpleName());
    private Source source;

    public Downloader(Source source) {
        this.source = source;
    }


    @Override
    public Boolean call() throws Exception {
        logger.info(MessageFormat.format("Starting download : {0}", this.getSource().getSourceURL()));
        try {
            download();
        } catch (Exception e) {
            logger.severe(MessageFormat.format("Failed to download : {0} error msg: {1}", this.getSource().getSourceURL(), e.getMessage()));
            throw e;
        }
        logger.info(MessageFormat.format("Finished download : {0}, the file is saved to {1}", this.getSource().getSourceURL(), this.getSource().getOutputFilePath()));
        return true;
    }

    public void writeStreamData(InputStream inputStream, long contentLength) throws IOException {
        ReadableByteChannel readableByteChannel = establishChannel(inputStream);
        writeDataFromChannel(readableByteChannel, this.source.getOutputFilePath(), contentLength);
    }

    protected void writeDataFromChannel(ReadableByteChannel readableByteChannel, String outputFilePath, long contentLength) throws IOException {
        try (FileOutputStream fileOutputStream = new FileOutputStream(outputFilePath)) {
            fileOutputStream.getChannel().transferFrom(readableByteChannel, START_BUFFER_POSITION, contentLength);
        }
    }

    protected ReadableByteChannel establishChannel(InputStream inputStream) throws IOException {
        return Channels.newChannel(inputStream);
    }

    public Source getSource() {
        return source;
    }

    public void setSource(Source source) {
        this.source = source;
    }

}

