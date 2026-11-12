# djinqMotd

A small and lightweight server list MOTD plugin for Paper.

Display a two-line MOTD with MiniMessage, gradients, legacy colors and hex colors. Choose a static message or a random message for each server ping.

Made by djinq ~ [djinq.dev](https://djinq.dev)

## Installation

1. Drop `djinqMotd.jar` into your server's `plugins` folder.
2. Start the server once.
3. Edit `plugins/djinqMotd/config.yml` and `messages.yml` however you want.
4. Run `/djinqmotd reload` or restart the server.

## Commands

| Command | Description |
| --- | --- |
| `/djinqmotd reload` | Reloads the configuration and messages. |

## Permissions

| Permission | Description |
| --- | --- |
| `djinqmotd.reload` | Allows `/djinqmotd reload`. |

## Configuration

In `config.yml`, you can edit:

- `motd.mode` ~ use `STATIC` for the first message or `RANDOM` for a random message.
- `motd.motd_line` ~ the list of MOTDs, each containing lines `'1'` and `'2'`.

`messages.yml` contains one `prefix` setting. It is added automatically to command messages.

Built against Paper 1.21.11 with Java 21.
