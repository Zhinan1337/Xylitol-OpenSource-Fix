//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Administrator\Downloads\Minecraft1.12.2 Mappings"!

//Decompiled by Procyon!

package shop.xiaoda.gui.altmanager.microsoft;

import net.minecraft.util.*;

import java.awt.*;
import java.net.*;
import java.util.*;
import com.alibaba.fastjson.*;
import java.io.*;
import com.sun.net.httpserver.*;
import org.lwjgl.compatibility.Sys;

public final class MicrosoftLogin implements Closeable
{
    private static final String CLIENT_ID = "67b74668-ef33-49c3-a75c-18cbb2481e0c";
    private static final String REDIRECT_URI = "http://localhost:3434/sad";
    private static final String SCOPE = "XboxLive.signin%20offline_access";
    private static final String URL;
    public volatile String uuid;
    public volatile String userName;
    public volatile String accessToken;
    public volatile String refreshToken;
    public volatile boolean logged;
    public volatile String status;
    private final HttpServer httpServer;
    private final MicrosoftHttpHandler handler;
    
    public MicrosoftLogin() throws IOException {
        this.uuid = null;
        this.userName = null;
        this.accessToken = null;
        this.refreshToken = null;
        this.logged = false;
        this.status = EnumChatFormatting.YELLOW + "Login...";
        this.handler = new MicrosoftHttpHandler();
        (this.httpServer = HttpServer.create(new InetSocketAddress("localhost", 3434), 0)).createContext("/sad", this.handler);
        this.httpServer.start();
        Sys.openURL(MicrosoftLogin.URL);
    }
    
    public MicrosoftLogin(final String refreshToken) throws IOException {
        this.uuid = null;
        this.userName = null;
        this.accessToken = null;
        this.refreshToken = null;
        this.logged = false;
        this.status = EnumChatFormatting.YELLOW + "Login...";
        this.refreshToken = refreshToken;
        this.httpServer = null;
        this.handler = null;
        final String microsoftTokenAndRefreshToken = this.getMicrosoftTokenFromRefreshToken(refreshToken);
        final String xBoxLiveToken = this.getXBoxLiveToken(microsoftTokenAndRefreshToken);
        final String[] xstsTokenAndHash = this.getXSTSTokenAndUserHash(xBoxLiveToken);
        final String accessToken = this.getAccessToken(xstsTokenAndHash[0], xstsTokenAndHash[1]);
        final URL url = new URL("https://api.minecraftservices.com/minecraft/profile");
        final HttpURLConnection connection = (HttpURLConnection)url.openConnection();
        connection.setDoInput(true);
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Authorization", "Bearer " + accessToken);
        final String read = this.read(connection.getInputStream());
        final JSONObject jsonObject = JSON.parseObject(read);
        final String uuid = jsonObject.getString("id");
        final String userName = jsonObject.getString("name");
        this.uuid = uuid;
        this.userName = userName;
        this.accessToken = accessToken;
        this.logged = true;
    }
    
    @Override
    public void close() {
        if (this.httpServer != null) {
            this.httpServer.stop(0);
        }
    }
    
    public void show() throws Exception {
        Desktop.getDesktop().browse(new URI(MicrosoftLogin.URL));
    }
    
    private String getAccessToken(final String xstsToken, final String uhs) throws IOException {
        this.status = EnumChatFormatting.YELLOW + "Getting access token";
        System.out.println("Getting access token");
        final URL url = new URL("https://api.minecraftservices.com/authentication/login_with_xbox");
        final HttpURLConnection connection = (HttpURLConnection)url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestProperty("Accept", "application/json");
        connection.setDoInput(true);
        connection.setDoOutput(true);
        final JSONObject input = new JSONObject(true);
        input.put("identityToken", ("XBL3.0 x=" + uhs + ";" + xstsToken));
        this.write(new BufferedWriter(new OutputStreamWriter(connection.getOutputStream())), JSON.toJSONString(input));
        final JSONObject jsonObject = JSON.parseObject(this.read(connection.getInputStream()));
        return jsonObject.getString("access_token");
    }
    
