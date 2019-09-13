package mlg.byneox.tc.Listeners;


import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import mlg.byneox.tc.Main;
import mlg.byneox.tc.Tasks.LoadTask;
import mlg.byneox.tc.Utils.ScoreboardSign;
import mlg.byneox.tc.api.INVENTORYAPI;
import mlg.byneox.tc.api.TABLISTAPI;
import mlg.byneox.tc.api.TCAPI;


public class ListenerCLASS implements Listener{
	
	TCAPI APITC = new TCAPI();
	INVENTORYAPI INVAPI = new INVENTORYAPI();
	TABLISTAPI TABLIST = new TABLISTAPI();
	
	@EventHandler(priority=EventPriority.HIGHEST)
	public void onPreLogin(AsyncPlayerPreLoginEvent e){
		Bukkit.getScheduler().scheduleSyncDelayedTask(Main.plugin, new Runnable()
		{
			public void run(){
				LoadTask ta = new LoadTask(e.getName(), Bukkit.getPlayer(e.getName()));
				ta.runTaskAsynchronously(Main.plugin);
			}
		}, (long) 2.3);
	}
	
	
	@EventHandler
	public void ondisable(PlayerQuitEvent e){
		e.setQuitMessage("");
		
		if(Main.plugin.dataPlayers.containsKey(e.getPlayer().getName())){
			TABLIST.removeprefix(e.getPlayer(), Main.plugin.dataPlayers.get(e.getPlayer().getName()).getGrade());
		}else{
			TABLIST.removeprefix(e.getPlayer(), "Joueur");
		}
		
		Main.plugin.mysqlload.remove(e.getPlayer().getName());
		Main.plugin.dataPlayers.remove(e.getPlayer().getName());
		Main.plugin.COMMANDEEXECUTE.remove(e.getPlayer().getName());
		Main.plugin.INJUMP.remove(e.getPlayer().getName());
		Main.plugin.load.remove(e.getPlayer().getName());
		if(Main.plugin.ARCHER.containsKey(e.getPlayer().getName())){
			Main.plugin.ARCHER.remove(e.getPlayer().getName());
			Main.plugin.GAMEARCHER = false;
		}
		for(Entry<Player, ScoreboardSign> sign : Main.plugin.boards.entrySet()){
			int d = Bukkit.getOnlinePlayers().size()-1;
			if(!APITC.isspec(sign.getKey().getName())){
				d = APITC.getSpecsize()+1;
				d=Bukkit.getOnlinePlayers().size()-d;
				if(d==0){
					if(Bukkit.getOnlinePlayers().size()>0){
						d=Bukkit.getOnlinePlayers().size()-1;
					}
				}
			}
			String lang = "FR";
			if(Main.plugin.dataPlayers.containsKey(sign.getKey().getName())){
				lang = Main.plugin.dataPlayers.get(sign.getKey().getName()).getLang();
			}
			if (lang == null || lang.equals("FR")){
				sign.getValue().setLine(11, "§7Joueurs: §e" + d +"§6/50");
			}else if (lang.equals("EN")){
				sign.getValue().setLine(11, "§7Players: §e" + d +"§6/50");
			}else if (lang.equals("ES")){
				sign.getValue().setLine(11, "§7Jugadores: §e" + d +"§6/50");
			}
		}
		Main.plugin.boards.remove(e.getPlayer());
		if(Main.plugin.boards.containsKey(e.getPlayer())){
			Main.plugin.boards.get(e.getPlayer()).destroy();
		}
		
	}
	
	@EventHandler
	public void Joininit(PlayerJoinEvent e){
		Player p = e.getPlayer();
		e.setJoinMessage("");
		for(Player pl: Bukkit.getOnlinePlayers()){
			if(APITC.isspec(pl.getName())){
				p.hidePlayer(pl);
			}
		}
		p.teleport(new Location(Bukkit.getWorld("world_"), 0.5, 151.3, 0.5, 180, 0));
		Bukkit.getScheduler().scheduleSyncDelayedTask(Main.plugin, new Runnable()
		{
			public void run(){
				p.playSound(p.getLocation(), Sound.valueOf("ITEM_PICKUP"), 2, 5);
				APITC.spawnnpc(p);
					
				if(!Main.plugin.mysqlload.containsKey(e.getPlayer().getName())){
					LoadTask ta = new LoadTask(e.getPlayer().getName(), e.getPlayer());
					ta.runTaskAsynchronously(Main.plugin);
				}
				
				if(Main.plugin.dataPlayers.containsKey(e.getPlayer().getName())){
					TABLIST.SetPrefix(e.getPlayer(), Main.plugin.dataPlayers.get(e.getPlayer().getName()).getGrade());
				}else{
					TABLIST.SetPrefix(e.getPlayer(), "Joueur");
				}
				if(APITC.isspec(p.getName())){
					for(Player pl: Bukkit.getOnlinePlayers()){
						pl.hidePlayer(p);
					}
					if(Main.plugin.dataPlayers.get(p.getName()).getSpecplayer().equals("no")){
						return;
					}else{
						p.teleport(Bukkit.getPlayer(Main.plugin.dataPlayers.get(p.getName()).getSpecplayer()));
						p.getInventory().clear();
						p.setGameMode(GameMode.CREATIVE);
					}
				}
			}
		}, 6L);
		
	}

	@EventHandler
	public void onJoin(PlayerJoinEvent e){
		Player p = e.getPlayer();
		Bukkit.getScheduler().scheduleSyncDelayedTask(Main.plugin, new Runnable()
		{
			public void run(){
				if(e.getPlayer() != null){
					if(p.isOnline()){
						for(Entry<Player, ScoreboardSign> sign : Main.plugin.boards.entrySet()){
							int d = Bukkit.getOnlinePlayers().size();
							if(!APITC.isspec(sign.getKey().getName())){
								d = APITC.getSpecsize();
								d=Bukkit.getOnlinePlayers().size()-d;
								if(d==0){
									if(Bukkit.getOnlinePlayers().size()>0){
										d=Bukkit.getOnlinePlayers().size()-1;
									}
								}
							}
							String lang = "FR";
							if(Main.plugin.dataPlayers.containsKey(p.getName())){
								lang = Main.plugin.dataPlayers.get(p.getName()).getLang();
							}
							if (lang == null || lang.equals("FR")){
								sign.getValue().setLine(11, "§7Joueurs: §e" + d +"§6/50");
							}else if (lang.equals("EN")){
								sign.getValue().setLine(11, "§7Players: §e" + d +"§6/50");
							}else if (lang.equals("ES")){
								sign.getValue().setLine(11, "§7Jugadores: §e" + d +"§6/50");
							}
						}
					}else{
						return;
					}
				}else{
					return;
				}
			}
		}, 3L);
	}
	
	@EventHandler
	public void onchat(AsyncPlayerChatEvent e){
		e.setCancelled(true);
		if(!Main.plugin.dataPlayers.get(e.getPlayer().getName()).getGrade().equals("Joueur")){
			String Prefix = "null";
			Prefix = APITC.returngrade(e.getPlayer().getName());
			Bukkit.broadcastMessage(Prefix+" "+e.getPlayer().getName()+" §f» " + ChatColor.translateAlternateColorCodes('&', e.getMessage()));
		}else{
			Bukkit.broadcastMessage("§7"+e.getPlayer().getName()+" §7» " + e.getMessage());
		}
	}

}
