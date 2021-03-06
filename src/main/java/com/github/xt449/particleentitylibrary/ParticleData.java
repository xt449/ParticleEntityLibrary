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

import org.bukkit.*;
import org.bukkit.block.data.BlockData;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;

import java.util.HashMap;
import java.util.Map;

/**
 * @author xt449
 */
public class ParticleData implements ConfigurationSerializable {

	static {
		registerSerialization();
	}

	public static void registerSerialization() {
		ConfigurationSerialization.registerClass(ParticleData.class, "ParticleData");
		ConfigurationSerialization.registerClass(ParticleData.Data.class, "ParticleData.Data");
	}

	public static final class Data implements ConfigurationSerializable {
		Particle.DustOptions dustOptions;
		ItemStack itemStack;
		BlockData blockData;

		public Data() {

		}

		public Data(Particle.DustOptions dustOptions) {
			this.dustOptions = dustOptions;
		}

		public Data(ItemStack itemStack) {
			this.itemStack = itemStack;
		}

		public Data(BlockData blockData) {
			this.blockData = blockData;
		}

		public Object resolve() {
			if(dustOptions != null) {
				return dustOptions;
			}
			if(itemStack != null) {
				return itemStack;
			}
			if(blockData != null) {
				return blockData;
			}
			return null;
		}

		private void setDustOptions(Particle.DustOptions dustOptions) {
			this.dustOptions = dustOptions;
			this.itemStack = null;
			this.blockData = null;
		}

		private void setItemStack(ItemStack itemStack) {
			this.dustOptions = null;
			this.itemStack = itemStack;
			this.blockData = null;
		}

		private void setBlockData(BlockData blockData) {
			this.dustOptions = null;
			this.itemStack = null;
			this.blockData = blockData;
		}

		private void setMaterialData(MaterialData materialData) {
			this.dustOptions = null;
			this.itemStack = null;
			this.blockData = null;
		}

		@Override
		public Map<String, Object> serialize() {
			final Map<String, Object> map = new HashMap<>();
			if(dustOptions != null) {
				map.put("type", Particle.DustOptions.class.getName());
				map.put("color", dustOptions.getColor());
				map.put("size", dustOptions.getSize());
			} else if(itemStack != null) {
				map.put("type", ItemStack.class.getName());
				map.put("material", itemStack.getType().name());
				map.put("amount", itemStack.getAmount());
				//map.put("durability", itemStack.getDurability());
			} else if(blockData != null) {
				map.put("type", BlockData.class.getName());
				map.put("material", blockData.getMaterial().name());
			}

			return map;
		}

		public static Data deserialize(final Map<String, Object> map) {
			final String type = (String) map.get("type");
			if(type != null) {
				if(type.equals(Particle.DustOptions.class.getName())) {
					return new Data(new Particle.DustOptions(
							(Color) map.get("color"),
							(float) (double) map.get("size")
					));
				} else if(type.equals(ItemStack.class.getName())) {
					return new Data(new ItemStack(
							Material.valueOf((String) map.get("material")),
							(int) map.get("amount")
							//,(short) (int) map.get("durability")
					));
				} else if(type.equals(BlockData.class.getName())) {
					return new Data(Bukkit.createBlockData(
							Material.valueOf((String) map.get("material"))
					));
				}
			}
			return null;
		}
	}

	private Particle particle;
	private int count = 1;
	private double offsetX = 0;
	private double offsetY = 0;
	private double offsetZ = 0;
	private double extra = 0;
	private Data data = null;
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

	public ParticleData(Particle particle, int count, double offsetX, double offsetY, double offsetZ, double extra, Data data) {
		this(particle);
		this.count = count;
		this.offsetX = offsetX;
		this.offsetY = offsetY;
		this.offsetZ = offsetZ;
		this.extra = extra;
		this.data = data;
	}

	public ParticleData(Particle particle, int count, double offsetX, double offsetY, double offsetZ, double extra, Data data, boolean force) {
		this(particle);
		this.count = count;
		this.offsetX = offsetX;
		this.offsetY = offsetY;
		this.offsetZ = offsetZ;
		this.extra = extra;
		this.data = data;
		this.force = force;
	}

	public ParticleData setParticle(Particle particle) {
		this.particle = particle;
		return this;
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

	public ParticleData setData(Data data) {
		this.data = data;
		return this;
	}

	public ParticleData setDataDustOptions(Particle.DustOptions dustOptions) {
		if(data == null) {
			data = new Data();
		}
		this.data.setDustOptions(dustOptions);
		return this;
	}

	public ParticleData setDataItemStack(ItemStack itemStack) {
		if(data == null) {
			data = new Data();
		}
		this.data.setItemStack(itemStack);
		return this;
	}

	public ParticleData setDataBlockData(BlockData blockData) {
		if(data == null) {
			data = new Data();
		}
		this.data.setBlockData(blockData);
		return this;
	}

	public ParticleData setDataMaterialData(MaterialData materialData) {
		if(data == null) {
			data = new Data();
		}
		this.data.setMaterialData(materialData);
		return this;
	}

	public ParticleData setForce(boolean force) {
		this.force = force;
		return this;
	}

	public void spawnParticle(Location location) {
		final World world = location.getWorld();
		if(world != null) {
			world.spawnParticle(particle, location, count, offsetX, offsetY, offsetZ, extra, data != null ? data.resolve() : null, force);
		} else {
			throw new IllegalArgumentException("Parameter location must have a non-null world field");
		}
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
		if(data != null) {
			map.put("data", data);
		}
		map.put("force", force);
		return map;
	}

	public static final ParticleData deserialize(final Map<String, Object> map) {
		Data data = null;
		if(map.containsKey("data")) {
			try {
				data = (Data) map.get("data");
			} catch(Exception exc) {
				//exc.printStackTrace();
			}
		}
		return new ParticleData(
				Particle.valueOf((String) map.get("particle")),
				(int) map.get("count"),
				(double) map.get("offsetX"),
				(double) map.get("offsetY"),
				(double) map.get("offsetZ"),
				(double) map.get("extra"),
				data,
				(boolean) map.get("force")
		);
	}
}
