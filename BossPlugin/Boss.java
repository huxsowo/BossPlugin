package BossPlugin;

import BossPlugin.Utils.ParticleUtil;
import me.libraryaddict.disguise.disguisetypes.FlagWatcher;
import me.libraryaddict.disguise.disguisetypes.PlayerDisguise;
import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;

public class Boss implements Listener {

    public static ArrayList<Entity> aliveBosses = new ArrayList<Entity>();

    protected String name;
    protected ChatColor nameColor;
    protected double maxHealth;
    protected boolean takesKB;
    protected EntityType entityType;

    protected double health;
    protected LivingEntity entity;
    protected int attackCooldown = 40;

    public boolean isAlive;

    protected Plugin plugin;

    public Boss(){this.plugin = Main.getInstance();}

    public void summon(Location spawnLoc){
        if (entityType != EntityType.PLAYER) {
            setEntity((LivingEntity) spawnLoc.getWorld().spawnEntity(spawnLoc, entityType));
        } else {
            setEntity((LivingEntity) spawnLoc.getWorld().spawnEntity(spawnLoc, EntityType.ZOMBIE));
            PlayerDisguise playerDisg = new PlayerDisguise("zxoicu");
            FlagWatcher watcher = playerDisg.getWatcher();
            playerDisg.setName("" + nameColor + ChatColor.BOLD + name);
            watcher.setCustomNameVisible(true);
            playerDisg.setEntity(entity);
            playerDisg.startDisguise();
        }
        entity.setCustomName("" + nameColor + ChatColor.BOLD + name);
        entity.setCustomNameVisible(true);
        entity.setAI(false);
        setHealth(maxHealth);
        isAlive = true;
        aliveBosses.add(entity);
        Bukkit.broadcastMessage("" + nameColor + ChatColor.BOLD + name + " has spawned!");
    }

    public void damage(double damageAmount){ //implement kb with takesKB boolean
        setHealth(getHealth() - damageAmount);
        Bukkit.broadcastMessage("Boss HP: " + health);
        if (health <= 0){
            die();
        }
    }

    public void heal(double healAmount){ //Heals the boss
        setHealth(Math.min((health + healAmount), maxHealth));
        Bukkit.broadcastMessage("Boss HP: " + health);
    }

    public void die(){ //Kills the boss
        isAlive = false;
        aliveBosses.remove(this.entity);
        Bukkit.broadcastMessage(name + " has been defeated!");
    }

    public void useAttack(Attack attack){
        attack.checkAndUse(this);
    }

    @EventHandler
    public void onBossHit(EntityDamageEvent e){
        Bukkit.broadcastMessage("entity hit");
        Entity eventEntity = e.getEntity();
        if (aliveBosses.contains(eventEntity)){
            Bukkit.broadcastMessage("Boss hit");
            e.setCancelled(true);
            damage(e.getDamage());
        }
    }

    public LivingEntity getEntity(){return entity;}

    public void setEntity(LivingEntity livingEntity){entity = livingEntity;}

    public String getName(){return name;}

    public double getMaxHealth(){return maxHealth;}

    public double getHealth(){return health;}

    public void setHealth(double newHealth){health = newHealth;}

    public int getAttackCooldown(){return attackCooldown;}

    public void setAttackCooldown(int newAttackCooldown){attackCooldown = newAttackCooldown;}
}
