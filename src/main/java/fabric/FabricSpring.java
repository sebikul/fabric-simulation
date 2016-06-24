package fabric;

import integrators.VerletIntegratableParticle;
import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;
import spring.ISpring;

public class FabricSpring implements ISpring {

    private final VerletIntegratableParticle[] particles;
    private boolean hasBeenApplied = false;

    private final double k;
    private final double naturalDistance;

    public FabricSpring(VerletIntegratableParticle[] particles, final double k, final double naturalDistance) {

        if (particles.length != 2) {
            throw new IllegalArgumentException();
        }

        this.particles = particles;

        this.k = k;
        this.naturalDistance = naturalDistance;
    }

    //FIXME
    @Override
    public void apply() {
        if (hasBeenApplied) {
            return;
//            throw new java.lang.IllegalStateException("A spring can not be applied more than one time without calling reset.");
        }

        final Vector3D distanceVector = particles[0].getPosition().subtract(particles[1].getPosition());

        double particleDistance = distanceVector.getNorm();
        double springForce = k * (particleDistance - naturalDistance);

        final Vector3D forceDirection = distanceVector.normalize();
        final Vector3D springForceVector = forceDirection.scalarMultiply(springForce);

        if (springForceVector.getNorm() != 0) {
            particles[1].addForce(springForceVector);
            particles[0].addForce(springForceVector.negate());
        }
        hasBeenApplied = true;
    }

    @Override
    public void reset() {
        hasBeenApplied = false;
    }

    @Override
    public boolean hasBeenApplied() {
        return hasBeenApplied;
    }
}
