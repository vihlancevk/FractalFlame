package backend.academy;

import java.util.List;

@SuppressWarnings({"MultipleStringLiterals", "MagicNumber"})
public final class FractalFlameConfigValidator {
    private FractalFlameConfigValidator() {

    }

    public static void validate(FractalFlameConfig config) {
        validateImageSize(config.width(), config.height());
        validateAffines(config.affines());
        validateSamples(config.samples());
        validateIterPerSample(config.iterPerSample());
        validateSymmetry(config.symmetry());
        validateNumberOfThreads(config.numberOfThreads());
    }

    private static void validateImageSize(int width, int height) {
        int min = 64;
        int max = 2048;

        if (!(min <= width && width <= max)) {
            throw new IllegalArgumentException(
                "The image width must be between " + min + " and " + max + ", but not: " + width
            );
        }

        if (!(min <= height && height <= max)) {
            throw new IllegalArgumentException(
                "The image height must be between " + min + " and " + max + ", but not: " + height
            );
        }
    }

    private static void validateAffines(List<List<Double>> affines) {
        for (List<Double> affine : affines) {
            validateAffine(affine);
        }
    }

    private static void validateAffine(List<Double> matrix) {
        int matrixSize = 9;
        int nCoffs = 6;

        if (matrix.size() != matrixSize) {
            throw new IllegalArgumentException(
                "Matrix size must be equal to " + matrixSize + ", but not: " + matrix.size()
            );
        }

        for (int i = 0; i < nCoffs; i++) {
            validateCoff(matrix.get(i));
        }

        for (int i = nCoffs; i < matrixSize; i++) {
            validateColor(matrix.get(i));
        }
    }

    private static void validateCoff(double coff) {
        double min = -1.5;
        double max = 1.5;

        if (!(min <= coff && coff <= max)) {
            throw new IllegalArgumentException(
                "Coefficient must be from " + min + " to " + max + ", but not: " + coff
            );
        }
    }

    private static void validateColor(double color) {
        double min = 0.0;
        double max = 1.0;

        if (!(min <= color && color <= max)) {
            throw new IllegalArgumentException(
                "Color must be from " + min + " to " + max + ", but not: " + color
            );
        }
    }

    private static void validateSamples(int samples) {
        int min = 128;
        int max = 8192;

        if (!(min <= samples && samples <= max)) {
            throw new IllegalArgumentException(
                "Samples must be from " + min + " to " + max + ", but not: " + samples
            );
        }
    }

    private static void validateIterPerSample(short iterPerSample) {
        short min = 128;
        short max = 8192;

        if (!(min <= iterPerSample && iterPerSample <= max)) {
            throw new IllegalArgumentException(
                "Iter per sample must be from " + min + " to " + max + ", but not: " + iterPerSample
            );
        }
    }

    private static void validateSymmetry(int symmetry) {
        int min = 1;
        int max = 16;

        if (!(min <= symmetry && symmetry <= max)) {
            throw new IllegalArgumentException(
                "Symmetry must be from " + min + " to " + max + ", but not: " + symmetry
            );
        }
    }

    private static void validateNumberOfThreads(short numberOfThreads) {
        short min = 1;
        short max = Short.MAX_VALUE;

        if (!(min <= numberOfThreads)) {
            throw new IllegalArgumentException(
                "Number of threads must be from " + min + " to " + max + ", but not: " + numberOfThreads
            );
        }
    }
}
