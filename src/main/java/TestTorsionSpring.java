import static org.junit.Assert.*;

import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;
import org.junit.Test;

import fabric.FabricParticle;
import fabric.TorsionSpring;
import integrators.VerletIntegratableParticle;

public class TestTorsionSpring {
	
	double radius = 1;
	double mass = 1;
	double k=1000;
	double naturalAngle=0.0;
	double stepInterval=0.00001;
	private TorsionSpring init(double [] position1, double [] position2 ,double [] middlePosition){
		
		
		double radius = 1;
		double mass = 1;
		double k=100;
		double naturalAngle=0.0;
		double stepInterval=0.00001;
		Vector3D velocity=new Vector3D(new double[]{0.0,0.0,0.0});
		VerletIntegratableParticle p1 = new FabricParticle(1
				, new Vector3D(position1)
				, velocity
				, radius
				, mass);
		
		VerletIntegratableParticle p2 = new FabricParticle(1
				, new Vector3D(position2)
				, velocity
				, radius
				, mass);
		VerletIntegratableParticle middle= new FabricParticle(1
				, new Vector3D(middlePosition)
				, velocity
				, radius
				, mass);
		
		VerletIntegratableParticle[] array={p1,p2};
		TorsionSpring spring=new TorsionSpring(array, middle, k, naturalAngle, stepInterval);	
		return spring;
	}

	@Test
	public void test1() {
		
		TorsionSpring spring=init(new double[]{0.0,1.0,0.0}
		, new double[]{3.0,1.0,0.0}
				, new double[]{1.0,0.0,0.0});
		spring.apply();
		VerletIntegratableParticle p1=spring.getParticles()[0];
		
		VerletIntegratableParticle p2=spring.getParticles()[1];
		
		Vector3D p1Force=p1.getTotalForces();
		Vector3D p2Force=p2.getTotalForces();
		
		
		assertTrue(p1Force.getX()<0.0);
		assertTrue(p1Force.getY()<0.0);
		assertTrue(p1Force.getZ()<stepInterval && p1Force.getZ()>(-stepInterval));
		
		
		assertTrue(p2Force.getX()>0.0);
		assertTrue(p2Force.getY()<0.0);
		assertTrue(p2Force.getZ()<stepInterval && p2Force.getZ()>(-stepInterval));
		
	}
	
	@Test
	public void test2() {
		
		TorsionSpring spring=init(new double[]{0.0,0.0,0.0}
		, new double[]{3.0,0.0,0.0}
				, new double[]{1.0,1.0,0.0});
		spring.apply();
		VerletIntegratableParticle p1=spring.getParticles()[0];
		
		VerletIntegratableParticle p2=spring.getParticles()[1];
		
		Vector3D p1Force=p1.getTotalForces();
		Vector3D p2Force=p2.getTotalForces();
		
		
		assertTrue(p1Force.getX()<0.0);
		assertTrue(p1Force.getY()>0.0);
		assertTrue(p1Force.getZ()<stepInterval && p1Force.getZ()>(-stepInterval));
		
		
		assertTrue(p2Force.getX()>0.0);
		assertTrue(p2Force.getY()>0.0);
		assertTrue(p2Force.getZ()<stepInterval && p2Force.getZ()>(-stepInterval));
		
	}
	
	@Test
	public void test3() {
		
		TorsionSpring spring=init(new double[]{0.0,1.0,0.0}
		, new double[]{3.0,1.0,0.0}
				, new double[]{1.0,1.0,0.0});
		spring.apply();
		VerletIntegratableParticle p1=spring.getParticles()[0];
		
		VerletIntegratableParticle p2=spring.getParticles()[1];
		
		Vector3D p1Force=p1.getTotalForces();
		Vector3D p2Force=p2.getTotalForces();
		
		
		assertTrue(p1Force.getX()<stepInterval && p1Force.getX()>(-stepInterval));
		assertTrue(p1Force.getY()<stepInterval && p1Force.getY()>(-stepInterval));
		assertTrue(p1Force.getZ()<stepInterval && p1Force.getZ()>(-stepInterval));
		
		
		assertTrue(p2Force.getX()<stepInterval && p2Force.getX()>(-stepInterval));
		assertTrue(p2Force.getY()<stepInterval && p2Force.getY()>(-stepInterval));
		assertTrue(p2Force.getZ()<stepInterval && p2Force.getZ()>(-stepInterval));
		
	}
	

}
