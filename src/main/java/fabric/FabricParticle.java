package fabric;

import integrators.VerletIntegratableParticle;
import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

public class FabricParticle extends VerletIntegratableParticle {


    public FabricParticle(int id, Vector3D position, Vector3D velocity, double radius, double mass) {
        super(id, position, velocity, radius, mass);
    }

    @Override
    public String toString() {
        return "Particle [id=" + id + ", mass=" + mass + ", position=" + position + ", radius=" + radius + ", velocity="
                + velocity + "]";
    }
}
