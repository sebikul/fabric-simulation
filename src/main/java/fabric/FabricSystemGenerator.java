package fabric;

import integrators.VerletIntegratableParticle;
import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;
import particle.IParticleSystemGenerator;

import java.util.HashSet;
import java.util.Set;

import spring.ISpring;

public class FabricSystemGenerator implements IParticleSystemGenerator {

    private final double radius;
    private final int width;
    private final int height;
    private final int particleAmount;
    private final double mass;
    private final double springConstant;

    private final double particleSeparation;
    private int particleId = 0;

    private double initialZ = 0;

    private boolean hasGeneratedParticles = false;

    private Set<FabricParticle> particleSet;
    private Set<ISpring> springSet;

    public FabricSystemGenerator(final double radius, final int width, final int height, final double mass, final double particleSeparation, final double springConstant) {
        this.radius = radius;
        this.width = width;
        this.height = height;
        this.mass = mass;

        this.particleSeparation = particleSeparation;

        this.particleAmount = width * height;

        this.springConstant = springConstant;

        this.particleSet = new HashSet<>(particleAmount);
        this.springSet = new HashSet<>();
    }

    @Override
    public void generateParticles() {

        if (hasGeneratedParticles) {
            throw new UnsupportedOperationException("generateParticles() can only be called once.");
        }

        System.out.println("FabricSystemGenerator.generateParticles: Generating random particle set.");

        FabricParticle[][] particleArray = new FabricParticle[width][height];

        final Vector3D initialVelocity = new Vector3D(0, 0, 0);

        // Para cada fila de particulas
        for (int i = 0; i < width; i++) {
            //Para cada columna de particulas
            for (int j = 0; j < height; j++) {

                final double xPosition = i * particleSeparation;
                final double yPosition = j * particleSeparation;
                final double zPosition = initialZ;

                final Vector3D position = new Vector3D(xPosition, yPosition, zPosition);

                particleArray[i][j] = new FabricParticle(getNextParticleId(), position, initialVelocity, radius, mass);
            }
        }

        /*

        + = Particula actual
        0 = Particulas con las que se vincula
        - = Otras particulas

        ---------------------------------
        |                               |
        |   0          0          -     |
        |                               |
        |                               |
        |                               |
        |   0          +          0     |
        |                               |
        |                               |
        |                               |
        |   -          0          0     |
        |                               |
        |-------------------------------
         */

        final int[][] deltaVector = new int[][]{
                {-1, 1},
                {0, 1},
                {-1, 0},
                {1, 0},
                {0, -1},
                {1, -1}
        };

        // Para cada fila de particulas
        for (int i = 0; i < width; i++) {

            //Para cada columna de particulas
            for (int j = 0; j < height; j++) {
                // Aca tenemos que formar los vinculos entre particulas

                FabricParticle currentParticle = particleArray[i][j];

                for (int[] delta : deltaVector) {

                    final int xPos = i + delta[0];
                    final int yPos = j + delta[1];

                    if (xPos < 0 || yPos < 0 || xPos >= width || yPos > height) {
                        continue;
                    }

                    FabricParticle otherParticle = particleArray[xPos][yPos];

                    VerletIntegratableParticle[] particles = new VerletIntegratableParticle[]{currentParticle, otherParticle};

                    ISpring spring = new FabricSpring(particles, springConstant);
                    springSet.add(spring);
                }

                particleSet.add(currentParticle);
            }
        }

        System.out.println("FabricSystemGenerator.generateParticles: Done!");
        hasGeneratedParticles = true;
    }

    @Override
    public Set<FabricParticle> getParticleSet() {

        if (!hasGeneratedParticles) {
            throw new UnsupportedOperationException("Please call generateParticles() first.");
        }

        return particleSet;
    }

    public Set<ISpring> getSpringSet() {

        if (!hasGeneratedParticles) {
            throw new UnsupportedOperationException("Please call generateParticles() first.");
        }

        return springSet;
    }

    private int getNextParticleId() {
        final int nextId = this.particleId;
        this.particleId++;

        return nextId;
    }

    public void setInitialZ(double initialZ) {
        this.initialZ = initialZ;
    }
}
