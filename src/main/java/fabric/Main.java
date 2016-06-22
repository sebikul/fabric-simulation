package fabric;

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

    private static final int WIDTH = 20;
    private static final int HEIGHT = 10;

    private static final double PARTICLE_SEPARATION = 1;
    private static final double SPRING_CONSTANT = Math.pow(10.0, 5.0);

    private static final double INTERVAL = 0.00001;
    private static final double WRITER_INTERVAL = 0.01;

    private static final double TIME_LIMIT = 20;

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

        TimeDrivenSimulation simulation = new FabricSimulation(parameters, INTERVAL, WIDTH, HEIGHT, PARTICLE_SEPARATION, RADIUS, SPRING_CONSTANT, MASS);

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
