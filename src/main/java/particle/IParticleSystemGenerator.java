package particle;

import fabric.FabricParticle;

import java.util.Set;

public interface IParticleSystemGenerator {
    void generateParticles();

    Set<FabricParticle> getParticleSet();
}
