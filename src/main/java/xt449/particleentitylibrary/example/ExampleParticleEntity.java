package xt449.particleentitylibrary.example;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import xt449.particleentitylibrary.AbstractParticleEntity;

import java.util.Iterator;

public class ExampleParticleEntity extends AbstractParticleEntity {

	public ExampleParticleEntity(Location location) {
		super(location, 0, 0);
	}

	private Entity target;

	private void acquireTarget() {
		final Iterator<Entity> entities = location.getWorld().getNearbyEntities(location, 16, 16, 16).iterator();

		if(entities.hasNext()) {
			target = entities.next();
		}
	}

	@Override
	protected void onSummon() {
		acquireTarget();
	}

	@Override
	protected void onTick() {
		if(onGround() || getLocation().getY() < 0) {
			destroy();
		}

		if(target == null) {
			acquireTarget();
		}

		if(target != null) {
			velocity = target.getLocation().subtract(location).getDirection().multiply(0.04F);
		}
	}
}
