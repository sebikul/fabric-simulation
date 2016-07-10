package spring;

public interface ISpring {

    void apply();

    void reset();

    boolean hasBeenApplied();

	double getElasticEnergy();
    void setDamping(boolean damping);
}

