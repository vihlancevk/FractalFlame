package backend.academy;

import backend.academy.transformation.Transformation;
import backend.academy.transformation.implementation.Affine;
import backend.academy.transformation.implementation.Horseshoe;
import backend.academy.transformation.implementation.Linear;
import backend.academy.transformation.implementation.Sinusoidal;
import backend.academy.transformation.implementation.Spherical;
import backend.academy.transformation.implementation.Swirl;
import backend.image.processor.ImageProcessor;
import backend.image.processor.implementation.LogarithmicGammaCorrection;
import java.nio.file.Path;
import java.util.List;
import java.util.Random;
import lombok.experimental.UtilityClass;

@UtilityClass
@SuppressWarnings("MagicNumber")
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

        int symmetry = 4;
        boolean isRelativeSymmetry = false;

        double l = 0.1;
        for (double y = -1; Double.compare(y + l, 1.0) < 0; y += l) {
            for (double x = -1; Double.compare(x + l, 1.0) < 0; x += l) {
                Rect world = new Rect(x, y, l, l);
                render(canvas, world, affines, variations, samples, iterPerSample, random, symmetry, isRelativeSymmetry);
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
        Random random,
        int symmetry,
        boolean isRelativeSymmetry
    ) {
        for (int num = 0; num < samples; num++) {
            Point point = world.getRandomPoint(random);

            for (short step = 0; step < iterPerSample; step++) {
                Affine affine = getRandomElement(affines, random);
                Transformation variation = getRandomElement(variations, random);

                point = affine.apply(point);
                point = variation.apply(point);

                double angle = 0;
                for (int s = 0; s < symmetry; angle += Math.PI * 2 / symmetry, s++) {
                    point = isRelativeSymmetry
                        ? rotateRelativeSymmetry(world, point, angle)
                        : rotateAbsoluteSymmetry(point, angle);
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
    }

    private static <T> T getRandomElement(List<T> elements, Random random) {
        int randomIndex = random.nextInt(elements.size());
        return elements.get(randomIndex);
    }

    private static Point rotateAbsoluteSymmetry(Point point, double angle) {
        double absoluteCenterX = 0;
        double absoluteCenterY = 0;

        return rotate(point, angle, absoluteCenterX, absoluteCenterY);
    }

    private static Point rotateRelativeSymmetry(Rect world, Point point, double angle) {
        double relativeCenterX = world.x() + world.width() / 2;
        double relativeCenterY = world.y() + world.height() / 2;

        return rotate(point, angle, relativeCenterX, relativeCenterY);
    }

    private static Point rotate(Point point, double angle, double centreX, double centreY) {
        double relativeX = point.x() - centreX;
        double relativeY = point.y() - centreY;

        double relativeRotatedX = relativeX * Math.cos(angle) - relativeY * Math.sin(angle);
        double relativeRotatedY = relativeX * Math.sin(angle) + relativeY * Math.cos(angle);

        return new Point(relativeRotatedX + centreX, relativeRotatedY + centreY);
    }
}
