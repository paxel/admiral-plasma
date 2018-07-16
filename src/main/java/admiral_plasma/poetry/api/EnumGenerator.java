package admiral_plasma.poetry.api;

import java.io.IOException;

public interface EnumGenerator {

    <T> T generate() throws IOException;

}
