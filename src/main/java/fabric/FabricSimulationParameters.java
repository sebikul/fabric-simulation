package fabric;

import simulation.TimeDrivenSimulationParameters;

public class FabricSimulationParameters extends TimeDrivenSimulationParameters {

    private double radius = 0;
    private int width = -1;
    private int height = -1;
    private double mass = 0;

    private double springConstant = 0;
    private double springNaturalDistance = 0;

    private double torsionSpringConstant = 0;
    private double torsionSpringNaturalAngle = -1;
    private boolean enableDamping;

    private double particleSeparation = 0;

    private double initialZ = 0;

    private boolean gravityEnabled = false;
    private boolean torsionSpringsEnabled = false;

    private boolean fixParticles;

    private double viscousDamping = -1.0;

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

    public FabricSimulationParameters setTorsionSpringConstant(double torsionSpringConstant) {
        this.torsionSpringConstant = torsionSpringConstant;
        return this;
    }

    public FabricSimulationParameters setTorsionSpringNaturalAngle(double torsionSpringNaturalAngle) {
        this.torsionSpringNaturalAngle = torsionSpringNaturalAngle;
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
                (torsionSpringConstant != 0) &&
                (torsionSpringNaturalAngle != -1) &&
                (viscousDamping != -1) &&
                super.areParametersSet();
    }

    public double getTorsionSpringConstant() {
        return torsionSpringConstant;
    }

    public double getTorsionSpringNaturalAngle() {
        return torsionSpringNaturalAngle;
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


    public FabricSimulationParameters setGravityEnabled(final boolean gravity) {
        this.gravityEnabled = gravity;
        return this;
    }

    public boolean isGravityEnabled() {
        return gravityEnabled;
    }

    public double getViscousDampingCoeficient() {
        return viscousDamping;
    }

    public FabricSimulationParameters setViscousDamping(double coeficient) {
        this.viscousDamping = coeficient;
        return this;
    }

    public FabricSimulationParameters setTorsionSpringsEnabled(final boolean torsion) {
        this.torsionSpringsEnabled = torsion;
        return this;
    }

    public boolean isTorsionSpringsEnabled() {
        return torsionSpringsEnabled;
    }
}
