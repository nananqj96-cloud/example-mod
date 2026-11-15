# bunnythekenzo

A Fabric client mod for Minecraft 1.21.11.

## Build

- Install JDK 21.
- On macOS/Linux: `./gradlew build`
- On Windows: `gradlew.bat build`

The mod JAR is written to `build/libs/`. Install it with Fabric Loader 0.19 or newer and Fabric API for Minecraft 1.21.11.

## Spotify HUD

The Spotify HUD defaults to **Demo** and does not launch another process. Choose **Auto** in the HUD's **Source** setting to use the real Spotify session on Windows. That opt-in starts the bundled local PowerShell bridge (with process-scoped `-ExecutionPolicy Bypass`), which reads the active Spotify track/artwork and handles the HUD's playback controls. It writes the bridge script and artwork under the Fabric config directory, removes the script on normal client shutdown, and makes no network requests.

## Network behavior

- Item-worth data is fetched from `donutsmpstats.com` with a GET request.
- Skin lookup uses Mojang's public profile/session APIs for the username entered in the skin setting.
- Spawner-protection alerts are sent only if the user supplies a webhook URL; the default is blank.

See [SECURITY.md](SECURITY.md) for the source review scope and limitations.
