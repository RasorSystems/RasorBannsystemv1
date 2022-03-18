

package de.rasorsystems.commands;

import java.io.IOException;
import java.io.File;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.ProxyServer;
import de.rasorsystems.Main;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;

public class CMD_Wartungen extends Command
{
    public CMD_Wartungen() {
        super("maintenance");
    }
    
    public void execute(final CommandSender commandSender, final String[] strings) {
        final ProxiedPlayer p = (ProxiedPlayer)commandSender;
        System.out.println("0");
        if (commandSender.hasPermission("rasorsystems.command.maintenance")) {
            System.out.println("1");
            if (strings.length == 1) {
                System.out.println("11");
                if (strings[0].equalsIgnoreCase("on")) {
                    System.out.println("111");
                    Main.getInstance().getConfiguration().set("maintenancestatus", (Object) true);
                    this.saveConfig();
                    p.sendMessage(Main.getInstance().getConfiguration().getString("prefix").replace("&", "§") + Main.getInstance().getConfiguration().getString("maintenanceactivated").replace("&", "§"));
                    for (final ProxiedPlayer pp : ProxyServer.getInstance().getPlayers()) {
                        if (!pp.hasPermission("rasorsystems.bypass.maintenance")) {
                            System.out.println("1111");
                            pp.disconnect((Main.getInstance().getConfiguration().getString("maintenancekick-1").replace("&", "§") + "\n\n" + Main.getInstance().getConfiguration().getString("maintenancekick").replace("&", "§")) + "\n\n" + Main.getInstance().getConfiguration().getString("maintenancekick-1").replace("&", "§"));
                        }
                    }
                } else if (strings[0].equalsIgnoreCase("off")) {
                    System.out.println("11111");
                    Main.getInstance().getConfiguration().set("maintenancestatus", (Object) false);
                    this.saveConfig();
                    p.sendMessage(Main.getInstance().getConfiguration().getString("prefix").replace("&", "§") + Main.getInstance().getConfiguration().getString("maintenancedeactivated").replace("&", "§"));
                } else {
                    System.out.println("nope kedfkdfm11111");
                    p.sendMessage(Main.getInstance().getConfiguration().getString(Main.getInstance().getConfiguration().getString("prefix").replace("&", "§") + Main.getInstance().getConfiguration().getString("maintenanceusage").replace("&", "§")));
                }
            }

            else {
                System.out.println("nope kedfkdfm22222");
                commandSender.sendMessage(Main.getInstance().getConfiguration().getString(Main.getInstance().getConfiguration().getString("prefix").replace("&", "§") + Main.getInstance().getConfiguration().getString("maintenanceusage").replace("&", "§")));

            }
        } else {
            System.out.println("nope kedfkdfm");
            commandSender.sendMessage(Main.getInstance().getConfiguration().getString(Main.getInstance().getConfiguration().getString("prefix").replace("&", "§") + Main.getInstance().getConfiguration().getString("noperm").replace("&", "§")));
        }
    }
    
    public void saveConfig() {
        try {
            ConfigurationProvider.getProvider((Class)YamlConfiguration.class).save(Main.getInstance().getConfiguration(), new File(Main.getInstance().getDataFolder(), "config.yml"));
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
