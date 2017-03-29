package com.agoda.downloaders;



import com.agoda.source.Source;

public class DownloaderFactory {
    public Downloadable getDownloader(Source source) throws TypeNotPresentException {
        switch (source.getProtocol()) {
            case HTTP:
                return new HttpDownloader(source);
            case HTTPS:
                return new HttpsDownloader(source);
            case FTP:
                return new FtpDownloader(source);
            case SFTP:
                return new SftpDownloader(source);
            default:
                throw new TypeNotPresentException(Protocol.class.getTypeName(), new Throwable("Download protocol is not supported."));
        }
    }
}
