package simulation;

import integrators.Integrator;
import particle.ParticleWriter;

public class SimulationParameters {

    private Integrator integrator = null;
    private ParticleWriter writer = null;
    private double writerInterval = 0;

    public SimulationParameters() {
    }

    public final Integrator getIntegrator() {
        return integrator;
    }

    public final ParticleWriter getWriter() {
        return writer;
    }

    public final double getWriterInterval() {
        return writerInterval;
    }

    public SimulationParameters setIntegrator(Integrator integrator) {
        this.integrator = integrator;
        return this;
    }

    public SimulationParameters setWriter(ParticleWriter writer) {
        this.writer = writer;
        return this;
    }

    public SimulationParameters setWriterInterval(double writerInterval) {
        this.writerInterval = writerInterval;
        return this;
    }

    public boolean areParametersSet() {
        return (writerInterval != 0) && (writer != null) && (integrator != null);
    }

}
