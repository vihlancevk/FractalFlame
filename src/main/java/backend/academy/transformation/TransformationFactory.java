package backend.academy.transformation;

import backend.academy.transformation.implementation.Horseshoe;
import backend.academy.transformation.implementation.Linear;
import backend.academy.transformation.implementation.Sinusoidal;
import backend.academy.transformation.implementation.Spherical;
import backend.academy.transformation.implementation.Swirl;
import java.util.List;

public final class TransformationFactory {
    private TransformationFactory() {

    }

    public static List<Transformation> createTransformations(List<String> names) {
        return names.stream().map(TransformationFactory::createTransformation).toList();
    }

    public static Transformation createTransformation(String name) {
        return switch (name) {
            case "Linear" -> new Linear();
            case "Sinusoidal" -> new Sinusoidal();
            case "Spherical" -> new Spherical();
            case "Swirl" -> new Swirl();
            case "Horseshoe" -> new Horseshoe();
            default -> throw new IllegalArgumentException("Unknown transformation: " + name);
        };
    }
}
