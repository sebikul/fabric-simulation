package fabric;

import integrators.VerletIntegratableParticle;
import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;
import spring.ISpring;

public class TorsionSpring implements ISpring {

    private final VerletIntegratableParticle[] particles;
    private final VerletIntegratableParticle through;

    private boolean hasBeenApplied = false;

    private final double k;
   // private final double gamma;
    private final double naturalAngle;

    private double previousAngle;
    private final double stepInterval;

    private boolean damping = false;

    public TorsionSpring(VerletIntegratableParticle[] particles, VerletIntegratableParticle through, final double k, final double naturalAngle, double stepInterval) {
        this.through = through;
        this.stepInterval = stepInterval;

        if (particles.length != 2) {
            throw new IllegalArgumentException();
        }

        this.particles = particles;

        this.k = k;

        // 2* sqrt(2/5*K*M*R^2)
        //this.gamma = 2.0 * Math.sqrt(k * (2.0 / 5.0) * through.getMass() * through.getRadius() * through.getRadius());
        
       // this.gamma=2*Math.pow(10, -7);
        this.naturalAngle = naturalAngle;

        final Vector3D distanceVector1 = through.getPosition().subtract(particles[0].getPosition());
        final Vector3D distanceVector2 = particles[1].getPosition().subtract(through.getPosition());

        this.previousAngle = Vector3D.angle(distanceVector1, distanceVector2);
    }

    @Override
    public void apply() {

        if (hasBeenApplied) {
            throw new java.lang.IllegalStateException("A spring can not be applied more than one time without calling reset.");
        }

        final Vector3D distanceVector1 = through.getPosition().subtract(particles[0].getPosition());
        final Vector3D distanceVector2 = particles[1].getPosition().subtract(through.getPosition());

        final double angle = Vector3D.angle(distanceVector1, distanceVector2);

        if (angle == naturalAngle) {
            return;
        }
        
        double springTorque = -k * (angle - naturalAngle);

//        if (damping) {
//            springTorque -= gamma * (angle - previousAngle) / stepInterval;
//        }

        final double distance1 = distanceVector1.getNorm();
        final double distance2 = distanceVector2.getNorm();

        final double springForce1 = springTorque / distance1;
        final double springForce2 = springTorque / distance2;

        final Vector3D orthogonalVector = distanceVector1.crossProduct(distanceVector2);

//        final Vector3D springForceVersor1 = orthogonalVector.crossProduct(distanceVector1).normalize();
//        final Vector3D springForceVector1 = springForceVersor1.scalarMultiply(springForce1);
        final Vector3D springForceVersor1 = orthogonalVector.crossProduct(distanceVector1);
        //final Vector3D springForceVector1 = springForceVersor1.scalarMultiply(springForce1 / springForceVersor1.getNorm());
        final Vector3D springForceVector1 = springForceVersor1.normalize().scalarMultiply(springForce1);
        particles[0].addForce(springForceVector1);

//        final Vector3D springForceVersor2 = orthogonalVector.crossProduct(distanceVector2).normalize();
//        final Vector3D springForceVector2 = springForceVersor2.scalarMultiply(springForce2);
        final Vector3D springForceVersor2 = orthogonalVector.crossProduct(distanceVector2);
       // final Vector3D springForceVector2 = springForceVersor2.scalarMultiply(springForce2 / springForceVersor2.getNorm());
        final Vector3D springForceVector2 = springForceVersor2.normalize().scalarMultiply(springForce2);
        particles[1].addForce(springForceVector2);

        previousAngle = angle;

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

    @Override
    public String toString() {
        return String.format("TorsionSpring{%s <--> %s <--> %s}", particles[0], through, particles[1]);
    }

    @Override
    public void setDamping(boolean damping) {
        this.damping = damping;
    }
    
    
    @Override
	public double getElasticEnergy() {
		return 0.5*k*Math.pow(this.getCompression(), 2);
	}
	
	public double getCompression(){
		 final Vector3D distanceVector1 = through.getPosition().subtract(particles[0].getPosition());
	        final Vector3D distanceVector2 = particles[1].getPosition().subtract(through.getPosition());

	        final double angle = Vector3D.angle(distanceVector1, distanceVector2);


	       return angle-naturalAngle;
	}
	
	public  VerletIntegratableParticle[] getParticles(){
		return particles;
	}
	public VerletIntegratableParticle getThrough(){
		return this.through;
	}
	
}
