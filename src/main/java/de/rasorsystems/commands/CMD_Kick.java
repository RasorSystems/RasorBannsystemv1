
package de.rasorsystems.commands;

import de.rasorsystems.Main;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;

public class CMD_Kick extends Command
{
    public CMD_Kick(final String name) {
        super(name);
    }
    
    public void execute(final CommandSender sender, final String[] args) {
        if (sender instanceof ProxiedPlayer) {
            final ProxiedPlayer p = (ProxiedPlayer)sender;
            if (p.hasPermission("rasorsystems.command.kick")) {
                if (args.length <= 1) {
                    sender.sendMessage(Main.getInstance().getConfiguration().getString("prefix").replace("&", "§") + Main.getInstance().getConfiguration().getString("kickusage").replace("&", "§"));
                }
                else {
                    final ProxiedPlayer t = Main.getInstance().getProxy().getPlayer(args[0]);
                    if (t != null) {
                        final StringBuilder sb = new StringBuilder();
                        for (int i = 1; i < args.length; ++i) {
                            if (i != 0) {
                                sb.append(' ');
                            }
                            sb.append(args[i]);
                        }
                        final String grund = sb.toString();;
                        t.disconnect(Main.getInstance().getConfiguration().getString("kick-1").replace("&", "§") + "\n\n" + Main.getInstance().getConfiguration().getString("kick").replace("&", "§") + "\n\n" + Main.getInstance().getConfiguration().getString("kickreason").replace("%reason%", grund).replace("&", "§") + "\n\n" + Main.getInstance().getConfiguration().getString("kick-1").replace("&", "§"));;
                        p.sendMessage(Main.getInstance().getConfiguration().getString("prefix").replace("&", "§") + Main.getInstance().getConfiguration().getString("kickplayer").replace("%player%", t.getName()).replace("%reason%", grund));
                    }
                    else {
                        sender.sendMessage(Main.getInstance().getConfiguration().getString("prefix").replace("&", "§") + Main.getInstance().getConfiguration().getString("isntonline").replace("&", "§"));
                    }
                }
            }
            else {
                sender.sendMessage(Main.getInstance().getConfiguration().getString("prefix").replace("&", "§") + Main.getInstance().getConfiguration().getString("noperm").replace("&", "§"));
            }
        }
        else if (args.length <= 1) {
            sender.sendMessage(Main.getInstance().getConfiguration().getString("prefix").replace("&", "§") + Main.getInstance().getConfiguration().getString("kickusage").replace("&", "§"));;
        }
        else {
            final ProxiedPlayer t2 = Main.getInstance().getProxy().getPlayer(args[0]);
            if (t2 != null) {
                final StringBuilder sb2 = new StringBuilder();
                for (int j = 1; j < args.length; ++j) {
                    if (j != 0) {
                        sb2.append(' ');
                    }
                    sb2.append(args[j]);
                }
                final String grund2 = sb2.toString();
                t2.disconnect(Main.getInstance().getConfiguration().getString("prefix").replace("&", "§") + Main.getInstance().getConfiguration().getString("kick").replace("&", "§").replace("%reason%", grund2));;
                sender.sendMessage(Main.getInstance().getConfiguration().getString("prefix").replace("&", "§") + Main.getInstance().getConfiguration().getString("kickplayer").replace("%player%", t2.getName()).replace("%reason%", grund2));
            }
            else {
                sender.sendMessage(Main.getInstance().getConfiguration().getString("prefix").replace("&", "§") + Main.getInstance().getConfiguration().getString("isntonline").replace("&", "§"));;
            }
        }
    }
}
