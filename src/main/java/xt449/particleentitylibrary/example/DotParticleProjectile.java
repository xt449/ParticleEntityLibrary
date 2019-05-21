package xt449.particleentitylibrary.example;

import org.bukkit.Particle;
import xt449.particleentitylibrary.AbstractParticleProjectile;
import xt449.particleentitylibrary.ParticleData;

public class DotParticleProjectile extends AbstractParticleProjectile {

	public DotParticleProjectile() {
		super(new ParticleData(Particle.TOTEM));
	}

	@Override
	protected void onLaunch() {
	}

	@Override
	protected void onHit() {
	}

	@Override
	protected void onIdle() {
		if(onGround() || getLocation().getY() < 0) {
			destroy();
		}
	}
}
