package particle;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Set;

import spring.ISpring;

public class EnergyWriter {
	

    private final BufferedWriter writer;
    private final String fileName;
    
    public EnergyWriter(final String fileName) throws IOException {
		this.fileName=fileName;
		 writer = new BufferedWriter(new FileWriter(fileName));
		  
	}
    
  //graba tiempo, enegía cinética media,energía potencial gravitatoria,energía potencial elastica, energía total
    public void writeEnergies(final double time,final Set<? extends IParticle> particles, Set<ISpring> springSet){
    	
    	double totalKineticEnergy=0.0;
    	double totalPot=0.0;
    	double totalElastic=0.0;
    	double totalEnergy=0.0;
    	double particleCount=particles.size();
    	
    	for(IParticle particle: particles){
    		totalKineticEnergy+=particle.getKineticEnergy();
    		totalPot+=particle.getPotentialEnergy();
    		
    	}
    	
    	for(ISpring spring:springSet){
    		
    		totalElastic+=spring.getElasticEnergy();
    	}
    	
    	totalEnergy=totalKineticEnergy+totalElastic+totalPot;
    	
    	double meanPot=totalPot/particleCount;
    	double meanElastic=totalElastic/particleCount;
    	double meanKinetic=totalKineticEnergy/particleCount;
    	double meanTotal=totalEnergy/particleCount;
    	
    	String line=String.format("%f, %f, %f, %f, %f\n", time,meanKinetic,meanPot,meanElastic,meanTotal);
    	try {
			writer.write(line);
		} catch (IOException e) {
			e.printStackTrace();
		}
    	
    }
    
    public void close() {
        try {
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        
    }

}
