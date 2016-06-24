import fabric.FabricSimulation;
import integrators.Integrator;
import integrators.VerletIntegrator;
import particle.ParticleWriter;
import simulation.SimulationParameters;
import simulation.TimeDrivenSimulation;

import java.io.IOException;
import java.util.Locale;

public class Main {

    private static final double RADIUS = 1.0;
    private static final double MASS = 1.0;

    private static final int WIDTH = 10;
    private static final int HEIGHT = 5;

    private static final double PARTICLE_SEPARATION = 6;
    private static final double SPRING_CONSTANT = 1000;
    private static final double SPRING_NATURAL_DISTANCE = 6;


    private static final double INTERVAL = 0.0001;
    private static final double WRITER_INTERVAL = 0.1;

    private static final double TIME_LIMIT = 1000;

    public static void main(String[] args) throws IOException {
        Locale.setDefault(new Locale("en", "US"));


        ParticleWriter writer = null;
        try {
            String outputFilename = String.format("simulations/FABRIC_SIM__W=%d_H=%d_STEP=%g_WSTEP=%g.xyz", WIDTH, HEIGHT, INTERVAL, WRITER_INTERVAL);
            writer = new ParticleWriter(outputFilename);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Integrator integrator = new VerletIntegrator();

        SimulationParameters parameters = new SimulationParameters(integrator, writer, WRITER_INTERVAL);

        TimeDrivenSimulation simulation = new FabricSimulation(parameters, INTERVAL, WIDTH, HEIGHT, PARTICLE_SEPARATION, RADIUS, SPRING_CONSTANT, SPRING_NATURAL_DISTANCE, MASS);

        simulation.start();

//        long last = System.currentTimeMillis();

        while (simulation.getCurrentTime() < TIME_LIMIT) {
            double currentTime = simulation.step();
//
//            System.out.printf("time=%g, prog= %g, remaining= %d seconds, step= %d\n", currentTime, currentTime / TIME_LIMIT, 0, System.currentTimeMillis() - last);
//
//            last = System.currentTimeMillis();
        }

        long elapsedTime = simulation.end();

        System.out.println("ElapsedTime: " + elapsedTime);

    }
}
