# MMOEnhancer

MMOItems Utilities Plugin for Spigot - Similar to Goop

## Overview

MMOEnhancer is a powerful utility plugin for Spigot servers that provides comprehensive tools for managing and enhancing MMOItems. It offers features similar to the Goop plugin, allowing server administrators and advanced players to manipulate MMOItems with ease.

## Features

### 📊 Core Features (Like Goop)

- **Stat Editor** (`/mmoenhancer stat`) - Edit MMOItems stats in real time
- **MMOItems Converter** (`/mmoenhancer converter`) - Convert vanilla items to MMOItems
- **Fix Stacks** (`/mmoenhancer fixstacks`) - Update MMOItems to latest NBT format
- **Regenerate** (`/mmoenhancer regenerate`) - Force item NBT regeneration
- **Modifier Management** (`/mmoenhancer modifier`) - Add, remove, or reroll item modifiers
- **Gem Slot Management** (`/mmoenhancer gemslot`) - Manage gem slots on items
- **Tier Tools** (`/mmoenhancer tier`) - Get or set item tier
- **Advanced GUI** (`/mmoenhancer gui`) - Visual item editor interface

### 🔧 Additional Features

- Full YAML configuration support
- NBT data manipulation
- Item serialization/deserialization
- MythicMobs integration (soft dependency)
- MMOCore integration (soft dependency)
- Comprehensive command system
- Permission-based access control
- Advanced inventory GUI editor

## Requirements

- **Java 17** or higher
- **Spigot 1.21** or higher
- **MMOItems** plugin (required)
- **Maven** (for building)

### Optional Dependencies

- **MythicMobs** - For advanced mob/ability features
- **MMOCore** - For class/stat features

## Installation

1. **Download/Build the plugin:**
   ```bash
   git clone https://github.com/TPnoRe/MMOEnhancer.git
   cd MMOEnhancer
   mvn clean package
   ```

2. **Install dependencies:**
   - Download and install MMOItems plugin
   - (Optional) Download and install MythicMobs
   - (Optional) Download and install MMOCore

3. **Copy JAR to server:**
   ```bash
   cp target/mmoenhancer-0.0.1.jar /path/to/server/plugins/
   ```

4. **Start your server:**
   ```bash
   java -Xmx2G -jar spigot.jar nogui
   ```

## Commands

### Stat Command
```
/mmoenhancer stat [stat] [value]
Example: /mmoenhancer stat MAGIC_DAMAGE 50
```
Show or edit MMOItems stats in real time.

### Converter Command
```
/mmoenhancer converter <type> <id>
Example: /mmoenhancer converter SWORD BASIC_SWORD
```
Convert vanilla items to MMOItems.

### FixStacks Command
```
/mmoenhancer fixstacks
```
Update all MMOItems in inventory to latest NBT format.

### Regenerate Command
```
/mmoenhancer regenerate <player> [slot]
Example: /mmoenhancer regenerate Player1 hand
```
Force regenerate an item's NBT data.

### Modifier Command
```
/mmoenhancer modifier <action> [name] [value]
Actions: add, remove, reroll, list
Example: /mmoenhancer modifier add CRITICAL_STRIKE 10
```
Manage item modifiers.

### Gem Slot Command
```
/mmoenhancer gemslot <action> [amount]
Actions: add, remove, count, set
Example: /mmoenhancer gemslot add 3
```
Manage gem slots on items.

### Tier Command
```
/mmoenhancer tier [tier_name]
Example: /mmoenhancer tier legendary
```
Get or set the tier of MMOItems.
Available tiers: COMMON, UNCOMMON, RARE, EPIC, LEGENDARY, MYTHIC

### GUI Command
```
/mmoenhancer gui
```
Open the advanced item editor GUI interface.

## Permissions

- `mmoenhancer.admin` - Admin permission for all features (default: OP)
- `mmoenhancer.stat` - Use stat command (default: OP)
- `mmoenhancer.converter` - Use converter command (default: OP)
- `mmoenhancer.fixstacks` - Use fixstacks command (default: OP)
- `mmoenhancer.regenerate` - Use regenerate command (default: OP)
- `mmoenhancer.modifier` - Use modifier command (default: OP)
- `mmoenhancer.gemslot` - Use gemslot command (default: OP)
- `mmoenhancer.tier` - Use tier command (default: OP)
- `mmoenhancer.gui` - Use GUI command (default: OP)

## Configuration

Configuration files are located in `plugins/MMOEnhancer/` folder:

- `config.yml` - Main configuration file (YAML format)
- `storage/` - Directory for item storage (YAML format)

### Default config.yml
```yaml
debug: false
version: 0.0.1
settings:
  # Add your settings here
```

## GUI Features

The advanced item editor GUI provides:

- **Item Preview** - See the item you're editing
- **Stat Editor** - Quick stat editing
- **Modifier Manager** - Manage item modifiers
- **Gem Slot Manager** - Add/remove gem slots
- **Tier Setter** - Set item tier
- **NBT Regenerator** - Force NBT regeneration
- **Save/Cancel** - Save changes or cancel

## Storage

All item data is stored in YAML format at:
```
plugins/MMOEnhancer/storage/
```

Items are serialized using Bukkit's built-in serialization for compatibility.

## Development

To build the plugin in development mode:

```bash
mvn clean install
```

## Dependencies

- Spigot API 1.21
- MMOItems 6.9.3
- MythicMobs 5.0.0 (optional)
- MMOCore 3.9.8 (optional)
- Gson 2.10.1 (JSON support)
- SnakeYAML 2.0 (YAML support)

## Troubleshooting

### Plugin won't load
- Make sure you have MMOItems installed
- Check console for error messages
- Verify Java version is 17 or higher

### Commands not working
- Check if you have the correct permissions
- Ensure MMOItems is properly loaded
- Run `/mmoenhancer help` for available commands

### GUI not opening
- Verify you're holding an item
- Check for permissions
- Ensure the GUI command is available

## Support

For issues and questions:
- GitHub Issues: https://github.com/TPnoRe/MMOEnhancer/issues
- GitHub Wiki: https://github.com/TPnoRe/MMOEnhancer/wiki

## License

All rights reserved © 2026 TPnoRe

## Credits

- Inspired by Goop plugin by Gunging
- MMOItems plugin developers
- Spigot community

## Version History

### v0.0.1 (Current Release)
- Added all core features similar to Goop
- Added YAML configuration support
- Added advanced item editor GUI
- Added NBT utilities
- Added item storage manager
- Added MythicMobs and MMOCore integration
- Full command system with 9 subcommands
