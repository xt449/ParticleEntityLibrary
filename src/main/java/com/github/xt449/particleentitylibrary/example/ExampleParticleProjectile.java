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

package com.github.xt449.particleentitylibrary.example;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Entity;
import com.github.xt449.particleentitylibrary.AbstractParticleProjectile;
import com.github.xt449.particleentitylibrary.ParticleData;

/**
 * @author xt449
 */
public class ExampleParticleProjectile extends AbstractParticleProjectile {

	ExampleParticleProjectile(Location location) {
		super(new ParticleData(Particle.FLAME, 25, 0.5F, 0.5F, 0.5F), location);
	}

	@Override
	protected void onSummon() {
	}

	@Override
	protected void onPreTick() {
	}

	@Override
	protected void onPostTick() {
		for(Entity entity : getLocation().getWorld().getNearbyEntities(getLocation(), 1, 1, 1)) {
			entity.setFireTicks(200); // 10 seconds
		}

		if(onGround() || getLocation().getY() < 0) {
			destroy();
		}
	}

	@Override
	protected void onHit() {
	}
}
