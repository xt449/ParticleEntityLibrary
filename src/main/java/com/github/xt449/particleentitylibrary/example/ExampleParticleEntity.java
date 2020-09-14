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

import com.github.xt449.particleentitylibrary.AbstractParticleEntity;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import com.github.xt449.particleentitylibrary.ParticleData;

import java.util.Iterator;

/**
 * @author xt449
 */
public class ExampleParticleEntity extends AbstractParticleEntity {

	ExampleParticleEntity(Location location) {
		super(new ParticleData(Particle.SPELL_WITCH, 1, 0, 0, 0, 0), location, 0, 0);
	}

	private Entity target;

	private void acquireTarget() {
		final Iterator<Entity> entities = location.getWorld().getNearbyEntities(location, 16, 16, 16, entity -> entity instanceof LivingEntity && entity.isValid()).iterator();

		if(entities.hasNext()) {
			target = entities.next();

			Bukkit.broadcastMessage("Acquired new target: " + target.getName());
			Bukkit.broadcastMessage("@ " + target.getLocation().toString());
		}
	}

	@Override
	protected void onSummon() {
		acquireTarget();
	}

	@Override
	protected void onPreTick() {
		if(target == null || !target.isValid()) {
			acquireTarget();
		}

		if(target != null && target.isValid()) {
			velocity = target.getLocation().subtract(location).toVector()/*.normalize()*/.multiply(0.1F);
		}
	}

	@Override
	protected void onPostTick() {
		if(getLocation().getY() < 0) {
			destroy();
		}
	}
}
