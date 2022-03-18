

package de.rasorsystems.commands;

import java.util.List;
import java.text.SimpleDateFormat;
import de.rasorsystems.BanAPI;
import de.rasorsystems.Main;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;

public class CMD_Banlist extends Command
{
    public CMD_Banlist(final String name) {
        super(name);
    }
    
    public void execute(final CommandSender sender, final String[] args) {
        if (sender.hasPermission("rasorsystems.command.banlist")) {
            if (args.length != 0) {
                sender.sendMessage(Main.getInstance().getConfiguration().getString("prefix").replace("&", "§") + Main.getInstance().getConfiguration().getString("banlistusage").replace("&", "§"));
            }
            else if (args.length == 0) {
                final List<String> list = BanAPI.getBannedPlayers();
                if (list.size() == 0) {
                    sender.sendMessage(Main.getInstance().getConfiguration().getString("prefix").replace("&", "§").replace("&", "§") + Main.getInstance().getConfiguration().getString("noplayersbanned").replace("&", "§"));
                }else {
                    sender.sendMessage(Main.getInstance().getConfiguration().getString("banlist-1").replace("&", "§"));
                    for (final String allband : BanAPI.getBannedPlayers()) {
                        sender.sendMessage(Main.getInstance().getConfiguration().getString("banlist").replace("&", "§").replace("%player%", allband).replace("%reason%", BanAPI.getReason(CMD_Unban.loadUUID(allband))).replace("%time%", new SimpleDateFormat("dd.MM.yyyy HH:mm").format(BanAPI.getEnd(CMD_Unban.loadUUID(allband)))));
                    }
                    sender.sendMessage(Main.getInstance().getConfiguration().getString("banlist-1").replace("&", "§"));
                }
            }
        }
        else {
            sender.sendMessage(Main.getInstance().getConfiguration().getString("prefix").replace("&", "§") + Main.getInstance().getConfiguration().getString("noperm").replace("&", "§"));
        }
    }
}
