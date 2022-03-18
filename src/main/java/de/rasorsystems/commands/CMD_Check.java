

package de.rasorsystems.commands;

import de.rasorsystems.MuteAPI;
import de.rasorsystems.BanAPI;
import de.rasorsystems.Main;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;

public class CMD_Check extends Command
{
    public CMD_Check(final String name) {
        super(name);
    }
    
    public void execute(final CommandSender commandSender, final String[] strings) {
        if (commandSender.hasPermission("rasorsystems.command.check")) {
            if (strings.length != 1) {
                commandSender.sendMessage(Main.getInstance().getConfiguration().getString("prefix").replace("&", "§").replace("&", "§") + Main.getInstance().getConfiguration().getString("checkusage").replace("&", "§"));
            }
            else {
                final String playername = strings[0];
                commandSender.sendMessage(Main.getInstance().getConfiguration().getString("check-1").replace("&", "§"));
                commandSender.sendMessage(Main.getInstance().getConfiguration().getString("checkisban").replace("&", "§").replace("%name%", playername).replace("%banned%", ((BanAPI.isPermBanned(CMD_Unban.loadUUID(playername)) || BanAPI.isTempBanned(CMD_Unban.loadUUID(playername))) ? "§aYes!" : "§cNo!")).replace("%muted%", ((MuteAPI.isPermMuted(CMD_Unban.loadUUID(playername)) || MuteAPI.isTempMuted(CMD_Unban.loadUUID(playername))) ? "§aYes!" : "§cNo!")));
                commandSender.sendMessage(Main.getInstance().getConfiguration().getString("checkismute").replace("&", "§").replace("%name%", playername).replace("%banned%", ((BanAPI.isPermBanned(CMD_Unban.loadUUID(playername)) || BanAPI.isTempBanned(CMD_Unban.loadUUID(playername))) ? "§aYes!" : "§cNo!")).replace("%muted%", ((MuteAPI.isPermMuted(CMD_Unban.loadUUID(playername)) || MuteAPI.isTempMuted(CMD_Unban.loadUUID(playername))) ? "§aYes!" : "§cNo!")));
                if (BanAPI.isTempBanned(CMD_Unban.loadUUID(playername)) || BanAPI.isPermBanned(CMD_Unban.loadUUID(playername))) {
                    commandSender.sendMessage(Main.getInstance().getConfiguration().getString("checkisbannedreason").replace("&", "§").replace("%reason%", BanAPI.getReason(CMD_Unban.loadUUID(playername))).replace("%weeks%", BanAPI.getRaimingTimeSep(CMD_Unban.loadUUID(playername), "weeks")).replace("%days%", BanAPI.getRaimingTimeSep(CMD_Unban.loadUUID(playername), "days")).replace("%minutes%", BanAPI.getRaimingTimeSep(CMD_Unban.loadUUID(playername), "minutes")).replace("%secounds%", BanAPI.getRaimingTimeSep(CMD_Unban.loadUUID(playername), "secounds")));
                    commandSender.sendMessage(Main.getInstance().getConfiguration().getString("checkisbannedtime").replace("&", "§").replace("%reason%", BanAPI.getReason(CMD_Unban.loadUUID(playername))).replace("%weeks%", BanAPI.getRaimingTimeSep(CMD_Unban.loadUUID(playername), "weeks")).replace("%days%", BanAPI.getRaimingTimeSep(CMD_Unban.loadUUID(playername), "days")).replace("%minutes%", BanAPI.getRaimingTimeSep(CMD_Unban.loadUUID(playername), "minutes")).replace("%secounds%", BanAPI.getRaimingTimeSep(CMD_Unban.loadUUID(playername), "secounds")));
                }
                if (MuteAPI.isTempMuted(CMD_Unban.loadUUID(playername)) || MuteAPI.isPermMuted(CMD_Unban.loadUUID(playername))) {
                    commandSender.sendMessage(Main.getInstance().getConfiguration().getString("checkismutedreason").replace("&", "§").replace("%reason%", MuteAPI.getReason(CMD_Unban.loadUUID(playername))).replace("%weeks%", MuteAPI.getRaimingTimeSep(CMD_Unban.loadUUID(playername), "weeks")).replace("%days%", MuteAPI.getRaimingTimeSep(CMD_Unban.loadUUID(playername), "days")).replace("%minutes%", MuteAPI.getRaimingTimeSep(CMD_Unban.loadUUID(playername), "minutes")).replace("%secounds%", MuteAPI.getRaimingTimeSep(CMD_Unban.loadUUID(playername), "secounds")));
                    commandSender.sendMessage(Main.getInstance().getConfiguration().getString("checkismutedtime").replace("&", "§").replace("%reason%", MuteAPI.getReason(CMD_Unban.loadUUID(playername))).replace("%weeks%", MuteAPI.getRaimingTimeSep(CMD_Unban.loadUUID(playername), "weeks")).replace("%days%", MuteAPI.getRaimingTimeSep(CMD_Unban.loadUUID(playername), "days")).replace("%minutes%", MuteAPI.getRaimingTimeSep(CMD_Unban.loadUUID(playername), "minutes")).replace("%secounds%", MuteAPI.getRaimingTimeSep(CMD_Unban.loadUUID(playername), "secounds")));
                }
                commandSender.sendMessage(Main.getInstance().getConfiguration().getString("check-1").replace("&", "§"));
            }
        }
        else {
            commandSender.sendMessage(Main.getInstance().getConfiguration().getString("prefix").replace("&", "§").replace("&", "§") + Main.getInstance().getConfiguration().getString("noperm").replace("&", "§"));
        }
    }
}
