package Leees.Anti.Bot;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import org.bukkit.Bukkit;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

public class ReCaptcha
{

    public static void main(String[] args) throws IOException {
        int port;

        port = Main.getPlugin().getConfig().getInt("port");

        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
        server.createContext("/", new MyHandler());
        server.createContext("/submit", new MyHandler2());
        server.setExecutor(null);
        server.start();
    }

    static String jsonText;

    static class MyHandler
            implements HttpHandler {
        public void handle(HttpExchange t) throws IOException {
            StringBuilder contentBuilder = new StringBuilder();
            try {
                BufferedReader in = new BufferedReader(new FileReader("plugins/LeeesAntiBot/index.html"));
                String str;
                while ((str = in.readLine()) != null) {
                    contentBuilder.append(str);
                }
                in.close();
            } catch (IOException e) {
            }
            String response = contentBuilder.toString();
            t.sendResponseHeaders(200, response.length());
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }

    public static Map<String, String> xd(String a) {
        HashMap<String, String> map = new HashMap<String, String>();
        for (String s : a.split("&")) {
            map.put(s.split("=")[0], s.split("=")[1]);
        }
        return map;
    }

    static class MyHandler2
            implements HttpHandler {
        public void handle(HttpExchange t) throws IOException {
            String privatekey;
            privatekey = Main.getPlugin().getConfig().getString("privatekey");
            boolean captchaValid = false;
            String response = null;
            Map<String, String> map = ReCaptcha.xd(t.getRequestURI().getRawQuery());
            try {
                if (ReCaptcha.isCaptchaValid("" + privatekey + "", (String)map.get("g-recaptcha-response"))) {
                    captchaValid = true;
                }
                if (captchaValid)
                {             StringBuilder contentBuilder = new StringBuilder();
                try {
                    BufferedReader in = new BufferedReader(new FileReader("plugins/LeeesAntiBot/passed.html"));
                    String str;
                    while ((str = in.readLine()) != null) {
                        contentBuilder.append(str);
                    }
                    in.close();
                } catch (IOException e) {
                }
                response = contentBuilder.toString(); }
                else { StringBuilder contentBuilder = new StringBuilder();
                    try {
                        BufferedReader in = new BufferedReader(new FileReader("plugins/LeeesAntiBot/failed.html"));
                        String str;
                        while ((str = in.readLine()) != null) {
                            contentBuilder.append(str);
                        }
                        in.close();
                    } catch (IOException e) {
                    }
                    response = contentBuilder.toString(); }
                if (captchaValid) {
                    String name = (String)map.get("username");
                    String uuid = Bukkit.getOfflinePlayer(name).getUniqueId().toString();
                    Main.verified.add(uuid);
                    Main.save();
                    System.out.println(name + " Has been Whitelisted");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            t.sendResponseHeaders(200, response.length());
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }

    public static boolean isCaptchaValid(String secretKey, String response) {
        String sitekey;
        sitekey = Main.getPlugin().getConfig().getString("sitekey");
        try {
            String url = "https://www.google.com/recaptcha/api/siteverify?secret=" + secretKey + "&response=" + response;
            InputStream res = (new URL(url)).openStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(res, Charset.forName("UTF-8")));
            jsonText = "";
            rd.lines().forEach(s -> jsonText += s);
            System.out.print(jsonText);
            res.close();

            JsonElement json = (new JsonParser()).parse(jsonText);
            System.out.print(json);
            return json.getAsJsonObject().get("success").getAsBoolean();
        } catch (Exception e) {
            e.printStackTrace();

            return false;
        }
    }
}
