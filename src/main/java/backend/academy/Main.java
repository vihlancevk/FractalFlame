package backend.academy;

import backend.academy.transformation.Transformation;
import backend.academy.transformation.implementation.Affine;
import backend.academy.transformation.implementation.Horseshoe;
import backend.academy.transformation.implementation.Linear;
import backend.academy.transformation.implementation.Sinusoidal;
import backend.academy.transformation.implementation.Spherical;
import backend.academy.transformation.implementation.Swirl;
import java.nio.file.Path;
import java.util.List;
import java.util.Random;
import backend.image.processor.ImageProcessor;
import backend.image.processor.implementation.LogarithmicGammaCorrection;
import lombok.experimental.UtilityClass;

@UtilityClass
public class Main {
    public static void main(String[] args) {
        int width = 1000;
        int height = 1000;
        FractalImage canvas = FractalImage.create(width, height);

        List<Affine> affines = List.of(
            new Affine(1.005, 0, 0, 0, 1.005, 0, 0.498, 0, 1),
            new Affine(0, 1.005, 0, 0, 0, 1.005, 0.498, 0, 1),
            new Affine(-0.995, 1.002, 0, -1.002, 0.995, 0, 1, 0.4, 0.698)
        );

        List<Transformation> variations = List.of(
            new Linear(), new Sinusoidal(), new Spherical(), new Swirl(), new Horseshoe()
        );

        int samples = 200;
        short iterPerSample = 10000;

        long seed = 100L;
        Random random = new Random(seed);

        double w = 0.1;
        double h = 0.1;
        for (double y = -1; Double.compare(y + h, 1.0) < 0; y += h) {
            for (double x = -1; Double.compare(x + w, 1.0) < 0; x += w) {
                Rect world = new Rect(x, y, w, h);
                render(canvas, world, affines, variations, samples, iterPerSample, random);
            }
        }

        ImageProcessor imageProcessor = new LogarithmicGammaCorrection();
        imageProcessor.process(canvas);

        ImageUtils.save(canvas, ImageFormat.PNG, Path.of("fractal-flame.png"));
    }

    private static void render(
        FractalImage canvas,
        Rect world,
        List<Affine> affines,
        List<Transformation> variations,
        int samples,
        short iterPerSample,
        Random random) {
        for (int num = 0; num < samples; num++) {
            Point point = world.getRandomPoint(random);

            for (short step = 0; step < iterPerSample; step++) {
                Affine affine = getRandomElement(affines, random);
                Transformation variation = getRandomElement(variations, random);

                point = affine.apply(point);
                point = variation.apply(point);
                if (!world.contains(point)) {
                    continue;
                }

                Pixel pixel = canvas.pixel(point.x(), point.y());
                if (pixel == null) {
                    continue;
                }

                if (pixel.getHitCount() == 0) {
                    pixel.setR(affine.getRgbR());
                    pixel.setG(affine.getRgbG());
                    pixel.setB(affine.getRgbB());
                } else {
                    pixel.setR((pixel.getR() + affine.getRgbR()) / 2);
                    pixel.setG((pixel.getG() + affine.getRgbG()) / 2);
                    pixel.setB((pixel.getB() + affine.getRgbB()) / 2);
                    pixel.setHitCount(pixel.getHitCount() + 1);
                }
                pixel.setHitCount(pixel.getHitCount() + 1);
            }
        }
    }

    private static <T> T getRandomElement(List<T> elements, Random random) {
        int randomIndex = random.nextInt(elements.size());
        return elements.get(randomIndex);
    }
}
