package com.agoda.downloaders;


import com.agoda.source.FtpSource;
import com.agoda.source.Source;

public class DownloaderFactory {
    public Downloadable getDownloader(Source source) throws TypeNotPresentException {
        switch (source.protocol) {
            case HTTP:
                return new HttpDownloader(source);
            case HTTPS:
                return new HttpsDownloader(source);
            case FTP:
                FtpSource ftpSource = (FtpSource) source;
                return new FtpDownloader(ftpSource);
            case SFTP:
                FtpSource sftpSource = (FtpSource) source;
                return new SftpDownloader(sftpSource);
            default:
                throw new TypeNotPresentException(Protocol.class.getTypeName(), new Throwable("Download protocol is not supported."));
        }
    }
}
