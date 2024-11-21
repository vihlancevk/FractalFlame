package backend.academy;

import backend.academy.image.processor.ImageProcessor;
import backend.academy.image.processor.implementation.LogarithmicGammaCorrection;
import backend.academy.transformation.Transformation;
import backend.academy.transformation.TransformationFactory;
import backend.academy.transformation.implementation.Affine;
import java.nio.file.Path;
import java.security.SecureRandom;
import java.util.List;
import java.util.Random;

public final class FractalFlame {
    private static final Random RANDOM = new SecureRandom();
    private static final double SQUARE_SIDE = 0.1;

    private FractalFlame() {

    }

    public static void render(FractalFlameConfig config) {
        FractalImage canvas = FractalImage.create(config.width(), config.height());

        List<Affine> affines = config.affines().stream().map(Affine::new).toList();
        List<Transformation> variations = TransformationFactory.createTransformations(config.variations());

        RANDOM.setSeed(config.seed());

        for (double y = -1; Double.compare(y + SQUARE_SIDE, 1.0) < 0; y += SQUARE_SIDE) {
            for (double x = -1; Double.compare(x + SQUARE_SIDE, 1.0) < 0; x += SQUARE_SIDE) {
                Rect world = new Rect(x, y, SQUARE_SIDE, SQUARE_SIDE);
                render(canvas, world, affines, variations, config);
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
        FractalFlameConfig config
    ) {
        for (int num = 0; num < config.samples(); num++) {
            Point point = world.getRandomPoint(RANDOM);

            for (short step = 0; step < config.iterPerSample(); step++) {
                Affine affine = getRandomElement(affines);
                Transformation variation = getRandomElement(variations);

                point = affine.apply(point);
                point = variation.apply(point);

                int symmetry = config.symmetry();
                double angle = 0;
                for (int s = 0; s < symmetry; angle += Math.PI * 2 / symmetry, s++) {
                    point = config.isRelativeSymmetry()
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

    private static <T> T getRandomElement(List<T> elements) {
        int randomIndex = RANDOM.nextInt(elements.size());
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
