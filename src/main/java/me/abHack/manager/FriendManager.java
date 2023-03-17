/*    */ package me.abHack.manager;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import java.util.Objects;
/*    */ import java.util.UUID;
/*    */ import me.abHack.features.Feature;
/*    */ import me.abHack.features.setting.Setting;
/*    */ import me.abHack.util.PlayerUtil;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ 
/*    */ public class FriendManager
/*    */   extends Feature
/*    */ {
/* 15 */   private List<Friend> friends = new ArrayList<>();
/*    */   
/*    */   public FriendManager() {
/* 18 */     super("Friends");
/*    */   }
/*    */   
/*    */   public boolean isFriend(String name) {
/* 22 */     cleanFriends();
/* 23 */     return this.friends.stream().anyMatch(friend -> friend.username.equalsIgnoreCase(name));
/*    */   }
/*    */   
/*    */   public boolean isFriend(EntityPlayer player) {
/* 27 */     return isFriend(player.getName());
/*    */   }
/*    */   
/*    */   public void addFriend(String name) {
/* 31 */     Friend friend = getFriendByName(name);
/* 32 */     if (friend != null) {
/* 33 */       this.friends.add(friend);
/*    */     }
/* 35 */     cleanFriends();
/*    */   }
/*    */   
/*    */   public void removeFriend(String name) {
/* 39 */     cleanFriends();
/* 40 */     for (Friend friend : this.friends) {
/* 41 */       if (!friend.getUsername().equalsIgnoreCase(name))
/* 42 */         continue;  this.friends.remove(friend);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public void onLoad() {
/* 48 */     this.friends = new ArrayList<>();
/* 49 */     clearSettings();
/*    */   }
/*    */   
/*    */   public void saveFriends() {
/* 53 */     clearSettings();
/* 54 */     cleanFriends();
/* 55 */     for (Friend friend : this.friends) {
/* 56 */       register(new Setting(friend.getUuid().toString(), friend.getUsername()));
/*    */     }
/*    */   }
/*    */   
/*    */   public void cleanFriends() {
/* 61 */     this.friends.stream().filter(Objects::nonNull).filter(friend -> (friend.getUsername() != null));
/*    */   }
/*    */   
/*    */   public List<Friend> getFriends() {
/* 65 */     cleanFriends();
/* 66 */     return this.friends;
/*    */   }
/*    */   
/*    */   public Friend getFriendByName(String input) {
/* 70 */     UUID uuid = PlayerUtil.getUUIDFromName(input);
/* 71 */     if (uuid != null) {
/* 72 */       Friend friend = new Friend(input, uuid);
/* 73 */       return friend;
/*    */     } 
/* 75 */     return null;
/*    */   }
/*    */   
/*    */   public void addFriend(Friend friend) {
/* 79 */     this.friends.add(friend);
/*    */   }
/*    */   
/*    */   public static class Friend {
/*    */     private final String username;
/*    */     private final UUID uuid;
/*    */     
/*    */     public Friend(String username, UUID uuid) {
/* 87 */       this.username = username;
/* 88 */       this.uuid = uuid;
/*    */     }
/*    */     
/*    */     public String getUsername() {
/* 92 */       return this.username;
/*    */     }
/*    */     
/*    */     public UUID getUuid() {
/* 96 */       return this.uuid;
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Videos\remapped.jar!\me\abHack\manager\FriendManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */