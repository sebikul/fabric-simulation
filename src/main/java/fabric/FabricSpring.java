package fabric;

import integrators.VerletIntegratableParticle;
import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;
import spring.ISpring;

public class FabricSpring implements ISpring {

    private final VerletIntegratableParticle[] particles;
    private boolean hasBeenApplied = false;

    private final double k;
    private final double gamma;
    private final double naturalDistance;

    private double previousDistance;
    private final double stepInterval;

    private boolean damping = false;

    public FabricSpring(VerletIntegratableParticle[] particles, final double k, final double naturalDistance, double stepInterval) {

        if (particles.length != 2) {
            throw new IllegalArgumentException();
        }

        this.particles = particles;

        this.k = k;
        this.naturalDistance = naturalDistance;

        this.stepInterval = stepInterval;

        // 2* sqrt(2/5*K*M*R^2)
        this.gamma = 2.0 * Math.sqrt(k * (2.0 / 5.0) * particles[0].getMass() * particles[0].getRadius() * particles[0].getRadius());


        final Vector3D distanceVector = particles[1].getPosition().subtract(particles[0].getPosition());
        this.previousDistance = distanceVector.getNorm();
    }

    @Override
    public void apply() {
        if (hasBeenApplied()) {
            throw new java.lang.IllegalStateException("A spring can not be applied more than one time without calling reset.");
        }

        final Vector3D distanceVector = particles[1].getPosition().subtract(particles[0].getPosition());

        double particleDistance = distanceVector.getNorm();
        double springForce = -k * (particleDistance - naturalDistance);

        if (damping) {
            springForce -= gamma * (particleDistance - previousDistance) / stepInterval;
        }

        //Probar optimizacion. Multiplicar directamente por (1/norma)*fuerza
//        final Vector3D forceDirection = distanceVector.normalize();
        final Vector3D springForceVector = distanceVector.scalarMultiply(springForce / distanceVector.getNorm());

        if (springForceVector.getNorm() != 0) {
            particles[1].addForce(springForceVector);
            particles[0].addForce(springForceVector.negate());
        }

        previousDistance = particleDistance;

        hasBeenApplied = true;
    }

    @Override
    public void reset() {
        hasBeenApplied = false;
    }

    @Override
    public String toString() {
        return String.format("FabricSpring{%s <--> %s}", particles[0], particles[1]);
    }

    @Override
    public boolean hasBeenApplied() {
        return hasBeenApplied;
    }

    @Override
    public void setDamping(boolean damping) {
        this.damping = damping;
    }
}
