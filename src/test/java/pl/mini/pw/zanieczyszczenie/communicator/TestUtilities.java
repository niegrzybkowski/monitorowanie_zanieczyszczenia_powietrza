package pl.mini.pw.zanieczyszczenie.communicator;

import java.io.IOException;
import java.util.Objects;

public final class TestUtilities {
    public static String loadFromTestResources(String path) throws IOException {
        return new String(Objects.requireNonNull(BasicParserTestFindAll
                .class
                .getClassLoader()
                .getResourceAsStream(path))
                .readAllBytes()
        );
    }

}
