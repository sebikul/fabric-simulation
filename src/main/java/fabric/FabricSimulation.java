package fabric;

import simulation.TimeDrivenSimulation;
import spring.ISpring;

import java.util.Set;

public class FabricSimulation extends TimeDrivenSimulation {


    private Set<ISpring> springSet;
    private Set<FabricParticle> particleSet;

    private final FabricSimulationParameters parameters;
    private final FabricSystemGenerator fabricSystemGenerator;

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
    }

    @Override
    public double step() {

        //springSet.forEach(ISpring::apply);
        springSet.parallelStream()
                .forEach(ISpring::apply);

        this.particleSet.parallelStream()
                .filter(particle -> !particle.isFixed())
                .forEach(particle -> parameters.getIntegrator().next(particle));

        super.step();

        if (this.shouldWrite()) {
            parameters.getWriter().write(getCurrentTime(), particleSet);
        }

        springSet.forEach(ISpring::reset);

        return getCurrentTime();
    }

    @Override
    public long end() {
        parameters.getWriter().close();

        return System.currentTimeMillis() - startTime;
    }


}
