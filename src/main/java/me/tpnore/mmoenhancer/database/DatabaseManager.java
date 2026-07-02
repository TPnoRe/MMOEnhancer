package me.tpnore.mmoenhancer.database;

import me.tpnore.mmoenhancer.MMOEnhancer;
import java.io.File;
import java.sql.*;

/**
 * Manages database operations for MMOEnhancer
 * Uses SQLite for data persistence
 */
public class DatabaseManager {

    private final MMOEnhancer plugin;
    private Connection connection;
    private final String databasePath;

    public DatabaseManager(MMOEnhancer plugin) throws Exception {
        this.plugin = plugin;
        this.databasePath = plugin.getDataFolder().getAbsolutePath() + File.separator + "database.db";
        
        // Create data folder if it doesn't exist
        if (!plugin.getDataFolder().exists()) {
            plugin.getDataFolder().mkdirs();
        }
        
        // Initialize database connection
        initializeConnection();
        
        // Create tables if they don't exist
        createTables();
    }

    private void initializeConnection() throws Exception {
        Class.forName("org.sqlite.JDBC");
        connection = DriverManager.getConnection("jdbc:sqlite:" + databasePath);
        plugin.getPluginLogger().info("Database connection established!");
    }

    private void createTables() throws SQLException {
        String createPlayersTable = "CREATE TABLE IF NOT EXISTS players (" +
                "uuid TEXT PRIMARY KEY," +
                "name TEXT NOT NULL," +
                "level INTEGER DEFAULT 1," +
                "experience LONG DEFAULT 0," +
                "joined_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP," +
                "last_login TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
                ")";

        String createPlayerStatsTable = "CREATE TABLE IF NOT EXISTS player_stats (" +
                "uuid TEXT PRIMARY KEY," +
                "kills INTEGER DEFAULT 0," +
                "deaths INTEGER DEFAULT 0," +
                "playtime LONG DEFAULT 0," +
                "FOREIGN KEY(uuid) REFERENCES players(uuid) ON DELETE CASCADE" +
                ")";

        try (Statement stmt = connection.createStatement()) {
            stmt.executeUpdate(createPlayersTable);
            stmt.executeUpdate(createPlayerStatsTable);
            plugin.getPluginLogger().info("Database tables created/verified!");
        }
    }

    /**
     * Execute a query and return the result set
     * @param query SQL query
     * @return ResultSet from the query
     */
    public ResultSet executeQuery(String query) throws SQLException {
        Statement stmt = connection.createStatement();
        return stmt.executeQuery(query);
    }

    /**
     * Execute an update query
     * @param query SQL update query
     * @return Number of affected rows
     */
    public int executeUpdate(String query) throws SQLException {
        try (Statement stmt = connection.createStatement()) {
            return stmt.executeUpdate(query);
        }
    }

    /**
     * Get a prepared statement
     * @param query SQL query with placeholders
     * @return PreparedStatement
     */
    public PreparedStatement getPreparedStatement(String query) throws SQLException {
        return connection.prepareStatement(query);
    }

    /**
     * Close the database connection
     */
    public void close() throws Exception {
        if (connection != null && !connection.isClosed()) {
            connection.close();
            plugin.getPluginLogger().info("Database connection closed!");
        }
    }

    /**
     * Check if the database connection is active
     * @return true if connected, false otherwise
     */
    public boolean isConnected() {
        try {
            return connection != null && !connection.isClosed();
        } catch (SQLException e) {
            return false;
        }
    }
}
