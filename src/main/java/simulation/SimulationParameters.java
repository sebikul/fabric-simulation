package simulation;

import integrators.Integrator;
import particle.ParticleWriter;

public class SimulationParameters {

    private final Integrator integrator;
    private final ParticleWriter writer;
    private final double writerInterval;

    public SimulationParameters(Integrator integrator, ParticleWriter writer, double writerInterval) {
        this.integrator = integrator;
        this.writer = writer;
        this.writerInterval = writerInterval;
    }

    public Integrator getIntegrator() {
        return integrator;
    }

    public ParticleWriter getWriter() {
        return writer;
    }

    public double getWriterInterval() {
        return writerInterval;
    }
}
