package net.pantherhq.panthergear.items;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.Vector;

import net.pantherhq.panthergear.PantherGear;
import net.pantherhq.panthergear.api.BetterItem;
import net.pantherhq.panthergear.items.utils.GearItem;
import net.pantherhq.panthergear.items.utils.GearItemType;
import net.pantherhq.panthergear.items.utils.Usable;

public class GrapplingHook extends GearItem implements Usable {
	
	/**
	 * The grappling hook name: <i>§9Grappling Hook</i>
	 */
	public static final String NAME;
	
	/**
	 * The grappling hook lore: <i>§7Uses: %uses%/5</i>
	 */
	public static final String LORE;
	
	/**
	 * The grappling hook max uses: <i>5</i>
	 */
	public static final int MAX_USES;
	
	/**
	 * The ItemStack (1 fishing rod with a damage of 0)
	 */
	public static final ItemStack ITEM;
	
	private int usesLeft;
	
	static {
		NAME = "§9Grappling Hook";
		LORE = "§7Uses: %uses%/5";
		MAX_USES = 5;
		ITEM = new BetterItem(Material.FISHING_ROD, 1, 0, NAME, LORE.replace("%uses%", ""+MAX_USES)).toItemStack();
	}

	public GrapplingHook(ItemStack item, int usesLeft) {
		super(item);
		this.usesLeft = usesLeft;
	}
	
	public GrapplingHook(int usesLeft) {
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
		Bukkit.getScheduler().runTask(PantherGear.getInstance(), () -> { //I have to run it on the next tick because it doesn't work on the current one.
			if(usesLeft > 0) {
				ItemMeta meta = item.getItemMeta();
				short maxDurability = item.getType().getMaxDurability();
				
				item.setDurability((short) (maxDurability - maxDurability / MAX_USES * usesLeft));
				meta.setLore(Arrays.asList(LORE.replace("%uses%", ""+usesLeft)));
				item.setItemMeta(meta);	
				player.setItemInHand(item);
			} else {
				player.setItemInHand(null);
				player.playSound(player.getLocation(), Sound.ITEM_BREAK, 1, 1);
			}
		});
	}

	@Override
	public GearItemType getType() {
		return GearItemType.GRAPPLING_HOOK;
	}
	
	@Override
	public boolean onLand(Event event) {
		return true;
	}

	@Override
	public boolean onRightClick(Event event) {
		if(event instanceof PlayerFishEvent) {
			PlayerFishEvent fishEvent = (PlayerFishEvent) event;
			
			switch(fishEvent.getState()) {
			case IN_GROUND:
				Player player = fishEvent.getPlayer();
				Vector propulsion = fishEvent.getHook().getLocation().toVector().subtract(player.getLocation().toVector()).add(new Vector(0, 0, 0));
				
				player.setVelocity(propulsion.multiply(0.17)); //Random value found by successive tests
				usesLeft--;
				return true;
			default:
				return false;
			}
		}
		return false;
	}

	@Override
	public boolean onLeftClick(Event event) { //TODO Augment the speed before right clicking.
		return true;
	}
	
	public static GrapplingHook fromStack(ItemStack stack, boolean nameChecked) {
		ItemMeta meta = stack.getItemMeta();
		
		if(nameChecked || meta.hasDisplayName() && meta.getDisplayName().equalsIgnoreCase(NAME)) {
			if(meta.hasLore()) {
				String line = meta.getLore().get(0);
				
				if(line.startsWith(LORE.split(":")[0]))
					return new GrapplingHook(stack, Integer.parseInt(line.split(":")[1].split("/")[0].trim()));
			}
		}
		
		return null;
	}

}
