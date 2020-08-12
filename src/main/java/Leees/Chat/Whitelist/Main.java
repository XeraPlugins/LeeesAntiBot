package Leees.Chat.Whitelist;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

public final class Main extends JavaPlugin implements Listener {
    public static ArrayList<String> verified = new ArrayList();
    public static ArrayList<String> blacklisted = new ArrayList();

    public static File completedFile;
    public static File blaclistFile;
    public static File indexhtml;
    public static File failedhtml;
    public static File passedhtml;

    public static Main getPlugin() {
        return (Main) getPlugin(Main.class);
    }

    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);
        this.saveDefaultConfig();
        File dir = new File("plugins/LeeesAntiBot");
        if (!dir.exists()) {
            dir.mkdirs();
        }
        completedFile = new File(dir, "completed.txt");
        if (!completedFile.exists()) {
            try {
                completedFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
                getServer().getPluginManager().disablePlugin(this);
                return;
            }
        }
        indexhtml = new File(dir, "index.html");
        if (!indexhtml.exists()) {
            try {
                indexhtml.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
                getServer().getPluginManager().disablePlugin(this);
                return;
            }
            if (indexhtml.exists()) {
                try {
                    String fileContent = "<html>\n" +
                            "  <head>\n" +
                            "    <title>LeeesAntiBot</title>\n" +
                            "    <script type=\"text/javascript\">\n" +
                            "      var onloadCallback = function() {\n" +
                            "        grecaptcha.render('html_element', {\n" +
                            "          'sitekey' : '6LfYDeMUAAAAAG0I-RkGVT49l1V0IgazaT2aKBhI'});\n" +
                            "      };\n" +
                            "    </script>\n" +
                            "  </head>\n" +
                            "  <body>\n" +
                            "    <form action=\"/submit\">\n" +
                            "      <div  align=\"center\"><label for=\"username\">\n" +
                            "\t  <br><br>\n" +
                            "\t  <img src=\"https://cdn.discordapp.com/attachments/703638060792938578/710023538840698880/server-icon.png\"  width=\"64\" height=\"64\">\n" +
                            "\t  <b><h1>LeeesAntiBot</h1></b></label>\n" +
                            " <label for=\"username\"><b><h2>Ver 3.0.0</h2></b></label>\n" +
                            "<label for=\"username\"><b><h3>Please enter your username with proper caps and lower case</h3></b></label>\n" +
                            " <label for=\"username\"><b><h3>Username</h3></b></label>\n" +
                            "      <input type=\"text\" placeholder=\"Enter Username\" name=\"username\" required>\n" +
                            "<br><br>      <div id=\"html_element\"></div>\n" +
                            "      <br>\n" +
                            "      <input type=\"submit\" value=\"Submit\">\n" +
                            "    </form>\n" +
                            "    <script src=\"https://www.google.com/recaptcha/api.js?onload=onloadCallback&render=explicit\"\n" +
                            "        async defer>\n" +
                            "    </script>\n" +
                            "      </div>  </body>\n" +
                            "\t        <style>\n" +
                            "body {\n" +
                            "    color: black;\n" +
                            "    background-image:url('https://cdn.discordapp.com/attachments/699025897373827073/714543738118209566/2020-05-25_11.21.34.png');\n" +
                            "    background-repeat: no-repeat;\n" +
                            "    background-size: 100% 100%;\n" +
                            "}\n" +
                            "h1 {\n" +
                            "  color: white;\n" +
                            "}\n" +
                            "h2 {\n" +
                            "  color: white;\n" +
                            "}\n" +
                            "h3 {\n" +
                            "  color: white;\n" +
                            "}\n" +
                            "h4 {\n" +
                            "  color: white;\n" +
                            "}\n" +
                            "h5 {\n" +
                            "  color: white;\n" +
                            "}\n" +
                            "h6 {\n" +
                            "  color: white;\n" +
                            "}\n" +
                            "html {\n" +
                            "    height: 100%\n" +
                            "}\n" +
                            ".class { \n" +
                            "\tfont-family: Verdana,Geneva,sans-serif; \n" +
                            "}\n" +
                            "            body, html\n" +
                            "            {\n" +
                            "                margin: 0; padding: 0; height: 100%; overflow: hidden;\n" +
                            "            }\n" +
                            "\n" +
                            "            #content\n" +
                            "            {\n" +
                            "                position:absolute; left: 0; right: 0; bottom: 0; top: 0px; \n" +
                            "            }\n" +
                            "</style>\n" +
                            "</html>";

                    BufferedWriter writer = new BufferedWriter(new FileWriter("plugins/LeeesAntiBot/index.html"));
                    writer.write(fileContent);
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    getServer().getPluginManager().disablePlugin(this);
                    return;
                }
            }
        }
        passedhtml = new File(dir, "passed.html");
        if (!passedhtml.exists()) {
            try {
                passedhtml.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
                getServer().getPluginManager().disablePlugin(this);
                return;
            }
            if (passedhtml.exists()) {
                try {
                    String fileContent = "<html>\n" +
                            "  <head>\n" +
                            "    <title>LeeesAntiBot</title>\n" +
                            "    <script type=\"text/javascript\">\n" +
                            "      var onloadCallback = function() {\n" +
                            "        grecaptcha.render('html_element', {\n" +
                            "          'sitekey' : '6LfYDeMUAAAAAG0I-RkGVT49l1V0IgazaT2aKBhI'});\n" +
                            "      };\n" +
                            "    </script>\n" +
                            "  </head>\n" +
                            "  <body>\n" +
                            "    <form action=\"/submit\">\n" +
                            "      <div  align=\"center\"><label for=\"username\">\n" +
                            "\t  <br><br>\n" +
                            "\t  <img src=\"https://cdn.discordapp.com/attachments/703638060792938578/710023538840698880/server-icon.png\"  width=\"64\" height=\"64\">\n" +
                            "\t  <b><h1>LeeesAntiBot</h1></b></label>\n" +
                            " <label for=\"username\"><b><h2>Ver 3.0.0</h2></b></label>\n" +
                            "<label for=\"username\"><b><h1>You have passed the test you can now register</h1></b></label>\n" +
                            "      </div>  </body>\n" +
                            "\t        <style>\n" +
                            "body {\n" +
                            "    color: black;\n" +
                            "    background-image:url('https://cdn.discordapp.com/attachments/699025897373827073/714543738118209566/2020-05-25_11.21.34.png');\n" +
                            "    background-repeat: no-repeat;\n" +
                            "    background-size: 100% 100%;\n" +
                            "}\n" +
                            "h1 {\n" +
                            "  color: white;\n" +
                            "}\n" +
                            "h2 {\n" +
                            "  color: white;\n" +
                            "}\n" +
                            "h3 {\n" +
                            "  color: white;\n" +
                            "}\n" +
                            "h4 {\n" +
                            "  color: white;\n" +
                            "}\n" +
                            "h5 {\n" +
                            "  color: white;\n" +
                            "}\n" +
                            "h6 {\n" +
                            "  color: white;\n" +
                            "}\n" +
                            "html {\n" +
                            "    height: 100%\n" +
                            "}\n" +
                            ".class { \n" +
                            "\tfont-family: Verdana,Geneva,sans-serif; \n" +
                            "}\n" +
                            "            body, html\n" +
                            "            {\n" +
                            "                margin: 0; padding: 0; height: 100%; overflow: hidden;\n" +
                            "            }\n" +
                            "\n" +
                            "            #content\n" +
                            "            {\n" +
                            "                position:absolute; left: 0; right: 0; bottom: 0; top: 0px; \n" +
                            "            }\n" +
                            "</style>\n" +
                            "</html>";

                    BufferedWriter writer = new BufferedWriter(new FileWriter("plugins/LeeesAntiBot/passed.html"));
                    writer.write(fileContent);
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    getServer().getPluginManager().disablePlugin(this);
                    return;
                }
            }
        }

        failedhtml = new File(dir, "failed.html");
        if (!failedhtml.exists()) {
            try {
                failedhtml.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
                getServer().getPluginManager().disablePlugin(this);
                return;
            }
            if (failedhtml.exists()) {
                try {
                    String fileContent = "<html>\n" +
                            "  <head>\n" +
                            "    <title>LeeesAntiBot</title>\n" +
                            "    <script type=\"text/javascript\">\n" +
                            "      var onloadCallback = function() {\n" +
                            "        grecaptcha.render('html_element', {\n" +
                            "          'sitekey' : '6LfYDeMUAAAAAG0I-RkGVT49l1V0IgazaT2aKBhI'});\n" +
                            "      };\n" +
                            "    </script>\n" +
                            "  </head>\n" +
                            "  <body>\n" +
                            "    <form action=\"/submit\">\n" +
                            "      <div  align=\"center\"><label for=\"username\">\n" +
                            "\t  <br><br>\n" +
                            "\t  <img src=\"https://cdn.discordapp.com/attachments/703638060792938578/710023538840698880/server-icon.png\"  width=\"64\" height=\"64\">\n" +
                            "\t  <b><h1>LeeesAntiBot</h1></b></label>\n" +
                            " <label for=\"username\"><b><h2>Ver 3.0.0</h2></b></label>\n" +
                            "<label for=\"username\"><b><h1>You have failed the test you can fuck off</h1></b></label>\n" +
                            "      </div>  </body>\n" +
                            "\t        <style>\n" +
                            "body {\n" +
                            "    color: black;\n" +
                            "    background-image:url('https://cdn.discordapp.com/attachments/699025897373827073/714543738118209566/2020-05-25_11.21.34.png');\n" +
                            "    background-repeat: no-repeat;\n" +
                            "    background-size: 100% 100%;\n" +
                            "}\n" +
                            "h1 {\n" +
                            "  color: white;\n" +
                            "}\n" +
                            "h2 {\n" +
                            "  color: white;\n" +
                            "}\n" +
                            "h3 {\n" +
                            "  color: white;\n" +
                            "}\n" +
                            "h4 {\n" +
                            "  color: white;\n" +
                            "}\n" +
                            "h5 {\n" +
                            "  color: white;\n" +
                            "}\n" +
                            "h6 {\n" +
                            "  color: white;\n" +
                            "}\n" +
                            "html {\n" +
                            "    height: 100%\n" +
                            "}\n" +
                            ".class { \n" +
                            "\tfont-family: Verdana,Geneva,sans-serif; \n" +
                            "}\n" +
                            "            body, html\n" +
                            "            {\n" +
                            "                margin: 0; padding: 0; height: 100%; overflow: hidden;\n" +
                            "            }\n" +
                            "\n" +
                            "            #content\n" +
                            "            {\n" +
                            "                position:absolute; left: 0; right: 0; bottom: 0; top: 0px; \n" +
                            "            }\n" +
                            "</style>\n" +
                            "</html>";

                    BufferedWriter writer = new BufferedWriter(new FileWriter("plugins/LeeesAntiBot/failed.html"));
                    writer.write(fileContent);
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    getServer().getPluginManager().disablePlugin(this);
                    return;
                }
            }
        }


        try {
            BufferedReader br = new BufferedReader(new FileReader(completedFile));
            br.lines().forEach(uuid ->
                    verified.add(uuid));
        } catch (IOException x) {
            x.printStackTrace();
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
        (new Thread(() -> {
            try {
                ReCaptcha.main(null);
            } catch (IOException e) {
                e.printStackTrace();
                getServer().getPluginManager().disablePlugin(this);
            }
            blaclistFile = new File(dir, "blacklist.txt");
            if (!blaclistFile.exists()) {
                try {
                    blaclistFile.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                    getServer().getPluginManager().disablePlugin(this);
                    return;
                }
            }
            try {
                BufferedReader br = new BufferedReader(new FileReader(blaclistFile));
                br.lines().forEach(uuid2 ->
                        blacklisted.add(uuid2));
            } catch (IOException x) {
                x.printStackTrace();
                getServer().getPluginManager().disablePlugin(this);
                return;
            }
        })).start();
    }
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equals("blacklist")) {
            sender.sendMessage(ChatColor.DARK_BLUE + "----------------------");
            sender.sendMessage(ChatColor.GOLD + "/blacklist add username");
            sender.sendMessage(ChatColor.DARK_BLUE + "----------------------");
            if (sender.hasPermission("whitelist.admin")) {
                if (args.length > 0) {
                    if (args[0].equals("add") && args.length > 1) {
                        UUID uuid2;
                        uuid2 = Bukkit.getOfflinePlayer(args[1]).getUniqueId();
                        Main.blacklisted.add(String.valueOf(uuid2));
                        sender.sendMessage(ChatColor.GREEN + "The player " + args[1] + " has been added to the blacklist");
                        return true;
                    }
                }
            }
        }
        if (cmd.getName().equals("whitelist")) {
            sender.sendMessage(ChatColor.DARK_BLUE + "----------------------");
            sender.sendMessage(ChatColor.GOLD + "/whitelist add username");
            sender.sendMessage(ChatColor.DARK_BLUE + "----------------------");
            if (sender.hasPermission("whitelist.admin")) {
                if (args.length > 0) {
                    if (args[0].equals("add") && args.length > 1) {
                        UUID uuid;
                        uuid = Bukkit.getOfflinePlayer(args[1]).getUniqueId();
                        Main.verified.add(String.valueOf(uuid));
                        sender.sendMessage(ChatColor.GREEN + "The player " + args[1] + " has been added to the whitelist");
                        return true;
                    }
                }
            }
        }
        return false;
    }
        @EventHandler
    public void onChat(PlayerChatEvent e) {
        if (!verified.contains(e.getPlayer().getUniqueId().toString())) {
            e.setCancelled(true);
            e.getPlayer().kickPlayer(this.getConfig().getString("kickmessage").replace("&", "§"));
        } else if(blacklisted.contains(e.getPlayer().getUniqueId().toString())) {
            e.getPlayer().kickPlayer(this.getConfig().getString("blacklistedkick").replace("&", "§"));
        }
    }

    @EventHandler
    public void onCmd(PlayerCommandPreprocessEvent e) {
        if (!verified.contains(e.getPlayer().getUniqueId().toString())) {
                e.setCancelled(true);
                e.getPlayer().kickPlayer(this.getConfig().getString("kickmessage").replace("&", "§"));
        } else if(blacklisted.contains(e.getPlayer().getUniqueId().toString())) {
            e.getPlayer().kickPlayer(this.getConfig().getString("blacklistedkick").replace("&", "§"));
        }
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        if (!verified.contains(e.getPlayer().getUniqueId().toString())) {
            List strings = this.getConfig().getStringList("not-whitelisted-message");
            Bukkit.getScheduler().runTaskTimer(this, new Runnable() {
                int time = Main.getPlugin().getConfig().getInt("countdownkick"); //or any other number you want to start countdown from

                @Override
                public void run() {
                    if (this.time == 0) {
                        e.getPlayer().kickPlayer(Main.getPlugin().getConfig().getString("kickmessage").replace("&", "§"));
                    }

                    this.time--;

                    ArrayList finalStrings = new ArrayList();
                    (new Thread(() -> {
                        Iterator var3 = strings.iterator();

                        String sss;
                        while (var3.hasNext()) {
                            sss = (String) var3.next();
                            finalStrings.add(sss.replace("&", "§").replace("{playername}", e.getPlayer().getDisplayName()));
                        }

                        var3 = finalStrings.iterator();

                        while (var3.hasNext()) {
                            sss = (String) var3.next();
                            e.getPlayer().sendMessage(sss);
                        }

                    })).start();
                }

            }, 0L, 20L);

        } else if (blacklisted.contains(e.getPlayer().getUniqueId().toString())) {
            List strings = this.getConfig().getStringList("blacklisted-message");
            Bukkit.getScheduler().runTaskTimer(this, new Runnable() {
                int time = Main.getPlugin().getConfig().getInt("countdownkick"); //or any other number you want to start countdown from

                @Override
                public void run() {
                    if (this.time == 0) {
                        e.getPlayer().kickPlayer(Main.getPlugin().getConfig().getString("blacklistedkick").replace("&", "§"));
                    }

                    this.time--;
                    ArrayList finalStrings = new ArrayList();
                    (new Thread(() -> {
                        Iterator var3 = strings.iterator();

                        String sss;
                        while (var3.hasNext()) {
                            sss = (String) var3.next();
                            finalStrings.add(sss.replace("&", "§").replace("{playername}", e.getPlayer().getDisplayName()));
                        }

                        var3 = finalStrings.iterator();

                        while (var3.hasNext()) {
                            sss = (String) var3.next();
                            e.getPlayer().sendMessage(sss);
                        }

                    })).start();
                }
            }, 0L, 20L);
        } else {
            List strings = this.getConfig().getStringList("whitelisted-message");
            ArrayList finalStrings = new ArrayList();
            (new Thread(() -> {
                Iterator var3 = strings.iterator();

                String sss;
                while (var3.hasNext()) {
                    sss = (String) var3.next();
                    finalStrings.add(sss.replace("&", "§").replace("{playername}", e.getPlayer().getDisplayName()));
                }

                var3 = finalStrings.iterator();

                while (var3.hasNext()) {
                    sss = (String) var3.next();
                    e.getPlayer().sendMessage(sss);
                }

            })).start();
        }
}
    public void onDisable() {}


    public static void save() {
        try {
            BufferedWriter br = new BufferedWriter(new FileWriter(blaclistFile));
            blacklisted.forEach(s -> {
                try {
                    br.append(s);
                    br.newLine();
                    br.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            BufferedWriter br = new BufferedWriter(new FileWriter(completedFile));
            verified.forEach(s -> {
                try {
                    br.append(s);
                    br.newLine();
                    br.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
