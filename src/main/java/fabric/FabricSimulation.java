package fabric;

import integrators.IIntegratableParticle;
import integrators.Integrator;
import simulation.SimulationParameters;
import simulation.TimeDrivenSimulation;
import spring.ISpring;

import java.util.Set;

public class FabricSimulation extends TimeDrivenSimulation {


    private Set<ISpring> springSet;
    private Set<FabricParticle> particleSet;

    private final SimulationParameters parameters;
    private final FabricSystemGenerator fabricSystemGenerator;

    private long startTime;

    public FabricSimulation(final SimulationParameters parameters, final double interval, final int width, final int height, final double particleSeparation, final double particleRadius, final double springConstant, final double mass) {
        super(interval, parameters.getWriterInterval());

        this.parameters = parameters;

        fabricSystemGenerator = new FabricSystemGenerator();

        fabricSystemGenerator.setWidth(width).setHeight(height);
        fabricSystemGenerator.setRadius(particleRadius).setMass(mass);
        fabricSystemGenerator.setSpringConstant(springConstant);
        fabricSystemGenerator.setParticleSeparation(particleSeparation);
    }

    @Override
    public void start() {

        fabricSystemGenerator.generateParticles();

        this.particleSet = fabricSystemGenerator.getParticleSet();
        this.springSet = fabricSystemGenerator.getSpringSet();

        startTime = System.currentTimeMillis();

        parameters.getWriter().write(getCurrentTime(), particleSet);
    }

    @Override
    public double step() {

        final Integrator integrator = parameters.getIntegrator();

        springSet.forEach(ISpring::apply);

        for (IIntegratableParticle particle : this.particleSet) {
            integrator.next(particle, this.getInterval());
        }

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
