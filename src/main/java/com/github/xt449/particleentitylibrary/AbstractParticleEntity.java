/*
 * Copyright (c) 2020 xt449/BinaryBanana
 *
 * This file is part of ParticleEntityLibrary.
 *
 * ParticleEntityLibrary is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * ParticleEntityLibrary is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with ParticleEntityLibrary.  If not, see <https://www.gnu.org/licenses/>.
 *
 */

package com.github.xt449.particleentitylibrary;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.BoundingBox;
import org.bukkit.util.Vector;

import java.util.ArrayList;

/**
 * @author xt449
 */
public abstract class AbstractParticleEntity {

	// static

	public static final float DRAG_LIVING = 0.02F;
	public static final float DRAG_THROWN = 0.01F;
	public static final float DRAG_ARROW = 0.01F;
	public static final float DRAG_OTHER = 0.02F;
	public static final float DRAG_VEHICLE = 0.05F;
	public static final float DRAG_DEFAULT = 0.015F;

	public static final float GRAVITY_LIVING = 0.08F;
	public static final float GRAVITY_THROWN = 0.03F;
	public static final float GRAVITY_ARROW = 0.05F;
	public static final float GRAVITY_OTHER = 0.04F;
	public static final float GRAVITY_VEHICLE = 0.04F;
	public static final float GRAVITY_DEFAULT = 0.045F;

	private static final ArrayList<AbstractParticleEntity> activeEntities = new ArrayList<>();

	public static AbstractParticleEntity getActiveParticleEntity(int id) {
		return activeEntities.get(id);
	}

	private static boolean registered = false;
	private static int taskId = -1;

	public static boolean register(Plugin plugin) {
		return registered = -1 != Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, () -> new ArrayList<>(activeEntities).forEach(AbstractParticleEntity::tick), 100, 1);
		/*if(taskId == -1) {
			taskId = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, () -> new ArrayList<>(activeEntities).forEach(AbstractParticleEntity::tick), 100, 1);
		}

		return taskId != -1;*/
	}

	// fields

	public final int id;

	protected final ParticleData particleData;

	protected Location location;
	protected Vector velocity = new Vector();
	protected float drag = DRAG_DEFAULT;
	protected float gravity = GRAVITY_DEFAULT;
	protected BoundingBox boundingBox = null;

	// constructors

	protected AbstractParticleEntity(ParticleData particleData, Location location) {
		this.id = activeEntities.size();
		this.particleData = particleData;
		this.location = location;

		activeEntities.add(this);
	}

	protected AbstractParticleEntity(ParticleData particleData, Location location, Vector velocity) {
		this(particleData, location);
		this.velocity = velocity;
	}

	@Deprecated
	protected AbstractParticleEntity(ParticleData particleData, Location location, Vector velocity, float drag) {
		this(particleData, location);
		this.velocity = velocity;
		this.drag = drag;
	}

	protected AbstractParticleEntity(ParticleData particleData, Location location, Vector velocity, float drag, float gravity) {
		this(particleData, location);
		this.velocity = velocity;
		this.drag = drag;
		this.gravity = gravity;
	}

	@Deprecated
	protected AbstractParticleEntity(ParticleData particleData, Location location, float drag) {
		this(particleData, location);
		this.drag = drag;
	}

	protected AbstractParticleEntity(ParticleData particleData, Location location, float drag, float gravity) {
		this(particleData, location);
		this.drag = drag;
		this.gravity = gravity;
	}

	// summon

	public final void summon() {
		onSummon();
	}

	public final void summon(Location location) {
		this.location = location;

		summon();
	}

	public final void summon(Location location, Vector velocity) {
		this.location = location;
		this.velocity = velocity;

		summon();
	}

	@Deprecated
	public final void summon(Location location, Vector velocity, float drag) {
		this.location = location;
		this.velocity = velocity;
		this.drag = drag;

		summon();
	}

	public final void summon(Location location, Vector velocity, float drag, float gravity) {
		this.location = location;
		this.velocity = velocity;
		this.drag = drag;
		this.gravity = gravity;

		summon();
	}

	@Deprecated
	public final void summon(Location location, float drag) {
		this.location = location;
		this.drag = drag;

		summon();
	}

	public final void summon(Location location, float drag, float gravity) {
		this.location = location;
		this.drag = drag;
		this.gravity = gravity;

		summon();
	}

	public final void summon(Vector velocity) {
		this.velocity = velocity;

		summon();
	}

	@Deprecated
	public final void summon(Vector velocity, float drag) {
		this.velocity = velocity;
		this.drag = drag;

		summon();
	}

	public final void summon(Vector velocity, float drag, float gravity) {
		this.velocity = velocity;
		this.drag = drag;
		this.gravity = gravity;

		summon();
	}

	@Deprecated
	public final void summon(float drag) {
		this.drag = drag;

		summon();
	}

	public final void summon(float drag, float gravity) {
		this.drag = drag;
		this.gravity = gravity;

		summon();
	}

	// getters

	public final Location getLocation() {
		return location;
	}

	public final Vector getVelocity() {
		return velocity;
	}

	public final float getDrag() {
		return drag;
	}

	public final float getGravity() {
		return gravity;
	}

	// setters

	public final AbstractParticleEntity setLocation(Location location) {
		this.location = location;

		return this;
	}

	public final AbstractParticleEntity setVelocity(Vector velocity) {
		this.velocity = velocity;

		return this;
	}

	public final AbstractParticleEntity setDrag(float drag) {
		this.drag = drag;

		return this;
	}

	public final AbstractParticleEntity setGravity(float gravity) {
		this.gravity = gravity;

		return this;
	}

	// miscellaneous

	protected void tick() {
		onPreTick();

		particleData.spawnParticle(location);

		//if(!onGround()) {
		processMovementTick();
		//}

		onPostTick();
	}

	protected void processMovementTick() {
		/*location = */location.add(velocity);
		/*velocity = */velocity.multiply(1 - drag).subtract(new Vector(0, gravity, 0));
	}

	public final void destroy() {
		activeEntities.remove(this);
	}

	public final boolean onGround() {
		return (location.getBlock().getType() != Material.AIR /*|| getNextLocation(id).getBlock().getType() != Material.AIR*/);
	}

	// events

	protected abstract void onSummon();

	protected abstract void onPreTick();

	protected abstract void onPostTick();
}
