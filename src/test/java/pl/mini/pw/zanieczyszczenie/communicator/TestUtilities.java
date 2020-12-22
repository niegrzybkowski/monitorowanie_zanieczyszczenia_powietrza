package pl.mini.pw.zanieczyszczenie.communicator;

import java.io.IOException;
import java.util.Objects;

public final class TestUtilities {
    public static String loadFromTestResources(String path) {
        try {
            return new String(Objects.requireNonNull(BasicParserTestFindAll
                    .class
                    .getClassLoader()
                    .getResourceAsStream("dummyAPI/" + path + ".json"))
                    .readAllBytes()
            );
        } catch (IOException e) {
            System.err.println("Error loading from resource: " + e.getMessage());
            return "";
        }
    }
}
