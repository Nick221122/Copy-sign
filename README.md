# Crystal PvP Kill Click Cooldown

Fabric client mod project for Minecraft Java Edition 1.21.11.

- Configurable 1–60 second cooldown (default 3 seconds).
- Blocks attack and item-use actions while active.
- HUD countdown.
- Press the key assigned to **Cooldown Settings** in Options → Controls to configure the duration.
- Settings are stored at `config/crystal-kill-click-cooldown.properties`.

## Build
Use Java 21 and run `gradle build`, or push to this branch and download the artifact from its GitHub Actions run.

## Limitations
Kill detection relies on client-visible recent damage attribution. Some multiplayer servers or combat mods may not expose attribution consistently. The project must successfully build and be tested in the target game before relying on it.
