package pl.mini.pw.zanieczyszczenie.communicator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Optional;

class Connection {
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

    public static String getDataFromURLThrowing(String urlString) throws IOException {
        URL url = new URL(urlString);
        HttpURLConnection connection = getConnection(url);
        return collectConnection(connection);
    }

    public static String getDataFromURL(String urlString) {
        try {
            return getDataFromURLThrowing(urlString);
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }
}
