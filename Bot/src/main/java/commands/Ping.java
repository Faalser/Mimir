/*
 * Mimir
 * Copyright (C) 2026  Faalser
 *
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package commands;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

public class Ping extends BaseCommands {

  public Ping(SlashCommandInteractionEvent event) {
    super(event, "Shows the bot's latency.");
  }

  public Ping() {
    super("Shows the bot's latency.");
  }

  public void main() {
    this.event
        .reply(
            (this.gatewayPing < 100
                ? "\nWaouh! Im lightning fast (" + this.gatewayPing + " ms)"
                : (this.gatewayPing < 500
                    ? "\nIm fast (" + this.gatewayPing + " ms)"
                    : "\nIm slow, please try again later (" + this.gatewayPing + " ms)")))
        .queue();
  }
}
