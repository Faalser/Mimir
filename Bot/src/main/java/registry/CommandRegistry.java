/*
 * Mimir
 * Copyright (C) 2026  Faalser
 *
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 * Code source : https://forgejo.itwontfix.dev/Faalser/Mimir
 * License : https://forgejo.itwontfix.dev/Faalser/Mimir/LICENSE
 * Contact : contact@itwontfix.dev
 */
package registry;

import commands.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;

public class CommandRegistry {

  private record Entry(
      BaseCommands commands, Function<SlashCommandInteractionEvent, BaseCommands> factory) {}

  private final Map<String, Entry> entries = new LinkedHashMap<>();

  public CommandRegistry register(
      BaseCommands commands, Function<SlashCommandInteractionEvent, BaseCommands> factory) {
    this.entries.put(commands.getName(), new Entry(commands, factory));
    return this;
  }

  public Collection<SlashCommandData> toDiscordData() {
    List<SlashCommandData> list = new ArrayList<>();
    for (Entry e : this.entries.values()) {
      list.add(
          Commands.slash(e.commands.getName(), e.commands.getDescription())
              .addOptions(e.commands.getOptions()));
    }
    return list;
  }

  public void dispatch(SlashCommandInteractionEvent event) {
    Entry entry = this.entries.get(event.getName());
    if (entry == null) {
      event.reply("Unknown command.").queue();
      return;
    }
    entry.factory.apply(event).Execute();
  }
}
