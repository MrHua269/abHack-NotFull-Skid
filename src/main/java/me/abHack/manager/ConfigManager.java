/*     */ package me.abHack.manager;
/*     */ import com.google.gson.*;
/*     */
/*     */
/*     */ import java.io.*;
/*     */
/*     */
/*     */
/*     */ import java.nio.file.Files;
/*     */ import java.nio.file.Path;
/*     */ import java.nio.file.Paths;
import java.nio.file.attribute.FileAttribute;
import java.util.*;
import java.util.stream.Collectors;
/*     */
/*     */
/*     */ import me.abHack.OyVey;
/*     */ import me.abHack.features.Feature;
/*     */ import me.abHack.features.modules.Module;
import me.abHack.features.setting.Bind;
import me.abHack.features.setting.EnumConverter;
/*     */ import me.abHack.features.setting.Setting;
import me.abHack.util.Util;

/*     */
/*     */ public class ConfigManager implements Util {
/*  20 */   public ArrayList<Feature> features = new ArrayList<>();
/*     */   
/*  22 */   public String config = "ab-Hack/config/";
/*     */   
/*     */   public static void setValueFromJson(Feature feature, Setting setting, JsonElement element) {
/*     */     String str;
/*  26 */     switch (setting.getType()) {
/*     */       case "Boolean":
/*  28 */         setting.setValue(Boolean.valueOf(element.getAsBoolean()));
/*     */         return;
/*     */       case "Double":
/*  31 */         setting.setValue(Double.valueOf(element.getAsDouble()));
/*     */         return;
/*     */       case "Float":
/*  34 */         setting.setValue(Float.valueOf(element.getAsFloat()));
/*     */         return;
/*     */       case "Integer":
/*  37 */         setting.setValue(Integer.valueOf(element.getAsInt()));
/*     */         return;
/*     */       case "String":
/*  40 */         str = element.getAsString();
/*  41 */         setting.setValue(str.replace("_", " "));
/*     */         return;
/*     */       case "Bind":
/*  44 */         setting.setValue((new Bind.BindConverter()).doBackward(element));
/*     */         return;
/*     */       case "Enum":
/*     */         try {
/*  48 */           EnumConverter converter = new EnumConverter(((Enum)setting.getValue()).getClass());
/*  49 */           Enum value = converter.doBackward(element);
/*  50 */           setting.setValue((value == null) ? setting.getDefaultValue() : value);
/*  51 */         } catch (Exception exception) {}
/*     */         return;
/*     */     } 
/*     */     
/*  55 */     OyVey.LOGGER.error("Unknown Setting type for: " + feature.getName() + " : " + setting.getName());
/*     */   }
/*     */   
/*     */   private static void loadFile(JsonObject input, Feature feature) {
/*  59 */     for (Map.Entry<String, JsonElement> entry : (Iterable<Map.Entry<String, JsonElement>>)input.entrySet()) {
/*  60 */       String settingName = entry.getKey();
/*  61 */       JsonElement element = entry.getValue();
/*  62 */       if (feature instanceof FriendManager) {
/*     */         try {
/*  64 */           OyVey.friendManager.addFriend(new FriendManager.Friend(element.getAsString(), UUID.fromString(settingName)));
/*  65 */         } catch (Exception e) {
/*  66 */           e.printStackTrace();
/*     */         } 
/*     */         continue;
/*     */       } 
/*  70 */       boolean settingFound = false;
/*  71 */       for (Setting setting : feature.getSettings()) {
/*  72 */         if (settingName.equals(setting.getName())) {
/*     */           try {
/*  74 */             setValueFromJson(feature, setting, element);
/*  75 */           } catch (Exception e) {
/*  76 */             e.printStackTrace();
/*     */           } 
/*  78 */           settingFound = true;
/*     */         } 
/*     */       } 
/*  81 */       if (settingFound);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void loadConfig(String name) {
/*  86 */     List<File> files = (List<File>)Arrays.stream(Objects.requireNonNull((new File("ab-Hack")).listFiles())).filter(File::isDirectory).collect(Collectors.toList());
/*  87 */     if (files.contains(new File("ab-Hack/" + name + "/"))) {
/*  88 */       this.config = "ab-Hack/" + name + "/";
/*     */     } else {
/*  90 */       this.config = "ab-Hack/config/";
/*     */     } 
/*  92 */     OyVey.friendManager.onLoad();
/*  93 */     for (Feature feature : this.features) {
/*     */       try {
/*  95 */         loadSettings(feature);
/*  96 */       } catch (IOException e) {
/*  97 */         e.printStackTrace();
/*     */       } 
/*     */     } 
/* 100 */     saveCurrentConfig();
/*     */   }
/*     */   
/*     */   public boolean configExists(String name) {
/* 104 */     List<File> files = Arrays.stream(Objects.requireNonNull((new File("ab-Hack")).listFiles())).filter(File::isDirectory).collect(Collectors.toList());
/* 105 */     return files.contains(new File("ab-Hack/" + name + "/"));
/*     */   }
/*     */   
/*     */   public void saveConfig(String name) {
/* 109 */     this.config = "ab-Hack/" + name + "/";
/* 110 */     File path = new File(this.config);
/* 111 */     if (!path.exists())
/* 112 */       path.mkdir(); 
/* 113 */     OyVey.friendManager.saveFriends();
/* 114 */     for (Feature feature : this.features) {
/*     */       try {
/* 116 */         saveSettings(feature);
/* 117 */       } catch (IOException e) {
/* 118 */         e.printStackTrace();
/*     */       } 
/*     */     } 
/* 121 */     saveCurrentConfig();
/*     */   }
/*     */   
/*     */   public void saveCurrentConfig() {
/* 125 */     File currentConfig = new File("ab-Hack/currentconfig.txt");
/*     */     try {
/* 127 */       if (currentConfig.exists()) {
/* 128 */         FileWriter writer = new FileWriter(currentConfig);
/* 129 */         String tempConfig = this.config.replaceAll("/", "");
/* 130 */         writer.write(tempConfig.replaceAll("ab-Hack", ""));
/* 131 */         writer.close();
/*     */       } else {
/* 133 */         currentConfig.createNewFile();
/* 134 */         FileWriter writer = new FileWriter(currentConfig);
/* 135 */         String tempConfig = this.config.replaceAll("/", "");
/* 136 */         writer.write(tempConfig.replaceAll("ab-Hack", ""));
/* 137 */         writer.close();
/*     */       } 
/* 139 */     } catch (Exception e) {
/* 140 */       e.printStackTrace();
/*     */     } 
/*     */   }
/*     */   
/*     */   public String loadCurrentConfig() {
/* 145 */     File currentConfig = new File("ab-Hack/currentconfig.txt");
/* 146 */     String name = "config";
/*     */     try {
/* 148 */       if (currentConfig.exists()) {
/* 149 */         Scanner reader = new Scanner(currentConfig);
/* 150 */         while (reader.hasNextLine())
/* 151 */           name = reader.nextLine(); 
/* 152 */         reader.close();
/*     */       } 
/* 154 */     } catch (Exception e) {
/* 155 */       e.printStackTrace();
/*     */     } 
/* 157 */     return name;
/*     */   }
/*     */   
/*     */   public void resetConfig(boolean saveConfig, String name) {
/* 161 */     for (Feature feature : this.features)
/* 162 */       feature.reset(); 
/* 163 */     if (saveConfig)
/* 164 */       saveConfig(name); 
/*     */   }
/*     */   
/*     */   public void saveSettings(Feature feature) throws IOException {
/* 168 */     JsonObject object = new JsonObject();
/* 169 */     File directory = new File(this.config + getDirectory(feature));
/* 170 */     if (!directory.exists())
/* 171 */       directory.mkdir(); 
/* 172 */     String featureName = this.config + getDirectory(feature) + feature.getName() + ".json";
/* 173 */     Path outputFile = Paths.get(featureName, new String[0]);
/* 174 */     if (!Files.exists(outputFile, new java.nio.file.LinkOption[0]))
/* 175 */       Files.createFile(outputFile, (FileAttribute<?>[])new FileAttribute[0]);
/* 176 */     Gson gson = (new GsonBuilder()).setPrettyPrinting().create();
/* 177 */     String json = gson.toJson((JsonElement)writeSettings(feature));
/* 178 */     BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(Files.newOutputStream(outputFile, new java.nio.file.OpenOption[0])));
/* 179 */     writer.write(json);
/* 180 */     writer.close();
/*     */   }
/*     */   
/*     */   public void init() {
/* 184 */     this.features.addAll(OyVey.moduleManager.modules);
/* 185 */     this.features.add(OyVey.friendManager);
/* 186 */     String name = loadCurrentConfig();
/* 187 */     loadConfig(name);
/* 188 */     OyVey.LOGGER.info("Config loaded.");
/*     */   }
/*     */   
/*     */   private void loadSettings(Feature feature) throws IOException {
/* 192 */     String featureName = this.config + getDirectory(feature) + feature.getName() + ".json";
/* 193 */     Path featurePath = Paths.get(featureName, new String[0]);
/* 194 */     if (!Files.exists(featurePath, new java.nio.file.LinkOption[0]))
/*     */       return; 
/* 196 */     loadPath(featurePath, feature);
/*     */   }
/*     */   
/*     */   private void loadPath(Path path, Feature feature) throws IOException {
/* 200 */     InputStream stream = Files.newInputStream(path, new java.nio.file.OpenOption[0]);
/*     */     try {
/* 202 */       loadFile((new JsonParser()).parse(new InputStreamReader(stream)).getAsJsonObject(), feature);
/* 203 */     } catch (IllegalStateException e) {
/* 204 */       OyVey.LOGGER.error("Bad Config File for: " + feature.getName() + ". Resetting...");
/* 205 */       loadFile(new JsonObject(), feature);
/*     */     } 
/* 207 */     stream.close();
/*     */   }
/*     */   
/*     */   public JsonObject writeSettings(Feature feature) {
/* 211 */     JsonObject object = new JsonObject();
/* 212 */     JsonParser jp = new JsonParser();
/* 213 */     for (Setting setting : feature.getSettings()) {
/* 214 */       if (setting.isEnumSetting()) {
/* 215 */         EnumConverter converter = new EnumConverter(((Enum)setting.getValue()).getClass());
/* 216 */         object.add(setting.getName(), converter.doForward((Enum)setting.getValue()));
/*     */         continue;
/*     */       } 
/* 219 */       if (setting.isStringSetting()) {
/* 220 */         String str = (String)setting.getValue();
/* 221 */         setting.setValue(str.replace(" ", "_"));
/*     */       } 
/*     */       try {
/* 224 */         object.add(setting.getName(), jp.parse(setting.getValueAsString()));
/* 225 */       } catch (Exception e) {
/* 226 */         e.printStackTrace();
/*     */       } 
/*     */     } 
/* 229 */     return object;
/*     */   }
/*     */   
/*     */   public String getDirectory(Feature feature) {
/* 233 */     String directory = "";
/* 234 */     if (feature instanceof Module)
/* 235 */       directory = directory + ((Module)feature).getCategory().getName() + "/"; 
/* 236 */     return directory;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Videos\remapped.jar!\me\abHack\manager\ConfigManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */