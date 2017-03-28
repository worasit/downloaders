package com.agoda.utilities;


import com.agoda.downloaders.Protocol;
import com.agoda.models.Source;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class SourceExtractor {


    public static List<Source> extract(String sources, String localOutputDirectory) throws MalformedURLException, URISyntaxException {

        String separator = ",";
        List<Source> extractedSources = new ArrayList<>();
        String[] sourceURLs = sources.split(separator);

        for (String sourceURL : sourceURLs) {
            if (isValidURL(sourceURL)) {

                URI uri = new URI(sourceURL);
                Protocol protocol = getSourceProtocol(uri.getScheme());
                String outputFilePath = getOutputFilePath(localOutputDirectory, uri);
                Source extractedSource = new Source(protocol, sourceURL, outputFilePath);
                extractedSources.add(extractedSource);

            } else {
                Logger.getGlobal().info(MessageFormat.format("The {0} URL is not valid", sourceURL));
            }
        }

        return extractedSources;
    }

    private static String getOutputFilePath(String localOutputDirectory, URI uri) {
        String outputFile = beautifyOutputFilename(uri);
        return String.valueOf(Paths.get(localOutputDirectory, outputFile));
    }

    private static String beautifyOutputFilename(URI uri) {
        String outputFile = uri.getPath().replace('/', '-').replaceAll(" ", "");
        if (outputFile.charAt(0) == '-') {
            outputFile = outputFile.substring(1);
        }
        return outputFile;
    }


    private static boolean isValidURL(String urlString) {
        try {
            URI uri = new URI(urlString);
            return true;
        } catch (URISyntaxException e) {
            return false;
        }
    }

    private static Protocol getSourceProtocol(String protocol) {
        switch (protocol) {
            case "http":
                return Protocol.HTTP;
            case "https":
                return Protocol.HTTPS;
            case "ftp":
                return Protocol.FTP;
            case "sftp":
                return Protocol.SFTP;
            default:
                throw new EnumConstantNotPresentException(Protocol.class, protocol);
        }
    }


}
