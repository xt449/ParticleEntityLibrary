package xt449.particleentitylibrary.example;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.java.JavaPlugin;
import xt449.particleentitylibrary.AbstractParticleEntity;
import xt449.particleentitylibrary.ParticleData;
import xt449.particleentitylibrary.ParticleEntityLibrary;

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
						stepLocation -> {
						},
						hitLocation -> new ParticleData(Particle.FLASH, 4, 0, 0, 0, 0).spawnParticle(hitLocation),
						false,
						true
				);
			}
		}
	}
}
