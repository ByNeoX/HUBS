package mlg.byneox.tc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.messaging.PluginMessageListener;
import org.bukkit.scoreboard.Scoreboard;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.EnumWrappers.EntityUseAction;
import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;

import mlg.byneox.tc.Commands.Commands;
import mlg.byneox.tc.Listeners.Events;
import mlg.byneox.tc.Listeners.GUI;
import mlg.byneox.tc.Listeners.JumpListeners;
import mlg.byneox.tc.Listeners.ListenerCLASS;
import mlg.byneox.tc.Mysql.DatabaseManager;
import mlg.byneox.tc.Mysql.PlayerData;
import mlg.byneox.tc.Utils.ProfileSerializationManager;
import mlg.byneox.tc.Utils.RepeatingTask;
import mlg.byneox.tc.Utils.ScoreboardSign;
import mlg.byneox.tc.api.ActionBar;
import mlg.byneox.tc.api.INVENTORYAPI;
import mlg.byneox.tc.api.TABLISTAPI;
import mlg.byneox.tc.api.TCAPI;

public class Main
  extends JavaPlugin
  implements Listener, PluginMessageListener
{
	
  public Map<String, PlayerData> dataPlayers = new HashMap<>();
  public HashMap<String, String> mysqlload = new HashMap<String, String>();
  private ProfileSerializationManager profileSerializationManager;
  public HashMap<String, String> COMMANDEEXECUTE = new HashMap<String, String>();
  public HashMap<String, Integer> INJUMP = new HashMap<String, Integer>();
  public Map<Player, ScoreboardSign> boards = new HashMap<Player, ScoreboardSign>();
  public HashMap<String, Integer> ARCHER = new HashMap<String, Integer>();
  public int cooldownTime = 60;
  public double cooldownTimeMENU = 0.5;
  public HashMap<String, Long> cooldowns = new HashMap<String, Long>();
  public HashMap<String, String> load = new HashMap<String, String>();
  public ArrayList<String> idreports = new ArrayList<String>();
  public HashMap<String, ArrayList<Integer>> npc = new HashMap<String, ArrayList<Integer>>();
  private static Main instance;
  public static Main plugin;
  TCAPI API = new TCAPI();
  public int MLG_HUB = 0;
  public int TNT_HUB = 0;
  public int TNT_01 = 0;
  public int MLG_01 = 0;
  public int HUB_01 = 0;
  public int HUB_02 = 0;
  public int HUB_03 = 0;
  public int MLGTOTAL = 0;
  public int TNTTOTAL = 0;
  public boolean GAMEARCHER = false;
  public Scoreboard sb;
  TABLISTAPI TABLIST = new TABLISTAPI();
  
  @Override
  public void onEnable()
  {
	plugin = this;
	instance = this;
	DatabaseManager.initalldatabaseconnections();
	TABLIST.initteam();
    Bukkit.getServer().getPluginManager().registerEvents(new ListenerCLASS(), this);
    Bukkit.getServer().getPluginManager().registerEvents(new GUI(), this);
    Bukkit.getServer().getPluginManager().registerEvents(new Events(), this);
    Bukkit.getServer().getPluginManager().registerEvents(new JumpListeners(), this);
    getCommand("hub").setExecutor(new Commands(this));
    getCommand("grade").setExecutor(new Commands(this));
    getCommand("commande").setExecutor(new Commands(this));
    getCommand("report").setExecutor(new Commands(this));
    getCommand("reportlist").setExecutor(new Commands(this));
    getCommand("stop").setExecutor(new Commands(this));
    this.profileSerializationManager = new ProfileSerializationManager();
    getServer().getMessenger().registerIncomingPluginChannel( this, "SPEC", this );
    getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
    Location loc = new Location(Bukkit.getWorld("world_"), -20.5, 181, -65.5, -90, 0);
    Entity vi = (Entity) Bukkit.getWorld("world_").spawnEntity(loc, EntityType.VILLAGER);
    vi.setCustomNameVisible(true);
    API.freezeEntity(vi);
    
    ProtocolLibrary.getProtocolManager().addPacketListener(
    		new PacketAdapter(this, PacketType.Play.Client.USE_ENTITY) {
                // Note that this is executed asynchronously
                @Override
              public void onPacketReceiving(PacketEvent event) {
                	if(event.getPlayer() == null){
                		return;
                	}
                	if(!event.getPlayer().isOnline()){
                		return;
                	}
                  PacketContainer packet = event.getPacket();
                  EntityUseAction a = packet.getEntityUseActions().read(0);
                  if(a == EntityUseAction.INTERACT){
                  		if(event.getPlayer() == null){
                  			return;
                  		}
                  		if(!event.getPlayer().isOnline()){
                  			return;
                		}
                	  ArrayList<Integer> f = npc.get(event.getPlayer().getName());
                	  int id = packet.getIntegers().read(0);
                	  if(f.get(0) == id){
              			ByteArrayDataOutput out = ByteStreams.newDataOutput();
            			out.writeUTF("Connect");
            			out.writeUTF("MLG_HUB");
            			event.getPlayer().sendPluginMessage(plugin, "BungeeCord", out.toByteArray());
                	  }else if(f.get(1) == id){
                		ByteArrayDataOutput out = ByteStreams.newDataOutput();
              			out.writeUTF("Connect");
              			out.writeUTF("TNT_HUB");
              			event.getPlayer().sendPluginMessage(plugin, "BungeeCord", out.toByteArray());
                	  }else{
                		  return; 
                	  }
                  }
                  
              }
          });
  }
  
  
  public ProfileSerializationManager getProfileSerializationManager(){
	  return profileSerializationManager;
  }
  
  @Override
  public void onDisable()
  {
	  DatabaseManager.closealldatabaseconnections();
  }
  
  public static Main getInstance()
  {
    return instance;
  }
  
  @Override
  public void onPluginMessageReceived(String channel, Player player, byte[] bytes)
  {
      if ( !channel.equalsIgnoreCase( "SPEC" ) )
      {
          return;
      }
      ByteArrayDataInput in = ByteStreams.newDataInput( bytes );
      String subChannel = in.readUTF();
      String serv = in.readUTF();
      Integer nbr = in.readInt();
      if ( subChannel.equalsIgnoreCase( "count" ) ){
  		Bukkit.getScheduler().scheduleSyncDelayedTask(Main.plugin, new Runnable()
  		{
  			public void run(){
  				API.updatecount(serv, nbr);
  			}
  		}, 15L);
  		return;
      }  
      if ( subChannel.equalsIgnoreCase( "SPECCHANNEL" ) )
      {
          String data1 = in.readUTF();
          String data2 = in.readUTF();
          if(API.isspec(data1)){
        	  if(Bukkit.getPlayer(data1).isOnline()){
        		  if(Bukkit.getPlayer(data2).isOnline()){
        			  Bukkit.getPlayer(data1).teleport(Bukkit.getPlayer(data2));
        		  }
        	  }
        	  return;
          }else{
        	  
        	  if(dataPlayers.containsKey(data1)){
        		  dataPlayers.get(data1).setSpec(data2);
        	  }else{
        		  PlayerData data = new PlayerData();
        		  data.setSpec(data2);
        		  dataPlayers.put(data1, data);
        	  }
        	  Player spec = Bukkit.getPlayer(data1);
        	  Player spec2 = Bukkit.getPlayer(data2);
        	  if(spec != null){
        		  if(spec2 != null){
        			  for(Player pl: Bukkit.getOnlinePlayers()){
  						pl.hidePlayer(spec);
  					}
        			  if(dataPlayers.containsKey(spec.getName())){
        				  PlayerData data = dataPlayers.get(spec.getName());
        				  if(data.getPart() != null){
        					  data.setPart(null);
        				  }
        			  }
  					spec.teleport(spec2);
  					spec.getInventory().clear();
  					spec.setGameMode(GameMode.CREATIVE);
  					for(Entry<Player, ScoreboardSign> sign : Main.plugin.boards.entrySet()){
  						int d = Bukkit.getOnlinePlayers().size();			
  						if(!API.isspec(sign.getKey().getName())){
  							d = API.getSpecsize();
  							d=Bukkit.getOnlinePlayers().size()-d;
  							if(d==0){
  								if(Bukkit.getOnlinePlayers().size()>0){
  									d=Bukkit.getOnlinePlayers().size()-1;
  								}
  							}
  						}
  						String lang = dataPlayers.get(sign.getKey().getName()).getLang();
  						if (lang == null || lang.equals("FR")){
  							sign.getValue().setLine(11, "§7Joueurs: §e" + d +"§6/50");
  						}else if (lang.equals("EN")){
  							sign.getValue().setLine(11, "§7Players: §e" + d +"§6/50");
  						}else if (lang.equals("ES")){
  							sign.getValue().setLine(11, "§7Jugadores: §e" + d +"§6/50");
  						}
  					}
  					Bukkit.getScheduler().scheduleSyncDelayedTask(this, new Runnable(){
  						public void run(){
  							RepeatingTask repeatingTask = new RepeatingTask(Main.getInstance(), 0, 5) {
  								@Override
  					            public void run() {
  									if(!API.isspec(spec.getName())){
  										canncel();
  										return;
  									}else{
  										ActionBar bar = new ActionBar("§9Mode §7➠ §fModérateur");
  										bar.sendToPlayer(spec);
  									}
  								}
  							};
  						}
  						
  					},10L);
        		  }
        		  
        	  }
          }

          // do things with the data
      }
  }
}