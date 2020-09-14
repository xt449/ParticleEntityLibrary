/*
 * Copyright (c) 2020 xt449/BinaryBanana
 *
 * This file is part of ParticleEntityLibrary.
 *
 * ParticleEntityLibrary is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * ParticleEntityLibrary is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with ParticleEntityLibrary.  If not, see <https://www.gnu.org/licenses/>.
 *
 */

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
