package net.pantherhq.panthergear.items.utils;

import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;

/**
 * This class must implement every items, it's mainly for polymorphism purposes.
 */
public abstract class GearItem {
	
	protected ItemStack item;
	
	public GearItem(ItemStack item) {
		this.item = item;
	}
	
	/**
	 * It returns the item set when instantiating this class.
	 * 
	 * @return the item used in this instance.
	 */
	public ItemStack getItem() {
		return item;
	}
	
	/**
	 * It returns the {@link GearItemType} which is set in the sub class.
	 * 
	 * @return the type of GearItem
	 */
	public abstract GearItemType getType();
	
	/**
	 * Executes an action when the item lands on the ground.
	 * 
	 * @param event The events that might be triggered by this action.
	 * @return true if the action has been successful.
	 * <br>false if the action failed.
	 */
	public abstract boolean onLand(Event event);
	
	/**
	 * Executes an action when the item is getting right clicked.
	 * 
	 * @param event The events that might be triggered by this action
	 * @return true if the action has been successful.
	 * <br>false if the action failed.
	 */
	public abstract boolean onRightClick(Event event);
	
	/**
	 * Executes an action when the item is getting left clicked.
	 * 
	 * @param event The events that might be triggered by this action
     * @return true if the action has been successful.
	 * <br>false if the action failed.
	 */
	public abstract boolean onLeftClick(Event event);
	
}
