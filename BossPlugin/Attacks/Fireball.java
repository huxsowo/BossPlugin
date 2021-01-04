package BossPlugin.Attacks;

import BossPlugin.Attack;
import BossPlugin.Boss;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.List;

public class Fireball extends Attack {

    public Fireball(){
        super();
        this.cooldown = 25;
    }

    private double range = 50;

    public void use(Boss boss){
        List<Entity> entities = boss.getEntity().getNearbyEntities(range,range,range);
        for (Entity entity : entities){
            if (entity instanceof Player){
                Vector vel = entity.getLocation().toVector().subtract(boss.getEntity().getLocation().toVector());
                Location fireballLoc = boss.getEntity().getLocation().add(vel.multiply(0.5));
                Vector fireballVel = entity.getLocation().toVector().subtract(fireballLoc.toVector());
                Entity fireball = boss.getEntity().getWorld().spawnEntity(fireballLoc, EntityType.FIREBALL);
                fireball.setVelocity(fireballVel.normalize());
                Bukkit.broadcastMessage("velocity set");
                new BukkitRunnable(){
                    @Override
                    public void run(){
                        fireball.setVelocity(fireballVel.normalize());
                    }
                }.runTaskLater(plugin,1);
            }
        }
        finish(boss);
    }
}
