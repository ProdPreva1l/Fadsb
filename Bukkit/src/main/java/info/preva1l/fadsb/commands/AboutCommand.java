package info.preva1l.fadsb.commands;

import info.preva1l.fadsb.Fadsb;
import info.preva1l.fadsb.user.CommandUser;
import info.preva1l.fadsb.utils.Text;
import info.preva1l.fadsb.utils.commands.BasicCommand;
import info.preva1l.fadsb.utils.commands.Command;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.william278.desertwell.about.AboutMenu;

public class AboutCommand extends BasicCommand {
    @Command(name = "fadsb", inGameOnly = false)
    public AboutCommand(Fadsb plugin) {
        super(plugin);
    }

    @Override
    public void execute(CommandUser sender, String[] args) {
        AboutMenu aboutMenu = AboutMenu.builder()
                .title(Component.text("Finally a Decent Skyblock"))
                .description(Component.text("The best feature packed Skyblock plugin on the market!"))
                .version(plugin.getPluginVersion())
                .credits("Author",
                        AboutMenu.Credit.of("Preva1l")
                                .description("Click to open website")
                                .url("https://please.vote-preva1l.today/"))
                .credits("Contributors")
                .credits("Translators")
                .buttons(AboutMenu.Link.of("https://please.vote-preva1l.today/projects/fadsb")
                        .text("Documentation"))
                .buttons(AboutMenu.Link.of("https://discord.gg/4KcF7S94HF")
                        .text("Support").icon("⭐").color(TextColor.fromHexString("#286aa1")))
                .themeColor(TextColor.fromHexString("#9667e6"))
                .secondaryColor(TextColor.fromHexString("#704EAB"))
                .build();

        sender.sendMessage(Text.miniToString(aboutMenu.toComponent()));
    }
}
