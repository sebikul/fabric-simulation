package fabric;

import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;
import simulation.TimeDrivenSimulation;
import spring.ISpring;

import java.util.Set;

public class FabricSimulation extends TimeDrivenSimulation {

	private Set<ISpring> springSet;
	private Set<FabricParticle> particleSet;

	private final FabricSimulationParameters parameters;
	private final FabricSystemGenerator fabricSystemGenerator;

	private static final double G = 9.81;

	private long startTime;

	public FabricSimulation(final FabricSimulationParameters parameters) {
		super(parameters);

		// Simple optimizacion para no tener que castear este objeto.
		this.parameters = parameters;

		fabricSystemGenerator = new FabricSystemGenerator(parameters);
	}

	@Override
	public void start() {

		fabricSystemGenerator.generateParticles();
		// fabricSystemGenerator.generateParticlesSinusoidal(4.0);
		this.particleSet = fabricSystemGenerator.getParticleSet();
		this.springSet = fabricSystemGenerator.getSpringSet();

		startTime = System.currentTimeMillis();

		System.out.println("Writing simulation data to: " + parameters.getWriter().getWriterPath());

		parameters.getWriter().write(getCurrentTime(), particleSet);
		parameters.getEnergyWriter().writeEnergies(getCurrentTime(), particleSet, springSet);
		System.out.println("Total Particles = " + particleSet.size());
		System.out.println("Total Springs = " + springSet.size());
	}

	@Override
    public double step() {

        // Aplicamos los resortes
        springSet.stream()
                .forEach(ISpring::apply);

        //En caso de estar habilitado, agregamos el peso a cada particula
        if (parameters.isGravityEnabled()) {
            final Vector3D weight = new Vector3D(0, -parameters.getMass() * G, 0);
            particleSet.forEach(particle -> particle.addForce(weight));
        }
        
        //Se agrega amortiguación viscosa (solo si esta habilitada).
        if(parameters.isDampingEnabled()){
        	//double dampingCoeficient=Math.pow(10, 1)*2;
        	double dampingCoeficient=parameters.getViscousDampingCoeficient();
        	double eps=Math.pow(10, -5);
        	
        	for(FabricParticle particle: particleSet){
        		
        		Vector3D velocity=particle.getVelocity();
        		double vx=velocity.getX();
        		double vy=velocity.getY();
        		double vz=velocity.getZ();
        		
        		if(vx<eps && vx>-eps
        			&& vy<eps && vy>-eps
        			&& vz<eps && vz>-eps){
        				continue;
        		
        		}
        		
        		
        		Vector3D dampingDirection=particle.getVelocity();
        		
        		Vector3D dampingForce=dampingDirection.scalarMultiply(-dampingCoeficient);
        		particle.addForce(dampingForce);
        		
        		
        	}
        	
        }
        

        //Integramos el movimiento en base a las fuerzas
        particleSet.stream()
                .filter(particle -> !particle.isFixed())
                .forEach(particle -> parameters.getIntegrator().next(particle));

        // Avanzamos el tiempo de la simulacion
        super.step();

        //Si se debe escribir, se guarda una instantanea de las particulas
        if (shouldWrite()) {
            parameters.getWriter().write(getCurrentTime(), particleSet);
            parameters.getEnergyWriter().writeEnergies(getCurrentTime(), particleSet,springSet);
        }

        //Reseteamos los resortes p
        springSet.forEach(ISpring::reset);

        return getCurrentTime();
    }

	@Override
	public long stop() {
		parameters.getWriter().close();
		parameters.getEnergyWriter().close();
		return System.currentTimeMillis() - startTime;
	}

}
