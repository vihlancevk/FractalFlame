package backend.academy;

import lombok.experimental.UtilityClass;

@UtilityClass
public class Main {
    public static void main(String[] args) {
        FractalFlameConfig config = FractalFlameConfigLoader.loadConfig("config.yml");
        FractalFlameConfigValidator.validate(config);
        FractalFlame.render(config);
    }
}
