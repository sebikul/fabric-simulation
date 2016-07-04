package integrators;

import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

public class VerletIntegrator implements Integrator {

    @Override
    public void next(IIntegratableParticle particle, double dt) {
        Vector3D force = particle.getTotalForces();
        Vector3D previousPosition = particle.getPreviousPosition();
        Vector3D acceleration = force.scalarMultiply(1.0 / particle.getMass());

        Vector3D pos = particle.getPosition().scalarMultiply(2).subtract(previousPosition).add(acceleration.scalarMultiply(Math.pow(dt, 2.0)));
        Vector3D vel = pos.subtract(previousPosition).scalarMultiply(1.0 / (2.0 * dt));

        particle.setPosition(pos);
        particle.setVelocity(vel);
    }
}
