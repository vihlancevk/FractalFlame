package backend.academy;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public final class FractalFlameConfigLoader {
    private static final ObjectMapper MAPPER = new ObjectMapper(new YAMLFactory());

    private FractalFlameConfigLoader() {

    }

    public static FractalFlameConfig loadConfig(String filePath) {
        try {
            return MAPPER.readValue(Files.newInputStream(Path.of(filePath)), FractalFlameConfig.class);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load configuration file: " + filePath, e);
        }
    }
}
