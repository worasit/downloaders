package com.agoda.source;


import com.agoda.downloaders.Protocol;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class SourceExtractor {

    public static List<Source> extract(String sources, String localOutputDirectory,
                                       String ftpUser, String ftpPassword,
                                       String sftpUser, String sftpPassword) throws MalformedURLException, URISyntaxException {

        String separator = ",";
        List<Source> extractedSources = new ArrayList<>();
        String[] sourceURLs = sources.split(separator);

        for (String sourceURL : sourceURLs) {
            if (isValidURL(sourceURL)) {
                Source extractedSource = buildSourceObject(sourceURL, localOutputDirectory, ftpUser, ftpPassword, sftpUser, sftpPassword);
                extractedSources.add(extractedSource);
            } else {
                Logger.getGlobal().info(MessageFormat.format("The {0} URL is not valid", sourceURL));
            }
        }

        return extractedSources;
    }


    private static Source buildSourceObject(String sourceURL, String localOutputDirectory,
                                            String ftpUser, String ftpPassword,
                                            String sftpUser, String sftpPassword) throws URISyntaxException {

        URI uri = new URI(sourceURL);
        String protocol = uri.getScheme();
        String outputFilePath = getOutputFilePath(localOutputDirectory, uri.getPath());
        String host = uri.getHost();
        String userInfo = uri.getUserInfo();

        switch (protocol) {
            case "http":
                return new Source(Protocol.HTTP, sourceURL, outputFilePath, host, null, null);
            case "https":
                return new Source(Protocol.HTTPS, sourceURL, outputFilePath, host, null, null);
            case "ftp":
                return new Source(Protocol.FTP, sourceURL, outputFilePath, host, getUser(userInfo, ftpUser), getPassword(userInfo, ftpPassword));
            case "sftp":
                return new Source(Protocol.SFTP, sourceURL, outputFilePath, host, getUser(userInfo, sftpUser), getPassword(userInfo, sftpPassword));
            default:
                throw new EnumConstantNotPresentException(Protocol.class, protocol);
        }
    }

    private static String getUser(String userInfo, String user) {
        return (userInfo != null) ? userInfo.split(":")[0] : user;
    }

    private static String getPassword(String userInfo, String password) {
        return (userInfo != null) ? userInfo.split(":")[1] : password;
    }

    private static boolean isValidURL(String urlString) {
        try {
            URI uri = new URI(urlString);
            return true;
        } catch (URISyntaxException e) {
            return false;
        }
    }

    private static String getOutputFilePath(String localOutputDirectory, String filePath) {
        String outputFile = beautifyOutputFilename(filePath);
        return String.valueOf(Paths.get(localOutputDirectory, outputFile));
    }

    private static String beautifyOutputFilename(String filePath) {
        String outputFile = filePath.replace('/', '-').replaceAll(" ", "");
        if (outputFile.charAt(0) == '-') {
            outputFile = outputFile.substring(1);
        }
        return outputFile;
    }


}
