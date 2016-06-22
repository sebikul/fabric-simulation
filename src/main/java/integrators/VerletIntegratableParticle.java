package integrators;


import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;
import particle.Abstract3DParticle;

import java.util.LinkedList;
import java.util.List;

public abstract class VerletIntegratableParticle extends Abstract3DParticle implements IIntegratableParticle {

    private Vector3D previousPosition;
    private List<Vector3D> forces;

    public VerletIntegratableParticle(int id, Vector3D position, Vector3D velocity, double radius, double mass) {
        super(id, position, velocity, radius, mass);

        this.previousPosition = position;

        this.forces = new LinkedList<>();
    }

    @Override
    public Vector3D getTotalForces() {
        double ansX = 0.0;
        double ansY = -this.getWeight();
        double ansZ = 0.0;

        for (Vector3D force : forces) {
            ansX += force.getX();
            ansY += force.getY();
            ansZ += force.getZ();
        }

        forces.clear();
        return new Vector3D(ansX, ansY, ansZ);
    }

    @Override
    public Vector3D getPreviousPosition() {
        return this.previousPosition;
    }

    public double getKineticEnergy() {
        return 0.5 * mass * velocity.dotProduct(velocity);
    }

    public void addForce(Vector3D f) {
        forces.add(f);
    }

    public void addForce(double xComponent, double yComponent, double zComponent) {
        forces.add(new Vector3D(xComponent, yComponent, zComponent));
    }

    public double getPotentialEnergy() {
        return this.getWeight() * getPosition().getY();
    }

    @Override
    public void setPosition(Vector3D position) {
        this.previousPosition = position;
        super.setPosition(position);
    }

}
