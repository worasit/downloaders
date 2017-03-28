package com.agoda.downloaders;


import com.agoda.source.FtpSource;
import com.agoda.source.Source;

public class DownloaderFactory {
    public Downloadable getDownloader(Source source) throws TypeNotPresentException {
        switch (source.protocol) {
            case HTTP:
                return new HttpDownloader(source.sourceURL, source.outputFilePath);
            case HTTPS:
                return new HttpsDownloader(source.sourceURL, source.outputFilePath);
            case FTP:
                FtpSource ftpSource = (FtpSource) source;
                return new FtpDownloader(ftpSource.sourceURL, ftpSource.outputFilePath, ftpSource.user, ftpSource.password);
            case SFTP:
                FtpSource sftpSource = (FtpSource) source;
                return new SftpDownloader(sftpSource.sourceURL, sftpSource.outputFilePath, sftpSource.user, sftpSource.password);
            default:
                throw new TypeNotPresentException(Protocol.class.getTypeName(), new Throwable("Download protocol is not supported."));
        }
    }
}
