package xt449.particleentitylibrary;

import org.bukkit.Location;
import org.bukkit.Particle;

public class ParticleData {

	private Particle particle;
	private int count = 1;
	private double offsetX = 0;
	private double offsetY = 0;
	private double offsetZ = 0;
	private double extra = 0;
	private Object data = null;
	private boolean force = false;

	public ParticleData(Particle particle) {
		this.particle = particle;
	}

	public ParticleData(Particle particle, int count) {
		this(particle);
		this.count = count;
	}

	public ParticleData(Particle particle, int count, double offsetX, double offsetY, double offsetZ) {
		this(particle);
		this.count = count;
		this.offsetX = offsetX;
		this.offsetY = offsetY;
		this.offsetZ = offsetZ;
	}

	public ParticleData(Particle particle, int count, double offsetX, double offsetY, double offsetZ, double extra) {
		this(particle);
		this.count = count;
		this.offsetX = offsetX;
		this.offsetY = offsetY;
		this.offsetZ = offsetZ;
		this.extra = extra;
	}

	public ParticleData(Particle particle, int count, double offsetX, double offsetY, double offsetZ, double extra, Object data) {
		this(particle);
		this.count = count;
		this.offsetX = offsetX;
		this.offsetY = offsetY;
		this.offsetZ = offsetZ;
		this.extra = extra;
		this.data = data;
	}

	public ParticleData(Particle particle, int count, double offsetX, double offsetY, double offsetZ, double extra, Object data, boolean force) {
		this(particle);
		this.count = count;
		this.offsetX = offsetX;
		this.offsetY = offsetY;
		this.offsetZ = offsetZ;
		this.extra = extra;
		this.data = data;
		this.force = force;
	}

	public ParticleData setCount(int count) {
		this.count = count;
		return this;
	}

	public ParticleData setOffsets(double offsetX, double offsetY, double offsetZ) {
		this.offsetX = offsetX;
		this.offsetY = offsetY;
		this.offsetZ = offsetZ;
		return this;
	}

	public ParticleData setExtra(double extra) {
		this.extra = extra;
		return this;
	}

	public ParticleData setData(Object data) {
		this.data = data;
		return this;
	}

	public ParticleData setForce(boolean force) {
		this.force = force;
		return this;
	}

	public void spawnParticle(Location location) {
		location.getWorld().spawnParticle(particle, location, count, offsetX, offsetY, offsetZ, extra, data, force);
	}
}
