package xt449.ParticleEntityLibrary;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Guardian;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.java.JavaPlugin;
import xt449.ParticleEntityLibrary.Example.FlameThrowerParticle;

public class ParticleEntityLibrary extends JavaPlugin implements Listener {

	@Override
	public final void onLoad() {
	}

	@Override
	public final void onEnable() {
		AbstractParticleProjectile.register(this);

		Bukkit.getPluginManager().registerEvents(this, this);
	}

	@Override
	public final void onDisable() {
		Bukkit.getScheduler().cancelTasks(this);
	}

	@EventHandler(priority = EventPriority.HIGH)
	protected final void onPlayerInteract(PlayerInteractEvent event) {
		//final Material material = event.getMaterial();
		//if(!material.equals(Material.AIR)) {
		if(/*material*/event.getMaterial().equals(Material.BLAZE_POWDER)) {
			Action action = event.getAction();
			if(action.equals(Action.RIGHT_CLICK_AIR) || action.equals(Action.RIGHT_CLICK_BLOCK)) {
				Player player = event.getPlayer();
				new FlameThrowerParticle().summon(player.getEyeLocation(), player.getLocation().getDirection());

				Guardian guardianEntity = player.getWorld().spawn(player.getLocation(), Guardian.class);
				guardianEntity.setAI(false);
				guardianEntity.setInvulnerable(true);
				guardianEntity.setTarget(null);
				guardianEntity.setTarget(player);

				//player.getWorld().playSound(player.getLocation(), Sound.WEATHER_RAIN_ABOVE, 0.09F, 2.0F);
				//player.getWorld().playSound(player.getLocation(), Sound.WEATHER_RAIN, 0.09F, 2.0F);
				player.getWorld().playSound(player.getLocation(), Sound.ENTITY_WITHER_SHOOT, 0.04F, 1.4F);
			}
		}
		//}
	}
}
