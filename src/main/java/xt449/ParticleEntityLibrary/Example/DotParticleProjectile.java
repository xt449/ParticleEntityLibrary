package xt449.ParticleEntityLibrary.Example;

import org.bukkit.Particle;
import org.bukkit.entity.Entity;
import xt449.ParticleEntityLibrary.AbstractParticleProjectile;
import xt449.ParticleEntityLibrary.ParticleData;

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
