package BossPlugin.BossAI;

import BossPlugin.Attacks.Fireball;
import BossPlugin.Boss;
import BossPlugin.Main;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

public class SteveAI {

    public static void start(Boss steveBoss){
        Bukkit.broadcastMessage("AI STARTED");
        new BukkitRunnable(){
            @Override
            public void run(){
                steveBoss.getEntity().setFireTicks(0);
                if (!steveBoss.isAlive){
                    cancel();
                }
                if (steveBoss.getAttackCooldown() > 0){
                    steveBoss.setAttackCooldown(steveBoss.getAttackCooldown() - 1);
                }
                Fireball fireball = new Fireball();
                fireball.checkAndUse(steveBoss);
            }
        }.runTaskTimer(Main.getInstance(), 0, 1);
    }

}
