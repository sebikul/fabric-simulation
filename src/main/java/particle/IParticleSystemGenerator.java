package particle;

import fabric.FabricParticle;

import java.util.Set;

public interface IParticleSystemGenerator {
    void generateParticles();
    void generateParticlesSinusoidal(double sin_divide_factor);
    Set<FabricParticle> getParticleSet();
    
}
