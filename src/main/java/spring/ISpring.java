package spring;

public interface ISpring {

    void apply();

    void reset();

    boolean hasBeenApplied();

    void setDamping(boolean damping);
}

