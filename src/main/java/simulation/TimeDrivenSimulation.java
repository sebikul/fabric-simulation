package simulation;

public abstract class TimeDrivenSimulation implements ISimulation {

    private final double interval;
    private final double writerInterval;

    private double currentTime = 0;
    private double lastTimeWritten = 0;

    public TimeDrivenSimulation(final double simulationInterval, final double writerInterval) {
        this.interval = simulationInterval;
        this.writerInterval = writerInterval;
    }

    @Override
    public double step() {
        currentTime = currentTime + interval;

        return currentTime;
    }

    public double getCurrentTime() {
        return currentTime;
    }

    public double getInterval() {
        return interval;
    }

    protected boolean shouldWrite() {
        if (lastTimeWritten + writerInterval < currentTime) {
            lastTimeWritten = currentTime;
            return true;
        }

        return false;
    }
}
