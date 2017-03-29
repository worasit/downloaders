package com.agoda.downloaders;

import com.agoda.source.Source;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;

public class HttpsDownloader extends HttpDownloader {


    public HttpsDownloader(Source source) {
        super(source);
    }

    @Override
    public void download() throws IOException {
        trustAllCertificate();
        super.download();
    }

    private void trustAllCertificate() {

        try {
            TrustManager[] trustManagers = initTrustManagers();
            SSLContext sslContext = initSSLContext(trustManagers);
            HttpsURLConnection.setDefaultSSLSocketFactory(sslContext.getSocketFactory());
        } catch (Exception ignored) {
        }
    }

    private SSLContext initSSLContext(TrustManager[] trustManagers) throws NoSuchAlgorithmException, KeyManagementException {
        SSLContext sslContext = SSLContext.getInstance("SSL");
        sslContext.init(null, trustManagers, new java.security.SecureRandom());
        return sslContext;
    }

    private TrustManager[] initTrustManagers() {
        return new TrustManager[]{
                new X509TrustManager() {
                    public X509Certificate[] getAcceptedIssuers() {
                        return null;
                    }

                    public void checkClientTrusted(
                            X509Certificate[] certs, String authType) {
                    }

                    public void checkServerTrusted(
                            X509Certificate[] certs, String authType) {
                    }
                }
        };
    }

    @Override
    protected URLConnection getUrlConnection(String downloadURL) throws IOException {
        URL url = new URL(downloadURL);
        URLConnection urlConnection = url.openConnection();
        urlConnection.setRequestProperty("User-Agent", "");
        return urlConnection;
    }
}
