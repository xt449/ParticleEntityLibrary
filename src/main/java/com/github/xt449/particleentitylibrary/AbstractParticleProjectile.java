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

/**
 * @author xt449
 */
public abstract class AbstractParticleProjectile extends AbstractParticleEntity {

	/*private static int taskId = -1;

	static boolean register(Plugin plugin) {
		if(taskId == -1) {
			taskId = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, () -> new ArrayList<>(activeProjectiles).forEach(AbstractParticleProjectile::idle), 100, 1);
		}

		return taskId != -1;
	}

	static void unregister() {
		Bukkit.getScheduler().cancelTask(taskId);
	}*/

	// minimum constructor

	protected AbstractParticleProjectile(ParticleData particleData, Location location) {
		super(particleData, location);
	}

	// miscellaneous

	@Override
	protected void tick() {
		onPreTick();

		particleData.spawnParticle(location);

		if(!onGround()) {
			processMovementTick();
		}

		onPostTick();
	}

	// events

	/**
	 * @deprecated Not yet implemented
	 */
	@Deprecated
	protected abstract void onHit();
}
