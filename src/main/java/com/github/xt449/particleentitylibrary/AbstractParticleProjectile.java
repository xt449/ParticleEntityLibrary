package com.github.xt449.particleentitylibrary;

import org.bukkit.Location;

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
