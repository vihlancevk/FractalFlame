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
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public final class FractalFlame {
    private static final Random RANDOM = new SecureRandom();
    private static final int HEATING_VALUE = -20;


    private FractalFlame() {

    }

    @SuppressWarnings("MagicNumber")
    public static void render(FractalFlameConfig config) {
        FractalImage canvas = FractalImage.create(config.width(), config.height());

        List<Affine> affines = config.affines().stream().map(Affine::new).toList();
        List<Transformation> variations = TransformationFactory.createTransformations(config.variations());

        RANDOM.setSeed(config.seed());

        int numberOfThreads = config.numberOfThreads();
        int samplesForThreads = config.samples() / (numberOfThreads * numberOfThreads);
        double squareSide = 2.0 / numberOfThreads;
        try (ExecutorService executorService = Executors.newFixedThreadPool(numberOfThreads)) {
            for (int j = 0; j < numberOfThreads; j++) {
                for (int i = 0; i < numberOfThreads; i++) {
                    double finalX = -1.0 + i * squareSide;
                    double finalY = -1.0 + j * squareSide;
                    executorService.submit(
                        () -> {
                            try {
                                Rect world = new Rect(finalX, finalY, squareSide, squareSide);
                                render(canvas, world, affines, variations, config, samplesForThreads);
                            } catch (Exception e) {
                                Thread.currentThread().interrupt();
                            }
                        }
                    );
                }
            }

            executorService.shutdown();
            try {
                if (!executorService.awaitTermination(10, TimeUnit.MINUTES)) {
                    executorService.shutdownNow();
                }
            } catch (InterruptedException e) {
                executorService.shutdownNow();
                Thread.currentThread().interrupt();
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
        FractalFlameConfig config,
        int samplesForThreads
    ) {
        for (int num = 0; num < samplesForThreads; num++) {
            Point point = world.getRandomPoint(RANDOM);

            for (short step = HEATING_VALUE; step < config.iterPerSample(); step++) {
                Affine affine = getRandomElement(affines);
                Transformation variation = getRandomElement(variations);

                point = affine.apply(point);
                point = variation.apply(point);

                if (step < 0) {
                    continue;
                }

                int symmetry = config.symmetry();
                double angle = 0.0;
                for (int s = 0; s < symmetry; angle += Math.PI * 2.0 / symmetry, s++) {
                    point = config.isRelativeSymmetry()
                        ? rotateRelativeSymmetry(world, point, angle)
                        : rotateAbsoluteSymmetry(point, angle);
                    if (!canvas.contains(point)) {
                        continue;
                    }

                    Pixel pixel = canvas.pixel(point.x(), point.y());
                    if (pixel == null) {
                        continue;
                    }

                    synchronized (pixel) {
                        if (pixel.getHitCount() == 0) {
                            pixel.setR(affine.getRgbR());
                            pixel.setG(affine.getRgbG());
                            pixel.setB(affine.getRgbB());
                        } else {
                            pixel.setR((pixel.getR() + affine.getRgbR()) / 2.0);
                            pixel.setG((pixel.getG() + affine.getRgbG()) / 2.0);
                            pixel.setB((pixel.getB() + affine.getRgbB()) / 2.0);
                        }
                        pixel.setHitCount(pixel.getHitCount() + 1);
                    }
                }
            }
        }
    }

    private static <T> T getRandomElement(List<T> elements) {
        int randomIndex = RANDOM.nextInt(elements.size());
        return elements.get(randomIndex);
    }

    private static Point rotateAbsoluteSymmetry(Point point, double angle) {
        double absoluteCenterX = 0.0;
        double absoluteCenterY = 0.0;

        return rotate(point, angle, absoluteCenterX, absoluteCenterY);
    }

    private static Point rotateRelativeSymmetry(Rect world, Point point, double angle) {
        double relativeCenterX = world.x() + world.width() / 2.0;
        double relativeCenterY = world.y() + world.height() / 2.0;

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
