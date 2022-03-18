

package de.rasorsystems.commands;

import de.rasorsystems.MuteAPI;
import de.rasorsystems.Main;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;

public class CMD_Unmute extends Command
{
    public CMD_Unmute(final String name) {
        super(name);
    }
    
    public void execute(final CommandSender sender, final String[] args) {
        if (sender.hasPermission("rasorsystems.command.unmute")) {
            if (args.length != 1) {
                sender.sendMessage(Main.getInstance().getConfiguration().getString("prefix").replace("&", "§") + Main.getInstance().getConfiguration().getString("unmuteusage").replace("&", "§"));
            }
            else if (MuteAPI.isTempMuted(CMD_Unban.loadUUID(args[0]))) {
                MuteAPI.unMute(CMD_Unban.loadUUID(args[0]));
                sender.sendMessage(Main.getInstance().getConfiguration().getString("prefix").replace("&", "§") + Main.getInstance().getConfiguration().getString("playerunmuted").replace("&", "§").replace("%player%", args[0]));
            }
            else if (MuteAPI.isPermMuted(CMD_Unban.loadUUID(args[0]))) {
                MuteAPI.unMute(CMD_Unban.loadUUID(args[0]));
                sender.sendMessage(Main.getInstance().getConfiguration().getString("prefix").replace("&", "§") + Main.getInstance().getConfiguration().getString("playerunmuted").replace("&", "§").replace("%player%", args[0]));
            }
            else {
                sender.sendMessage(Main.getInstance().getConfiguration().getString("prefix").replace("&", "§") + Main.getInstance().getConfiguration().getString("notmuted").replace("&", "§"));
            }
        }
        else {
            sender.sendMessage(Main.getInstance().getConfiguration().getString("prefix").replace("&", "§") + Main.getInstance().getConfiguration().getString("noperm").replace("&", "§"));
        }
    }
}
