package com.github.xt449.particleentitylibrary.example;

import com.github.xt449.particleentitylibrary.ParticleEntityLibrary;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import com.github.xt449.particleentitylibrary.AbstractParticleEntity;
import com.github.xt449.particleentitylibrary.Laser;
import com.github.xt449.particleentitylibrary.ParticleData;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class ExamplePlugin extends JavaPlugin implements Listener {

	private Particle particle = Particle.values()[0];

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(command.getName().equalsIgnoreCase("pel")) {
			if(args.length > 0) {
				try {
					int num = Integer.parseInt(args[0]);
					particle = Particle.values()[num];
					return true;
				} catch(NumberFormatException exc) {
					//
				}
				try {
					particle = Particle.valueOf(args[0]);
					return true;
				} catch(IllegalArgumentException exc) {
					//
				}
			} else {
				sender.sendMessage(Arrays.toString(Particle.values()));
				return true;
			}
			return false;
		}

		return false;
	}

	@Override
	public final void onLoad() {
		getLogger().warning("ParticleEntityLibrary has loaded an example plugin!");
	}

	@Override
	public void onEnable() {
		Bukkit.getPluginManager().registerEvents(this, this);

		AbstractParticleEntity.register(this);
	}

	@EventHandler(priority = EventPriority.NORMAL)
	protected final void onPlayerInteract(PlayerInteractEvent event) {
		final Material material = event.getMaterial();
		final Action action = event.getAction();
		final Player player = event.getPlayer();

		if(action.equals(Action.RIGHT_CLICK_AIR) || action.equals(Action.RIGHT_CLICK_BLOCK)) {
			if(material == Material.COOKIE) {
				new ExampleParticleEntity(player.getEyeLocation()).summon();
			} else if(material == Material.BLAZE_POWDER) {
				new ExampleParticleProjectile(player.getEyeLocation()).summon(player.getLocation().getDirection());

				player.getWorld().playSound(player.getLocation(), Sound.ENTITY_WITHER_SHOOT, 0.04F, 1.4F);
			} else if(material == Material.EMERALD) {
				ParticleEntityLibrary.particleRaycast(
						new ParticleData(particle, 1, 0, 0, 0),
						player.getEyeLocation(),
						player.getEyeLocation().getDirection().normalize().multiply(0.2F),
						30,
						false,
						stepLocation -> {},
						hitLocation -> new ParticleData(Particle.FLASH, 4, 0, 0, 0, 0).spawnParticle(hitLocation),
						true
				);
			}
		}
	}

	private Map<Player, LaserRunnable> lasers = new HashMap<>();

	@Override
	public void onDisable() {
		lasers.forEach((player, run) -> run.laser.stop());
	}

	@EventHandler
	public void onInteract(PlayerInteractEvent event) {
		if(event.getAction() != Action.LEFT_CLICK_AIR) return;
		Player player = event.getPlayer();
		if(!lasers.containsKey(player)) return;

		LaserRunnable laserRunnable = lasers.get(player);
		if(laserRunnable.loading != LaserRunnable.LOADING_TIME) return;
		player.getWorld().playSound(player.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 2, 1);
		laserRunnable.loading = 0;

		for(Block block : player.getLineOfSight(null, LaserRunnable.RANGE / 2)) {
			for(Entity entity : player.getWorld().getNearbyEntities(block.getLocation(), 1, 1, 1)) {
				if(entity instanceof Player) continue;
				if(entity instanceof LivingEntity) {
					((LivingEntity) entity).damage(20, player);
					entity.getWorld().spawnParticle(Particle.EXPLOSION_LARGE, entity.getLocation(), 4, 1, 1, 1, 0.1);
				}
			}
		}
		player.getWorld().spawnParticle(Particle.SMOKE_LARGE, laserRunnable.laser.getEnd(), 5);
	}

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		if(lasers.containsKey(event.getPlayer())) {
			try {
				lasers.put(event.getPlayer(), new LaserRunnable(event.getPlayer()));
				lasers.get(event.getPlayer()).runTaskTimer(this, 5, 1);
			} catch(ReflectiveOperationException exc) {
				exc.printStackTrace();
			}
		}
	}

	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event) {
		if(lasers.containsKey(event.getPlayer())) {
			lasers.get(event.getPlayer()).cancel();
		}
	}

	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent event) {
		if(lasers.containsKey(event.getEntity())) {
			lasers.get(event.getEntity()).cancel();
		}
	}

	public class LaserRunnable extends BukkitRunnable {
		public static final byte LOADING_TIME = 30;
		public static final byte RANGE = 10;

		private final Laser laser;
		private final Player player;

		public byte loading = 0;

		public LaserRunnable(Player player) throws ReflectiveOperationException {
			this.player = player;
			this.laser = new Laser(player.getLocation(), player.getLocation().add(0, 1, 0), -1, 50);
			this.laser.start(ExamplePlugin.this);
		}

		public void run() {
			if(loading != LOADING_TIME) {
				loading++;
				player.getWorld().playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 0.7f, loading == LOADING_TIME ? 1.5f : 0.2f);
			}
			try {
				laser.moveStart(player.getLocation().add(0, 0.8, 0));
				laser.moveEnd(player.getLocation().add(0, 1.2, 0).add(player.getLocation().getDirection().multiply(loading == LOADING_TIME ? RANGE : loading / (LOADING_TIME / RANGE * 1.3))));
			} catch(ReflectiveOperationException exc) {
				exc.printStackTrace();
			}
		}

		public synchronized void cancel() throws IllegalStateException {
			laser.stop();
			lasers.remove(player);
			super.cancel();
		}
	}
}
