import fabric.FabricSimulation;
import fabric.FabricSimulationParameters;
import integrators.Integrator;
import integrators.VerletIntegrator;
import particle.EnergyWriter;
import particle.ParticleWriter;
import simulation.TimeDrivenSimulation;

import java.io.IOException;
import java.util.Locale;

import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

public class Main {

	private static final double RADIUS = 1.0;
	private static final double MASS = 1.0;

	private static final int WIDTH = 4;
	private static final int HEIGHT = 4;

	private static final double PARTICLE_SEPARATION = 3;
	private static final double SPRING_CONSTANT = 1000;
	private static final double SPRING_NATURAL_DISTANCE = 3;

	private static final double TORSION_SPRING_CONSTANT = SPRING_CONSTANT / 10;
	private static final double TORSION_SPRING_NATURAL_ANGLE = 0;

	private static final boolean FIX_TOP_ROW = true;
	private static final boolean ENABLE_GRAVITY = true;
	private static final boolean ENABLE_TORSION_DAMPING = true;

	private static final double INTERVAL = 0.00001;
	private static final double WRITER_INTERVAL = 0.1; // A 60 FPS es RT

	private static final double TIME_LIMIT = 300;//300

	private static final double VISCOUS_DAMPING_COEFICIENT = Math.pow(10, -1);

	public static void main(String[] args) throws IOException {

		Vector3D baseForce=new Vector3D(0, 0, 100000.0);//fuerza completamente horizontal, luego se le modifica el ángulo
		
		for (double dampingExp = -2; dampingExp <= 2; dampingExp += 0.5) {
		//double dampingExp = 0.5;
			
			for(double forceAngle=0.0;forceAngle<=90.0;forceAngle+=22.5 ){
				double forceAngleRadians=Math.toRadians(forceAngle);
				Vector3D initialForce=new Vector3D(0
													,baseForce.getZ()*Math.sin(-forceAngleRadians)
													,baseForce.getZ()*Math.cos(-forceAngleRadians)) ;
				System.out.println();
				run(dampingExp,initialForce,forceAngle);
			}
		}
	}

	public static void run(double dampingCoef, Vector3D initialForce, double forceAngle) throws IOException {
		Locale.setDefault(new Locale("en", "US"));

		ParticleWriter writer = null;
		EnergyWriter energyWriter = null;
		try {

			String dampingMsg = ENABLE_TORSION_DAMPING 
								? ("exp" + String.format("%.1f", dampingCoef)) 
										: "false";
			String baseFilename = String.format("W=%d_H=%d_STEP=%g_WSTEP=%g_GRAV=%b_DAMPING=%s_ANGLE=%.1f", WIDTH, HEIGHT,
					INTERVAL, WRITER_INTERVAL, ENABLE_GRAVITY, dampingMsg,forceAngle);

			String outputFilename = String.format("simulations/FABRIC_SIM__%s.xyz", baseFilename);
			writer = new ParticleWriter(outputFilename);

			String energyOutputFilename = String.format("simulations/Kinetic__%s.csv", baseFilename);
			energyWriter = new EnergyWriter(energyOutputFilename);

		} catch (IOException e) {
			e.printStackTrace();
		}

		Integrator integrator = new VerletIntegrator(INTERVAL);

		FabricSimulationParameters parameters = new FabricSimulationParameters();

		// Simulation
		parameters.setIntegrator(integrator).setWriter(writer).setWriterInterval(WRITER_INTERVAL)
				.setEnergyWriter(energyWriter);
		parameters.setInitialForce(initialForce);

		// TimeDrivenSimulation
		parameters.setStepInterval(INTERVAL);

		// FabricSimulation
		parameters.setWidth(WIDTH).setHeight(HEIGHT).setRadius(RADIUS).setMass(MASS).setSpringConstant(SPRING_CONSTANT)
				.setSpringNaturalDistance(SPRING_NATURAL_DISTANCE).setTorsionSpringConstant(TORSION_SPRING_CONSTANT)
				.setTorsionSpringNaturalAngle(TORSION_SPRING_NATURAL_ANGLE).setParticleSeparation(PARTICLE_SEPARATION)
				.setFixParticles(FIX_TOP_ROW).setDamping(ENABLE_TORSION_DAMPING).setGravityEnabled(ENABLE_GRAVITY)
				.setViscousDamping(Math.pow(10,dampingCoef));

		TimeDrivenSimulation simulation = new FabricSimulation(parameters);

		simulation.start();

		while (simulation.getCurrentTime() < TIME_LIMIT) {
			double currentTime = simulation.step();
		}

		long elapsedTime = simulation.stop();

		System.out.println("ElapsedTime: " + elapsedTime);
	}
}
