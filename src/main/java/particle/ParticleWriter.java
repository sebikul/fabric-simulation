package particle;

import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Set;

public class ParticleWriter {

    private final BufferedWriter writer;

    public ParticleWriter(String fileName) throws IOException {
        writer = new BufferedWriter(new FileWriter(fileName));
    }

    public void write(final double time, final Set<? extends IParticle> particles) {

        try {
            writer.write(String.format("%d\nTime = %g\n", particles.size(), time));

            for (IParticle particle : particles) {
                writer.write(formatParticle(particle));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String formatParticle(final IParticle p) {

        Vector3D pos = p.getPosition();
        Vector3D vel = p.getVelocity();
        // id posX posY mass radius
        return String.format("%s %f %f %f %f %f %f\n", p.getId(), pos.getX(), pos.getY(), pos.getZ(), vel.getX(), vel.getY(), vel.getZ());
    }

    public void close() {
        try {
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
