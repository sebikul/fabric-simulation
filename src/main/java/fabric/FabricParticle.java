package fabric;

import integrators.VerletIntegratableParticle;
import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

public class FabricParticle extends VerletIntegratableParticle {

    private boolean isFixed = false;

    public FabricParticle(int id, Vector3D position, Vector3D velocity, double radius, double mass) {
        super(id, position, velocity, radius, mass);
    }

    @Override
    public String toString() {
        return "Particle [id=" + id + ", position=" + position + ", velocity=" + velocity + "]";
    }

    @Override
    public void setPosition(Vector3D position) {
        if (!isFixed) {
            super.setPosition(position);
        }
    }

    public void setFixed(boolean fixed) {
        isFixed = fixed;
    }
}
