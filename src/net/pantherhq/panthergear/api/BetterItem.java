package net.pantherhq.panthergear.api;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import net.minecraft.util.com.google.common.collect.Lists;

public class BetterItem implements Cloneable {
	
	private String name;
	private List<String> lore;
	private ItemStack item;
	private Material material;
	private int amount, level;
	private short damage;
	private Enchantment enchantment;
	private OfflinePlayer offline;
	
	public BetterItem(Material material, int amount, int damage, String name, List<String> lore) {
		this.material = material;
		this.amount = amount;
		this.damage = (short) damage;
		this.name = name;
		this.lore = lore;
	}
	
	public BetterItem(int amount, String name, List<String> lore, OfflinePlayer offline) {
		this.name = name;
		this.offline = offline;
		this.amount = amount;
		this.lore = lore;
	}
	
	public BetterItem(Material material, int amount, int damage, String name, String... lore) {
		this(material, amount, damage, name, Lists.newArrayList(lore));
	}
	
	public BetterItem(Material material, int amount, int damage, String name) {
		this(material, amount, damage, name, Collections.emptyList());
	}
	
	public BetterItem(Material material, int amount, int damage) {
		this(material, amount, damage, null, "");
	}
	
	public BetterItem(Material material, int amount) {
		this(material, amount, 0, null, "");
	}
	
	public BetterItem(ItemStack item, String name, String... lore) {
		this(item.getType(), item.getAmount(), item.getDurability(), name, lore);
	}
	
	public BetterItem(ItemStack item, String name, List<String> lore) {
		this(item.getType(), item.getAmount(), item.getDurability(), name, lore);
	}
	
	public BetterItem setLore(String... lore) {
		this.lore = Arrays.asList(lore);
		return this;
	}
	
	public BetterItem setLore(List<String> lore) {
		this.lore = lore;
		return this;
	}
	
	public BetterItem addLore(String str) {
		if(lore == null) lore = new ArrayList<>();
		lore.add(str);
		return this;
	}
	
	public BetterItem removeLore(int index) {
		if(lore == null) return this;
		lore.remove(index);
		return this;
	}
	
	public BetterItem setName(String name) {
		this.name = name;
		return this;
	}
	
	public BetterItem addEnchant(Enchantment enchantment, int level) {
		this.enchantment = enchantment;
		this.level = level;
		return this;
	}
	
	public boolean hasLore() {
		return lore != null;
	}
	
	public boolean hasName() {
		return name != null;
	}
	
	public String getName() {
		return name;
	}
	
	public List<String> getLore() {
		return lore;
	}
	
	public ItemStack toItemStack() {
		ItemMeta meta = null;
		if(material == null && offline != null) {
			material = Material.SKULL_ITEM;
			damage = 3;
			SkullMeta sMeta = (SkullMeta) Bukkit.getItemFactory().getItemMeta(material);
			
			sMeta.setDisplayName(name);
			sMeta.setOwner(offline.getName());
			meta = sMeta;
		}
		
		item = new ItemStack(material, amount, damage);
		if(meta == null)
			meta = item.getItemMeta();
		
		if(name != null)
			meta.setDisplayName(name);
		if(lore != null)
			meta.setLore(lore);
		if(enchantment != null && level != 0)
			item.addEnchantment(enchantment, level);
		
		item.setItemMeta(meta);
		
		return item;
	}
	
	@Override
	public BetterItem clone() {
		BetterItem cloned = offline == null ? new BetterItem(material, amount, damage, name, lore) : new BetterItem(amount, name, lore, offline);
		
		cloned.addEnchant(enchantment, level);
		
		return cloned;
	}
	
}