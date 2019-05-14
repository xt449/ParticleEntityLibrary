package xt449.ParticleEntityLibrary;

import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;
import xt449.ParticleEntityLibrary.Example.DotParticleProjectile;
import xt449.ParticleEntityLibrary.Example.FlameThrowerParticle;

import java.util.Arrays;

public class ParticleEntityLibrary extends JavaPlugin implements Listener {

	int particleIndex = 0;
	Particle particle = Particle.EXPLOSION_NORMAL;

	@Override
	public final void onLoad() {
	}

	@Override
	public final void onEnable() {
		getCommand("pel").setExecutor((sender, command, label, args) -> {
			if(args.length > 0) {
				try {
					int num = Integer.parseInt(args[0]);
					this.particle = Particle.values()[num];
					particleIndex = Arrays.binarySearch(Particle.values(), particle);
					return true;
				} catch(NumberFormatException exc) {
					//
				}

				try {
					this.particle = Particle.valueOf(args[0]);
					particleIndex = Arrays.binarySearch(Particle.values(), particle);
					return true;
				} catch(IllegalArgumentException exc) {
					//
				}
			} else {
				sender.sendMessage(Arrays.toString(Particle.values()));
				return true;
			}
			return false;
		});

		AbstractParticleProjectile.register(this);

		Bukkit.getPluginManager().registerEvents(this, this);
	}

	@Override
	public final void onDisable() {
		Bukkit.getScheduler().cancelTasks(this);
	}

	@EventHandler(priority = EventPriority.HIGH)
	protected final void onPlayerInteract(PlayerInteractEvent event) {
		final Material material = event.getMaterial();
		//if(!material.equals(Material.AIR)) {
		final Action action = event.getAction();
		final Player player = event.getPlayer();
		if(action.equals(Action.RIGHT_CLICK_AIR) || action.equals(Action.RIGHT_CLICK_BLOCK)) {
			if(material == Material.BLAZE_POWDER) {
				new FlameThrowerParticle().summon(player.getEyeLocation(), player.getLocation().getDirection());

				//Guardian guardianEntity = player.getWorld().spawn(player.getLocation(), Guardian.class);
				//guardianEntity.setAI(false);
				//guardianEntity.setInvulnerable(true);
				//guardianEntity.setTarget(null);
				//guardianEntity.setTarget(player);

				//player.getWorld().playSound(player.getLocation(), Sound.WEATHER_RAIN_ABOVE, 0.09F, 2.0F);
				//player.getWorld().playSound(player.getLocation(), Sound.WEATHER_RAIN, 0.09F, 2.0F);
				player.getWorld().playSound(player.getLocation(), Sound.ENTITY_WITHER_SHOOT, 0.04F, 1.4F);
			} else if(material == Material.EMERALD) {
				//RayTraceResult result = player.rayTraceBlocks(16 * 8, FluidCollisionMode.NEVER);
				final Location location = player.getEyeLocation();
				final Vector direction = location.getDirection().normalize().multiply(0.2F);
				final Location point = player.getEyeLocation();
				final World world = player.getWorld();
				do {
					point.add(direction);
					world.getNearbyEntities(point, 0.2F, 0.2F, 0.2F, (entity) -> entity instanceof LivingEntity && entity.getType() != EntityType.PLAYER).forEach((entity) -> ((LivingEntity) entity).setHealth(0));
					world.spawnParticle(particle /*Particle.VILLAGER_HAPPY*/, point, 1, 0, 0, 0, player.getLevel());
				} while(point.distance(location) < 30 && point.getBlock().isPassable());
				//player.getWorld().playSound(player.getLocation(), Sound.BLOCK_ANVIL_PLACE, SoundCategory.PLAYERS, 0.1F, 2);
				//player.getWorld().playSound(point, Sound.ENTITY_DRAGON_FIREBALL_EXPLODE, SoundCategory.HOSTILE, 3.0F, 1);
				player.getWorld().spawnParticle(Particle.FLASH, point, 4, 0, 0, 0, 0);
				//player.getWorld().spawnParticle(Particle.SNEEZE, point, 32, 1, 1, 1, 0.05F);
				//> 16) {
				//}
				//if(res)
			}
		} else if(action.equals(Action.LEFT_CLICK_AIR) || action.equals(Action.LEFT_CLICK_BLOCK)) {
			if(material == Material.BOW) {
				final Arrow arrow = player.launchProjectile(Arrow.class);
				arrow.setCritical(true);
				arrow.setColor(Color.BLUE);
				arrow.setGlowing(true);
				new DotParticleProjectile().summon(player.getEyeLocation(), arrow.getVelocity(), AbstractParticleProjectile.DRAG_ARROW, AbstractParticleProjectile.GRAVITY_ARROW);
			}
		}
		//}
	}
}
