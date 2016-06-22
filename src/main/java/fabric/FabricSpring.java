package fabric;

import integrators.VerletIntegratableParticle;
import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;
import spring.ISpring;

public class FabricSpring implements ISpring {

    private final VerletIntegratableParticle[] particles;
    private boolean hasBeenApplied = false;

    private final double k;

    public FabricSpring(VerletIntegratableParticle[] particles, final double k) {

        if (particles.length != 2) {
            throw new IllegalArgumentException();
        }

        this.particles = particles;

        this.k = k;
    }

    //FIXME
    @Override
    public void apply() {
        if (hasBeenApplied) {
            return;
//            throw new java.lang.IllegalStateException("A spring can not be applied more than one time without calling reset.");
        }

        double centerDistance = particles[0].getPosition().distance(particles[1].getPosition());

        Vector3D positionDifference = particles[1].getPosition().subtract(particles[0].getPosition());
        double enx = positionDifference.getX() / centerDistance;
        double eny = positionDifference.getY() / centerDistance;
        double enz = positionDifference.getZ() / centerDistance; //FIXME

        double borderDistance = particles[0].getRadius() + particles[1].getRadius() - centerDistance;

        double f_n = k * borderDistance;

        Vector3D rRel = particles[1].getVelocity().subtract(particles[0].getVelocity());

        //FIXME
        Vector3D tangencialVersor = new Vector3D(-eny, enx);

        double f_t = -k * borderDistance * (rRel.dotProduct(tangencialVersor));

        //FIXME
        double f_x = f_n * enx + f_t * (-eny);
        double f_y = f_n * eny + f_t * enx;
        double f_z = f_n * eny + f_t * enx;

        //FIXME
        particles[1].addForce(f_x, f_y, f_z);
        particles[0].addForce(-f_x, -f_y, f_z);

        hasBeenApplied = true;
    }

    @Override
    public void reset() {
        hasBeenApplied = false;
    }

    @Override
    public boolean hasBeenApplied() {
        return hasBeenApplied;
    }
}
