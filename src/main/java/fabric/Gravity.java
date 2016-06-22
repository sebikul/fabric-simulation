package fabric;
import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

public class Gravity {

    public static final double G = 9.81;

    public static Vector2D gravitationalForce(FabricParticle p1) {
        double modulus = p1.getMass() * G;

        Vector2D direction = new Vector2D(0.0, -1.0);

        return direction.scalarMultiply(modulus);
    }
}
