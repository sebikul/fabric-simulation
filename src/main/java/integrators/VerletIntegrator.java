package integrators;

import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

public class VerletIntegrator implements Integrator {

    public void next(IIntegratableParticle particle, double dt) {
        Vector3D force = particle.getTotalForces();
        Vector3D previous_position = particle.getPreviousPosition();
        Vector3D acceleration = force.scalarMultiply(1.0/particle.getMass());

        Vector3D pos = particle.getPosition().scalarMultiply(2).subtract(previous_position).add(acceleration.scalarMultiply(Math.pow(dt, 2)));
        Vector3D vel = pos.subtract(previous_position).scalarMultiply(1.0/(2*dt));
        
        particle.setPosition(pos);
        particle.setVelocity(vel);
	}

}
