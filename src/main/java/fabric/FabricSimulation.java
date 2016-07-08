package fabric;

import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;
import simulation.TimeDrivenSimulation;
import spring.ISpring;

import java.util.Set;

public class FabricSimulation extends TimeDrivenSimulation {

    private Set<ISpring> springSet;
    private Set<FabricParticle> particleSet;

    private final FabricSimulationParameters parameters;
    private final FabricSystemGenerator fabricSystemGenerator;

    private static final double G = 9.81;

    private long startTime;

    public FabricSimulation(final FabricSimulationParameters parameters) {
        super(parameters);


        //Simple optimizacion para no tener que castear este objeto.
        this.parameters = parameters;

        fabricSystemGenerator = new FabricSystemGenerator(parameters);
    }

    @Override
    public void start() {

        fabricSystemGenerator.generateParticles();
        //fabricSystemGenerator.generateParticlesSinusoidal(4.0);
        this.particleSet = fabricSystemGenerator.getParticleSet();
        this.springSet = fabricSystemGenerator.getSpringSet();

        startTime = System.currentTimeMillis();

        System.out.println("Writing simulation data to: " + parameters.getWriter().getWriterPath());

        parameters.getWriter().write(getCurrentTime(), particleSet);

        System.out.println("Total Particles = " + particleSet.size());
        System.out.println("Total Springs = " + springSet.size());
    }

    @Override
    public double step() {

        // Aplicamos los resortes
        springSet.parallelStream()
                .forEach(ISpring::apply);

        //En caso de estar habilitado, agregamos el peso a cada particula
        if (parameters.isGravityEnabled()) {
            final Vector3D weight = new Vector3D(0, -parameters.getMass() * G, 0);
            particleSet.forEach(particle -> particle.addForce(weight));
        }

        //Integramos el movimiento en base a las fuerzas
        particleSet.parallelStream()
                .filter(particle -> !particle.isFixed())
                .forEach(particle -> parameters.getIntegrator().next(particle));

        // Avanzamos el tiempo de la simulacion
        super.step();

        //Si se debe escribir, se guarda una instantanea de las particulas
        if (shouldWrite()) {
            parameters.getWriter().write(getCurrentTime(), particleSet);
        }

        //Reseteamos los resortes p
        springSet.forEach(ISpring::reset);

        return getCurrentTime();
    }

    @Override
    public long stop() {
        parameters.getWriter().close();

        return System.currentTimeMillis() - startTime;
    }


}
