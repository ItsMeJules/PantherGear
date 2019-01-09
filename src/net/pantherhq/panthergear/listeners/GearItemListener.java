package net.pantherhq.panthergear.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import net.pantherhq.panthergear.PantherGear;
import net.pantherhq.panthergear.items.GrapplingHook;
import net.pantherhq.panthergear.items.utils.GearItem;
import net.pantherhq.panthergear.items.utils.GearItemType;
import net.pantherhq.panthergear.items.utils.Usable;

public class GearItemListener implements Listener {
	
	@EventHandler
	public void onPlayerFish(PlayerFishEvent event) {
		Player player = event.getPlayer();
		ItemStack hand = player.getItemInHand();
		GearItem gearItem = PantherGear.getGearItem(hand);
		
		if(gearItem == null || !(gearItem instanceof GrapplingHook)) return;
		
		if(gearItem.onRightClick(event))
			((GrapplingHook) gearItem).updateItem(player);
	}
	
	@EventHandler
	public void onInteract(PlayerInteractEvent event) {
		String stringAction = event.getAction().toString();
		GearItem gearItem = PantherGear.getGearItem(event.getItem());
		Player player = event.getPlayer();
		
		if(gearItem == null) return;
		
		if(stringAction.contains("RIGHT")) {
			if(gearItem.onRightClick(event) && gearItem instanceof Usable)
				((Usable) gearItem).updateItem(player);
		} else if(stringAction.contains("LEFT")) {
			if(gearItem.onRightClick(event) && gearItem instanceof Usable)
				((Usable) gearItem).updateItem(player);
		}
	}
	
	@EventHandler
	public void onProjectileLaunch(ProjectileLaunchEvent event) {
		PantherGear.getGearItem(GearItemType.EGG_BOMB).onRightClick(event);
		PantherGear.getGearItem(GearItemType.COBBLE_POPPER).onRightClick(event);
	}
	
	@EventHandler
	public void onProjetileHit(ProjectileHitEvent event) {
		PantherGear.getGearItem(GearItemType.EGG_BOMB).onLand(event);
		PantherGear.getGearItem(GearItemType.COBBLE_POPPER).onLand(event);
	}
	
}
