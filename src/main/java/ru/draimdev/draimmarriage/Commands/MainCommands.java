package ru.draimdev.draimmarriage.Commands;

import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import ru.draimdev.draimmarriage.Config.MessageConfig;
import ru.draimdev.draimmarriage.DraimMarriage;
import ru.draimdev.draimmarriage.Utils.ColorUtils;
import ru.draimdev.draimmarriage.Utils.MessageUtils;
import ru.draimdev.draimmarriage.Utils.SoundUtils;


public class MainCommands implements CommandExecutor {
    private DraimMarriage pl;

    public MainCommands(DraimMarriage pl) {
        super();
        this.pl = pl;
    }

    public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
        if (command.getName().equals("draimmarriage")) {
            final Player p = (Player) sender;
            if (args.length == 0) {
                for (final String s : MessageConfig.getMSG().getCFG().getStringList("Messages.Help")) {
                    p.sendMessage(ColorUtils.color(s));
                }
                return false;
            }
            final String lowerCase = args[0].toLowerCase();
            switch (lowerCase) {
                case "reload": {
                    if (p.hasPermission("draimmarriage.reload")) {
                        MessageUtils.sendMessage(DraimMarriage.getConfigString("Messages.Another.Plugin-Reload"), sender);
                        MessageUtils.sendLog(DraimMarriage.getConfigString("Messages.Another.Plugin-Reload"));
                        SoundUtils.playSound(p, Sound.ENTITY_EXPERIENCE_ORB_PICKUP);
                        break;
                    }
                    MessageUtils.sendMessage(DraimMarriage.getConfigString("Messages.Another.No-Permission"), sender);
                    SoundUtils.playSound(p, Sound.BLOCK_ANVIL_PLACE);
                    break;
                }
                default: {
                    p.sendMessage(MessageUtils.config("config", "Messages.Another.NoArg", p, 0));
                    for (final String s2 : MessageConfig.getMSG().getCFG().getStringList("Messages.Help")) {
                        p.sendMessage(ColorUtils.color(s2));
                    }
                    SoundUtils.playSound(p, Sound.BLOCK_ANVIL_PLACE);
                    break;
                }
            }
        }
        return false;
    }
}
