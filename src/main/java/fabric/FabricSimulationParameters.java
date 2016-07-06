package fabric;

import simulation.TimeDrivenSimulationParameters;

public class FabricSimulationParameters extends TimeDrivenSimulationParameters {

    private double radius = 0;
    private int width = -1;
    private int height = -1;
    private double mass = 0;

    private double springConstant = 0;
    private double springNaturalDistance = 0;

    private double flexionSpringConstant = 0;
    private double flexionSpringNaturalAngle = -1;
    private boolean enableDamping;

    private double particleSeparation = 0;

    private double initialZ = 0;

    private boolean fixParticles;

    public double getRadius() {
        return radius;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public double getMass() {
        return mass;
    }

    public double getSpringConstant() {
        return springConstant;
    }

    public double getSpringNaturalDistance() {
        return springNaturalDistance;
    }

    public double getParticleSeparation() {
        return particleSeparation;
    }


    public FabricSimulationParameters setInitialZ(final double initialZ) {
        this.initialZ = initialZ;
        return this;
    }

    public FabricSimulationParameters setRadius(final double radius) {
        this.radius = radius;

        return this;
    }

    public FabricSimulationParameters setWidth(final int width) {
        this.width = width;
        return this;
    }

    public FabricSimulationParameters setHeight(final int height) {
        this.height = height;
        return this;
    }

    public FabricSimulationParameters setMass(final double mass) {
        this.mass = mass;
        return this;
    }

    public FabricSimulationParameters setSpringConstant(final double springConstant) {
        this.springConstant = springConstant;
        return this;
    }

    public FabricSimulationParameters setSpringNaturalDistance(final double springNaturalDistance) {
        this.springNaturalDistance = springNaturalDistance;
        return this;
    }

    public FabricSimulationParameters setParticleSeparation(final double particleSeparation) {
        this.particleSeparation = particleSeparation;
        return this;
    }

    public FabricSimulationParameters setFlexionSpringConstant(double flexionSpringConstant) {
        this.flexionSpringConstant = flexionSpringConstant;
        return this;
    }

    public FabricSimulationParameters setFlexionSpringNaturalAngle(double flexionSpringNaturalAngle) {
        this.flexionSpringNaturalAngle = flexionSpringNaturalAngle;
        return this;
    }

    public boolean areParametersSet() {
        return (radius != 0) &&
                (width != -1) &&
                (height != -1) &&
                (mass != 0) &&
                (springConstant != 0) &&
                (springNaturalDistance != 0) &&
                (particleSeparation != 0) &&
                (flexionSpringConstant != 0) &&
                (flexionSpringNaturalAngle != -1) &&
                super.areParametersSet();
    }

    public double getFlexionSpringConstant() {
        return flexionSpringConstant;
    }

    public double getFlexionSpringNaturalAngle() {
        return flexionSpringNaturalAngle;
    }

    public double getInitialZ() {
        return initialZ;
    }

    public boolean hasToFixParticles() {
        return fixParticles;
    }

    public FabricSimulationParameters setFixParticles(boolean fixParticles) {
        this.fixParticles = fixParticles;
        return this;
    }

    public FabricSimulationParameters setDamping(boolean enableDamping) {
        this.enableDamping = enableDamping;
        return this;
    }

    public boolean isDampingEnabled() {
        return enableDamping;
    }
}
