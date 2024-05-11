//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Administrator\Downloads\Minecraft1.12.2 Mappings"!

//Decompiled by Procyon!

package shop.xiaoda.utils;

import java.net.*;
import java.io.*;
import java.util.regex.*;

public class RegionalAbuseUtil
{
    public static String country;
    
    public static void getAddressByIP() {
        try {
            final URL url = new URL("https://ip.appworlds.cn");
            final HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            connection.setRequestMethod("GET");
            final int responseCode = connection.getResponseCode();
            if (responseCode == 200) {
                final BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
                final StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();
                final String jsonResponse = response.toString();
                final Pattern pattern = Pattern.compile("\"province\":\"(.*?)\"");
                final Matcher matcher = pattern.matcher(jsonResponse);
                if (matcher.find()) {
                    RegionalAbuseUtil.country = matcher.group(1).replace("\u0443\u044e\u0402", "");
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    static {
        RegionalAbuseUtil.country = "\u54ea\u91cc";
    }
}
