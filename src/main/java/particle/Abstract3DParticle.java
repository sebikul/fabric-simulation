package particle;

import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

public class Abstract3DParticle implements IParticle {

    protected final int id;
    protected final double mass;
    protected Vector3D position;
    protected double radius;
    protected Vector3D velocity;

    public Abstract3DParticle(int id, Vector3D position, Vector3D velocity, double radius, double mass) {
        this.id = id;
        this.position = position;
        this.radius = radius;
        this.velocity = velocity;
        this.mass = mass;
    }

    @Override
    public Vector3D getVelocity() {
        return velocity;
    }

    @Override
    public void setVelocity(Vector3D velocity) {
        this.velocity = velocity;
    }

    @Override
    public double getRadius() {
        return radius;
    }

    @Override
    public double getMass() {
        return mass;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public Vector3D getPosition() {
        return position;
    }

    @Override
    public void setPosition(Vector3D position) {
        this.position = position;
    }
}
