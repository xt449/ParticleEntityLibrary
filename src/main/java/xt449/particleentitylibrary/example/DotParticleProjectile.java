package xt449.particleentitylibrary.example;

import org.bukkit.Location;
import org.bukkit.Particle;
import xt449.particleentitylibrary.AbstractParticleProjectile;
import xt449.particleentitylibrary.ParticleData;

public class DotParticleProjectile extends AbstractParticleProjectile {

	public DotParticleProjectile(Location location) {
		super(location, new ParticleData(Particle.TOTEM));
	}

	@Override
	protected void onSummon() {
	}

	@Override
	protected void onTick() {
		if(onGround() || getLocation().getY() < 0) {
			destroy();
		}
	}

	@Override
	protected void onHit() {
	}
}
