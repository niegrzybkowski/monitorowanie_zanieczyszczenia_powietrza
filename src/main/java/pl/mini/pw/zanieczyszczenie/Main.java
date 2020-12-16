package pl.mini.pw.zanieczyszczenie;

import org.json.*;

public class Main {
    public static void main(String[] args) {
        System.out.println("Test");
        JSONObject jooo = new JSONObject();
        jooo.put("me", "ja");
        jooo.put("not me", "nie ja");
        System.out.println(jooo.toString());
    }
}
