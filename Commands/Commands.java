package mlg.byneox.tc.Commands;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;

import mlg.byneox.tc.Main;
import mlg.byneox.tc.Mysql.DatabaseManager;
import mlg.byneox.tc.api.INVENTORYAPI;


public class Commands implements CommandExecutor{
 
   public Main plugin;
	
   public Commands(Main plugin){
     this.plugin = plugin;
   }
   
public ItemStack ItemCreator(Material i,String Name, String lore){
	ItemStack item = new ItemStack(i);
	if (i == Material.STAINED_GLASS_PANE){
		item = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short)13);
	}
	ItemMeta meta = item.getItemMeta();
	meta.setDisplayName(Name);
	ArrayList<String> lore2 = new ArrayList<String>();
	lore2.add(lore);
	item.setItemMeta(meta);
	return item;
	
	
} 
 
public boolean onCommand(CommandSender sender, Command cmd, String label, String args[]){
	if(!(sender instanceof Player)){
		if(label.equalsIgnoreCase("close") || label.equalsIgnoreCase("shutdown") || label.equalsIgnoreCase("stop")){
    		for(Player pl : Bukkit.getOnlinePlayers()){
    			pl.kickPlayer("§c§l§nSHUTDOWN\n\n§r§fVous avez été éjecté temporairement\n§fRaison: §bAucune\n\n§e■ §fExpiration: §bTemporaire §e■\n\n§fS'agit-il d'une erreur ? Faites une réclamation:\n\n§c§lDiscord :§r §c§ohttps://discord.me/TolariaMC");
    		}
    		Bukkit.shutdown();
	    }
	}
	if(sender instanceof Player){
		Player p = (Player) sender;
		if(label.equalsIgnoreCase("close") || label.equalsIgnoreCase("shutdown") || label.equalsIgnoreCase("stop")){
			if(Main.plugin.dataPlayers.containsKey(p.getName())){
				if (!(plugin.dataPlayers.get(p.getName()).getGrade().equals("Admin") || plugin.dataPlayers.get(p.getName()).getGrade().equals("Owner"))){
					p.sendMessage("§7➠ §fCommande inconnue... :x");
					return true;
				}
			}else{
				p.sendMessage("§7➠ §fCommande inconnue... :x");
				return true;
			}
			if(args.length < 1){
	    		for(Player pl : Bukkit.getOnlinePlayers()){
	    			pl.kickPlayer("§c§l§nSHUTDOWN\n\n§r§fVous avez été éjecté temporairement\n§fRaison: §bAucune\n\n§e■ §fExpiration: §bTemporaire §e■\n\n§fS'agit-il d'une erreur ? Faites une réclamation:\n\n§c§lDiscord :§r §c§ohttps://discord.me/TolariaMC");
	    		}
	    		Bukkit.shutdown();
	    	}else{
	    		String d = args[0];
	    		d = d.replace("_", " ");
	    		for(Player pl : Bukkit.getOnlinePlayers()){
	    			pl.kickPlayer("§c§l§nSHUTDOWN\n\n§r§fVous avez été éjecté temporairement\n§fRaison: §b"+d+"\n\n§e■ §fExpiration: §bTemporaire §e■\n\n§fS'agit-il d'une erreur ? Faites une réclamation:\n\n§c§lDiscord :§r §c§ohttps://discord.me/TolariaMC");
	    		}
	    		Bukkit.shutdown();
	    	}
		}else if(label.equalsIgnoreCase("hub") || label.equalsIgnoreCase("spawn") || label.equalsIgnoreCase("lobby")){
			ByteArrayDataOutput out = ByteStreams.newDataOutput();
			out.writeUTF("Connect");
			out.writeUTF("HUB_01");
			p.sendPluginMessage(plugin, "BungeeCord", out.toByteArray());
		}else if(label.equalsIgnoreCase("commande")){
			if(Main.plugin.dataPlayers.containsKey(p.getName())){
				if (!(plugin.dataPlayers.get(p.getName()).getGrade().equals("Admin") || plugin.dataPlayers.get(p.getName()).getGrade().equals("Owner"))){
					p.sendMessage("§7➠ §fCommande inconnue... :x");
					return true;
				}
			}else{
				p.sendMessage("§7➠ §fCommande inconnue... :x");
				return true;
			}
			if(args.length < 1){
				p.sendMessage(" ");
				p.sendMessage("              §7➢ §f§lCOMMANDE");
				p.sendMessage(" ");
				p.sendMessage(" §7➠ §8/§7commande (on) §8▎ §fActiver les commandes.");
				p.sendMessage(" §7➠ §8/§7commande (off) §8▎ §fDésactiver les commandes.");
				p.sendMessage(" ");
	    	}else{
	    		if(args[0].equals("on")){
	    			if (plugin.COMMANDEEXECUTE.containsKey(p.getName())){
	    				plugin.COMMANDEEXECUTE.replace(p.getName(), "on");
	    			}else{
	    				plugin.COMMANDEEXECUTE.put(p.getName(), "on");
	    			}
	    			p.sendMessage("§3(TolariaMC) §7➠ §fCommande(s) Staff §8▎ §aActivée(s)");
	    		} else if(args[0].equals("off")){
	    			plugin.COMMANDEEXECUTE.remove(p.getName());
	    			p.sendMessage("§3(TolariaMC) §7➠ §fCommande(s) Staff §8▎ §cDésactivée(s)");
	    		}else{
	    			p.sendMessage(" ");
					p.sendMessage("              §7➢ §f§lCOMMANDE");
					p.sendMessage(" ");
					p.sendMessage(" §7➠ §8/§7commande (on) §8▎ §fActiver les commandes.");
					p.sendMessage(" §7➠ §8/§7commande (off) §8▎ §fDésactiver les commandes.");
					p.sendMessage(" ");
					return true;
	    		}
	    	}	
		}else if(label.equalsIgnoreCase("grade")){
			if(Main.plugin.dataPlayers.containsKey(p.getName())){
				if (!(plugin.dataPlayers.get(p.getName()).getGrade().equals("Admin") || plugin.dataPlayers.get(p.getName()).getGrade().equals("Owner"))){
					p.sendMessage("§7➠ §fCommande inconnue... :x");
					return true;
				}
			}else{
				p.sendMessage("§7➠ §fCommande inconnue... :x");
				return true;
			}
			if(args.length < 1){
				p.sendMessage("§6(Grade) §7➠ §c/grade <joueur>");
				return true;
	    	}
			if(args[0].isEmpty() == false){
				Player sd = p.getServer().getPlayer(args[0]);
	    		if(sd != null){
	    			if(args[0].equals(sd.getName())){
						Inventory inv = Bukkit.createInventory(p, 54, "§8[Grade] » " + args[0]);
						for (int i = 36; i < 45; i++) {
							inv.setItem(i, new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 4));
						}
						INVENTORYAPI APIGET = new INVENTORYAPI();
						inv.setItem(4, APIGET.createItemhead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMjg2OWJkZDlhOGY3N2VlZmY3NWQ4ZjY3ZWQwMzIyYmQ5YzE2ZGQ0OTQ5NzIzMTRlZDcwN2RkMTBhMzEzOWE1OCJ9fX0=", "§cADMIN", "§8Type : Grade##§7➥ §aClique pour accéder à ce grade!"));
						inv.setItem(5, APIGET.createItemhead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMjg2OWJkZDlhOGY3N2VlZmY3NWQ4ZjY3ZWQwMzIyYmQ5YzE2ZGQ0OTQ5NzIzMTRlZDcwN2RkMTBhMzEzOWE1OCJ9fX0=", "§4OWNER", "§8Type : Grade##§7➥ §aClique pour accéder à ce grade!"));
						inv.setItem(10, APIGET.createItemhead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZmY0NTdiZGIxMTJjMDAxMWJhNTMxODBmZTRiM2Q2NmZhMDEwYjM0OTFhOTJiMjQ1NjExNmE2NzY3ZDgxY2ZkOCJ9fX0=", "§6RESP", "§8Type : Grade##§7➥ §aClique pour accéder à ce grade!"));
						inv.setItem(11, APIGET.createItemhead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNzQwNzdhMGY3ZTZjZjIyMjRhMTJlNGNhOWIzM2FhZDA3Mzk5NjUyNDMwYzk4MjEyNDI5MWY2MzI5ODFiMzg2MSJ9fX0=", "§2STAFF", "§8Type : Grade##§7➥ §aClique pour accéder à ce grade!"));
						inv.setItem(12, APIGET.createItemhead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNTAwNDhlODQ3MGM0MTYxMjI2OWZmZmU1ZTkyODI4MmI3NjlmYjVhNzU1ZDkyNzg5Njk0NjA5Y2EzNWQwZWU2NyJ9fX0=", "§9MOD", "§8Type : Grade##§7➥ §aClique pour accéder à ce grade!"));
						inv.setItem(13, APIGET.createItemhead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNzVhYTY4YjdmMWI4ZDViZjg0Yjc4MGY0ZWU3Nzk1MTZjMzhmNWNiYTE5ZDY1MzcxMmVkOWUwZDU5Njk2ZTg1NiJ9fX0=", "§3GUIDE", "§8Type : Grade##§7➥ §aClique pour accéder à ce grade!"));
						inv.setItem(14, APIGET.createItemhead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNzNiNGYzYjNjZWY2ODE3ZTcxZmViMGRhNDc5NDI1ZWI5ZDc5ZjFlZmQ1YWViNWFkN2M5ZTI2MTZiMTI0Y2U4NCJ9fX0=", "§dFAMOUS", "§8Type : Grade##§7➥ §aClique pour accéder à ce grade!"));
						inv.setItem(15, APIGET.createItemhead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNDhjYTk4ZmNjNDBlNDcxNTk1YzE1MDYwNTliMWQwNDkxMjkzYjk1NzNmY2QyYmY4MmYxZDVkZTQxYjk3ZTJhMSJ9fX0=", "§bVIP+", "§8Type : Grade##§7➥ §aClique pour accéder à ce grade!"));
						inv.setItem(16, APIGET.createItemhead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZmY0NTdiZGIxMTJjMDAxMWJhNTMxODBmZTRiM2Q2NmZhMDEwYjM0OTFhOTJiMjQ1NjExNmE2NzY3ZDgxY2ZkOCJ9fX0=", "§eVIP", "§8Type : Grade##§7➥ §aClique pour accéder à ce grade!"));
						inv.setItem(22, APIGET.createItemhead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvM2VkMWFiYTczZjYzOWY0YmM0MmJkNDgxOTZjNzE1MTk3YmUyNzEyYzNiOTYyYzk3ZWJmOWU5ZWQ4ZWZhMDI1In19fQ==", "§7JOUEUR", "§8Type : Grade##§7➥ §aClique pour accéder à ce grade!"));
						p.openInventory(inv);
	    			}else{
	    				p.sendMessage("§6(Grade) §7➠ §cJoueur déconnecté(e)/serveur différent...");
		    			return true;
	    			}
	    		}else{
	    			p.sendMessage("§6(Grade) §7➠ §cJoueur déconnecté(e)/serveur différent...");
	    			return true;
	    		}
			}
		}else if(label.equalsIgnoreCase("reportlist")){
			if(Main.plugin.dataPlayers.containsKey(p.getName())){
				if(Main.plugin.dataPlayers.get(p.getName()).getGrade().equals("Admin") || Main.plugin.dataPlayers.get(p.getName()).getGrade().equals("Mod") || Main.plugin.dataPlayers.get(p.getName()).getGrade().equals("Guide") || Main.plugin.dataPlayers.get(p.getName()).getGrade().equals("Staff") || Main.plugin.dataPlayers.get(p.getName()).getGrade().equals("Resp") || Main.plugin.dataPlayers.get(p.getName()).getGrade().equals("Owner")){
					ArrayList<String> namesender = new ArrayList<String>();
					ArrayList<String> nametarget = new ArrayList<String>();
					ArrayList<String> motif = new ArrayList<String>();
					ArrayList<String> hours = new ArrayList<String>();
					ArrayList<String> server = new ArrayList<String>();
					Main.plugin.idreports.clear();
					try{
						final Connection connection = DatabaseManager.playersinfo.getDatabaseAccess().getConnection();
						final PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM reports");
						preparedStatement.executeQuery();
						
						final ResultSet rs = preparedStatement.getResultSet();
						while(rs.next()){
							namesender.add(rs.getString("namesender"));
							nametarget.add(rs.getString("nametarget"));
							motif.add(rs.getString("motif"));
							hours.add(rs.getString("hours"));
							server.add(rs.getString("server"));
							Main.plugin.idreports.add(rs.getString("idreports"));
						}
						connection.close();
					}catch(SQLException e){
						e.printStackTrace();
					}
					Inventory inv = Bukkit.createInventory(p, 54, "§8[Report] » Liste");
					for (int i = 36; i < 45; i++) {
						inv.setItem(i, new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 4));
					}
					INVENTORYAPI APIGET = new INVENTORYAPI();
					int reports = namesender.size();
					if(reports>36){
						reports=37;
					}
					if(reports == 0){
						p.sendMessage("§3(Report) §7➠ §cIl n'y a pas de report!");
						return true;
					}
					for(int i = 1; i<=reports; i++){
						inv.setItem((i-1), APIGET.createItem(Material.PAPER, 1, 0, "§bReport §7(#"+i+")", "§8Type: Infraction##§7("+hours.get((i-1))+")##§8• §fReporteur: §3"+namesender.get((i-1))+"#§8• §fReporté: §3"+nametarget.get((i-1))+"#§8• §fServeur: §3"+server.get((i-1))+"##§8• §fRaison: §e"+motif.get((i-1))+"##§7➥ §aClique pour supprimer!"));
					}
					p.openInventory(inv);
				}else{
					p.sendMessage("§7➠ §fCommande inconnue... :x");
					return true;
				}
			}else{
				p.sendMessage("§7➠ §fCommande inconnue... :x");
				return true;
			}
			
			
		}else if(label.equalsIgnoreCase("report")){
			if(args.length < 1){
				p.sendMessage("§3(Report) §7➠ §c/report <joueur>");
				return true;
	    	}else{
	    		Player sd = p.getServer().getPlayer(args[0]);
	    		if(sd != null){
	    			if(sd.getDisplayName().equals(p.getDisplayName())){
	    				p.sendMessage("§3(Report) §7➠ §cAction impossible... §8▎ §7(Vous-même)");
	    				return true;
	    			}else{
	    				if(args[0].equals(sd.getName())){
		    				if(Main.plugin.cooldowns.containsKey(sender.getName())) {
		    		            long secondsLeft = ((Main.plugin.cooldowns.get(sender.getName())/1000)+Main.plugin.cooldownTime) - (System.currentTimeMillis()/1000);
		    		            if(secondsLeft>0) {
		    		                sender.sendMessage("§3(Report) §7➠ §cAction impossible... §8▎ §7(Patientez)");
		    		                return true;
		    		            }else{
		    		            	Main.plugin.cooldowns.remove(sender.getName());
		    		            }
		    				}
		    				Main.plugin.cooldowns.put(sender.getName(), System.currentTimeMillis());
			    			Inventory inv = Bukkit.createInventory(p, 45, "§8[Report] » "+ sd.getDisplayName());
			    			for (int i = 27; i < 36; i++) {
			    				inv.setItem(i, new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 4));
			    			}
			    			INVENTORYAPI APIGET = new INVENTORYAPI();
			    			inv.setItem(11, APIGET.createItem(Material.BOOK_AND_QUILL, 1, 0, "§eCHAT", "§8Type: Infraction##§8• §fSpam#§8• §fInsulte#§8• §fPublicité##§7§l⚠ §r§3Abus §7§l=§r §bBannissement##§7➥ §aClique pour reporter!"));
			    			inv.setItem(12, APIGET.createItem(Material.IRON_AXE, 1, 0, "§eCOMBAT", "§8Type: Infraction##§8• §fForceField / KillAura#§8• §fAntiKnockback#§8• §fAimbot#§8• §fReach##§7§l⚠ §r§3Abus §7§l=§r §bBannissement##§7➥ §aClique pour reporter!"));
			    			inv.setItem(13, APIGET.createItem(Material.LAVA_BUCKET, 1, 0, "§eANTI-JEU", "§8Type: Infraction##§8• §fSpawn Kill#§8• §fDeco Combat#§8• §fTeam#§8• §fCamp##§7§l⚠ §r§3Abus §7§l=§r §bBannissement##§7➥ §aClique pour reporter!"));
			    			inv.setItem(14, APIGET.createItem(Material.FEATHER, 1, 0, "§eMOUVEMENT", "§8Type: Infraction##§8• §fFly#§8• §fSpeed Hack#§8• §fSpider / Glide#§8• §fNofall##§7§l⚠ §r§3Abus §7§l=§r §bBannissement##§7➥ §aClique pour reporter!"));
			    			inv.setItem(15, APIGET.createItem(Material.MINECART, 1, 0, "§eAUTRE", "§8Type: Infraction##§8• §fPseudo interdit#§8• §fSkin interdit#§8• §fAutre..##§7§l⚠ §r§3Abus §7§l=§r §bBannissement##§7➥ §aClique pour reporter!"));
			    			p.openInventory(inv);
	    				}else{
	    					p.sendMessage("§3(Report) §7➠ §cJoueur déconnecté(e)/serveur différent...");
	    	    			return true;
	    				}
	    			}
	    		}else{
	    			p.sendMessage("§3(Report) §7➠ §cJoueur déconnecté(e)/serveur différent...");
	    			return true;
	    		}
	    	}
			
		}
	}
	
	return true;
	
}
	   
	   
}