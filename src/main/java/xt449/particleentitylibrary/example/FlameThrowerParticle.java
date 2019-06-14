package xt449.particleentitylibrary.example;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Entity;
import xt449.particleentitylibrary.AbstractParticleProjectile;
import xt449.particleentitylibrary.ParticleData;

public class FlameThrowerParticle extends AbstractParticleProjectile {

	public FlameThrowerParticle(Location location) {
		super(location, new ParticleData(Particle.FLAME, 25, 0.5F, 0.5F, 0.5F));
	}

	@Override
	protected void onSummon() {
	}

	@Override
	protected void onTick() {
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
