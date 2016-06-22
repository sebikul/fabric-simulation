package particle;

import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

public interface IParticle {

    int getId();

    double getMass();

    double getWeight();

    double getRadius();

    Vector3D getPosition();

    Vector3D getVelocity();

    void setPosition(final Vector3D position);

    void setVelocity(final Vector3D velocity);
}
