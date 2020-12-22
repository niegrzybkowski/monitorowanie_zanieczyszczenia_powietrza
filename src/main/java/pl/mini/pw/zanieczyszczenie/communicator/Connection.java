package pl.mini.pw.zanieczyszczenie.communicator;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
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

    private static HttpURLConnection getConnection(URL url) {
        HttpURLConnection connection = null;
        try {
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            int responseCode = connection.getResponseCode();
            if (responseCode!=200) {
                throw new IOException("HttpResponseCode: " + responseCode + " "
                        + connection.getResponseMessage());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return connection;
    }

    public String getData() {
        HttpURLConnection connection = getConnection(this.url); // TODO: poprawić, connection jest nieużywane

        StringBuilder input = new StringBuilder();
        Scanner scanner = null;
        try {
            scanner = new Scanner(this.url.openStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (scanner==null) {
            throw new NullPointerException("No data");
        }
        while (scanner.hasNext()) {
            input.append(scanner.nextLine());
        }
        scanner.close();

        return input.toString();
    }
}
