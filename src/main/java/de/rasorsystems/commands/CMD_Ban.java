

package de.rasorsystems.commands;

import de.rasorsystems.Main;
import de.rasorsystems.BanAPI;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;

public class CMD_Ban extends Command
{
    public CMD_Ban() {
        super("ban");
    }
    
    public static boolean isInt(final String s) {
        try {
            Integer.parseInt(s);
        }
        catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }
    
    public void execute(final CommandSender commandSender, final String[] strings) {
        if (commandSender.hasPermission("rasorsystem.command.ban")) {
            if (strings.length == 2) {
                if (BanAPI.isTempBanned(CMD_Unban.loadUUID(strings[0])) || BanAPI.isPermBanned(CMD_Unban.loadUUID(strings[0]))) {
                    commandSender.sendMessage(Main.getInstance().getConfiguration().getString("prefix").replace("&", "§") + Main.getInstance().getConfiguration().getString("allreadybanned") );
                }
                else if (isInt(strings[1])) {
                    final int ID = Integer.parseInt(strings[1]);
                    if (ID == 1) {
                        BanAPI.ban(CMD_Unban.loadUUID(strings[0]), strings[0], "BugUsing", 2419200L, false, commandSender);
                    }
                    else if (ID == 2) {
                        BanAPI.ban(CMD_Unban.loadUUID(strings[0]), strings[0], "Provocation", 259200L, false, commandSender);
                    }
                    else if (ID == 3) {
                        BanAPI.ban(CMD_Unban.loadUUID(strings[0]), strings[0], "Teaming", 1209600L, false, commandSender);
                    }
                    else if (ID == 4) {
                        BanAPI.ban(CMD_Unban.loadUUID(strings[0]), strings[0], "Hacking", 7776000L, false, commandSender);
                    }
                    else if (ID == 5) {
                        BanAPI.ban(CMD_Unban.loadUUID(strings[0]), strings[0], "Other", 604800L, false, commandSender);
                    }
                    else if (ID == 6) {
                        BanAPI.ban(CMD_Unban.loadUUID(strings[0]), strings[0], "Skin/Name/Builing", 1209600L, false, commandSender);
                    }
                    else if (ID == 7) {
                        BanAPI.ban(CMD_Unban.loadUUID(strings[0]), strings[0], "Stats Push", 1296000L, false, commandSender);
                    }
                    else if (ID == 8) {
                        if (commandSender.hasPermission("bansystem.command.ban.id8")) {
                            BanAPI.ban(CMD_Unban.loadUUID(strings[0]), strings[0], "House ban", 1L, true, commandSender);
                        }
                        else {
                            commandSender.sendMessage(Main.getInstance().getConfiguration().getString("prefix").replace("&", "§") + Main.getInstance().getConfiguration().getString("noperm").replace("&", "§"));;
                        }
                    }
                    else if (ID == 9) {
                        BanAPI.ban(CMD_Unban.loadUUID(strings[0]), strings[0], "Advertising", 1L, true, commandSender);
                    }
                }
                else {
                    commandSender.sendMessage(Main.getInstance().getConfiguration().getString("prefix").replace("&", "§") + Main.getInstance().getConfiguration().getString("novalidId").replace("&", "§"));
                }
            }
            else if (strings.length != 2) {
                commandSender.sendMessage("§8§m--------§8[§b§lBann§8]§8§m--------");
                commandSender.sendMessage("");
                commandSender.sendMessage("§cUsing: /ban <Player> <ID>");
                commandSender.sendMessage("");
                commandSender.sendMessage("§bID  §eReason      §6Time");
                commandSender.sendMessage("§b1  §cBugUsing  §61 Month");
                commandSender.sendMessage("§b2  §cProvocation  §63 Days");
                commandSender.sendMessage("§b3  §cTeaming  §614 Days");
                commandSender.sendMessage("§b4  §cHacking  §63 Month");
                commandSender.sendMessage("§b5  §cOther  §67 Days");
                commandSender.sendMessage("§b6  §cSkin/Name/Builing §614 Days");
                commandSender.sendMessage("§b7  §cStats Push §615 Days");
                commandSender.sendMessage("§b8  §cHouse ban  §4§lPermanently");
                commandSender.sendMessage("§b9  §cAdvertising  §4§lPermanently");
                commandSender.sendMessage("");
                commandSender.sendMessage("§8§m--------§8[§b§lBann§8]§8§m--------");
            }
        }
        else {
            commandSender.sendMessage(Main.getInstance().getConfiguration().getString("prefix").replace("&", "§") + Main.getInstance().getConfiguration().getString("noperm").replace("&", "§"));
        }
    }
}
