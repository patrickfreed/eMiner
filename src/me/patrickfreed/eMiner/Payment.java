/*    */ package me.patrickfreed.eMiner;
/*    */ 
/*    */ import com.nijikokun.register.payment.Method;
/*    */ import com.nijikokun.register.payment.Method.MethodAccount;
/*    */ import com.nijikokun.register.payment.Methods;
/*    */ import java.sql.ResultSet;
/*    */ import java.sql.SQLException;
/*    */ import java.sql.Statement;
/*    */ import org.bukkit.Material;
/*    */ import org.bukkit.block.Block;
/*    */ import org.bukkit.configuration.file.YamlConfiguration;
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.inventory.ItemStack;
/*    */ import org.bukkit.inventory.PlayerInventory;
/*    */ 
/*    */ public class Payment
/*    */ {
/*    */   public void SQL(Player player, Block block, int blockID, ItemStack item, int item_amount, String configitem)
/*    */   {
/* 18 */     ResultSet rs = null;
/* 19 */     String query = " select id,amount from `iMiner` where `player` = '" + player.getName() + "' and `block` = '" + block.getType() + "';";
/* 20 */     int id = 0;
/* 21 */     int amountaux = 0;
/*    */     try {
/* 23 */       rs = eMiner.stmt.executeQuery(query);
/*    */     } catch (SQLException e1) {
/* 25 */       e1.printStackTrace();
/*    */     }
/*    */     try {
/* 28 */       while (rs.next()) {
/* 29 */         id = rs.getInt(1);
/* 30 */         amountaux = rs.getInt(2);
/*    */       }
/*    */     } catch (SQLException e1) {
/* 33 */       e1.printStackTrace();
/*    */     }
/* 35 */     if (id == 0) {
/* 36 */       query = "INSERT INTO `iMiner` (`player`,`block`,`amount`) VALUES ('" + player.getName() + "','" + block.getType() + "','1');";
/*    */       try {
/* 38 */         eMiner.stmt.execute(query);
/*    */       } catch (SQLException e) {
/* 40 */         e.printStackTrace();
/*    */       }
/*    */     }
/*    */     else {
/* 44 */       int amount = eMiner.config.getInt("Materials." + blockID + ".Amount", 0);
/* 45 */       amountaux++;
/* 46 */       if (amount > amountaux) {
/* 47 */         query = "update iMiner SET amount=" + amountaux + " where id=" + id + ";";
/*    */         try {
/* 49 */           eMiner.stmt.execute(query);
/*    */         } catch (SQLException e) {
/* 51 */           e.printStackTrace();
/*    */         }
/*    */       }
/*    */       else
/*    */       {
/* 56 */         double value = eMiner.config.getDouble("Materials." + blockID + ".Money", 0.0D);
/* 57 */         query = "update iMiner SET amount=0 where id=" + id + ";";
/*    */         try
/*    */         {
/* 60 */           eMiner.stmt.execute(query);
/*    */         } catch (SQLException e) {
/* 62 */           e.printStackTrace();
/*    */         }
/*    */ 
/* 65 */         Method.MethodAccount account = Methods.getMethod().getAccount(player.getName());
/* 66 */         account.add(value);
/* 67 */         player.getInventory().addItem(new ItemStack[] { item });
/*    */ 
/* 70 */         eMiner.Payment_Msg = eMiner.config.getString("Payment-Msg", "defalt").replace("%v", Methods.getMethod().format(value));
/* 71 */         String msg = eMiner.Payment_Msg.replace("%b", block.getType().toString());
/* 72 */         msg = msg.replace("%a", String.valueOf(amount));
/* 73 */         msg = msg.replace("%i", configitem);
/* 74 */         msg = msg.replace("%m", String.valueOf(item_amount));
/*    */ 
/* 76 */         player.sendMessage(msg);
/*    */       }
/*    */     }
/*    */   }
/*    */ }

/* Location:           C:\Users\Patrick\Desktop\eMiner.jar
 * Qualified Name:     me.patrickfreed.eMiner.Payment
 * JD-Core Version:    0.6.0
 */