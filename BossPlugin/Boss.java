package BossPlugin;

import BossPlugin.Utils.ParticleUtil;
import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.plugin.Plugin;

public class Boss implements Listener {

    protected String name;
    protected ChatColor nameColor;
    protected double maxHealth;
    protected boolean takesKB;
    protected EntityType entityType;

    protected double health;
    protected LivingEntity entity;

    protected Plugin plugin;

    public Boss(){this.plugin = Main.getInstance();}

    public void summon(Location spawnLoc){
        entity = (LivingEntity) spawnLoc.getWorld().spawnEntity(spawnLoc, entityType);
        entity.setCustomName("" + nameColor + ChatColor.BOLD + name);
        entity.setCustomNameVisible(true);
        entity.setHealth(maxHealth);
        health = maxHealth;
        Bukkit.broadcastMessage("" + nameColor + ChatColor.BOLD + name + " has spawned!");
    }

    public void damage(double damageAmount){ //implement kb with takesKB boolean
        health -= damageAmount;
        Bukkit.broadcastMessage("Boss HP: " + health);
        if (health <= 0){
            die();
        }
    }

    public void heal(double healAmount){
        health = Math.min((health + healAmount), maxHealth);
        Bukkit.broadcastMessage("Boss HP: " + health);
    }

    public void die(){
        Location deathLoc = entity.getLocation();
        World world = entity.getWorld();
        entity.remove();
        ParticleUtil.spawnParticle(Particle.EXPLOSION_HUGE, deathLoc, world);
        world.playSound(deathLoc, Sound.ENTITY_WITHER_DEATH,2,1);
        Bukkit.broadcastMessage(name + " has been defeated!");
    }

    @EventHandler
    public void onBossHit(EntityDamageEvent e){
        Entity eventEntity = e.getEntity();
        if (eventEntity == entity){
            e.setCancelled(true);
            damage(e.getDamage());
        }
    }
}
