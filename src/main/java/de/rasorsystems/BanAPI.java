

package de.rasorsystems;

import net.md_5.bungee.api.CommandSender;

import java.util.ArrayList;
import java.util.List;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

public class BanAPI
{
    public static void ban(final String uuid, final String playername, final String reason, final long seconds, final Boolean bool, final CommandSender sender) {
        final long current = System.currentTimeMillis();
        final long millis = seconds * 1000L;
        final long end = current + millis;
        MySQL.update("INSERT INTO BannedPlayers (Playername, UUID, End, Reason, Permanent) VALUES('" + playername + "','" + uuid + "','" + end + "','" + reason + "','" + bool + "')");
        long milli = end - current;
        int second = 0;
        int minutes = 0;
        int hours = 0;
        int days = 0;
        int weeks = 0;
        while (milli > 1000L) {
            milli -= 1000L;
            ++second;
        }
        while (second > 60) {
            second -= 60;
            ++minutes;
        }
        while (minutes > 60) {
            minutes -= 60;
            ++hours;
        }
        while (hours > 24) {
            hours -= 24;
            ++days;
        }
        while (days > 7) {
            days -= 7;
            ++weeks;
        }
        if (Main.getInstance().getProxy().getPlayer(playername) != null) {
            if (!bool) {
                Main.getInstance().getProxy().getPlayer(playername).disconnect(Main.getInstance().getConfiguration().getString("ban-1").replace("&", "§") + "\n\n" + Main.getInstance().getConfiguration().getString("ban").replace("&", "§") + "\n\n" + Main.getInstance().getConfiguration().getString("banreason").replace("&", "§").replace("%reason%", reason) + "\n\n" + Main.getInstance().getConfiguration().getString("bantime").replace("&", "§").replace("%weeks%", "" + weeks).replace("%hours%", "" + hours).replace("%days%", "" + days).replace("%minutes%", "" + minutes).replace("%secounds%", "" + second) + "\n\n" + Main.getInstance().getConfiguration().getString("banend").replace("&", "§") + "\n\n" + Main.getInstance().getConfiguration().getString("ban-1").replace("&", "§"));
                sender.sendMessage(Main.getInstance().getConfiguration().getString("banplayer").replace("&", "§").replace("%player%", playername).replace("%reason%", reason));
            }
            else if (bool) {
                Main.getInstance().getProxy().getPlayer(playername).disconnect(Main.getInstance().getConfiguration().getString("ban-1").replace("&", "§") + "\n\n" + Main.getInstance().getConfiguration().getString("permban").replace("&", "§") + "\n\n" + Main.getInstance().getConfiguration().getString("banreason").replace("&", "§").replace("%reason%", reason) + "\n\n" + Main.getInstance().getConfiguration().getString("banend").replace("&", "§") + "\n\n" + Main.getInstance().getConfiguration().getString("ban-1").replace("&", "§"));
                sender.sendMessage(Main.getInstance().getConfiguration().getString("banplayer").replace("&", "§").replace("%player%", playername).replace("%reason%", reason));
            }
        }
    }
    
    public static void unban(final String uuid) {
        MySQL.update("DELETE FROM BannedPlayers WHERE UUID = '" + uuid + "'");
    }
    
    public static boolean isTempBanned(final String uuid) {
        final ResultSet rs = MySQL.getResult("SELECT * FROM BannedPlayers WHERE UUID = '" + uuid + "'");
        try {
            return rs.next();
        }
        catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public static Boolean isPermBanned(final String uuid) {
        final ResultSet rs = MySQL.getResult("SELECT * FROM BannedPlayers WHERE UUID = '" + uuid + "'");
        try {
            if (rs.next()) {
                return rs.getBoolean("Permanent");
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public static String getReason(final String uuid) {
        final ResultSet rs = MySQL.getResult("SELECT * FROM BannedPlayers WHERE UUID = '" + uuid + "'");
        try {
            if (rs.next()) {
                return rs.getString("Reason");
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return "";
    }
    
    public static Long getEnd(final String uuid) {
        final ResultSet rs = MySQL.getResult("SELECT * FROM BannedPlayers WHERE UUID = '" + uuid + "'");
        try {
            if (rs.next()) {
                return rs.getLong("End");
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public static List<String> getBannedPlayers() {
        final List<String> list = new ArrayList<String>();
        final ResultSet rs = MySQL.getResult("SELECT * FROM BannedPlayers");
        try {
            while (rs.next()) {
                list.add(rs.getString("Playername"));
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    
    public static String getRaimingTime(final String uuid) {
        final long current = System.currentTimeMillis();
        final long end = getEnd(uuid);
        long millis = end - current;
        int seconds = 0;
        int minutes = 0;
        int hours = 0;
        int days = 0;
        int weeks = 0;
        while (millis > 1000L) {
            millis -= 1000L;
            ++seconds;
        }
        while (seconds > 60) {
            seconds -= 60;
            ++minutes;
        }
        while (minutes > 60) {
            minutes -= 60;
            ++hours;
        }
        while (hours > 24) {
            hours -= 24;
            ++days;
        }
        while (days > 7) {
            days -= 7;
            ++weeks;
        }
        return "§e" + weeks + " Woche(n) " + days + " Tag(e) " + hours + " Stunde(n) " + minutes + " Minute(n) " + seconds + " Sekunde(n)";
    }

    public static String getRaimingTimeSep(final String uuid, String time) {
        final long current = System.currentTimeMillis();
        final long end = getEnd(uuid);
        long millis = end - current;
        int seconds = 0;
        int minutes = 0;
        int hours = 0;
        int days = 0;
        int weeks = 0;
        while (millis > 1000L) {
            millis -= 1000L;
            ++seconds;
        }
        while (seconds > 60) {
            seconds -= 60;
            ++minutes;
        }
        while (minutes > 60) {
            minutes -= 60;
            ++hours;
        }
        while (hours > 24) {
            hours -= 24;
            ++days;
        }
        while (days > 7) {
            days -= 7;
            ++weeks;
        }
        if(time.equalsIgnoreCase("weeks")){
            return "" + weeks;
        }else if(time.equalsIgnoreCase("days")){
            return "" + days;
        }else if(time.equalsIgnoreCase("hours")){
            return "" + hours;
        }else if(time.equalsIgnoreCase("minutes")){
            return "" + minutes;
        }else if(time.equalsIgnoreCase("seconds")){
            return "" + seconds;
        }else{
            return null;
        }
    }
}