    public String getMicrosoftTokenFromRefreshToken(final String refreshToken) throws IOException {
        this.status = EnumChatFormatting.YELLOW + "Getting microsoft token from refresh token";
        System.out.println("Getting microsoft token from refresh token");
        final URL url = new URL("https://login.live.com/oauth20_token.srf");
        final HttpURLConnection connection = (HttpURLConnection)url.openConnection();
        final String param = "client_id=67b74668-ef33-49c3-a75c-18cbb2481e0c&refresh_token=" + refreshToken + "&grant_type=refresh_token&redirect_uri=" + "http://localhost:3434/sad";
        connection.setDoInput(true);
        connection.setDoOutput(true);
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        this.write(new BufferedWriter(new OutputStreamWriter(connection.getOutputStream())), param);
        final JSONObject response_obj = JSON.parseObject(this.read(connection.getInputStream()));
        return response_obj.getString("access_token");
    }
    
    public String[] getMicrosoftTokenAndRefreshToken(final String code) throws IOException {
        this.status = EnumChatFormatting.YELLOW + "Getting microsoft token";
        System.out.println("Getting microsoft token");
        final URL url = new URL("https://login.live.com/oauth20_token.srf");
        final HttpURLConnection connection = (HttpURLConnection)url.openConnection();
        final String param = "client_id=67b74668-ef33-49c3-a75c-18cbb2481e0c&code=" + code + "&grant_type=authorization_code&redirect_uri=" + "http://localhost:3434/sad" + "&scope=" + "XboxLive.signin%20offline_access";
        connection.setDoInput(true);
        connection.setDoOutput(true);
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        this.write(new BufferedWriter(new OutputStreamWriter(connection.getOutputStream())), param);
        final JSONObject response_obj = JSON.parseObject(this.read(connection.getInputStream()));
        return new String[] { response_obj.getString("access_token"), response_obj.getString("refresh_token") };
    }
    
    public String getXBoxLiveToken(final String microsoftToken) throws IOException {
        this.status = EnumChatFormatting.YELLOW + "Getting xbox live token";
        System.out.println("Getting xbox live token");
        final URL connectUrl = new URL("https://user.auth.xboxlive.com/user/authenticate");
        final JSONObject xbl_param = new JSONObject(true);
        final JSONObject xbl_properties = new JSONObject(true);
        xbl_properties.put("AuthMethod", "RPS");
        xbl_properties.put("SiteName", "user.auth.xboxlive.com");
        xbl_properties.put("RpsTicket", ("d=" + microsoftToken));
        xbl_param.put("Properties", xbl_properties);
        xbl_param.put("RelyingParty", "http://auth.xboxlive.com");
        xbl_param.put("TokenType", "JWT");
        final String param = JSON.toJSONString(xbl_param);
        final HttpURLConnection connection = (HttpURLConnection)connectUrl.openConnection();
        connection.setDoInput(true);
        connection.setDoOutput(true);
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestProperty("Accept", "application/json");
        this.write(new BufferedWriter(new OutputStreamWriter(connection.getOutputStream())), param);
        final JSONObject response_obj = JSON.parseObject(this.read(connection.getInputStream()));
        return response_obj.getString("Token");
    }
    
