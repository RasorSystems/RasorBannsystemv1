

package de.rasorsystems.commands;

import java.text.SimpleDateFormat;
import de.rasorsystems.Main;
import de.rasorsystems.MuteAPI;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;

public class CMD_Mute extends Command
{
    public CMD_Mute(final String name) {
        super(name);
    }
    
    public void execute(final CommandSender sender, final String[] args) {
        if (sender.hasPermission("rasorsystems.command.mute")) {
            if (args.length == 2) {
                if (MuteAPI.isTempMuted(CMD_Unban.loadUUID(args[0])) || MuteAPI.isPermMuted(CMD_Unban.loadUUID(args[0]))) {
                    sender.sendMessage(Main.getInstance().getConfiguration().getString("prefix").replace("&", "§") + Main.getInstance().getConfiguration().getString("allreadymuted").replace("&", "§"));
                }
                else if (CMD_Ban.isInt(args[1])) {
                    final int ID = Integer.parseInt(args[1]);
                    if (ID == 1) {
                        MuteAPI.mute(CMD_Unban.loadUUID(args[0]), args[0], "Spamming", 259200L, false);
                        sender.sendMessage(Main.getInstance().getConfiguration().getString("prefix").replace("&", "§") + Main.getInstance().getConfiguration().getString("muteplayer").replace("&", "§").replace("%player%", args[0]).replace("%reason%", MuteAPI.getReason(CMD_Unban.loadUUID(args[0]))).replace("%time%", new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(MuteAPI.getEnd(CMD_Unban.loadUUID(args[0])))));
                    }
                    else if (ID == 2) {
                        MuteAPI.mute(CMD_Unban.loadUUID(args[0]), args[0], "Provocation", 604800L, false);
                        sender.sendMessage(Main.getInstance().getConfiguration().getString("prefix").replace("&", "§") + Main.getInstance().getConfiguration().getString("muteplayer").replace("&", "§").replace("%player%", args[0]).replace("%reason%", MuteAPI.getReason(CMD_Unban.loadUUID(args[0]))).replace("%time%", new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(MuteAPI.getEnd(CMD_Unban.loadUUID(args[0])))));
                    }
                    else if (ID == 3) {
                        MuteAPI.mute(CMD_Unban.loadUUID(args[0]), args[0], "Chat behavior", 2592000L, false);
                        sender.sendMessage(Main.getInstance().getConfiguration().getString("prefix").replace("&", "§") + Main.getInstance().getConfiguration().getString("muteplayer").replace("&", "§").replace("%player%", args[0]).replace("%reason%", MuteAPI.getReason(CMD_Unban.loadUUID(args[0]))).replace("%time%", new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(MuteAPI.getEnd(CMD_Unban.loadUUID(args[0])))));
                    }
                    else if (ID == 4) {
                        MuteAPI.mute(CMD_Unban.loadUUID(args[0]), args[0], "Other", 1209600L, false);
                        sender.sendMessage(Main.getInstance().getConfiguration().getString("prefix").replace("&", "§") + Main.getInstance().getConfiguration().getString("muteplayer").replace("&", "§").replace("%player%", args[0]).replace("%reason%", MuteAPI.getReason(CMD_Unban.loadUUID(args[0]))).replace("%time%", new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(MuteAPI.getEnd(CMD_Unban.loadUUID(args[0])))));
                    }
                    else if (ID == 5) {
                        if (sender.hasPermission("rasorsystems.command.mute.id4")) {
                            MuteAPI.mute(CMD_Unban.loadUUID(args[0]), args[0], "Advertising", 1L, true);
                            sender.sendMessage(Main.getInstance().getConfiguration().getString("prefix").replace("&", "§") + Main.getInstance().getConfiguration().getString("permwmuteplayer").replace("&", "§").replace("%player%", args[0]).replace("%reason%", MuteAPI.getReason(CMD_Unban.loadUUID(args[0]))));
                        }
                        else {
                            sender.sendMessage(Main.getInstance().getConfiguration().getString("prefix").replace("&", "§") + Main.getInstance().getConfiguration().getString("noperm").replace("&", "§"));
                        }
                    }
                }else{
                    sender.sendMessage(Main.getInstance().getConfiguration().getString("prefix").replace("&", "§") + Main.getInstance().getConfiguration().getString("novalidId").replace("&", "§"));
                }
            }
            else if (args.length != 2) {
                sender.sendMessage("§8§m--------§8[§b§lMute§8]§8§m--------");
                sender.sendMessage("");
                sender.sendMessage("§cUsing: §6/mute <Player> <ID>");
                sender.sendMessage("");
                sender.sendMessage("§bID   §eReason      §6Time");
                sender.sendMessage("§b1  §cSpamming  §63 Days");
                sender.sendMessage("§b2  §cProvocation  §67 Days");
                sender.sendMessage("§b3  §cChat behavior  §630 Days");
                sender.sendMessage("§b4  §cOther  §614 Days");
                sender.sendMessage("§b5  §cAdvertising  §6Permanently");
                sender.sendMessage("");
                sender.sendMessage("§8§m--------§8[§b§lMute§8]§8§m--------");
            }
        }
        else {
            sender.sendMessage(Main.getInstance().getConfiguration().getString("prefix").replace("&", "§") + Main.getInstance().getConfiguration().getString("noperm").replace("&", "§"));
        }
    }
}
