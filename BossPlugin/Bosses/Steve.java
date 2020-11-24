package BossPlugin.Bosses;

import BossPlugin.Boss;
import BossPlugin.Utils.ParticleUtil;
import org.bukkit.*;
import org.bukkit.entity.EntityType;

public class Steve extends Boss {

    public Steve(){
        super();
        this.entityType = EntityType.PLAYER;
        this.name = "Steve";
        this.nameColor = ChatColor.RED;
        this.maxHealth = 100;
    }

    public void summon(Location location){
        super.summon(location);
        World world = location.getWorld();
        ParticleUtil.spawnParticle(Particle.EXPLOSION_HUGE, location, world);
        world.playSound(location, Sound.BLOCK_FIRE_EXTINGUISH, 2, 1);
    }
}
