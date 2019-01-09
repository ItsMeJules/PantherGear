package net.pantherhq.panthergear.items;

import java.util.Arrays;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.pantherhq.panthergear.api.BetterItem;
import net.pantherhq.panthergear.items.utils.GearItem;
import net.pantherhq.panthergear.items.utils.GearItemType;
import net.pantherhq.panthergear.items.utils.Usable;

/**
 * This item when right clicked shoots a blaze fire ball at the default speed.
 */
public class FireWand extends GearItem implements Usable {
	
	/**
	 * The fire wand name: <i>§cFire §6Wand</i>
	 */
	public static final String NAME;
	
	/**
	 * The fire wand lore: <i>§7Uses: %uses%/3</i>
	 */
	public static final String LORE;
	
	/**
	 * The fire wand max uses: <i>3</i>
	 */
	public static final int MAX_USES;
	
	/**
	 * The ItemStack (1 blaze rod with a damage of 0)
	 */
	public static final ItemStack ITEM;
	
	private int usesLeft;
	
	static {
		NAME = "§cFire §6Wand";
		LORE = "§7Uses: %uses%/3";
		MAX_USES = 3;
		ITEM = new BetterItem(Material.BLAZE_ROD, 1, 0, NAME, LORE.replace("%uses%", ""+MAX_USES)).toItemStack();
	}

	public FireWand(ItemStack item, int usesLeft) {
		super(item);
		this.usesLeft = usesLeft;
	}
	
	public FireWand(int usesLeft) {
		this(ITEM, usesLeft);
	}
	
	@Override
	public int getUsesLeft() {
		return usesLeft;
	}
	
	@Override
	public void setUses(int usesLeft) {
		this.usesLeft = usesLeft;
	}
	
	@Override
	public void removeUse() {
		usesLeft--;
	}
	
	@Override
	public void addUse() {
		usesLeft++;
	}

	@Override
	public void updateItem(Player player) {
		if(usesLeft > 0) {
			ItemMeta meta = item.getItemMeta();
			meta.setLore(Arrays.asList(LORE.replace("%uses%", ""+usesLeft)));
			item.setItemMeta(meta);	
		} else {
			player.setItemInHand(null);
			player.playSound(player.getLocation(), Sound.ITEM_BREAK, 1, 1);
		}
	}

	@Override
	public GearItemType getType() {
		return GearItemType.FIRE_WAND;
	}

	@Override
	public boolean onLand(Event event) {
		return true;
	}

	@Override
	public boolean onRightClick(Event event) {
		if(event instanceof PlayerInteractEvent) {
			PlayerInteractEvent interactEvent = (PlayerInteractEvent) event;
			
			interactEvent.getPlayer().launchProjectile(Fireball.class);
			usesLeft--;
			return true;
		}
		return false;
	}

	@Override
	public boolean onLeftClick(Event event) { //TODO: Shoots faster fireball (if possible) / multi fire ball
		return true;
	}

	public static GearItem fromStack(ItemStack stack, boolean nameChecked) {
		ItemMeta meta = stack.getItemMeta();
		
		if(nameChecked || meta.hasDisplayName() && meta.getDisplayName().equalsIgnoreCase(NAME)) {
			if(meta.hasLore()) {
				String line = meta.getLore().get(0);
				
				if(line.startsWith(LORE.split(":")[0]))
					return new FireWand(stack, Integer.parseInt(line.split(":")[1].split("/")[0].trim()));
			}
		}
		
		return null;
	}

}
