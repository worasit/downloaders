package com.agoda.downloaders;


import com.agoda.models.Source;

public class DownloaderFactory {
    public Downloadable getDownloader(Source source) throws TypeNotPresentException {
        switch (source.protocol) {
            case HTTP:
                return new HttpDownloader(source.sourceURL, source.outputFilePath);
            case HTTPS:
                return new HttpsDownloader(source.sourceURL, source.outputFilePath);
            case FTP:
                return new FtpDownloader(source.sourceURL, source.outputFilePath);
            case SFTP:
                return new SftpDownloader(source.sourceURL, source.outputFilePath);
            default:
                throw new TypeNotPresentException(Protocol.class.getTypeName(), new Throwable("Download protocol is not supported."));
        }
    }
}
