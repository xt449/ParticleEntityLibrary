package xt449.particleentitylibrary;

import org.bukkit.Location;
import org.bukkit.util.Vector;

public abstract class AbstractParticleProjectile extends AbstractParticleEntity {

	/*private static int taskId = -1;

	private static final List<AbstractParticleProjectile> activeProjectiles = new ArrayList<>();

	public static AbstractParticleProjectile getActiveParticleProjectile(int id) {
		return activeProjectiles.get(id);
	}

	static boolean register(Plugin plugin) {
		if(taskId == -1) {
			taskId = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, () -> new ArrayList<>(activeProjectiles).forEach(AbstractParticleProjectile::idle), 100, 1);
		}

		return taskId != -1;
	}

	static void unregister() {
		Bukkit.getScheduler().cancelTask(taskId);
	}*/

	protected ParticleData particleData;

	// constructors

	protected AbstractParticleProjectile(Location location, ParticleData particleData) {
		super(location);
		/*data.*/
		this.particleData = particleData;
	}

	protected AbstractParticleProjectile(Location location, ParticleData particleData, Vector velocity) {
		this(location, particleData);
		/*data.*/
		this.velocity = velocity;
	}

	@Deprecated
	protected AbstractParticleProjectile(Location location, ParticleData particleData, Vector velocity, float drag) {
		this(location, particleData);
		/*data.*/
		this.velocity = velocity;
		/*data.*/
		this.drag = drag;
	}

	protected AbstractParticleProjectile(Location location, ParticleData particleData, Vector velocity, float drag, float gravity) {
		this(location, particleData);
		/*data.*/
		this.velocity = velocity;
		/*data.*/
		this.drag = drag;
		/*data.*/
		this.gravity = gravity;
	}

	@Deprecated
	protected AbstractParticleProjectile(Location location, ParticleData particleData, float drag) {
		this(location, particleData);
		/*data.*/
		this.drag = drag;
	}

	protected AbstractParticleProjectile(Location location, ParticleData particleData, float drag, float gravity) {
		this(location, particleData);
		/*data.*/
		this.drag = drag;
		/*data.*/
		this.gravity = gravity;
	}

	// section: 'miscellaneous'

	@Override
	protected void tick() {
		particleData.spawnParticle(location);

		if(!onGround()) {
			processMovementTick();
		}

		onTick();
	}

	/*public final boolean onGround() {
		return (location.getBlock().getType() != Material.AIR *//*|| getNextLocation(id).getBlock().getType() != Material.AIR*//*);
	}*/

	// section: 'events'

	//protected abstract void onLaunch();

	protected abstract void onHit();
}
