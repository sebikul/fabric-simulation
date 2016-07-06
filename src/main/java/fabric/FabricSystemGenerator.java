package fabric;

import integrators.VerletIntegratableParticle;
import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;
import particle.IParticleSystemGenerator;
import spring.ISpring;

import java.util.HashSet;
import java.util.Set;

public class FabricSystemGenerator implements IParticleSystemGenerator {

    private int nextParticleId = 0;

    private boolean hasGeneratedParticles = false;

    private Set<FabricParticle> particleSet;
    private Set<ISpring> springSet;

    private FabricParticle[][] particleArray;

    private final FabricSimulationParameters parameters;

    public FabricSystemGenerator(final FabricSimulationParameters parameters) {

        if (!parameters.areParametersSet()) {
            throw new UnsupportedOperationException("Please set the necessary parameters first.");
        }

        this.parameters = parameters;

        this.particleSet = new HashSet<>();
        this.springSet = new HashSet<>();
    }

    @Override
    public void generateParticles() {

        if (hasGeneratedParticles) {
            throw new UnsupportedOperationException("generateParticles() can only be called once.");
        }

        System.out.println("FabricSystemGenerator.generateParticles: Generating random particle set.");

        this.particleArray = new FabricParticle[parameters.getHeight()][parameters.getWidth()];

        final Vector3D initialVelocity = new Vector3D(0, 0, 0);

        final double radius = parameters.getRadius();
        final double mass = parameters.getMass();
        final double particleSeparation = parameters.getParticleSeparation();
        final double initialZ = parameters.getInitialZ();

        // Para cada fila de particulas
        for (int i = 0; i < parameters.getHeight(); i++) {
            //Para cada columna de particulas
            for (int j = 0; j < parameters.getWidth(); j++) {

                final double xPosition = j * particleSeparation;
                final double yPosition = i * particleSeparation;
                final double zPosition = initialZ;

                final Vector3D position = new Vector3D(xPosition, yPosition, zPosition);

                particleArray[i][j] = new FabricParticle(getNextParticleId(), position, initialVelocity, radius, mass);

                particleSet.add(particleArray[i][j]);
            }
        }

        addFabricSprings();
        addFlexionSprings();

        if (parameters.hasToFixParticles()) {
            setFixedParticles();
        }


        System.out.println("FabricSystemGenerator.generateParticles: Done!");
        hasGeneratedParticles = true;
    }

