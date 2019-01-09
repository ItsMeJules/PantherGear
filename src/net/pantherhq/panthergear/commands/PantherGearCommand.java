package net.pantherhq.panthergear.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

import net.pantherhq.panthergear.PantherGear;
import net.pantherhq.panthergear.items.utils.GearItemType;
import net.pantherhq.panthergear.utils.Configuration;

public class PantherGearCommand implements CommandExecutor, TabCompleter {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(!(sender instanceof Player)) {
			sender.sendMessage(Configuration.ONLY_PLAYERS_MESSAGE);
			return false;
		}
		
		Player player = (Player) sender;
		
		if(args.length == 0) {
			player.sendMessage("§7Usage: /panthergear <item>");
			return false;
		} else if(args.length > 0) {
			GearItemType type = GearItemType.fromName(args[0]);
			
			if(type == null) {
				player.sendMessage("§cThe item "+args[0]+" doesn't exist!");
				return true;
			}
			
			player.getInventory().addItem(PantherGear.getGearItem(type).getItem());
			return true;
		}
		return false;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
		List<String> completions = new ArrayList<>(4);
		
		if(args.length == 1 && sender instanceof Player) {
			completions.addAll(Arrays.stream(GearItemType.values())
					.map(GearItemType::name)
					.filter(name -> StringUtil.startsWithIgnoreCase(name, args[0]))
					.collect(Collectors.toList()));
		}
		
		return completions;
	}

}
