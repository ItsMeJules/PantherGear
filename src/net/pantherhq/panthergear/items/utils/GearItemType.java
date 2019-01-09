package net.pantherhq.panthergear.items.utils;

import net.pantherhq.panthergear.items.CobblePopper;
import net.pantherhq.panthergear.items.EggBomb;
import net.pantherhq.panthergear.items.FireWand;
import net.pantherhq.panthergear.items.GrapplingHook;

public enum GearItemType {

	/**
	 * A grappling hook {@link GrapplingHook}
	 */
	GRAPPLING_HOOK,
	
	/**
	 * A wand which throws fire ball {@link FireWand}
	 */
	FIRE_WAND,
	
	/**
	 * An egg which creates explosions when landing {@link EggBomb}
	 */
	EGG_BOMB,
	
	/**
	 * A snowball which spawns a cobble block when landing {@link CobblePopper}
	 */
	COBBLE_POPPER;

	public static GearItemType fromName(String name) {
		switch(name) {
		case "GRAPPLING_HOOK":
			return GRAPPLING_HOOK;
		case "FIRE_WAND":
			return FIRE_WAND;
		case "EGG_BOMB":
			return EGG_BOMB;
		case "COBBLE_POPPER":
			return COBBLE_POPPER;
		default:
			return null;
		}
	}
	
}