    public String[] getXSTSTokenAndUserHash(final String xboxLiveToken) throws IOException {
        this.status = EnumChatFormatting.YELLOW + "Getting xsts token and user hash";
        System.out.println("Getting xsts token and user hash");
        final URL ConnectUrl = new URL("https://xsts.auth.xboxlive.com/xsts/authorize");
        final ArrayList<String> tokens = new ArrayList<String>();
        tokens.add(xboxLiveToken);
        final JSONObject xbl_param = new JSONObject(true);
        final JSONObject xbl_properties = new JSONObject(true);
        xbl_properties.put("SandboxId", "RETAIL");
        xbl_properties.put("UserTokens", JSONArray.parse(JSON.toJSONString(tokens)));
        xbl_param.put("Properties", xbl_properties);
        xbl_param.put("RelyingParty", "rp://api.minecraftservices.com/");
        xbl_param.put("TokenType", "JWT");
        final String param = JSON.toJSONString(xbl_param);
        final HttpURLConnection connection = (HttpURLConnection)ConnectUrl.openConnection();
        connection.setDoInput(true);
        connection.setDoOutput(true);
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json");
        this.write(new BufferedWriter(new OutputStreamWriter(connection.getOutputStream())), param);
        final JSONObject response_obj = JSON.parseObject(this.read(connection.getInputStream()));
        final String token = response_obj.getString("Token");
        final String uhs = response_obj.getJSONObject("DisplayClaims").getJSONArray("xui").getJSONObject(0).getString("uhs");
        return new String[] { token, uhs };
    }
    
    private void write(final BufferedWriter writer, final String s) throws IOException {
        writer.write(s);
        writer.close();
    }
    
    private String read(final InputStream stream) throws IOException {
        final BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        final StringBuilder stringBuilder = new StringBuilder();
        String s;
        while ((s = reader.readLine()) != null) {
            stringBuilder.append(s);
        }
        stream.close();
        reader.close();
        return stringBuilder.toString();
    }
    
    public boolean isLogged() {
        return this.logged;
    }
    
    public String getUserName() {
        return this.userName;
    }
    
    public String getUuid() {
        return this.uuid;
    }
    
    public String getAccessToken() {
        return this.accessToken;
    }
    
    public String getStatus() {
        return this.status;
    }
    
    public void setStatus(final String status) {
        this.status = status;
    }
    
    public void setLogged(final boolean logged) {
        this.logged = logged;
    }
    
    static {
        URL = "https://login.live.com/oauth20_authorize.srf?client_id=<client_id>&redirect_uri=<redirect_uri>&response_type=code&display=touch&scope=<scope>".replace("<client_id>", "67b74668-ef33-49c3-a75c-18cbb2481e0c").replace("<redirect_uri>", "http://localhost:3434/sad").replace("<scope>", "XboxLive.signin%20offline_access");
    }
    
    private class MicrosoftHttpHandler implements HttpHandler
    {
        private boolean got;
        
        private MicrosoftHttpHandler() {
            this.got = false;
        }
        
        @Override
        public void handle(final HttpExchange httpExchange) throws IOException {
            if (!this.got) {
                final String query = httpExchange.getRequestURI().getQuery();
                if (query.contains("code")) {
                    this.got = true;
                    final String code = query.split("code=")[1];
                    final String[] microsoftTokenAndRefreshToken = MicrosoftLogin.this.getMicrosoftTokenAndRefreshToken(code);
                    final String xBoxLiveToken = MicrosoftLogin.this.getXBoxLiveToken(microsoftTokenAndRefreshToken[0]);
                    final String[] xstsTokenAndHash = MicrosoftLogin.this.getXSTSTokenAndUserHash(xBoxLiveToken);
                    final String accessToken = MicrosoftLogin.this.getAccessToken(xstsTokenAndHash[0], xstsTokenAndHash[1]);
                    final URL url = new URL("https://api.minecraftservices.com/minecraft/profile");
                    final HttpURLConnection connection = (HttpURLConnection)url.openConnection();
                    connection.setDoInput(true);
                    connection.setRequestMethod("GET");
                    connection.setRequestProperty("Authorization", "Bearer " + accessToken);
                    final String read = MicrosoftLogin.this.read(connection.getInputStream());
                    final JSONObject jsonObject = JSON.parseObject(read);
                    final String uuid = jsonObject.getString("id");
                    final String userName = jsonObject.getString("name");
                    MicrosoftLogin.this.uuid = uuid;
                    MicrosoftLogin.this.userName = userName;
                    MicrosoftLogin.this.accessToken = accessToken;
                    MicrosoftLogin.this.refreshToken = microsoftTokenAndRefreshToken[1];
                    MicrosoftLogin.this.logged = true;
                }
            }
        }
    }
}
