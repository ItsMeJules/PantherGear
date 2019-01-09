package net.pantherhq.panthergear;

import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import net.pantherhq.panthergear.commands.PantherGearCommand;
import net.pantherhq.panthergear.items.CobblePopper;
import net.pantherhq.panthergear.items.EggBomb;
import net.pantherhq.panthergear.items.FireWand;
import net.pantherhq.panthergear.items.GrapplingHook;
import net.pantherhq.panthergear.items.utils.GearItem;
import net.pantherhq.panthergear.items.utils.GearItemType;
import net.pantherhq.panthergear.listeners.GearItemListener;

public class PantherGear extends JavaPlugin {
	
	private static PantherGear instance;
	
	private static EggBomb eggBomb;
	private static CobblePopper cobblePopper;
	
	static {
		eggBomb = new EggBomb();
		cobblePopper = new CobblePopper();
	}
	
	@Override
	public void onEnable() {
		instance = this;
		registerListeners();
		registerCommands();
	}

	@Override
	public void onDisable() {
	}

	
	private void registerListeners() {
		PluginManager pm = Bukkit.getPluginManager();
		
		pm.registerEvents(new GearItemListener(), this);
	}
	
	private void registerCommands() {
		getCommand("panthergear").setExecutor(new PantherGearCommand());
	}
	
	/**
	 * @return {@link PantherGear} class instance
	 */
	public static PantherGear getInstance() {
		return instance;
	}
	
	
	/**
	 * This method gets the {@link GearItem} from a given {@link ItemStack}.
	 * <p>
	 * The classes which doesn't implement {@link Usable} have a static reference in the main plugin class.<br>
	 * Why ? Because you don't need to instantiate them every time as you don't have to apply certain actions to the item (they're all the same).
	 * 
	 * @param stack ItemStack to parse in a GearItem.
	 * @return a specific GearItem or <tt>null</tt> if this is not a GearItem.
	 */
	public static GearItem getGearItem(ItemStack stack) {
		if(stack != null) {
			ItemMeta meta = stack.getItemMeta();
			
			if(meta.hasDisplayName()) {
				String name = meta.getDisplayName();
				
				if(name.equalsIgnoreCase(GrapplingHook.NAME))
					return GrapplingHook.fromStack(stack, true);
				else if(name.equalsIgnoreCase(FireWand.NAME))
					return FireWand.fromStack(stack, true);
				else if(name.equalsIgnoreCase(EggBomb.NAME))
					return eggBomb;
				else return null;
			}
		}
		return null;
	}
	
	/**
	 * Same as {@link #getGearItem(ItemStack)} but with the {@link GearItemType} as a parameter
	 * 
	 * @param type GearItemType to parse in a GearItem.
	 * @return a specific GearItem or <tt>null</tt> if this is not a GearItem.
	 */
	public static GearItem getGearItem(GearItemType type) {
		switch(type) {
		case GRAPPLING_HOOK:
			return new GrapplingHook(GrapplingHook.MAX_USES);
		case FIRE_WAND:
			return new FireWand(FireWand.MAX_USES);
		case EGG_BOMB:
			return eggBomb;
		case COBBLE_POPPER:
			return cobblePopper;
		default:
			return null;
		}
	}
	
	
}
