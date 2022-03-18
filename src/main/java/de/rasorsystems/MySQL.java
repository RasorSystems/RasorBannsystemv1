
package de.rasorsystems;

import java.sql.PreparedStatement;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.DriverManager;
import java.sql.Connection;

public class MySQL
{
    public static String username;
    public static String password;
    public static String database;
    public static String host;
    public static String port;
    public static Connection con;
    
    public static void connect() {
        if (!isConnected()) {
            try {
                MySQL.con = DriverManager.getConnection("jdbc:mysql://" + MySQL.host + ":" + MySQL.port + "/" + MySQL.database + "?useJDBCCompliantTimezoneShift=true&&serverTimezone=UTC&&useUnicode=true&autoReconnect=true", MySQL.username, MySQL.password);
                Main.getInstance().getProxy().getConsole().sendMessage("§aDatabase is connected!");
                update("CREATE TABLE IF NOT EXISTS BannedPlayers(Playername VARCHAR(100), UUID VARCHAR(100), End VARCHAR(100), Reason VARCHAR(100), Permanent VARCHAR(100))");
                update("CREATE TABLE IF NOT EXISTS MutedPlayers(Playername VARCHAR(100), UUID VARCHAR(100), End VARCHAR(100), Reason VARCHAR(100), Permanent VARCHAR(100), Warns VARCHAR(100))");
                //update("CREATE TABLE IF NOT EXISTS Warns(Playername VARCHAR(100), UUID VARCHAR(100), Warns VARCHAR(100))");
            }
            catch (SQLException e) {
                Main.getInstance().getProxy().getConsole().sendMessage("§cThe connection to the database dosnt work, please show config file!");
            }
        }
    }
    
    public static void close() {
        if (isConnected()) {
            try {
                MySQL.con.close();
                Main.getInstance().getProxy().getConsole().sendMessage("§cDatabase closed!");
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    
    public static boolean isConnected() {
        return MySQL.con != null;
    }
    
    public static void update(final String qry) {
        if (isConnected()) {
            try {
                MySQL.con.createStatement().executeUpdate(qry);
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
        }
        else if (!isConnected()) {
            connect();
        }
    }
    
    public static ResultSet getResult(final String qry) {
        if (isConnected()) {
            try {
                return MySQL.con.createStatement().executeQuery(qry);
            }
            catch (SQLException e) {
                e.printStackTrace();
                return null;
            }
        }
        if (!isConnected()) {
            connect();
        }
        return null;
    }


    public static void register(ProxiedPlayer p) {
        try {
            PreparedStatement pr = getStatement("INSERT INTO Warns (Spielername, UUID, Warns) VALUES (?, ?, ?)");
            pr.setString(1, p.getName());
            pr.setString(2, p.getUniqueId().toString());
            pr.setLong(3, 0L);
            pr.setString(4, "");
            pr.setLong(5, 100L);
            pr.setLong(6, 0L);
            pr.executeUpdate();
            pr.close();
        } catch (SQLException var2) {
            var2.printStackTrace();
        }

    }

    public static PreparedStatement getStatement(String sql) {
        if (con != null) {
            try {
                return con.prepareStatement(sql);
            } catch (SQLException var2) {
                var2.printStackTrace();
            }
        }

        return null;
    }

    public static boolean isRegistered(ProxiedPlayer p) {
        try {
            PreparedStatement ps = getStatement("SELECT * FROM Warns WHERE UUID= ?");
            ps.setString(1, p.getUniqueId().toString());
            ResultSet rs = ps.executeQuery();
            boolean Playercoins = rs.next();
            rs.close();
            rs.close();
            return Playercoins;
        } catch (Exception var4) {
            var4.printStackTrace();
            return false;
        }
    }
    

    
    public static long getWarns(final ProxiedPlayer p) {
        try {
            final PreparedStatement ps = getStatement("SELECT * FROM Warns WHERE UUID= ?");
            ps.setString(1, p.getUniqueId().toString());
            final ResultSet rs = ps.executeQuery();
            rs.next();
            final long points = rs.getInt("Warns");
            rs.close();
            ps.close();
            return points;
        }
        catch (Exception var5) {
            var5.printStackTrace();
            return -1L;
        }
    }
    
    public static void addWarns(final ProxiedPlayer p, final int warns) {
        try {
            final PreparedStatement ps = getStatement("UPDATE Warns SET Warns= ? WHERE UUID= ?");
            ps.setInt(1, (int)(getWarns(p) + warns));
            ps.setString(2, p.getUniqueId().toString());
            ps.executeUpdate();
            ps.close();
        }
        catch (Exception var4) {
            var4.printStackTrace();
        }
    }
    
    public static void setWarns(final ProxiedPlayer p, final int warns) {
        try {
            final PreparedStatement ps = getStatement("UPDATE Warns SET Warns= ? WHERE UUID= ?");
            ps.setInt(1, warns);
            ps.setString(2, p.getUniqueId().toString());
            ps.executeUpdate();
            ps.close();
        }
        catch (Exception var4) {
            var4.printStackTrace();
        }
    }
    
    static {
        MySQL.username = Main.getInstance().getConfiguration().getString("user");
        MySQL.password = Main.getInstance().getConfiguration().getString("passwort");
        MySQL.database = Main.getInstance().getConfiguration().getString("database");
        MySQL.host = Main.getInstance().getConfiguration().getString("host");
        MySQL.port = Main.getInstance().getConfiguration().getString("port");
    }
}
