# LevelSystem Plugin

### Commands

- **/day**
  - **Description:** Set the time to day.
  - **Usage:** `/day`

- **/fly**
  - **Description:** Toggle flight mode.
  - **Usage:** `/fly`

- **/get-ls**
  - **Description:** Get a custom LevelSystem item.
  - **Usage:** `/get-ls <item_name>`
  - **Available Items:**
    - `stonebreaker`
    - `magicstick`
    - `cowspawnerstick`
    - `tp-sword`
    - `daystick`
    - `butchersword`
    - `extrawoolshears`
    - `fireworkstick`

- **/shop**
  - **Description:** Open the shop.
  - **Usage:** `/shop`

- **/maxkelp**
  - **Description:** Set the max kelp value for a player.
  - **Usage:** `/maxkelp <player> <max kelp>`

### Permissions

- **levelsystem.maxkelp**
  - **Description:** Allows the player to use the `/maxkelp` command.
  - **Default:** OP

### Custom Items

- **StoneBreaker**
  - A tool designed for efficient stone mining.

- **MagicStick**
  - A magical stick with knock back 255.

- **CowSpawnerStick**
  - A stick that allows players to spawn cows.

- **TeleportSword**
  - A sword that enables the player to teleport 10 blocks in view.

- **DayStick**
  - A stick that sets the time to day.

- **ButcherSword**
  - More Loot

- **ExtraWoolShears**
  - Shears that provide extra wool when used.

- **FireworkStick**
  - A stick that launches fireworks.

## Events and Listeners

The plugin includes various event listeners to handle different in-game events:

- **StoneListener**
- **FarmListener**
- **MinerListener**
- **KelpPlaceListener**
- **TeleportSwordListener**
- **CowSpawnerStickListener**
- **DayStickListener**
- **NightStickListener**
- **ButcherSwordListener**
- **ExtraWoolShearsListener**
- **FireworkStickListener**

These listeners ensure that the custom items and commands work seamlessly during gameplay.

## Data Management

The plugin saves player data such as stone counts, farm counts, and miner counts to YAML files:

- `stoneCounts.yml`
- `farmCounts.yml`
- `minerCounts.yml`

These files are automatically loaded and saved when the plugin is enabled and disabled.