package simulation;

public class TimeDrivenSimulationParameters extends SimulationParameters {

    private double stepInterval = 0;

    public double getStepInterval() {
        return stepInterval;
    }

    public TimeDrivenSimulationParameters setStepInterval(double stepInterval) {
        this.stepInterval = stepInterval;
        return this;
    }

    public boolean areParametersSet() {
        return (stepInterval != 0) && super.areParametersSet();
    }
}
