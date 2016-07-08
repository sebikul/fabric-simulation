package integrators;

import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

public class VerletIntegrator implements Integrator {

    private final double stepInterval;

    public VerletIntegrator(final double stepInterval) {
        this.stepInterval = stepInterval;
    }

    @Override
    public void next(IIntegratableParticle particle) {
        Vector3D force = particle.getTotalForces();
        Vector3D previousPosition = particle.getPreviousPosition();

        final double forceTerm = (stepInterval * stepInterval) / particle.getMass();

        final double posX = 2 * particle.getPosition().getX() - previousPosition.getX() + force.getX() * forceTerm;
        final double posY = 2 * particle.getPosition().getY() - previousPosition.getY() + force.getY() * forceTerm;
        final double posZ = 2 * particle.getPosition().getZ() - previousPosition.getZ() + force.getZ() * forceTerm;


//        Vector3D pos = particle.getPosition().
//                scalarMultiply(2).
//                subtract(previousPosition).
//                add(force.scalarMultiply((stepInterval * stepInterval) / particle.getMass()));
        final Vector3D pos = new Vector3D(posX, posY, posZ);

        final double velX = (posX - previousPosition.getX()) / (2.0 * stepInterval);
        final double velY = (posY - previousPosition.getY()) / (2.0 * stepInterval);
        final double velZ = (posZ - previousPosition.getZ()) / (2.0 * stepInterval);

        final Vector3D vel = new Vector3D(velX, velY, velZ);

//        Vector3D vel = pos.subtract(previousPosition).
//                scalarMultiply(1.0 / (2.0 * stepInterval));

        particle.setPosition(pos);
        particle.setVelocity(vel);
    }
}
