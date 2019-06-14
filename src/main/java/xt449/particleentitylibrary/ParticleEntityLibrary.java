package xt449.particleentitylibrary;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

import java.util.function.Consumer;

public class ParticleEntityLibrary extends JavaPlugin {

	@Override
	public final void onEnable() {
		AbstractParticleProjectile.register(this);
	}

	@Override
	public final void onDisable() {
		Bukkit.getScheduler().cancelTasks(this);
	}

	public static void particleRaycast(ParticleData particleData, Location origin, Vector direction, float range, Consumer<Location> onRay) {
		final Location point = origin.clone();
		do {
			point.add(direction);
			onRay.accept(point);
			particleData.spawnParticle(point);
		} while(point.distance(origin) < range && point.getBlock().isPassable());
	}

	public static void particleRaycast(ParticleData particleData, Location origin, Vector direction, float range, Consumer<Location> onRay, Consumer<Location> onHit, boolean rangeTrigger) {
		final Location point = origin.clone();
		do {
			point.add(direction);
			onRay.accept(point);
			particleData.spawnParticle(point);

			if(!point.getBlock().isPassable()) {
				rangeTrigger = true;
				break;
			}
		} while(point.distance(origin) < range);

		if(rangeTrigger) {
			onHit.accept(point);
		}
	}

	public static void particleRaycast(ParticleData particleData, Location origin, Vector direction, float range, Consumer<Location> onRay, boolean strictBoundingBoxes) {
		final Location point = origin.clone();
		do {
			point.add(direction);
			onRay.accept(point);
			particleData.spawnParticle(point);

			if(!point.getBlock().isPassable()) {
				if(strictBoundingBoxes) {
					if(point.getBlock().getBoundingBox().contains(point.toVector())) {
						break;
					}
				}
			}
		} while(point.distance(origin) < range);
	}

	public static void particleRaycast(ParticleData particleData, Location origin, Vector direction, float range, Consumer<Location> onRay, Consumer<Location> onHit, boolean rangeTrigger, boolean strictBoundingBoxes) {
		final Location point = origin.clone();
		do {
			point.add(direction);
			onRay.accept(point);
			particleData.spawnParticle(point);

			if(!point.getBlock().isPassable()) {
				if(strictBoundingBoxes) {
					if(point.getBlock().getBoundingBox().contains(point.toVector())) {
						rangeTrigger = true;
						break;
					}
				} else {
					rangeTrigger = true;
					break;
				}
			}
		} while(point.distance(origin) < range);

		if(rangeTrigger) {
			onHit.accept(point);
		}
	}
}
