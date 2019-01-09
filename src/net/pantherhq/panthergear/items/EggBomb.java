package net.pantherhq.panthergear.items;

import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.Event;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.projectiles.ProjectileSource;

import net.pantherhq.panthergear.PantherGear;
import net.pantherhq.panthergear.api.BetterItem;
import net.pantherhq.panthergear.items.utils.GearItem;
import net.pantherhq.panthergear.items.utils.GearItemType;

/**
 * This item when thrown spawns an explosion with a power of 4F.
 */
public class EggBomb extends GearItem {

	/**
	 * The egg bomb name "§2Egg bomb"
	 */
	public static final String NAME;
	
	/**
	 * The ItemStack (4 eggs with a damage of 0). 
	 */
	public static final ItemStack ITEM;
	
	static {
		NAME = "§2Egg bomb";
		ITEM = new BetterItem(Material.EGG, 4, 0, NAME).toItemStack();
	}
	
	public EggBomb(ItemStack item) {
		super(item);
	}

	public EggBomb() {
		this(ITEM);
	}
	
	@Override
	public GearItemType getType() {
		return GearItemType.EGG_BOMB;
	}

	@Override
	public boolean onLand(Event event) {
		if(event instanceof ProjectileHitEvent) {
			ProjectileHitEvent hitEvent = (ProjectileHitEvent) event;
			Entity entity = hitEvent.getEntity();
			
			if(entity.hasMetadata("JestizHandsomeAF"))
				return entity.getWorld().createExplosion(entity.getLocation(), 4F, true);
		}
		return false;
	}

	@SuppressWarnings("deprecation")
	@Override
	public boolean onRightClick(Event event) {
		if(event instanceof ProjectileLaunchEvent) {
			ProjectileLaunchEvent launchEvent = (ProjectileLaunchEvent) event;
			Projectile launched = launchEvent.getEntity();
			ProjectileSource source = launched.getShooter();

			if(source instanceof Player) {
				Player shooter = (Player) source;
				ItemStack hand = shooter.getItemInHand();
				
				if(hand != null) {
					ItemMeta meta = hand.getItemMeta();
					
					if(meta.hasDisplayName() && meta.getDisplayName().equalsIgnoreCase(NAME) && launched.getType() == EntityType.EGG) {
						launched.setMetadata("JestizHandsomeAF", new FixedMetadataValue(PantherGear.getInstance(), "l"));
						return true;
					}
				}
			}
			
		}
		return false;
	}

	@Override
	public boolean onLeftClick(Event event) {
		return true;
	}

}
