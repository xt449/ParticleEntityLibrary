package com.github.xt449.particleentitylibrary;

import org.bukkit.Location;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.Vector;

import java.util.function.Consumer;

/**
 * @author xt449
 */
public final class ParticleEntityLibrary {

	public static void particleRaycast(ParticleData particleData, Location origin, Vector direction, float range) {
		final Location point = origin.clone();
		do {
			point.add(direction);
			particleData.spawnParticle(point);
		} while(point.distance(origin) < range && point.getBlock().isPassable());
	}

	public static void particleRaycast(ParticleData particleData, Location origin, Vector direction, float range, Consumer<Location> onHit, boolean rangeTrigger) {
		final Location point = origin.clone();
		do {
			point.add(direction);
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

	public static void particleRaycast(ParticleData particleData, Location origin, Vector direction, float range, boolean strictBoundingBoxes) {
		final Location point = origin.clone();
		do {
			point.add(direction);
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

	public static void particleRaycast(ParticleData particleData, Location origin, Vector direction, float range, boolean strictBoundingBoxes, Consumer<Location> onHit, boolean rangeTrigger) {
		final Location point = origin.clone();
		do {
			point.add(direction);
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

	public static void particleRaycast(ParticleData particleData, Location origin, Vector direction, float range, Consumer<Location> onStep) {
		final Location point = origin.clone();
		do {
			point.add(direction);
			onStep.accept(point);
			particleData.spawnParticle(point);
		} while(point.distance(origin) < range && point.getBlock().isPassable());
	}

	public static void particleRaycast(ParticleData particleData, Location origin, Vector direction, float range, Consumer<Location> onStep, Consumer<Location> onHit, boolean rangeTrigger) {
		final Location point = origin.clone();
		do {
			point.add(direction);
			onStep.accept(point);
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

	public static void particleRaycast(ParticleData particleData, Location origin, Vector direction, float range, boolean strictBoundingBoxes, Consumer<Location> onStep) {
		final Location point = origin.clone();
		do {
			point.add(direction);
			onStep.accept(point);
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

	public static void particleRaycast(ParticleData particleData, Location origin, Vector direction, float range, boolean strictBoundingBoxes, Consumer<Location> onStep, Consumer<Location> onHit, boolean rangeTrigger) {
		final Location point = origin.clone();
		do {
			point.add(direction);
			onStep.accept(point);
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

	static {
		register();
	}

	public static void register() {
		ParticleData.registerSerialization();
	}

	public static boolean register(final Plugin plugin) {
		register();
		return AbstractParticleEntity.register(plugin);
	}
}
