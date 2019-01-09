package net.pantherhq.panthergear.items.utils;

import org.bukkit.entity.Player;

/** 
 * Interface which define if an item should break after a certain amount of uses.
 * 
 */
public interface Usable {

	/**
	 * Gets the uses left for the current item.
	 * 
	 * @return Uses left
	 */
	public int getUsesLeft();
	
	/**
	 * Sets the amount of uses left.
	 * 
	 * @param usesLeft The uses left to set.
	 */
	public void setUses(int usesLeft);
	
	/**
	 * Removes one use for the current item.
	 */
	public void removeUse();
	
	/**
	 * Adds one use for the current item.
	 */
	public void addUse();
	
	/**
	 * Applies the new values to the item.
	 * 
	 * @param player If the item has 0 uses it has to be removed from the player inventory.
	 */
	public void updateItem(Player player);
	
}
