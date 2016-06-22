package integrators;

public interface Integrator {

    void next(IIntegratableParticle particle, double dt);

}
