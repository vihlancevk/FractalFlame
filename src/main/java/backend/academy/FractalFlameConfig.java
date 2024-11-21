package backend.academy;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;
import java.util.Collections;
import java.util.List;

public class FractalFlameConfig {
    private static final Integer DEFAULT_WIDTH = 1024;
    private static final Integer DEFAULT_HEIGHT = 1024;

    private static final List<List<Double>> DEFAULT_AFFINES = List.of(
        List.of(1.005, 0.0, 0.0, 0.0, 1.005, 0.0, 0.498, 0.0, 1.0),
        List.of(0.0, 1.005, 0.0, 0.0, 0.0, 1.005, 0.498, 0.0, 1.0),
        List.of(-0.995, 1.002, 0.0, -1.002, 0.995, 0.0, 1.0, 0.4, 0.698)
    );
    private static final List<String> DEFAULT_VARIATIONS = List.of("Linear");

    private static final Integer DEFAULT_SAMPLES = 512;
    private static final Short DEFAULT_ITER_PER_SAMPLE = 512;

    private static final Long DEFAULT_SEED = 0L;

    private static final Integer DEFAULT_SYMMETRY = 0;

    private final Integer width;
    private final Integer height;

    private final List<List<Double>> affines;
    private final List<String> variations;

    private final Integer samples;
    private final Short iterPerSample;

    private final Long seed;

    private final Integer symmetry;
    private final boolean isRelativeSymmetry;

    @JsonCreator
    @SuppressWarnings("ParameterNumber")
    public FractalFlameConfig(
        @JsonProperty("width") Integer width,
        @JsonSetter("height") Integer height,
        @JsonProperty("affines") List<List<Double>> affines,
        @JsonProperty("variations") List<String> variations,
        @JsonProperty("samples") Integer samples,
        @JsonProperty("iter-per-sample") Short iterPerSample,
        @JsonProperty("seed") Long seed,
        @JsonProperty("symmetry") Integer symmetry,
        @JsonProperty("is-relative-symmetry") boolean isRelativeSymmetry
    ) {
        this.width = width == null ? DEFAULT_WIDTH : width;
        this.height = height == null ? DEFAULT_HEIGHT : height;
        this.affines = affines == null ? DEFAULT_AFFINES : affines;
        this.variations = variations == null ? DEFAULT_VARIATIONS : variations;
        this.samples = samples == null ? DEFAULT_SAMPLES : samples;
        this.iterPerSample = iterPerSample == null ? DEFAULT_ITER_PER_SAMPLE : iterPerSample;
        this.seed = seed == null ? DEFAULT_SEED : seed;
        this.symmetry = symmetry == null ? DEFAULT_SYMMETRY : symmetry;
        this.isRelativeSymmetry = isRelativeSymmetry;
    }

    public int width() {
        return width;
    }

    public int height() {
        return height;
    }

    public List<List<Double>> affines() {
        return Collections.unmodifiableList(affines);
    }

    public List<String> variations() {
        return Collections.unmodifiableList(variations);
    }

    public int samples() {
        return samples;
    }

    public short iterPerSample() {
        return iterPerSample;
    }

    public long seed() {
        return seed;
    }

    public int symmetry() {
        return symmetry;
    }

    public boolean isRelativeSymmetry() {
        return isRelativeSymmetry;
    }
}
