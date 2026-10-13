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
package commands;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import net.dv8tion.jda.api.entities.MessageHistory;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

public class Clear extends BaseCommands {

  private int amount = 100;

  public Clear(SlashCommandInteractionEvent event) {
    super(event, "Clears a specified number of messages.");
  }

  public Clear() {
    super("Clears a specified number of messages.");
  }

  public void main() {
    this.setAmount(event.getOption("amount").getAsInt());
    event.deferReply(true).queue();
    MessageChannel channel = event.getChannel();
    MessageHistory history = channel.getHistory();
    purge(channel, history, this.amount, event);
  }

  private void purge(
      MessageChannel channel,
      MessageHistory history,
      int remaining,
      SlashCommandInteractionEvent event) {
    if (remaining <= 0) {
      event.getHook().sendMessage("Cleared " + this.amount + " messages.").queue();
      return;
    }
    int nb = Math.min(remaining, 100);
    history
        .retrievePast(nb)
        .queue(
            messages -> {
              if (messages.isEmpty()) {
                event.getHook().sendMessage("Cleared " + this.amount + " messages.").queue();
                return;
              }
              CompletableFuture.allOf(
                      channel.purgeMessages(messages).toArray(new CompletableFuture[0]))
                  .whenComplete(
                      (v, err) -> {
                        if (err != null) {
                          event.getHook().sendMessage("Erreur: " + err.getMessage()).queue();
                          return;
                        }
                        purge(channel, history, remaining - messages.size(), event);
                      });
            },
            err -> event.getHook().sendMessage("Erreur: " + err.getMessage()).queue());
  }

  public Collection<OptionData> getOptions() {
    return List.of(
        new OptionData(OptionType.INTEGER, "amount", "amount of messages to clear", true));
  }

  public int getNumberOptions() {
    return 1;
  }

  private void setAmount(int amount) {
    this.amount = amount;
  }
}
