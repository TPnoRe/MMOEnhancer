# MMOEnhancer

A Minecraft Spigot plugin to enhance the MMO experience with various features.

## Features

- 🎮 Player level system
- 📊 Statistics tracking (kills, deaths, playtime)
- 💾 SQLite database integration
- 🔧 Command system
- 📝 Event listeners for player actions

## Requirements

- Java 17 or higher
- Spigot 1.21 or higher
- Maven (for building)

## Installation

1. Build the plugin:
```bash
mvn clean package
```

2. Copy the generated JAR file to your Spigot server's `plugins` folder

3. Start the server to generate the configuration files

4. Restart the server

## Commands

- `/mmo help` - Show help message
- `/mmo info` - Show plugin information
- `/mmo status` - Show your player status
- `/mmo reload` - Reload the plugin (Admin only)

## Permissions

- `mmo.admin` - Admin commands (default: OP)
- `mmo.user` - User commands (default: true)

## Configuration

Configuration files are located in the `plugins/MMOEnhancer` folder after first run.

## Database

The plugin uses SQLite for data persistence. The database file is stored at `plugins/MMOEnhancer/database.db`.

### Tables

- **players** - Player information (UUID, name, level, experience, etc.)
- **player_stats** - Player statistics (kills, deaths, playtime, etc.)

## Development

To build the plugin in development mode:

```bash
mvn clean install
```

## License

All rights reserved © 2026 TPnoRe

## Support

For issues and questions, visit: https://github.com/TPnoRe/MMOEnhancer