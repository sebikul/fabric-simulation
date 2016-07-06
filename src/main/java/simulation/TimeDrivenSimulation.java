package simulation;

public abstract class TimeDrivenSimulation implements ISimulation {

    private double currentTime = 0;
    private double lastTimeWritten = 0;

    protected final TimeDrivenSimulationParameters parameters;

    public TimeDrivenSimulation(TimeDrivenSimulationParameters parameters) {
        this.parameters = parameters;
    }

    @Override
    public double step() {
        currentTime = currentTime + parameters.getStepInterval();

        return currentTime;
    }

    public double getCurrentTime() {
        return currentTime;
    }

    public double getInterval() {
        return parameters.getStepInterval();
    }

    protected boolean shouldWrite() {
        if (lastTimeWritten + parameters.getWriterInterval() < currentTime) {
            lastTimeWritten = currentTime;
            return true;
        }

        return false;
    }
}
