package BossPlugin;

import BossPlugin.Bosses.Steve;
import BossPlugin.Managers.EventManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;

public class Main extends JavaPlugin implements Listener {

    public static Plugin ourInstance;
    public static Boss[] allBosses;
    public static String bossList = "All bosses:";
    public double dps;

    public static void main(String[] args){
    }

    public static Plugin getInstance() {return ourInstance;}

    @Override
    public void onEnable(){
        ourInstance = this;
        getServer().getPluginManager().registerEvents(this, this);
        getServer().getPluginManager().registerEvents(new EventManager(), this);
        getServer().getPluginManager().registerEvents(new Boss(), this);

        allBosses = new Boss[]{
                new Steve(),
        };

        for (Boss boss : allBosses){
            bossList = bossList + " " + boss.name + ",";
        }

        dps = 0;
    }

    @Override
    public void onDisable(){

    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
        Player player = (Player) sender;
        switch (cmd.getName().toLowerCase()){
            case "spawnboss":
                if (args.length == 1){
                    for (Boss boss : allBosses) {
                        if (boss.name.equalsIgnoreCase(args[0])) {
                            boss.summon(player.getLocation());
                            player.sendMessage("You have summoned " + boss.name);
                            return true;
                        }
                    }
                    player.sendMessage("§cThat boss does not exist. Please check for spelling errors and try again");
                    player.sendMessage(bossList);
                    return false;
                } else if (args.length == 4) {
                    Location spawnLoc = new Location(player.getWorld(), Integer.parseInt(args[1]),Integer.parseInt(args[2]),Integer.parseInt(args[3]));
                    for (Boss boss : allBosses) {
                        if (boss.name.equalsIgnoreCase(args[0])) {
                            boss.summon(spawnLoc);
                            player.sendMessage("You have summoned " + boss.name);
                            return true;
                        }
                    }
                } else {
                    player.sendMessage("§cYou did not use the command correctly. §rCorrect usage: /spawnboss <Boss Name>, <Location (optional)>");
                    return false;
                }
                break;
            case "bosslist":
                player.sendMessage(bossList);
                break;
        }
        return true;
    }

    public void addDps(double dmg){
        dps += dmg;
        Bukkit.broadcastMessage("DPS: " + dps);
        new BukkitRunnable(){
            @Override
            public void run(){
                dps -= dmg;
            }
        }.runTaskLater(getInstance(), 20);
    }

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent e) {
        if (e.getDamager() instanceof Player) {
            double dmg = e.getDamage();
            addDps(dmg);
        }
    }
}
