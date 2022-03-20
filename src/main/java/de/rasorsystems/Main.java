

package de.rasorsystems;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.CopyOption;
import de.rasorsystems.commands.CMD_Kick;
import de.rasorsystems.commands.CMD_Mutelist;
import de.rasorsystems.commands.CMD_Mute;
import de.rasorsystems.commands.CMD_Check;
import de.rasorsystems.commands.CMD_Banlist;
import de.rasorsystems.commands.CMD_Unmute;
import de.rasorsystems.commands.CMD_Unban;
import de.rasorsystems.commands.CMD_Ban;
import net.md_5.bungee.api.plugin.Command;
import de.rasorsystems.commands.CMD_Wartungen;
import de.rasorsystems.listener.PlayerChatToProxy;
import de.rasorsystems.listener.ProxyPlayerJoinListener;
import net.md_5.bungee.api.plugin.Listener;
import de.rasorsystems.listener.ProxyPingListener;
import net.md_5.bungee.api.ProxyServer;
import java.io.IOException;
import java.io.File;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.api.plugin.Plugin;

public class Main extends Plugin
{
    private Configuration configuration;
    public static Main main;
    
    public void onEnable() {
        (Main.main = this).saveDefaultConfig();
        try {
            this.configuration = ConfigurationProvider.getProvider((Class)YamlConfiguration.class).load(new File(this.getDataFolder(), "config.yml"));
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        try {
            if(Licensesystem.checkLicense(getConfiguration().getString("licenskey")) == true){
                ProxyServer.getInstance().getConsole().sendMessage("§aLicense valid. Thanks for using our product!");
            }else{
                ProxyServer.getInstance().getConsole().sendMessage("§cLicense not valid! shutdown");
                ProxyServer.getInstance().stop();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        ProxyServer.getInstance().getPluginManager().registerListener((Plugin)this, (Listener)new ProxyPingListener(this));
        ProxyServer.getInstance().getPluginManager().registerListener((Plugin)this, (Listener)new ProxyPlayerJoinListener(this));
        ProxyServer.getInstance().getPluginManager().registerListener((Plugin)this, (Listener)new PlayerChatToProxy(this));
        ProxyServer.getInstance().getPluginManager().registerCommand((Plugin)this, (Command)new CMD_Wartungen());
        ProxyServer.getInstance().getPluginManager().registerCommand((Plugin)this, (Command)new CMD_Ban());
        ProxyServer.getInstance().getPluginManager().registerCommand((Plugin)this, (Command)new CMD_Unban("unban"));
        ProxyServer.getInstance().getPluginManager().registerCommand((Plugin)this, (Command)new CMD_Unmute("unmute"));
        ProxyServer.getInstance().getPluginManager().registerCommand((Plugin)this, (Command)new CMD_Banlist("banlist"));
        ProxyServer.getInstance().getPluginManager().registerCommand((Plugin)this, (Command)new CMD_Check("check"));
        ProxyServer.getInstance().getPluginManager().registerCommand((Plugin)this, (Command)new CMD_Mute("mute"));
        ProxyServer.getInstance().getPluginManager().registerCommand((Plugin)this, (Command)new CMD_Mutelist("mutelist"));
        ProxyServer.getInstance().getPluginManager().registerCommand((Plugin)this, (Command)new CMD_Kick("kick"));
        MySQL.connect();
        if(!MySQL.isConnected()){
            this.onDisable();
        }
    }
    
    public void onDisable() {
        MySQL.close();
    }
    
    private void saveDefaultConfig() {
        if (!this.getDataFolder().exists()) {
            this.getDataFolder().mkdir();
        }
        final File file = new File(this.getDataFolder(), "config.yml");
        if (!file.exists()) {
            try (final InputStream in = this.getResourceAsStream("config.yml")) {
                Files.copy(in, file.toPath(), new CopyOption[0]);
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    public Configuration getConfiguration() {
        return this.configuration;
    }
    
    public static Main getInstance() {
        return Main.main;
    }
}
