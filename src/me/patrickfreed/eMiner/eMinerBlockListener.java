/*    */ package me.patrickfreed.eMiner;
/*    */ 
/*    */ import org.bukkit.Material;
/*    */ import org.bukkit.block.Block;
/*    */ import org.bukkit.configuration.file.YamlConfiguration;
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.event.block.BlockBreakEvent;
/*    */ import org.bukkit.event.block.BlockListener;
/*    */ import org.bukkit.inventory.ItemStack;
/*    */ import org.bukkit.material.MaterialData;
/*    */ 
/*    */ public class eMinerBlockListener extends BlockListener
/*    */ {
/*    */   public String configitem;
/*    */   public ItemStack item;
/*    */   public static Player player;
/*    */   public String blockstring;
/*    */   public Block block;
/*    */   Material material;
/*    */   public int item_amount;
/* 19 */   public Payment pay = new Payment();
/*    */ 
/*    */   public void onBlockBreak(BlockBreakEvent event) {
/* 22 */     if (event.isCancelled()) {
/* 23 */       return;
/*    */     }
/*    */ 
/* 26 */     Block block = event.getBlock();
/* 27 */     Player player = event.getPlayer();
/* 28 */     int blockID = block.getTypeId();
/* 29 */     this.blockstring = block.getType().toString();
/*    */ 
/* 32 */     if ((eMiner.config.getString("Materials." + blockID) != null) && ((player.hasPermission("eMiner.use")) || (player.isOp())))
/*    */     {
/* 34 */       this.item_amount = eMiner.config.getInt("Materials." + blockID + ".Item.Item-Amount", 0);
/* 35 */       this.configitem = eMiner.config.getString("Materials." + blockID + ".Item.Type", "default");
/*    */       try {
/* 37 */         this.material = Material.matchMaterial(this.configitem);
/* 38 */         MaterialData materialdata = new MaterialData(this.material);
/* 39 */         this.item = materialdata.toItemStack(this.item_amount);
/*    */       }
/*    */       catch (NullPointerException ex) {
/* 42 */         return;
/*    */       }
/* 44 */       if (eMiner.useSql)
/* 45 */         this.pay.SQL(player, block, blockID, this.item, this.item_amount, this.configitem);
/*    */     }
/*    */   }
/*    */ }

/* Location:           C:\Users\Patrick\Desktop\eMiner.jar
 * Qualified Name:     me.patrickfreed.eMiner.eMinerBlockListener
 * JD-Core Version:    0.6.0
 */