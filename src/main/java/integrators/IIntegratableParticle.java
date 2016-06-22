package integrators;

import particle.IParticle;
import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

public interface IIntegratableParticle extends IParticle {

    Vector3D getTotalForces();

    Vector3D getPreviousPosition();
}
