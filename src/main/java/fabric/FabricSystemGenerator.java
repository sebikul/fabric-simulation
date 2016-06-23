package fabric;

import integrators.VerletIntegratableParticle;
import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;
import particle.IParticleSystemGenerator;
import spring.ISpring;

import java.util.HashSet;
import java.util.Set;

public class FabricSystemGenerator implements IParticleSystemGenerator {

    private double radius = 0;
    private int width = -1;
    private int height = -1;
    private double mass = 0;

    private double springConstant = 0;
    private double springNaturalDistance = 0;

    private double particleSeparation = 0;

    private int nextParticleId = 0;

    private double initialZ = 0;

    private boolean hasGeneratedParticles = false;

    private Set<FabricParticle> particleSet;
    private Set<ISpring> springSet;

    public FabricSystemGenerator() {
        this.particleSet = new HashSet<>();
        this.springSet = new HashSet<>();
    }

    @Override
    public void generateParticles() {

        if (!areParametersSet()) {
            throw new UnsupportedOperationException("Please set the necessary parameters first.");
        }

        if (hasGeneratedParticles) {
            throw new UnsupportedOperationException("generateParticles() can only be called once.");
        }

        System.out.println("FabricSystemGenerator.generateParticles: Generating random particle set.");

        FabricParticle[][] particleArray = new FabricParticle[height][width];

        final Vector3D initialVelocity = new Vector3D(0, 0, 0);

        // Para cada fila de particulas
        for (int i = 0; i < height; i++) {
            //Para cada columna de particulas
            for (int j = 0; j < width; j++) {

                final double xPosition = j * particleSeparation;
                final double yPosition = i * particleSeparation;
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
        for (int i = 0; i < height; i++) {

            //Para cada columna de particulas
            for (int j = 0; j < width; j++) {
                // Aca tenemos que formar los vinculos entre particulas

                FabricParticle currentParticle = particleArray[i][j];

                for (int[] delta : deltaVector) {

                    final int xPos = j + delta[0];
                    final int yPos = i + delta[1];

                    if (xPos < 0 || yPos < 0 || xPos >= width || yPos >= height) {
                        continue;
                    }

                    FabricParticle otherParticle = particleArray[yPos][xPos];

                    VerletIntegratableParticle[] particles = new VerletIntegratableParticle[]{currentParticle, otherParticle};

                    ISpring spring = new FabricSpring(particles, springConstant, springNaturalDistance);
                    springSet.add(spring);
                }

                particleSet.add(currentParticle);
            }
        }

        particleArray[0][0].addForce(0, 0, 10);

        System.out.println("FabricSystemGenerator.generateParticles: Done!");
        hasGeneratedParticles = true;
    }

    @Override
    public Set<FabricParticle> getParticleSet() {

        if (!areParametersSet()) {
            throw new UnsupportedOperationException("Please set the necessary parameters first.");
        }

        if (!hasGeneratedParticles) {
            throw new UnsupportedOperationException("Please call generateParticles() first.");
        }

        return particleSet;
    }

    public Set<ISpring> getSpringSet() {

        if (!areParametersSet()) {
            throw new UnsupportedOperationException("Please set the necessary parameters first.");
        }

        if (!hasGeneratedParticles) {
            throw new UnsupportedOperationException("Please call generateParticles() first.");
        }

        return springSet;
    }

    private int getNextParticleId() {
        final int nextId = this.nextParticleId;
        this.nextParticleId++;

        return nextId;
    }

    public FabricSystemGenerator setInitialZ(final double initialZ) {
        this.initialZ = initialZ;
        return this;
    }

    public FabricSystemGenerator setRadius(final double radius) {
        this.radius = radius;

        return this;
    }

    public FabricSystemGenerator setWidth(final int width) {
        this.width = width;
        return this;
    }

    public FabricSystemGenerator setHeight(final int height) {
        this.height = height;
        return this;
    }

    public FabricSystemGenerator setMass(final double mass) {
        this.mass = mass;
        return this;
    }

    public FabricSystemGenerator setSpringConstant(final double springConstant) {
        this.springConstant = springConstant;
        return this;
    }

    public FabricSystemGenerator setSpringNaturalDistance(final double springNaturalDistance) {
        this.springNaturalDistance = springNaturalDistance;
        return this;
    }

    public FabricSystemGenerator setParticleSeparation(final double particleSeparation) {
        this.particleSeparation = particleSeparation;

        return this;
    }

    private boolean areParametersSet() {
        return (radius != 0) && (width != -1) && (height != -1) && (mass != 0) && (springConstant != 0) && (particleSeparation != 0) && (springNaturalDistance != 0);
    }
}