    private void addFabricSprings() {

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
        |   0          +          -     |
        |                               |
        |                               |
        |                               |
        |   0          -          -     |
        |                               |
        |-------------------------------|
         */

        final int[][] deltaVector = new int[][]{
                {-1, 1},
                {0, 1},
                {-1, 0},
                {-1, -1}
//                {1, 0},
//                {0, -1},
//                {1, -1}
        };

        final double height = parameters.getHeight();
        final double width = parameters.getWidth();
        final double springNaturalDistance = parameters.getSpringNaturalDistance();
        final double particleSeparation = parameters.getParticleSeparation();
        final double springConstant = parameters.getSpringConstant();

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

                    double localSpringNaturalDistance = springNaturalDistance;

                    if (xPos != j && yPos != i) {
                        //Resorte en diagonal

                        localSpringNaturalDistance = Math.sqrt(2 * particleSeparation * particleSeparation);
                    }

                    ISpring spring = new FabricSpring(particles, springConstant, localSpringNaturalDistance);
                    springSet.add(spring);
                }
            }
        }


        particleArray[0][0].addForce(new Vector3D(0, 0, 10000000.0));
        //particleArray[0][0].setInitialPosition(new Vector3D(0, 0, 6.0));

    }

    private void addFlexionSprings() {

        /*

        + = Particula actual
        0 = Particulas con las que se vincula
        - = Otras particulas

        |-------------------------------------------------------|
        |                                                       |
        |   -          -          0          -          -       |
        |                                                       |
        |                                                       |
        |                                                       |
        |   -          -          -          -          -       |
        |                                                       |
        |                                                       |
        |                                                       |
        |   ?          -          +          -          0       |
        |                                                       |
        |                                                       |
        |                                                       |
        |   -          -          -          -          -       |
        |                                                       |
        |                                                       |
        |                                                       |
        |   -          -          ?          -          -       |
        |                                                       |
        |-------------------------------------------------------|
         */

        final int[][] deltaVector = new int[][]{
                //{-2, 0},
                {2, 0},
                //{0, -2},
                {0, 2}
        };

        final double height = parameters.getHeight();
        final double width = parameters.getWidth();

        final double flexionSpringConstant = parameters.getFlexionSpringConstant();
        final double flexionSpringNaturalAngle = parameters.getFlexionSpringNaturalAngle();

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

                    VerletIntegratableParticle through = null;

                    int throughYPos;
                    int throughXPos;

                    if (delta[0] == 0) {
                        // delta_x=0

                        throughYPos = i + delta[1] / 2;
                        throughXPos = xPos;
                    } else if (delta[1] == 0) {
                        // delta_y=0

                        throughXPos = j + delta[0] / 2;
                        throughYPos = yPos;
                    } else {
                        throw new IllegalArgumentException("Invalid delta vector.");
                    }

                    through = particleArray[throughYPos][throughXPos];

                    ISpring spring = new FlexionSpring(particles, through, flexionSpringConstant, flexionSpringNaturalAngle, parameters.getStepInterval());

                    if (parameters.isDampingEnabled()) {
                        ((FlexionSpring) spring).setDamping(parameters.isDampingEnabled());
                    }
                    springSet.add(spring);
                }
            }
        }

    }

    private void setFixedParticles() {

        final int topRow = parameters.getHeight() - 1;

        final double width = parameters.getWidth();

        for (int i = 0; i < width; i++) {
            particleArray[topRow][i].setFixed(true);
        }
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
        final int nextId = this.nextParticleId;
        this.nextParticleId++;

        return nextId;
    }


    //se agregar partículas en forma sinusoidal en el eje z.

    public void generateParticlesSinusoidal(double sin_divide_factor) {

        if (hasGeneratedParticles) {
            throw new UnsupportedOperationException("generateParticles() can only be called once.");
        }

        System.out.println("FabricSystemGenerator.generateParticlesSinusoidal: Generating random particle set.");

        final double radius = parameters.getRadius();
        final double mass = parameters.getMass();
        final double particleSeparation = parameters.getParticleSeparation();
        final double initialZ = parameters.getInitialZ();

        this.particleArray = new FabricParticle[parameters.getHeight()][parameters.getWidth()];

        final Vector3D initialVelocity = new Vector3D(0, 0, 0);

        int verticalLines = parameters.getWidth();
        double totalWidth = (verticalLines - 1.0) * particleSeparation;
        double z_values[] = new double[verticalLines];


        for (int x = 0; x < verticalLines; x++) {
            z_values[x] = Math.sin(x * particleSeparation / sin_divide_factor) * 4;
        }

        // Para cada fila de particulas
        for (int i = 0; i < parameters.getHeight(); i++) {
            //Para cada columna de particulas
            for (int j = 0; j < parameters.getWidth(); j++) {

                final double xPosition = j * particleSeparation;
                final double yPosition = i * particleSeparation;
                final double zPosition = z_values[j];

                final Vector3D position = new Vector3D(xPosition, yPosition, zPosition);

                particleArray[i][j] = new FabricParticle(getNextParticleId(), position, initialVelocity, radius, mass);

                particleSet.add(particleArray[i][j]);
            }
        }

        addFabricSprings();
        addFlexionSprings();

        setFixedParticles();

        System.out.println("FabricSystemGenerator.generateParticlesSinusoidal: Done!");
        hasGeneratedParticles = true;
    }

}
