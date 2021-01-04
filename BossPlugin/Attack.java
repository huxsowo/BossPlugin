package BossPlugin;

import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

public abstract class Attack implements Listener {

    protected int cooldown;

    protected Plugin plugin;

    public Attack(){
        this.plugin = Main.getInstance();
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    public abstract void use(Boss boss);

    public void finish(Boss boss){
        boss.setAttackCooldown(cooldown);
    }

    public void checkAndUse(Boss boss){
        if (boss.getAttackCooldown() > 0){
            return;
        }
        use(boss);
    }

    public int getCooldown(){return cooldown;}

}
