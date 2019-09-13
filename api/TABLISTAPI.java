package mlg.byneox.tc.api;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;

import mlg.byneox.tc.Main;

public class TABLISTAPI {
	
	public void SetPrefix(Player p, String Rank){
		if(Rank == null){
			Rank = "Joueur";
		}
		String team= "00009Joueur";
		if(Rank.equals("Owner")){
			team="00000Owner";
		}else if(Rank.equals("Admin")){
			team="00001Admin";
		}else if(Rank.equals("Resp")){
			team="00002Resp";
		}else if(Rank.equals("Mod")){
			team="00003Mod";
		}else if(Rank.equals("Staff")){
			team="00004Staff";
		}else if(Rank.equals("Guide")){
			team="00005Guide";
		}else if(Rank.equals("Famous")){
			team="00006Famous";
		}else if(Rank.equals("VIP+")){
			team="00007VIP+";
		}else if(Rank.equals("VIP")){
			team="00008VIP";
		}
		if(Main.plugin.sb.getPlayers().contains(p)){
			return;
		}else{
			Main.plugin.sb.getTeam(team).addPlayer(p);
			p.setDisplayName(Main.plugin.sb.getTeam(team).getPrefix() + p.getName());
			
			for(Player all : Bukkit.getOnlinePlayers()){
				all.setScoreboard(Main.plugin.sb);
			}
		}
		
	}
		
		public void removeprefix(Player p, String Rank){
			String team= "00009Joueur";
			if(Rank.equals("Owner")){
				team="00000Owner";
			}else if(Rank.equals("Admin")){
				team="00001Admin";
			}else if(Rank.equals("Resp")){
				team="00002Resp";
			}else if(Rank.equals("Mod")){
				team="00003Mod";
			}else if(Rank.equals("Staff")){
				team="00004Staff";
			}else if(Rank.equals("Guide")){
				team="00005Guide";
			}else if(Rank.equals("Famous")){
				team="00006Famous";
			}else if(Rank.equals("VIP+")){
				team="00007VIP+";
			}else if(Rank.equals("VIP")){
				team="00008VIP";
			}
			Main.plugin.sb.getTeam(team).removePlayer(p);
		
		
	}
		
	
		public void initteam(){
			Main.plugin.sb = Bukkit.getScoreboardManager().getNewScoreboard();
			Main.plugin.sb.registerNewTeam("00000Owner");
			Main.plugin.sb.registerNewTeam("00001Admin");
			Main.plugin.sb.registerNewTeam("00002Resp");
			Main.plugin.sb.registerNewTeam("00003Mod");
			Main.plugin.sb.registerNewTeam("00004Staff");
			Main.plugin.sb.registerNewTeam("00005Guide");
			Main.plugin.sb.registerNewTeam("00006Famous");
			Main.plugin.sb.registerNewTeam("00007VIP+");
			Main.plugin.sb.registerNewTeam("00008VIP");
			Main.plugin.sb.registerNewTeam("00009Joueur");
			
			Main.plugin.sb.getTeam("00000Owner").setPrefix("§4§lOWNER§r §4");
			Main.plugin.sb.getTeam("00001Admin").setPrefix("§c§lADMIN§r §c");
			Main.plugin.sb.getTeam("00002Resp").setPrefix("§6§lRESP§r §6");
			Main.plugin.sb.getTeam("00003Mod").setPrefix("§9§lMOD§r §9");
			Main.plugin.sb.getTeam("00004Staff").setPrefix("§2§lSTAFF§r §2");
			Main.plugin.sb.getTeam("00005Guide").setPrefix("§3§lGUIDE§r §3");
			Main.plugin.sb.getTeam("00006Famous").setPrefix("§d§lFAMOUS§r §d");
			Main.plugin.sb.getTeam("00007VIP+").setPrefix("§b§lVIP+§r §b");
			Main.plugin.sb.getTeam("00008VIP").setPrefix("§e§lVIP§r §e");
			Main.plugin.sb.getTeam("00009Joueur").setPrefix("§7");
			
		}
	

}
