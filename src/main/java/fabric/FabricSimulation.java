package fabric;

import integrators.Integrator;
import spring.ISpring;

import java.io.IOException;
import java.util.Set;

public class FabricSimulation {


    private Set<ISpring> springSet;
    private Set<FabricParticle> particles;

    private final Integrator integrator;
    private final double interval;
    private final ParticleWriter writer;
    private double width;
    private double height;
    private double d;
    private double dStart;
    private double diameter;
    private double time = 0.0;

    private double k_n;
    private double k_t;
    private double drop_depht;

    private long previousParticlesCount;


    public FabricSimulation(Integrator integrator, double interval
            , ParticleWriter writer, double width
            , double height, double d, double dStart, int n
            , double k_n, double k_t
            , double drop_depth) {
        this.width = width;
        this.height = height;
        this.d = d;
        this.dStart = dStart;

        this.integrator = integrator;
        this.interval = interval;
        this.writer = writer;

        this.diameter = d / 10.0;

        this.particles = FabricParticle.generateRandomParticles(n, this.diameter, width, height, 10000L, drop_depth);

        this.k_n = k_n;
        this.k_t = k_t;

        this.drop_depht = drop_depth;

        this.previousParticlesCount = n;
    }

    public long simulate() {

        long flow = 0;


        particles = Collider.collisions(particles, width, height, dStart, d, k_n, k_t, drop_depht, cim);
        //VERSION ORIGINAL
        for (FabricParticle particle : particles) {
            integrator.next(particle, interval);
        }

        //VERSION CON THREADS(para N=100 tarda mas)
        // particles.stream().forEach(e->integrator.next(e, interval));


        time = time + interval;
        return flow;
    }


    public void writeData() {
        try {
            writer.write(time, particles);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


    public double getMeanKineticEnergy() {
        return getTotalKineticEnergy() / particles.size();
    }

    public double getTotalKineticEnergy() {
        double ans = 0.0;

        for (FabricParticle particle : particles) {
            ans += particle.getKineticEnergy();
        }
        return ans;

    }

    public double getMeanPotentialEnergy() {
        return getTotalPotentialEnergy() / particles.size();
    }

    public double getTotalPotentialEnergy() {
        double ans = 0.0;

        for (FabricParticle particle : particles) {

            ans += particle.getPotentialEnergy();
        }
        return ans;

    }

    public void clean() {
        try {
            writer.closeWriter();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }


}
