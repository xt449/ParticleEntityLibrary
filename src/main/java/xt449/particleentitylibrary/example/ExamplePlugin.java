package xt449.particleentitylibrary.example;

import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;
import xt449.particleentitylibrary.AbstractParticleProjectile;

import java.util.Arrays;

/**
 * @author xt449
 * Copyright BinaryBanana/xt449 2019
 * All Rights Reserved
 */
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
	public final void onEnable() {
		Bukkit.getPluginManager().registerEvents(this, this);
	}

	@EventHandler(priority = EventPriority.NORMAL)
	protected final void onPlayerInteract(PlayerInteractEvent event) {
		final Material material = event.getMaterial();
		final Action action = event.getAction();
		final Player player = event.getPlayer();

		if(action.equals(Action.RIGHT_CLICK_AIR) || action.equals(Action.RIGHT_CLICK_BLOCK)) {
			if(material == Material.BLAZE_POWDER) {
				new FlameThrowerParticle().summon(player.getEyeLocation(), player.getLocation().getDirection());

				player.getWorld().playSound(player.getLocation(), Sound.ENTITY_WITHER_SHOOT, 0.04F, 1.4F);
			} else if(material == Material.EMERALD) {
				final Location location = player.getEyeLocation();
				final Vector direction = location.getDirection().normalize().multiply(0.2F);
				final Location point = player.getEyeLocation();
				final World world = player.getWorld();

				do {
					point.add(direction);
					world.spawnParticle(particle /*Particle.VILLAGER_HAPPY*/, point, 1, 0, 0, 0, player.getLevel());
				} while(point.distance(location) < 30 && point.getBlock().isPassable());

				player.getWorld().spawnParticle(Particle.FLASH, point, 4, 0, 0, 0, 0);
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
	}
}
