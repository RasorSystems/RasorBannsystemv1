

package de.rasorsystems.commands;

import de.rasorsystems.BanAPI;
import de.rasorsystems.Main;
import net.md_5.bungee.api.CommandSender;
import java.util.UUID;
import com.google.gson.JsonObject;
import com.google.gson.JsonElement;
import java.net.URLConnection;
import java.io.IOException;
import java.net.MalformedURLException;
import java.io.Reader;
import java.io.InputStreamReader;
import java.io.InputStream;
import com.google.gson.JsonParser;
import java.net.URL;
import net.md_5.bungee.api.plugin.Command;

public class CMD_Unban extends Command
{
    private static final String API_URL = "https://api.mojang.com/users/profiles/minecraft/";
    
    public CMD_Unban(final String name) {
        super(name);
    }
    
    public static String loadUUID(final String playerName) {
        try {
            final URLConnection urlConnection = new URL("https://api.mojang.com/users/profiles/minecraft/" + playerName).openConnection();
            urlConnection.connect();
            final JsonParser jsonParser = new JsonParser();
            final JsonElement jsonElement = jsonParser.parse((Reader)new InputStreamReader((InputStream)urlConnection.getContent()));
            final JsonObject jsonObject = jsonElement.getAsJsonObject();
            System.out.println(getUniqueIdFromString(jsonObject.get("id").getAsString()));
            return jsonObject.get("id").getAsString().replaceFirst("(\\w{8})(\\w{4})(\\w{4})(\\w{4})(\\w{12})", "$1-$2-$3-$4-$5");
        }
        catch (MalformedURLException e) {
            e.printStackTrace();
        }
        catch (IOException e2) {
            e2.printStackTrace();
        }
        catch (Exception ex) {}
        return null;
    }
    
    private static UUID getUniqueIdFromString(final String uuid) {
        try {
            return UUID.fromString(uuid.replaceFirst("(\\w{8})(\\w{4})(\\w{4})(\\w{4})(\\w{12})", "$1-$2-$3-$4-$5"));
        }
        catch (Exception e) {
            return null;
        }
    }
    
    public void execute(final CommandSender commandSender, final String[] strings) {
        if (commandSender.hasPermission("rasorsystems.command.unban")) {
            if (strings.length != 1) {
                commandSender.sendMessage(Main.getInstance().getConfiguration().getString("prefix").replace("&", "§") + Main.getInstance().getConfiguration().getString("unbanusage").replace("&", "§"));
            }
            else {
                try {
                    if (BanAPI.isTempBanned(String.valueOf(loadUUID(strings[0])))) {
                        BanAPI.unban(loadUUID(strings[0]));
                        commandSender.sendMessage(Main.getInstance().getConfiguration().getString("prefix").replace("&", "§") + Main.getInstance().getConfiguration().getString("playerunbanned").replace("&", "§").replace("%player%", strings[0]));
                    }
                    else if (BanAPI.isPermBanned(String.valueOf(loadUUID(strings[0])))) {
                        BanAPI.unban(loadUUID(strings[0]));
                        commandSender.sendMessage(Main.getInstance().getConfiguration().getString("prefix").replace("&", "§") + Main.getInstance().getConfiguration().getString("playerunbanned").replace("&", "§").replace("%player%", strings[0]));
                    }
                    else {
                        commandSender.sendMessage(Main.getInstance().getConfiguration().getString("prefix").replace("&", "§") + Main.getInstance().getConfiguration().getString("notbanned").replace("&", "§"));
                    }
                }
                catch (Exception ex) {}
            }
        }
        else {
            commandSender.sendMessage(Main.getInstance().getConfiguration().getString("prefix").replace("&", "§") + Main.getInstance().getConfiguration().getString("noperm").replace("&", "§"));
        }
    }
}
