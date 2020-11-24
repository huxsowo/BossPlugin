package BossPlugin.Utils;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.World;

public class ParticleUtil {

    public static void spawnParticle( Particle particle, Location location, World world){
        world.spawnParticle(particle, location,0,0,0,0,0, null, true);
    }
}
