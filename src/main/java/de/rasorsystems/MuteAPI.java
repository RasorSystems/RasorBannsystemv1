
package de.rasorsystems;

import java.util.ArrayList;
import java.util.List;
import java.sql.ResultSet;
import java.sql.SQLException;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class MuteAPI
{
    public static void mute(final String uuid, final String playername, final String reason, final long seconds, final Boolean bool) {
        final ProxiedPlayer p = Main.getInstance().getProxy().getPlayer(playername);
        final long current = System.currentTimeMillis();
        final long millis = seconds * 1000L;
        final long end = current + millis;
        MySQL.update("INSERT INTO MutedPlayers (Playername, UUID, End, Reason, Permanent, Warns) VALUES('" + playername + "','" + uuid + "','" + end + "','" + reason + "','" + bool+ "','"+ 0 + "')");
        if (p != null) {
            if (!bool) {
                p.sendMessage(Main.getInstance().getConfiguration().getString("mute").replace("&", "§").replace("%reason%", getReason(uuid)));
            }
            else if (bool) {
                p.sendMessage(Main.getInstance().getConfiguration().getString("permmute").replace("&", "§").replace("%reason%", getReason(uuid)));
            }
        }
    }
    
    public static void unMute(final String uuid) {
        MySQL.update("DELETE FROM MutedPlayers WHERE UUID = '" + uuid + "'");
    }
    
    public static boolean isTempMuted(final String uuid) {
        final ResultSet rs = MySQL.getResult("SELECT * FROM MutedPlayers WHERE UUID = '" + uuid + "'");
        try {
            return rs.next();
        }
        catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public static Boolean isPermMuted(final String uuid) {
        final ResultSet rs = MySQL.getResult("SELECT Permanent FROM MutedPlayers WHERE UUID = '" + uuid + "'");
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
        final ResultSet rs = MySQL.getResult("SELECT Reason FROM MutedPlayers WHERE UUID = '" + uuid + "'");
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
        final ResultSet rs = MySQL.getResult("SELECT End FROM MutedPlayers WHERE UUID = '" + uuid + "'");
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
    
    public static List<String> getMutedPlayers() {
        final List<String> list = new ArrayList<String>();
        final ResultSet rs = MySQL.getResult("SELECT * FROM MutedPlayers");
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
        return String.valueOf(weeks) + " Woche(n) " + days + " Tag(e) " + hours + " Stunde(n) " + minutes + " Minute(n) " + seconds + " Sekunde(n)";
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
        }else if(time.equalsIgnoreCase("secounds")){
            return "" + seconds;
        }else{
            return null;
        }
    }
}
