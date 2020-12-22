package pl.mini.pw.zanieczyszczenie.communicator;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Optional;
import java.util.Scanner;

public class Connection {
    private java.net.URL url;

    public Connection(String URLstring) {
        try {
            this.url = new URL(URLstring);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    private static HttpURLConnection getConnection(URL url) throws IOException{
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.connect();
        int responseCode = connection.getResponseCode();
        if (responseCode!=200) {
            throw new IOException("URL "+ url.toString() + " produced HttpResponseCode: " + responseCode + " "
                    + connection.getResponseMessage());
        }
        return connection;
    }

    private static String collectConnection(HttpURLConnection connection) throws IOException {
        BufferedReader br = new BufferedReader(
                new InputStreamReader(connection.getInputStream())
        );
        Optional<String> response = br.lines().reduce(String::concat);
        br.close();
        return response.orElseThrow(()-> new IOException("Empty response and/or something has gone terribly wrong"));
    }

    public String getData() {
        try {
            HttpURLConnection connection = getConnection(url);
            return collectConnection(connection);
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String readDataFromURL(String urlString) throws IOException {
        URL url = new URL(urlString);
        HttpURLConnection connection = getConnection(url);
        BufferedReader br = new BufferedReader(
                new InputStreamReader(connection.getInputStream())
        );
        Optional<String> response = br.lines().reduce(String::concat);
        br.close();
        return response.orElseThrow(()-> new IOException("Empty response"));
    }

    public static String getDataFromURL(String urlString) {
        try {
            return readDataFromURL(urlString);
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }
}
