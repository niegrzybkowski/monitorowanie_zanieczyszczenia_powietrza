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
            throw new IOException("HttpResponseCode: " + responseCode + " "
                    + connection.getResponseMessage());
        }
        return connection;
    }

    public String getData() {
        try {
            HttpURLConnection connection = getConnection(this.url); // TODO: poprawić, connection jest nieużywane
            BufferedReader br = new BufferedReader(
                    new InputStreamReader(connection.getInputStream())
            );
            Optional<String> response = br.lines().reduce(String::concat);
            br.close();
            return response.orElseThrow(()-> new IOException("Empty response"));
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }
}
