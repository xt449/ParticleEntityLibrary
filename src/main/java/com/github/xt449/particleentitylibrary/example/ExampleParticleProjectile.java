package com.github.xt449.particleentitylibrary.example;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Entity;
import com.github.xt449.particleentitylibrary.AbstractParticleProjectile;
import com.github.xt449.particleentitylibrary.ParticleData;

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
