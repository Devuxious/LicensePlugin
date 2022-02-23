package me.ziue.license.command.impl;

import me.ziue.api.chat.ChatUtil;
import me.ziue.api.command.BaseCommand;
import me.ziue.api.command.Command;
import me.ziue.api.command.CommandArgs;
import me.ziue.license.License;
import me.ziue.license.backend.Server;
import org.bukkit.entity.Player;

public class LicenseCheckCommand extends BaseCommand {

    public String server = License.getLicense().getConfig().getString("SERVER");

    @Command(name = "license.check")
    @Override
    public void onCommand(CommandArgs command) {
        Player player = command.getPlayer();
        String[] args = command.getArgs();


        if(args.length < 2) {
            player.sendMessage(ChatUtil.translate("&cUsage: /license check <license> <plugin>"));
            return;
        }

        Server database = new Server(args[0], server, args[1]);
        database.request();

        player.sendMessage(ChatUtil.translate("&eChecking: &f" + database.getLicense() + " &efor plugin, &f" + database.getPlugin()));

        if (database.isValid()) {
            player.sendMessage(ChatUtil.translate("§7§m------------------------------"));
            player.sendMessage(ChatUtil.translate("&9&lLicense Information:"));
            player.sendMessage(ChatUtil.translate("&7| &9Time Generated: &f" + database.getGeneratedIn()));
            player.sendMessage(ChatUtil.translate("&7| &9Generated by: &f" + database.getGeneratedBy()));
            player.sendMessage(ChatUtil.translate("&7| &9License: &f" + database.getLicense()));
            player.sendMessage(ChatUtil.translate("&7| &9Plugin: &f" + database.getPlugin()));
            player.sendMessage(ChatUtil.translate("§7§m------------------------------"));
        } else {
            player.sendMessage(ChatUtil.translate("&9&lLicense Information:"));
            player.sendMessage(ChatUtil.translate("&7| &9License: &f" + database.getLicense()));
            player.sendMessage(ChatUtil.translate("&7| &9State: &f" + database.getReturn()));
        }
    }
}