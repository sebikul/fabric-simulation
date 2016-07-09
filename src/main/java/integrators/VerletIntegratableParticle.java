package integrators;


import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;
import particle.Abstract3DParticle;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public abstract class VerletIntegratableParticle extends Abstract3DParticle implements IIntegratableParticle {

    private Vector3D previousPosition;
    private List<Vector3D> forces;

    public VerletIntegratableParticle(int id, Vector3D position, Vector3D velocity, double radius, double mass) {
        super(id, position, velocity, radius, mass);

        this.previousPosition = position;

        this.forces =new ArrayList<>();
    }

    @Override
    public Vector3D getTotalForces() {
        double ansX = 0.0;
        double ansY = 0.0;
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

    public void addForce(Vector3D force) {
        forces.add(force);
    }

    @Override
    public void setPosition(Vector3D position) {
        this.previousPosition = this.getPosition();
        super.setPosition(position);
    }

    public final void setInitialPosition(Vector3D position) {
        this.previousPosition = position;
        super.setPosition(position);
    }


}
