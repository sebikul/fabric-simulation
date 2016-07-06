import fabric.FabricSimulation;
import fabric.FabricSimulationParameters;
import integrators.Integrator;
import integrators.VerletIntegrator;
import particle.ParticleWriter;
import simulation.TimeDrivenSimulation;

import java.io.IOException;
import java.util.Locale;

public class Main {

    private static final double RADIUS = 1.0;
    private static final double MASS = 1.0;

    private static final int WIDTH = 20;
    private static final int HEIGHT = 20;

    private static final double PARTICLE_SEPARATION = 3;
    private static final double SPRING_CONSTANT = 1000;
    private static final double SPRING_NATURAL_DISTANCE = 3;

    private static final double FLEXION_SPRING_CONSTANT = 10;
    private static final double FLEXION_SPRING_NATURAL_ANGLE = 0;

    private static final boolean FIX_TOP_ROW = true;

    private static final double INTERVAL = 0.00001;
    private static final double WRITER_INTERVAL = 0.01;

    private static final double TIME_LIMIT = 10;

    public static void main(String[] args) throws IOException {
        Locale.setDefault(new Locale("en", "US"));

        ParticleWriter writer = null;
        try {
            String outputFilename = String.format("simulations/FABRIC_SIM__W=%d_H=%d_STEP=%g_WSTEP=%g.xyz", WIDTH, HEIGHT, INTERVAL, WRITER_INTERVAL);
            writer = new ParticleWriter(outputFilename);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Integrator integrator = new VerletIntegrator(INTERVAL);

        FabricSimulationParameters parameters = new FabricSimulationParameters();

        //Simulation
        parameters.setIntegrator(integrator)
                .setWriter(writer)
                .setWriterInterval(WRITER_INTERVAL);

        //TimeDrivenSimulation
        parameters.setStepInterval(INTERVAL);

        //FabricSimulation
        parameters.setWidth(WIDTH)
                .setHeight(HEIGHT)
                .setRadius(RADIUS)
                .setMass(MASS)
                .setSpringConstant(SPRING_CONSTANT)
                .setSpringNaturalDistance(SPRING_NATURAL_DISTANCE)
                .setFlexionSpringConstant(FLEXION_SPRING_CONSTANT)
                .setFlexionSpringNaturalAngle(FLEXION_SPRING_NATURAL_ANGLE)
                .setParticleSeparation(PARTICLE_SEPARATION)
                .setFixParticles(FIX_TOP_ROW)
                .setDamping(true);


        TimeDrivenSimulation simulation = new FabricSimulation(parameters);

        simulation.start();

//        long last = System.currentTimeMillis();

        while (simulation.getCurrentTime() < TIME_LIMIT) {
            double currentTime = simulation.step();

//           long aux=(long) currentTime;
//           if(( aux % 10)==0 && aux>=1)
//                  System.out.printf("time=%g \n",currentTime );
//
//            last = System.currentTimeMillis();
        }

        long elapsedTime = simulation.end();

        System.out.println("ElapsedTime: " + elapsedTime);

    }
}
