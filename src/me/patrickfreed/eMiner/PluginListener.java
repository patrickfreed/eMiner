/*    */ package me.patrickfreed.eMiner;
/*    */ 
/*    */ import com.nijikokun.register.payment.Method;
/*    */ import com.nijikokun.register.payment.Methods;
/*    */ import java.io.PrintStream;
/*    */ import org.bukkit.Bukkit;
/*    */ import org.bukkit.event.server.PluginEnableEvent;
/*    */ import org.bukkit.event.server.ServerListener;
/*    */ 
/*    */ public class PluginListener extends ServerListener
/*    */ {
/*    */   public void onPluginEnable(PluginEnableEvent event)
/*    */   {
/* 12 */     if ((!Methods.hasMethod()) && 
/* 13 */       (Methods.setMethod(Bukkit.getPluginManager())))
/* 14 */       System.out.println("[eMiner] Successfully linked with " + Methods.getMethod().getName() + " v." + Methods.getMethod().getVersion());
/*    */   }
/*    */ }

/* Location:           C:\Users\Patrick\Desktop\eMiner.jar
 * Qualified Name:     me.patrickfreed.eMiner.PluginListener
 * JD-Core Version:    0.6.0
 */