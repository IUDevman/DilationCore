# First public release [(DOWNLOAD)](https://github.com/IUDevman/DilationCore/releases/download/r0.5.0/DilationCore-r0.5.0.jar). All features work on multiplayer. :)


DilationCore - First publicly released hacked client for ReIndev. Client base from my previous [(1.17 client)](https://github.com/IUDevman/Tensor).

Note: This client is for the ReIndev version 2.9_03 [(Discord)](https://discord.com/invite/pNT8YerjUd) and requires FoxLoader [(Github)](https://github.com/Fox2Code/FoxLoader/releases).

**Features:**
- ArrayList: Renders a list of enabled hacks on the screen. Use -drawn hack to hide Hacks from the ArrayList.
- Coordinates: Renders the player's overworld/nether coordinates below the TabGUI.
- ESP: Renders a box around entities and containers (Red = Hostile, Green = Passive, Cyan = Player, Pink = TileEntity, Yellow = Item, Orange = Container, etc.).
- Fastbreak:  Seems to work pretty well on multiplayer. Has jank tiered system to make fastbreak work around 25% faster with all levels of tools (stone, iron, diamond, etc.).
- Fly: Good multiplayer fly. Change speed with -set fly speed [int]. Default is 5, max is 20.
- Fullbright: Makes the world bright.
- Jesus: Walk on fluids. Doesn't kill you if you fall on them from a height. Works on Multiplayer.
- KillAura: Attacks entitites. Has settings to target players, hostiles, passive mobs, or a combination of these. All are enabled by default (command is -set killaura [range int] or [players/hostiles/animals true/false]).
- NoExhaustion: Disables ReIndev's sprint timer. Works on Multiplayer.
- NoFall: Take no fall damage. Recommended when flight is enabled. Also works on multiplayer.
- NoWeather: Self explanatory.
- Sneak: Keeps your player sneaked when in multiplayer servers.
- TorchNuker: Automatically breaks torchers within a specified range of the player. Lights out.
- Tracers: Draws a cyan tracer to nearby players and nether portals (-set tracers portals true/false). Skidded partially from [qe7/Osiris](https://github.com/qe7/Osiris).
- Velocity: Disables blocks, fluids, entities, and explosions from pushing the player.
- Xray: Only renders desirable blocks such as ores. Also turns on fullbright, Has secret command "-xdo" to only show diamonds.

**Toggles:**
- See on player hud for default keybinds (ex: [X]). Change hack binds with -bind hack key.
- Enable/disable hack toggle messages with -message hack.
- Use the left and right arrow keys to page through the HUD.
- See the full list of hacks with -Hacks, full list of commands with -Commands, and full list of hack settings with -Settings.
- Default command prefix is "-". Change with -prefix char.

**Images:**

<img width="2560" height="1440" alt="2026-03-15_20 28 54" src="https://github.com/user-attachments/assets/abf6fe1d-f0dc-4dd4-adbb-81b23b1e63de" />

<img width="2560" height="1440" alt="2026-03-15_20 29 16" src="https://github.com/user-attachments/assets/6efb8b09-be06-47a5-ada7-0e9446fca109" />
