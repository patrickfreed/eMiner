/*     */ package me.patrickfreed.eMiner;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.PrintStream;
/*     */ import java.sql.Connection;
/*     */ import java.sql.DriverManager;
/*     */ import java.sql.SQLException;
/*     */ import java.sql.Statement;
/*     */ import java.util.HashMap;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.Server;
/*     */ import org.bukkit.configuration.file.YamlConfiguration;
/*     */ import org.bukkit.event.Event.Priority;
/*     */ import org.bukkit.event.Event.Type;
/*     */ import org.bukkit.plugin.PluginDescriptionFile;
/*     */ import org.bukkit.plugin.PluginManager;
/*     */ import org.bukkit.plugin.java.JavaPlugin;
/*     */ 
/*     */ public class eMiner extends JavaPlugin
/*     */ {
/*  21 */   private static eMinerBlockListener BlockListener = new eMinerBlockListener();
/*  22 */   public static HashMap<String, String> BList = new HashMap();
/*     */   public Connection conn;
/*     */   public static Statement stmt;
/*  25 */   public static String MySQL_Host = "";
/*  26 */   public String MySQL_User = "";
/*  27 */   public String MySQL_Password = "";
/*  28 */   public String MySQL_Database = "";
/*  29 */   public int MySQL_Port = 0;
/*  30 */   public int Stoneamount = 0;
/*     */   public static YamlConfiguration config;
/*  32 */   public static String Payment_Msg = "";
/*  33 */   public static boolean useSql = false;
/*     */ 
/*     */   public void onDisable() {
/*  36 */     System.out.println("[" + getDescription().getName() + "] Disabled!");
/*     */   }
/*     */ 
/*     */   public void onEnable() {
/*  40 */     System.out.println("[" + getDescription().getName() + "] eMiner v." + getDescription().getVersion() + " enabled!");
/*  41 */     File conf = makeConfig(new File(getDataFolder(), "config.yml"));
/*     */ 
/*  43 */     if (conf.exists()) {
/*  44 */       config = YamlConfiguration.loadConfiguration(new File("plugins/eMiner", "config.yml"));
/*  45 */       System.out.println("[" + getDescription().getName() + "] Config loaded successfully!");
/*     */     }
/*     */     else {
/*  48 */       getServer().getPluginManager().disablePlugin(this);
/*  49 */       System.out.println("[" + getDescription().getName() + "] Error loading config, disabling...");
/*     */     }
/*     */ 
/*  52 */     PluginManager pm = Bukkit.getPluginManager();
/*  53 */     useSql = config.getBoolean("Use_SQL");
/*     */ 
/*  55 */     if (useSql) {
/*  56 */       System.out.println("[eMiner] Logging to SQL");
/*     */       try {
/*  58 */         connectMySQL();
/*     */       }
/*     */       catch (SQLException e) {
/*  61 */         e.printStackTrace();
/*     */       }
/*     */     } else {
/*  64 */       System.out.println("[eMiner] Logging to data.bin");
/*     */     }
/*     */ 
/*  67 */     pm.registerEvent(Event.Type.PLUGIN_ENABLE, new PluginListener(), Event.Priority.Monitor, this);
/*  68 */     pm.registerEvent(Event.Type.BLOCK_BREAK, BlockListener, Event.Priority.Monitor, this);
/*     */   }
/*     */ 
/*     */   private void connectMySQL() throws SQLException
/*     */   {
/*     */     try {
/*  74 */       Class.forName("com.mysql.jdbc.Driver");
/*     */     }
/*     */     catch (Exception e) {
/*  77 */       e.printStackTrace();
/*     */     }
/*     */ 
/*  80 */     MySQL_Host = config.getString("MySQL-Host", "default");
/*  81 */     this.MySQL_Port = config.getInt("MySQL-Port", 1);
/*  82 */     this.MySQL_Database = config.getString("MySQL-Database", "default");
/*  83 */     this.MySQL_User = config.getString("MySQL-User", "default");
/*  84 */     this.MySQL_Password = config.getString("MySQL-Password", "default");
/*     */ 
/*  86 */     this.conn = DriverManager.getConnection("jdbc:mysql://" + MySQL_Host + ":" + this.MySQL_Port + "/" + this.MySQL_Database, this.MySQL_User, this.MySQL_Password);
/*  87 */     stmt = this.conn.createStatement(1003, 1008);
/*  88 */     System.out.println("[" + this + "] Connected to MySQL!");
/*  89 */     String query = "CREATE TABLE IF NOT EXISTS `iMiner` (`id` INT( 255 ) NOT NULL AUTO_INCREMENT PRIMARY KEY, `player` VARCHAR( 255 ) NOT NULL, `block` VARCHAR( 255 ) NOT NULL, `amount` INT( 255 ) NOT NULL)";
/*  90 */     stmt.execute(query);
/*     */   }
/*     */ 
/*     */   private File makeConfig(File file)
/*     */   {
/*  98 */     if (!file.exists()) {
/*  99 */       System.out.println("[" + getDescription().getName() + "] Generating config...");
/* 100 */       new File(file.getParent()).mkdirs();
/* 101 */       InputStream in = eMiner.class.getResourceAsStream("/resources/" + file.getName());
/* 102 */       if (in != null) {
/* 103 */         FileOutputStream out = null;
/*     */         try {
/* 105 */           out = new FileOutputStream(file);
/* 106 */           byte[] buffer = new byte[2048];
/* 107 */           int length = 0;
/* 108 */           while ((length = in.read(buffer)) > 0)
/* 109 */             out.write(buffer, 0, length);
/*     */         }
/*     */         catch (IOException e) {
/* 112 */           e.printStackTrace();
/*     */           try
/*     */           {
/* 115 */             in.close();
/* 116 */             out.close();
/*     */           } catch (IOException e) {
/* 118 */             e.printStackTrace();
/*     */           }
/*     */         }
/*     */         finally
/*     */         {
/*     */           try
/*     */           {
/* 115 */             in.close();
/* 116 */             out.close();
/*     */           } catch (IOException e) {
/* 118 */             e.printStackTrace();
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/* 123 */     return file;
/*     */   }
/*     */ }

/* Location:           C:\Users\Patrick\Desktop\eMiner.jar
 * Qualified Name:     me.patrickfreed.eMiner.eMiner
 * JD-Core Version:    0.6.0
 */