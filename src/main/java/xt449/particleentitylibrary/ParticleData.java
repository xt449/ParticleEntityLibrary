package xt449.particleentitylibrary;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.configuration.serialization.ConfigurationSerializable;

import java.util.HashMap;
import java.util.Map;

public class ParticleData implements ConfigurationSerializable {

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

	@Override
	public final Map<String, Object> serialize() {
		final Map<String, Object> map = new HashMap<>();
		map.put("particle", particle.name());
		map.put("count", count);
		map.put("offsetX", offsetX);
		map.put("offsetY", offsetY);
		map.put("offsetZ", offsetZ);
		map.put("extra", extra);
		map.put("data", data);
		map.put("force", force);
		return map;
	}

	public static final ParticleData deserialize(final Map<String, Object> map) {
		return new ParticleData(
			Particle.valueOf((String) map.get("particle")),
			(int) map.get("count"),
			(double) map.get("offsetX"),
			(double) map.get("offsetY"),
			(double) map.get("offsetZ"),
			(double) map.get("extra"),
			map.get("data"),
			(boolean) map.get("force")
		);
	}
}
