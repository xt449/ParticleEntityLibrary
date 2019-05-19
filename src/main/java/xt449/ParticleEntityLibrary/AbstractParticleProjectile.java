package xt449.ParticleEntityLibrary;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractParticleProjectile {

	public static final float DRAG_LIVING = 0.02F;
	public static final float DRAG_THROWN = 0.01F;
	public static final float DRAG_ARROW = 0.01F;
	public static final float DRAG_OTHER = 0.02F;
	public static final float DRAG_VEHICLE = 0.05F;
	private static final float DRAG_DEFAULT = 0.015F;

	public static final float GRAVITY_LIVING = 0.08F;
	public static final float GRAVITY_THROWN = 0.03F;
	public static final float GRAVITY_ARROW = 0.05F;
	public static final float GRAVITY_OTHER = 0.04F;
	public static final float GRAVITY_VEHICLE = 0.04F;
	private static final float GRAVITY_DEFAULT = 0.045F;

	private static final class Data {
		Location location = null;
		Vector velocity = new Vector();
		float drag = DRAG_DEFAULT;
		float gravity = GRAVITY_DEFAULT;
		ParticleData particleData;
	}

	private static boolean registered;

	private static final List<AbstractParticleProjectile> activeProjectiles = new ArrayList<>();

	public static final AbstractParticleProjectile getActiveParticleProjectile(int id) {
		return activeProjectiles.get(id);
	}

	static final void register(Plugin plugin) {
		if(!registered) {
			// ConcurrentModificationException - Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, () -> activeProjectiles.forEach(AbstractParticleProjectile::idle), 100, 1);
			Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, () -> new ArrayList<>(activeProjectiles).forEach(AbstractParticleProjectile::idle), 100, 1);
			registered = true;
		}
	}

	public final int id;
	private Data data = new Data();

	protected AbstractParticleProjectile(ParticleData particleData) {
		id = activeProjectiles.size();
		data.particleData = particleData;
	}

	protected AbstractParticleProjectile(ParticleData particleData, Location location) {
		this(particleData);
		data.location = location;
	}

	protected AbstractParticleProjectile(ParticleData particleData, Location location, Vector velocity) {
		this(particleData);
		data.location = location;
		data.velocity = velocity;
	}

	protected AbstractParticleProjectile(ParticleData particleData, Location location, Vector velocity, float drag) {
		this(particleData);
		data.location = location;
		data.velocity = velocity;
		data.drag = drag;
	}

	protected AbstractParticleProjectile(ParticleData particleData, Location location, Vector velocity, float drag, float gravity) {
		this(particleData);
		data.location = location;
		data.velocity = velocity;
		data.drag = drag;
		data.gravity = gravity;
	}

	protected AbstractParticleProjectile(ParticleData particleData, Location location, float drag) {
		this(particleData);
		data.location = location;
		data.drag = drag;
	}

	protected AbstractParticleProjectile(ParticleData particleData, Location location, float drag, float gravity) {
		this(particleData);
		data.location = location;
		data.drag = drag;
		data.gravity = gravity;
	}

	protected AbstractParticleProjectile(ParticleData particleData, Vector velocity) {
		this(particleData);
		data.velocity = velocity;
	}

	protected AbstractParticleProjectile(ParticleData particleData, Vector velocity, float drag) {
		this(particleData);
		data.velocity = velocity;
		data.drag = drag;
	}

	protected AbstractParticleProjectile(ParticleData particleData, Vector velocity, float drag, float gravity) {
		this(particleData);
		data.velocity = velocity;
		data.drag = drag;
		data.gravity = gravity;
	}

	protected AbstractParticleProjectile(ParticleData particleData, float drag) {
		this(particleData);
		data.drag = drag;
	}

	protected AbstractParticleProjectile(ParticleData particleData, float drag, float gravity) {
		this(particleData);
		data.drag = drag;
		data.gravity = gravity;
	}

	private final void summon() {
		activeProjectiles.add(this);

		onLaunch();
	}

	public final void summon(Location location) {
		data.location = location;

		this.summon();
	}

	public final void summon(Location location, Vector velocity) {
		data.location = location;
		data.velocity = velocity;

		this.summon();
	}

	public final void summon(Location location, Vector velocity, float drag) {
		data.location = location;
		data.velocity = velocity;
		data.drag = drag;

		this.summon();
	}

	public final void summon(Location location, Vector velocity, float drag, float gravity) {
		data.location = location;
		data.velocity = velocity;
		data.drag = drag;
		data.gravity = gravity;

		this.summon();
	}

	public final void summon(Location location, float drag) {
		data.location = location;
		data.drag = drag;

		this.summon();
	}

	public final void summon(Location location, float drag, float gravity) {
		data.location = location;
		data.drag = drag;
		data.gravity = gravity;

		this.summon();
	}

	public final void summon(Vector velocity) {
		data.velocity = velocity;

		this.summon();
	}

	public final void summon(Vector velocity, float drag) {
		data.velocity = velocity;
		data.drag = drag;

		this.summon();
	}

	public final void summon(Vector velocity, float drag, float gravity) {
		data.velocity = velocity;
		data.drag = drag;
		data.gravity = gravity;

		this.summon();
	}

	public final void summon(float drag) {
		data.drag = drag;

		this.summon();
	}

	public final void summon(float drag, float gravity) {
		data.drag = drag;
		data.gravity = gravity;

		this.summon();
	}

	public final Location getLocation() {
		return data.location;
	}

	public final Vector getVelocity() {
		return data.velocity;
	}

	public final float getDrag() {
		return data.drag;
	}

	public final float getGravity() {
		return data.gravity;
	}

	private void idle() {
		data.particleData.spawnParticle(data.location);

		if(!onGround()) {
			processMovementTick();
		}

		onIdle();
	}

	private void processMovementTick() {
		data.location = data.location.add(data.velocity);
		data.velocity = data.velocity.multiply(1 - data.drag).subtract(new Vector(0, data.gravity, 0));
	}

	public final AbstractParticleProjectile setLocation(Location location) {
		data.location = location;

		return this;
	}

	public final AbstractParticleProjectile setVelocity(Vector velocity) {
		data.velocity = velocity;

		return this;
	}

	public final AbstractParticleProjectile setDrag(float drag) {
		data.drag = drag;

		return this;
	}

	public final AbstractParticleProjectile setGravity(float gravity) {
		data.gravity = gravity;

		return this;
	}

	public final boolean onGround() {
		return (data.location.getBlock().getType() != Material.AIR /*|| getNextLocation(id).getBlock().getType() != Material.AIR*/);
	}

	public final void destroy() {
		activeProjectiles.remove(this);
	}

	protected abstract void onLaunch();

	protected abstract void onHit();

	protected abstract void onIdle();
}
