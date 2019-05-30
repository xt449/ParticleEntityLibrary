package xt449.particleentitylibrary;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

import java.util.function.Consumer;

public class ParticleEntityLibrary extends JavaPlugin {

	/* TODO: Disabled Examples
    int particleIndex = 0;
	Particle particle = Particle.EXPLOSION_NORMAL;
	*/

	@Override
	public final void onEnable() {
		/* TODO: Disabled Examples
	    getCommand("pel").setExecutor((sender, command, label, args) -> {
			if(args.length > 0) {
				try {
					int num = Integer.parseInt(args[0]);
					this.particle = Particle.values()[num];
					particleIndex = Arrays.binarySearch(Particle.values(), particle);
					return true;
				} catch(NumberFormatException exc) {
					//
				}

				try {
					this.particle = Particle.valueOf(args[0]);
					particleIndex = Arrays.binarySearch(Particle.values(), particle);
					return true;
				} catch(IllegalArgumentException exc) {
					//
				}
			} else {
				sender.sendMessage(Arrays.toString(Particle.values()));
				return true;
			}
			return false;
		});
		*/

		AbstractParticleProjectile.register(this);

		/* TODO: Disabled Examples
		Bukkit.getPluginManager().registerEvents(this, this);
		*/
	}

	@Override
	public final void onDisable() {
		Bukkit.getScheduler().cancelTasks(this);
	}

	/* TODO: Disabled Examples
    @EventHandler(priority = EventPriority.HIGH)
	protected final void onPlayerInteract(PlayerInteractEvent event) {
		final Material material = event.getMaterial();
		//if(!material.equals(Material.AIR)) {
		final Action action = event.getAction();
		final Player player = event.getPlayer();
		if(action.equals(Action.RIGHT_CLICK_AIR) || action.equals(Action.RIGHT_CLICK_BLOCK)) {
			if(material == Material.BLAZE_POWDER) {
				new FlameThrowerParticle().summon(player.getEyeLocation(), player.getLocation().getDirection());

				//Guardian guardianEntity = player.getWorld().spawn(player.getLocation(), Guardian.class);
				//guardianEntity.setAI(false);
				//guardianEntity.setInvulnerable(true);
				//guardianEntity.setTarget(null);
				//guardianEntity.setTarget(player);

				//player.getWorld().playSound(player.getLocation(), Sound.WEATHER_RAIN_ABOVE, 0.09F, 2.0F);
				//player.getWorld().playSound(player.getLocation(), Sound.WEATHER_RAIN, 0.09F, 2.0F);
				player.getWorld().playSound(player.getLocation(), Sound.ENTITY_WITHER_SHOOT, 0.04F, 1.4F);
			} else if(material == Material.EMERALD) {
				//RayTraceResult result = player.rayTraceBlocks(16 * 8, FluidCollisionMode.NEVER);
				final Location location = player.getEyeLocation();
				final Vector direction = location.getDirection().normalize().multiply(0.2F);
				final Location point = player.getEyeLocation();
				final World world = player.getWorld();
				do {
					point.add(direction);
					//world.getNearbyEntities(point, 0.2F, 0.2F, 0.2F, (entity) -> entity instanceof LivingEntity && entity.getType() != EntityType.PLAYER).forEach((entity) -> ((LivingEntity) entity).setHealth(0));
					world.spawnParticle(particle *//*Particle.VILLAGER_HAPPY*//*, point, 1, 0, 0, 0, player.getLevel());
				} while(point.distance(location) < 30 && point.getBlock().isPassable());
				//player.getWorld().playSound(player.getLocation(), Sound.BLOCK_ANVIL_PLACE, SoundCategory.PLAYERS, 0.1F, 2);
				//player.getWorld().playSound(point, Sound.ENTITY_DRAGON_FIREBALL_EXPLODE, SoundCategory.HOSTILE, 3.0F, 1);
				player.getWorld().spawnParticle(Particle.FLASH, point, 4, 0, 0, 0, 0);
				//player.getWorld().spawnParticle(Particle.SNEEZE, point, 32, 1, 1, 1, 0.05F);
				//> 16) {
				//}
				//if(res)
			}
		} else if(action.equals(Action.LEFT_CLICK_AIR) || action.equals(Action.LEFT_CLICK_BLOCK)) {
			if(material == Material.BOW) {
				final Arrow arrow = player.launchProjectile(Arrow.class);
				arrow.setCritical(true);
				arrow.setColor(Color.BLUE);
				arrow.setGlowing(true);
				new DotParticleProjectile().summon(player.getEyeLocation(), arrow.getVelocity(), AbstractParticleProjectile.DRAG_ARROW, AbstractParticleProjectile.GRAVITY_ARROW);
			}
		}
		//}
	}
	*/

	public static void particleRaycast(ParticleData particleData, Location origin, Vector direction, float range, Consumer<Location> onRay) {
		final Location point = origin.clone();
		do {
			point.add(direction);
			onRay.accept(point);
			particleData.spawnParticle(point);
		} while(point.distance(origin) < range && point.getBlock().isPassable());
	}

	public static void particleRaycast(ParticleData particleData, Location origin, Vector direction, float range, Consumer<Location> onRay, Consumer<Location> onHit, boolean rangeTrigger) {
		final Location point = origin.clone();
		do {
			point.add(direction);
			onRay.accept(point);
			particleData.spawnParticle(point);

			if(!point.getBlock().isPassable()) {
				rangeTrigger = true;
				break;
			}
		} while(point.distance(origin) < range);

		if(rangeTrigger) {
			onHit.accept(point);
		}
	}

	public static void particleRaycast(ParticleData particleData, Location origin, Vector direction, float range, Consumer<Location> onRay, boolean strictBoundingBoxes) {
		final Location point = origin.clone();
		System.out.println("debug -1");
		do {
			System.out.println("debug 0");
			point.add(direction);
			System.out.println("debug 1");
			onRay.accept(point);
			System.out.println("debug 2");
			particleData.spawnParticle(point);
			System.out.println("debug 3");

			if(!point.getBlock().isPassable()) {
				System.out.println("debug 4");
				if(strictBoundingBoxes) {
					System.out.println("debug 5");
					if(point.getBlock().getBoundingBox().contains(point.toVector())) {
						System.out.println("debug 6");
						break;
					}
					System.out.println("debug 7");
				}
				System.out.println("debug 8");
			}
			System.out.println("debug 9");
		} while(point.distance(origin) < range);
		System.out.println("debug 10");
	}

	public static void particleRaycast(ParticleData particleData, Location origin, Vector direction, float range, Consumer<Location> onRay, Consumer<Location> onHit, boolean rangeTrigger, boolean strictBoundingBoxes) {
		final Location point = origin.clone();
		do {
			point.add(direction);
			onRay.accept(point);
			particleData.spawnParticle(point);

			if(!point.getBlock().isPassable()) {
				if(strictBoundingBoxes) {
					if(point.getBlock().getBoundingBox().contains(point.toVector())) {
						rangeTrigger = true;
						break;
					}
				} else {
					rangeTrigger = true;
					break;
				}
			}
		} while(point.distance(origin) < range);

		if(rangeTrigger) {
			onHit.accept(point);
		}
	}
}
