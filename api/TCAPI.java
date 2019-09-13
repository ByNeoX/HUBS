package mlg.byneox.tc.api;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import mlg.byneox.tc.Main;
import mlg.byneox.tc.Listeners.PARTICLESHAPEAPI;
import mlg.byneox.tc.Mysql.DatabaseManager;
import mlg.byneox.tc.Mysql.PlayerData;
import mlg.byneox.tc.Utils.RepeatingTask;
import mlg.byneox.tc.Utils.ScoreboardSign;
import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.IChatBaseComponent.ChatSerializer;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import net.minecraft.server.v1_8_R3.PacketPlayOutPlayerListHeaderFooter;
import net.seocraft.npcs.NPC;
import net.seocraft.npcs.NpcAPI;
import net.seocraft.npcs.enums.Action;

public class TCAPI {
	static INVENTORYAPI INVAPI = new INVENTORYAPI();
	PARTICLESHAPEAPI PART = new PARTICLESHAPEAPI();
	TABLISTAPI TABLIST = new TABLISTAPI();
	ArmorStand as = null;
	
	
	
	public void InitConnection(Player p){
		initscoreboard(p);
		if(Main.plugin.dataPlayers.containsKey(p.getName())){
			TABLIST.SetPrefix(p, Main.plugin.dataPlayers.get(p.getName()).getGrade());
		}else{
			TABLIST.SetPrefix(p, "Joueur");	
		}
		int d = 5;
		if(!isspec(p.getName())){
			d=90;
			ItemStack n = new ItemStack(Material.COMPASS, 1);
			ItemMeta m = n.getItemMeta();
			String lang = "FR";
			if(Main.plugin.dataPlayers.containsKey(p.getName())){
				lang = Main.plugin.dataPlayers.get(p.getName()).getLang();
			}
	        if (lang.equals("FR")){
	        	p.getInventory().setItem(8, INVAPI.creatcustomitem(Material.NETHER_STAR, false, Enchantment.ARROW_FIRE, "§cHubs §8• §7(Clic-droit)"));
	        	p.getInventory().setItem(7, INVAPI.createItemhead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYmY3NWQxYjc4NWQxOGQ0N2IzZWE4ZjBhN2UwZmQ0YTFmYWU5ZTdkMzIzY2YzYjEzOGM4Yzc4Y2ZlMjRlZTU5In19fQ==", "§eBoutique §8• §7(Clic-droit)", " "));
	        	sendHeaderFooter(p, "§7• §3§lTOLARIA§f§lMC §7•\n§fBienvenue sur le serveur §eTolariaMC\n", "§0 \n§7• §3§lGUIDES§r §fet §9§lMODS§r §fsont §bdisponibles §fsi besoin... §7•");
				m.setDisplayName("§bMenu principal §8• §7(Clic-droit)");
				p.getInventory().setItem(1, INVAPI.Itemlanguage("GIVE", p, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNTEyNjlhMDY3ZWUzN2U2MzYzNWNhMWU3MjNiNjc2ZjEzOWRjMmRiZGRmZjk2YmJmZWY5OWQ4YjM1Yzk5NmJjIn19fQ==", lang, "GIVE", "GIVE", "GIVE"));
	        }else if (lang.equals("EN")){
	        	p.getInventory().setItem(8, INVAPI.creatcustomitem(Material.NETHER_STAR, false, Enchantment.ARROW_FIRE, "§cHubs §8• §7(Right-click)"));
	        	p.getInventory().setItem(7, INVAPI.createItemhead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYmY3NWQxYjc4NWQxOGQ0N2IzZWE4ZjBhN2UwZmQ0YTFmYWU5ZTdkMzIzY2YzYjEzOGM4Yzc4Y2ZlMjRlZTU5In19fQ==", "§eShop §8• §7(Right-click)", " "));
	        	sendHeaderFooter(p, "§7• §3§lTOLARIA§f§lMC §7•\n§fWelcome to the server §eTolariaMC\n", "§0 \n§7• §3§lHELPERS§r §fand §9§lMODS§r §fare §bavailable §fif necessary... §7•");
				m.setDisplayName("§bMain menu §8• §7(Right-click)");
				p.getInventory().setItem(1, INVAPI.Itemlanguage("GIVE", p, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvODc5ZDk5ZDljNDY0NzRlMjcxM2E3ZTg0YTk1ZTRjZTdlOGZmOGVhNGQxNjQ0MTNhNTkyZTQ0MzVkMmM2ZjlkYyJ9fX0=", lang, "GIVE", "GIVE", "GIVE"));
	        }else if (lang.equals("ES")){
	        	p.getInventory().setItem(8, INVAPI.creatcustomitem(Material.NETHER_STAR, false, Enchantment.ARROW_FIRE, "§cHubs §8• §7(Haga-clic)"));
	        	p.getInventory().setItem(7, INVAPI.createItemhead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYmY3NWQxYjc4NWQxOGQ0N2IzZWE4ZjBhN2UwZmQ0YTFmYWU5ZTdkMzIzY2YzYjEzOGM4Yzc4Y2ZlMjRlZTU5In19fQ==", "§eTienda §8• §7(Haga-clic)", " "));
	        	sendHeaderFooter(p, "§7• §3§lTOLARIA§f§lMC §7•\n§fBienvenido al servidor de §eTolariaMC\n", "§0 \n§7• §3§lGUIAS§r §fy §9§lMODS§r §festán §bdisponibles §fsi es necesario... §7•");
				m.setDisplayName("§bMenú principal §8• §7(Haga-clic)");
				p.getInventory().setItem(1, INVAPI.Itemlanguage("GIVE", p, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMzJiZDQ1MjE5ODMzMDllMGFkNzZjMWVlMjk4NzQyODc5NTdlYzNkOTZmOGQ4ODkzMjRkYThjODg3ZTQ4NWVhOCJ9fX0=", lang, "GIVE", "GIVE", "GIVE"));
	        }
			n.setItemMeta(m);
			p.getInventory().setItem(0, n);	
		}
		RepeatingTask repeatingTask = new RepeatingTask(Main.plugin, 15, d) {
			int action = 1;
			@Override
            public void run() {
				if(p.isOnline()){
					if(!Main.plugin.INJUMP.containsKey(p.getName())){
						if(!isspec(p.getName())){
							action++;
							if(action>3){
								action =1;
							}
							String lang = Main.plugin.dataPlayers.get(p.getName()).getLang();
							String message = "§9Mode §7➠ §fModérateur";
							if(!isspec(p.getName())){
								message = returnactionbar(action, lang);
							ActionBar bar = new ActionBar(message);
							bar.sendToPlayer(p);
							}
						}
					}else{
						canncel();
					}
				}
			}
		};
		
	}
	
	
	public void checkpart(String name, String part){
		Player p = Bukkit.getPlayer(name);
		String Grade = Main.plugin.dataPlayers.get(p.getName()).getGrade();
		if(p != null){
			if(p.isOnline()){
				PART.displaypart(p);
				if(Grade != null || Grade != "VIP"){
					p.setAllowFlight(true);
				}
			}
		}
	}
	
	
	public boolean isspec(String name){
		if(Main.plugin.dataPlayers.containsKey(name)){
			return Main.plugin.dataPlayers.get(name).getSpec();
		}else{
			return false;
		}
	}
	
	
	public void gradebc(Player p, String langs){
		if(!isspec(p.getName())){
			Bukkit.getScheduler().scheduleSyncDelayedTask(Main.plugin, new Runnable(){
				public void run(){
					String Grade = returngrade(p.getName());
					if(Grade.equals("§7§lJOUEUR")){
						return;
					}
					for(Player pss: Bukkit.getOnlinePlayers()){
						String lang = "FR";	
						if(Main.plugin.dataPlayers.containsKey(pss.getName())){
							lang = Main.plugin.dataPlayers.get(pss.getName()).getLang();
						}
						if (lang == null || lang.equals("FR")){
							pss.sendMessage(Grade+" " + p.getName() +" §8▎ §7a rejoint le hub.");
						}else if (lang.equals("EN")){
							pss.sendMessage(Grade+" " + p.getName() +" §8▎ §7joined the hub.");
						}else if (lang.equals("ES")){
							pss.sendMessage(Grade+" " + p.getName() +" §8▎ §7se unió al servidor.");
						}
					}
				}
			}, 3L);
		}
	}
	
	public String Mysqlgetinfo(String name, String Database, String information){
		String result = "nostring";
		try{
			final Connection connection = DatabaseManager.playersinfo.getDatabaseAccess().getConnection();
			final PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM "+Database+" WHERE name = ?");
			preparedStatement.setString(1, name);
			
			preparedStatement.executeQuery();
			
			final ResultSet rs = preparedStatement.getResultSet();
			if(rs.next()){
				result = rs.getString(information);
			}
			connection.close();
			return result;
		}catch(SQLException e){
			e.printStackTrace();
		}
		return result;
	}
	
	
	public String returnactionbar(int i, String lang){
		String action = "§eInscrivez-vous sur le forum §6www.TolariaMC.fr/Forum";
		if(lang.equals("EN")){
			action = "§eRegister in the forum §6www.TolariaMC.fr/Forum";
		}else if(lang.equals("ES")){
			action = "§eRegistrarse en el foro §6www.TolariaMC.fr/Forum";	
		}
		if(i==2){
			action = "§eSuivez-nous sur twitter §3@TolariaV2";
			if(lang.equals("EN")){
				action = "§eFollow us on twitter §3@TolariaV2";
			}else if(lang.equals("ES")){
				action = "§eSiguenos en twitter §3@TolariaV2";	
			}
		}else if(i==3){
			action = "§bUn bug ? Signalez-le ici §9www.TolariaMC.fr/Feedback";
			if(lang.equals("EN")){
				action = "§bA bug? report it here §9www.TolariaMC.fr/Feedback";
			}else if(lang.equals("ES")){
				action = "§bUn error ? reportalo aqui §9www.TolariaMC.fr/Feedback";	
			}
		}
		return action;
	}
	
	public String gethealth(int health){
		String coeur = "§c❤❤❤";
		if(health==1){
			coeur = "§c❤§7❤❤";
		}else if(health==2){
			coeur = "§c❤❤§7❤";
		}
		return coeur;
	}
	
	
	public List<String> Mysqlgetallinfo(String name, String Database, List<String> information){
		String result = "nostring";
		ArrayList<String> result23 = new ArrayList<String>();
		try{
			Connection connection = DatabaseManager.playersinfo.getDatabaseAccess().getConnection();
			
			PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM "+Database+" WHERE name = ?");
			if(name=="null"){
				preparedStatement = connection.prepareStatement("SELECT * FROM "+Database);
			}
			if(name!="null"){
				preparedStatement.setString(1, name);
			}
			
			preparedStatement.executeQuery();
			
			ResultSet rs = preparedStatement.getResultSet();
			if(rs.next()){
				for(int i = 0; i<information.size(); i++){
					result = rs.getString(information.get(i));
					result23.add(result);
				}
			}
			if(result23.isEmpty()){
				for(int i = 0; i<information.size(); i++){
					result23.add("nostring");
				}
			}
			connection.close();
			return result23;
		}catch(SQLException e){
			Bukkit.getPlayer(name).kickPlayer("§c§l§nERREUR\n\n§r§fVous avez été éjecté temporairement\n§fRaison: §bChargement impossible §7(No_loadsql)\n\n§e■ §fExpiration: §bTemporaire §e■\n\n§fS'agit-il d'une erreur ? Faites une réclamation:\n\n§c§lDiscord :§r §c§ohttps://discord.me/TolariaMC");
			e.printStackTrace();
		}
		return result23;
	}
	
	public String returngrade(String name){
		if(Main.plugin.dataPlayers.containsKey(name)){
			if(Main.plugin.dataPlayers.get(name).getGrade().equals("Joueur")){
				return "§7§lJOUEUR";
			}
		}else{
			return "§7§lJOUEUR";
		}	
		String Gradestring = "§7§lJOUEUR";
		if (Main.plugin.dataPlayers.get(name).getGrade().equals("VIP")){
			Gradestring = "§e§lVIP§r§e";
		}else if (Main.plugin.dataPlayers.get(name).getGrade().equals("Admin")){
			Gradestring = "§c§lADMIN§r§c";
		}else if (Main.plugin.dataPlayers.get(name).getGrade().equals("Owner")){
			Gradestring = "§4§lOWNER§r§4";
		}else if (Main.plugin.dataPlayers.get(name).getGrade().equals("Resp")){
			Gradestring = "§6§lRESP§r§6";
		}else if (Main.plugin.dataPlayers.get(name).getGrade().equals("Mod")){
			Gradestring = "§9§lMOD§r§9";
		}else if (Main.plugin.dataPlayers.get(name).getGrade().equals("Guide")){
			Gradestring = "§3§lGUIDE§r§3";
		}else if (Main.plugin.dataPlayers.get(name).getGrade().equals("Famous")){
			Gradestring = "§d§lFAMOUS§r§d";
		}else if (Main.plugin.dataPlayers.get(name).getGrade().equals("VIP+")){
			Gradestring = "§b§lVIP+§r§b";
		}else if (Main.plugin.dataPlayers.get(name).getGrade().equals("Staff")){
			Gradestring = "§2§lSTAFF§r§2";
		}
		return Gradestring;
		
		
	}
	
	
	public void updatecount(String serv, Integer nbr){
		if(serv.equals("HUB_01")){
			Main.plugin.HUB_01 = nbr;
		}else if(serv.equals("MLG_HUB")){
			Main.plugin.MLG_HUB = nbr;
		}else if(serv.equals("TNT_HUB")){
			Main.plugin.TNT_HUB = nbr;
		}else if(serv.equals("HUB_02")){
			Main.plugin.HUB_02 = nbr;
		}else if(serv.equals("HUB_03")){
			Main.plugin.HUB_03 = nbr;
		}else if(serv.equals("MLG_01")){
			Main.plugin.MLG_01 = nbr;
		}else if(serv.equals("TNT_01")){
			Main.plugin.TNT_01 = nbr;
		}
		if(serv.equals("TNT_HUB") || serv.equals("TNT_01") || serv.equals("MLG_HUB") || serv.equals("MLG_01")){
			Main.plugin.MLGTOTAL = (Main.plugin.MLG_HUB + Main.plugin.MLG_01);
			Main.plugin.TNTTOTAL = (Main.plugin.TNT_HUB + Main.plugin.TNT_01);
			Bukkit.getScheduler().scheduleSyncDelayedTask(Main.plugin, new Runnable()
			{
				public void run(){
					Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "holo setline n3 3 §7" +Main.plugin.MLGTOTAL+" joueur(s)");
					Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "holo setline n2 3 §7" +Main.plugin.TNTTOTAL+" joueur(s)");
				}
			}, 3L);
		}

		
	}
	
	public void Mysqlupdate(String name, String Database, String information, String newvalue){
		try{
			final Connection connection = DatabaseManager.playersinfo.getDatabaseAccess().getConnection();
			final PreparedStatement preparedStatement = connection.prepareStatement("UPDATE "+Database+" SET "+information+" = ? WHERE name = ?");
			preparedStatement.setString(1, newvalue);
			preparedStatement.setString(2, name);
			preparedStatement.executeUpdate();
			connection.close();
		}catch(SQLException e){
			e.printStackTrace();
		}
	}
	
	public int getSpecsize() {
		int count = 0;
		for(Entry<String, PlayerData> name : Main.plugin.dataPlayers.entrySet()){
			if(name.getValue().getSpec() == true){
				count++;
			}
		}
		return count;
	}
	

	
	public void initscoreboard(Player p){
		Bukkit.getScheduler().scheduleSyncDelayedTask(Main.plugin, new Runnable(){
			public void run(){
				if(p != null){
					if(!p.isOnline()){
						return;
					}else{
						int d = getSpecsize();
						if(!isspec(p.getName())){
							d=Bukkit.getOnlinePlayers().size()-d;
							if(d==0){
								if(Bukkit.getOnlinePlayers().size()>0){
									d=1;
								}
							}
						}else{
							d=Bukkit.getOnlinePlayers().size();
						}
						ScoreboardSign scoreboard = new ScoreboardSign(p, "§f• §3§lTOLARIA §f•");
						scoreboard.create();
						scoreboard.setLine(5, "§9");
						if(Main.plugin.dataPlayers.containsKey(p.getName())){
							if(Main.plugin.dataPlayers.get(p.getName()).getLang().equals("FR")){
								scoreboard.setLine(6, "§7Pseudo: §b" + p.getName());
								scoreboard.setLine(7, "§7Grade §8➥ " + returngrade(p.getName()));
							}else if(Main.plugin.dataPlayers.get(p.getName()).getLang().equals("EN")){
								scoreboard.setLine(6, "§7Pseudo: §b" + p.getName());
								scoreboard.setLine(7, "§7Rank §8➥ " + returngrade(p.getName()));
							}else if(Main.plugin.dataPlayers.get(p.getName()).getLang().equals("ES")){
								scoreboard.setLine(6, "§7Seudónimo: §b" + p.getName());
								scoreboard.setLine(7, "§7Rango §8➥ " + returngrade(p.getName()));
							}
						}else{
							scoreboard.setLine(6, "§7Pseudo: §b" + p.getName());
							scoreboard.setLine(7, "§7Grade §8➥ " + returngrade(p.getName()));
							PlayerData data = new PlayerData();
							data.setLang("FR");
							Main.plugin.dataPlayers.put(p.getName(), data);
						}
						scoreboard.setLine(8, "§a");
						scoreboard.setLine(9, "§eⓉ Coins §7▎ §f" + Main.plugin.dataPlayers.get(p.getName()).getCoins() +".0 ✞");
						scoreboard.setLine(10, "§d ");
						if(Main.plugin.dataPlayers.get(p.getName()).getLang().equals("FR")){
							scoreboard.setLine(11, "§7Joueurs: §e" + d +"§6/50");
						}else if(Main.plugin.dataPlayers.get(p.getName()).getLang().equals("EN")){
							scoreboard.setLine(11, "§7Players: §e" + d +"§6/50");
						}else if(Main.plugin.dataPlayers.get(p.getName()).getLang().equals("ES")){
							scoreboard.setLine(11, "§7Jugadores: §e" + d +"§6/50");
						}
						scoreboard.setLine(12, "§7ID: §d#1_hub");
						scoreboard.setLine(13, "§6");
						scoreboard.setLine(14, "§3play.TolariaMC.fr");
						Main.plugin.boards.put(p, scoreboard);
						if(Main.plugin.dataPlayers.containsKey(p.getName())){
							if(Main.plugin.dataPlayers.get(p.getName()).getPart() != null){
								if(!isspec(p.getName())){
									checkpart(p.getName(), Main.plugin.dataPlayers.get(p.getName()).getPart());
								}
							}
						}
					}
				}
			}
		}, 2L);
	}
	
	public void editlinecoreboard(Player p, int line, String ChangeFR, String ChangeEN, String ChangeES){
		if(p != null){
			if(!p.isOnline()){
				return;
			}else{
				if(Main.plugin.boards.containsKey(p)){
					ScoreboardSign scoreboard = Main.plugin.boards.get(p);
					if(Main.plugin.dataPlayers.containsKey(p.getName())){
						PlayerData data = Main.plugin.dataPlayers.get(p.getName());
						if(data.getLang().equals("FR")){
							scoreboard.setLine(line, ChangeFR);
						}else if(data.getLang().equals("EN")){
							scoreboard.setLine(line, ChangeEN);
						}else{
							scoreboard.setLine(line, ChangeES);	
						}
					}else{
						scoreboard.setLine(line, ChangeFR);
					}
				}
			}
		}
		
	}
	 
	 
	 public void sendHeaderFooter(Player player, String header, String footer) {
		 Bukkit.getScheduler().scheduleSyncDelayedTask(Main.plugin,new Runnable(){
	          
             @Override
             public void run() {
			        IChatBaseComponent tabHeader = ChatSerializer.a("{\"text\": \"" + header + "\"}");
			        IChatBaseComponent tabFooter = ChatSerializer.a("{\"text\": \"" + footer + "\"}");
			        PacketPlayOutPlayerListHeaderFooter packet = new PacketPlayOutPlayerListHeaderFooter();
			        try
			        {
			            Field headerField = packet.getClass().getDeclaredField("a");
			            headerField.setAccessible(true);
			            headerField.set(packet, tabHeader);
			            headerField.setAccessible(false);
			            Field footerField = packet.getClass().getDeclaredField("b");
			            footerField.setAccessible(true);
			            footerField.set(packet, tabFooter);
			            footerField.setAccessible(false);
			        }
			        catch (Exception e)
			        {
			            e.printStackTrace();
			        }
		
			        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
             }
	 	},3L);
	}

	  public void freezeEntity(Entity en){
		     net.minecraft.server.v1_8_R3.Entity nmsEn = ((CraftEntity) en).getHandle();
		      NBTTagCompound compound = new NBTTagCompound();
		      nmsEn.c(compound);
		      compound.setByte("NoAI", (byte) 1);
		      nmsEn.f(compound);
		  }
	 
	 public void spawnnpc(Player p){
		 Bukkit.getScheduler().scheduleSyncDelayedTask(Main.plugin, new Runnable(){
				public void run(){
					 if(p != null){
						 if(!p.isOnline()){
							return; 
						 }else{
							 NPC npc = NpcAPI.createNPC("§8[NPC] 1q99FyR", "eyJ0aW1lc3RhbXAiOjE1MzQwNjY4MjgxMjEsInByb2ZpbGVJZCI6ImIwZDczMmZlMDBmNzQwN2U5ZTdmNzQ2MzAxY2Q5OGNhIiwicHJvZmlsZU5hbWUiOiJPUHBscyIsInNpZ25hdHVyZVJlcXVpcmVkIjp0cnVlLCJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMjA3ODcxZDFjOWI4YzI5MTJlMDkzZThlOWQ3YzYyZWU1MDRiYjc5MDFjYWQ2ZTM0OWFlNWMzNmYxZmExNTAyMSJ9fX0=", "h395Twa2eVv6fQazGbTBL+xBdENvHNeLwxsjsxn9JGwQ2mxuHd8xdp7OnE0bmYT7+/LsgFJbwPseTxZag/dYX5dc2WOK5rMFRvv7Exhh7i6ARLTmP6ADHp2xqXftZB+LAk/aVelCVXObT/IhcX+MwD8ueeAAlleEvpVVtXGuSA7+jilqUxhA3rbFqClgPerTCtICLNq0/STfLBzt3OHBiUk1YdNjR70CgGdjJj+zh2CW30XIDLeBnynaHcXCrj8igpt8dkMe3HqCy00Nfzdq7goSkB3d0Xdmf+1EYGK9gmM7n63QA8PigDFDYW49FHa2uSw5oFDgq5sq6KS2VcqG2h8ktYuId/BAqoDbJyG9/G//UGA6XfeO87ywK11iXdm7Qi0usWkORZ0RNFMwqVxvP84Mq9cJ/K8+bjMmeUUeSHkYt1r1sVNZLIoBMLkb/+Bm6cIKAB/ype9G6INuDeoel/Yt4icpsJVvR+JSULJNyWroZrQEiUd5FTowLirYd0nFmhBzNinN61XZnlnjLu0n3BshdRxKJFROW6ph6dNwcJBMBKO8IRynJlhXpKGirSGNtBj+inRgQEbCT59O7MLLNUhCeQLBlboGPYvA2FuEHYwbDnPi58/mfOW0qF9c2w6L0A1wezyUoIuTSQxTogLmnMjKvzPo35T/nkFOxU9KaOc=", new Location(p.getWorld(), 0.5, 151.06250, -36.5), false, p);
							 NPC npc2 = NpcAPI.createNPC("§8[NPC] 54qdffq", "eyJ0aW1lc3RhbXAiOjE1NjYzNDQ0MjgwMjUsInByb2ZpbGVJZCI6ImU3OTNiMmNhN2EyZjQxMjZhMDk4MDkyZDdjOTk0MTdiIiwicHJvZmlsZU5hbWUiOiJUaGVfSG9zdGVyX01hbiIsInNpZ25hdHVyZVJlcXVpcmVkIjp0cnVlLCJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYjRkMGM1MzY2Nzk0ZDc3YmZkYWY3NmMwMjhhOWY1ZDgyMjI0MWE0ODU2MDBjNzIzODY0OGEwZDFkY2U1ZThiYSJ9fX0=", "MnIFb3NbGewzh1XZ4vsv+xy4k8YreoCaLRGoUweztkIpF7RLwnvnb6PwM+NhDzpkMLZcxmRHKEdhBBMBRVgnfqVqibFIeT7IHDzKkxEPiL8+1Z0ypqKhlhGPKIklzzB4KO4OIrUiz77MhXp/lVoaSO5Aq8DKZVYYAQpx8qy6qaaAMUebOqf3zLHDjbOrcGT1mqoZRiWguoLofYgBFgrEuJQa8O6IDhlGmCtdTdzfNWxkCrrfiMcAAiMhoY8Up2v1WtfKBYCf08qeommcue95c/ADXWZ4y0S4F23K6FYonCppxQeKG31AR1jeqZMGt4VMz0/3aEKvARBB0IycC6nFvGDOsAd5eDaAFAXoM9SDFKUSkbJ1o21OvlciEsYvCv9Qyvi2WPxTFAYApSyHoZ0q/Ve8ZvSuuebYJ/9UIap6mev0TCzcxHnHEJzQq/xi1yJR4HO+d8wyvkINl44DNtXHRQ9bYRYMvCLN8RLHJ96KdnApTT1WzgSEJ0Wds0I53XrQrNK1o921zsP7j7ASfjPzAXuxp5rt10jJq+0IDzaxkofrRf55C6GmY44lJBkT77Xvp+5wn7OnfCS8VD/q43QxU9GKT7xi1T/2YsLyCPo1QT6qvYlbwhAfGo8bPQuojY8M10vCi+hExzdOuib1S4WuZ2mhyrilsZlHMomLnrt3WWs=", new Location(p.getWorld(), -2.5, 151.06250, -35.5), false, p);
							 NPC npc3 = NpcAPI.createNPC("§8[NPC] sfg23hg", "eyJ0aW1lc3RhbXAiOjE1NDE1MjMzODYzMzcsInByb2ZpbGVJZCI6ImMxZWQ5N2Q0ZDE2NzQyYzI5OGI1ODFiZmRiODhhMjFmIiwicHJvZmlsZU5hbWUiOiJ5b2xvX21hdGlzIiwic2lnbmF0dXJlUmVxdWlyZWQiOnRydWUsInRleHR1cmVzIjp7IlNLSU4iOnsidXJsIjoiaHR0cDovL3RleHR1cmVzLm1pbmVjcmFmdC5uZXQvdGV4dHVyZS9lOWI2ZDVkM2U1ODQ5N2VhZjhhZGQ5ZTBlOGMxMTE5N2M2MmE1ZmJmYjBmZmQ5ZjU1MDhlYWM4NmVmZWI4ZmZiIn19fQ==", "LGMrMWRI28++mvYmbBk8/R9J+Rp5NKgeAOzrLBtZWYUWQjkiehiQ5uHmcmXsa8lqqTnj2N1VObd+u+BZsDU0rg05zSZEhVX1dIpkm+TXOyly1aIlG3bGpP8FgG3jtohbsEdYHH2ir1JiWs8Hjs60ZO3mqI7oPSx2JysRQT/NC40ff0jmLqEcHj3WITb1ZvBP3LhRRUueG7xStoGrjViV9BE1eDuNMuSu7slf/99XY4zzmTdZFEEI2gmp5Liymdt3bmZPLagzs8Ni/0dOaYsHAYQ089jm4Hn4X+uKdmw8NpJEP5jVUipT7NbTBHDiwQG8ZeP54aYKpsiBG2mP30JJ1xNVv/bBrreWUzS6wJPPsW/Q4rpmSQCr+NYvGcB/WCEMJgnMnlqnOXfx6iU+A44rJgYowYUmO7qLszJ2AwZBzxBr/S4x9lAlxVQ2fNzHzE1u+h+EesknoCXqvcezLCoND/Nj194sIPyz63wB6iXd4FnWD6E7o7zj+p8zGuTbFb9yGUF0GTMiY+dtKAEdDpE4Rwi+qZS1j75jK9LUBymWLjDYqgfger0567aGNHmcOCP4KpxCXDNgMfB1g9YoIhN/o7AxiYyfx9J8X169Z0SRGH5NRU721PAEdFiE+ua7f1SEpsnLnuHAmYr0bGEx7rKW0O8DTphNEHKagsIu1amkBc4=", new Location(p.getWorld(), -5.5, 151.06250, -34.5), false, p);
							 NPC npc4 = NpcAPI.createNPC("§8[NPC] gff4sb2", "eyJ0aW1lc3RhbXAiOjE1NjYzNDQ2NTA3NTEsInByb2ZpbGVJZCI6ImIwZDRiMjhiYzFkNzQ4ODlhZjBlODY2MWNlZTk2YWFiIiwicHJvZmlsZU5hbWUiOiJ4RmFpaUxlUiIsInNpZ25hdHVyZVJlcXVpcmVkIjp0cnVlLCJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvN2ZmZDgzZjcyNTY1YzAyNDFjYTc3MjkwMTY0OTU0NjU0OGI5MWExOWQ2ZTIyMjVhMzM1ZDkxNzc4ZTNkY2M0MCJ9fX0=", "EoGUIQWkDvl+Xu2XQD+aKtOb/0AZxww4weBzXpyMWthW7UaysCxGjscXFY+h+SeSQKgPVbr4BrM+4+iNLiYmYfb4e6ikf9jDjZB1caIEYdm6h2SmHPMHzEhgwtmRVxzEETz1eacRqYAACwjJYuXlsSukHl2r0jVFdPrqoHs0OGjZ+krP/2OKF1Xys6tt2GfJr2grBCKQmmL2r+oBY9Rb37Auxnsww2ldFEtA3vBK2Be0H5d93tCkJfe42nqkdrBbvddHbRGeKGz/KJiyLY7j4L1W1kvvvLWl7DCFTPiPBrVhP65eGtZOoKwTe5VfEjUH7vTNjUT77UNgW+uRhWZnK/QhfT7HvVY0Rn81ZBGLiZUnf+q84zqQJo7dyFLV1XUIqyEVyt13DZyP4Jw5vWRhrSF9hpBSUbYXrOXe3vHXZZpf8X5u2HRn+rh76NVDzqOJcEl9NLwARH/AuwrHNSuiAVyr8zTmBc7Eu7DST4ofoi01W6y7XmOZQyY5c+j4dLy7pSDol/C4JIwQ4Qf0mUu6QYYhP0XCtywLQoGKy6VfLXTB9xLMzPSFLCMUDl6bQOyDidFJVlQTvN9tUphLoAEWngmQBeVUyilwPlBklhz3vR9g2fYN67Gkt2Ibns3MVTjN/2+cMrKMCs9/yXntFZz0x68HinqB5wA9hPW/binu2C8=", new Location(p.getWorld(), 0.5, 150.06250, -21.5), false, p);
							 NPC npc5 = NpcAPI.createNPC("§8[NPC] fbb56jh", "eyJ0aW1lc3RhbXAiOjE1NTkwNDM2MTQ4MjUsInByb2ZpbGVJZCI6Ijc1MTQ0NDgxOTFlNjQ1NDY4Yzk3MzlhNmUzOTU3YmViIiwicHJvZmlsZU5hbWUiOiJUaGFua3NNb2phbmciLCJzaWduYXR1cmVSZXF1aXJlZCI6dHJ1ZSwidGV4dHVyZXMiOnsiU0tJTiI6eyJ1cmwiOiJodHRwOi8vdGV4dHVyZXMubWluZWNyYWZ0Lm5ldC90ZXh0dXJlLzU1ZDQ0MmYwZWE5NGQ2ZDQ1YzMwYzUzNjUxY2ExZmRiMzc1MGI0Y2FlOTg5NDE2YWZiZDIxMmE0NGE0NGUxYWYifX19", "nm1nKjB92x1TpakqFoWfskj8UZr/GhHFSfF0u6fhtVsx9i4PWyWvWGatdWYnLe+9qVQPLJbXxojh1R4YtrlnLlXxdnwCeF0TkOytDguk5KM271BqGTy9AF4yh688roEYhj3JSBEQwngKgBwy/+FJ26oa9/N/JZf7Lkft/+Q20dWaz6b3hl3C2N3btT6WspBFvn48LUb/AzF249zos8gbL7RXHniweb2ohcXx9cB6TEeOPQJhDQBIGOQDP7Bc15xgbLoc9nr5Y3BpT30bmK+Jl/T0R5O5bYf93RKEr6KSOAryCNsn14tc+l29HLmDFZOENw8otgvecKsb2EykhJwucAeZZ4nJMl1pfRnR9PervFXNNonuzlLgLl2ODkvMfOwVKFf0uTQRmC46QWdh0l247kBArbas4MIZPUVyzPjPoJv5p2lQOSmT/BrMKp+Nt5a5Tjs/pTZQ/MeDCABmtNGKUvhMzvCHP5X63ilxYu3pQiYv/bYiqXxWnrAnT5GtpPFuBvgGfnptpmQMUDXcVAs8hQdRFOVz9JUeP3XIwVb+rYxrWN5M6k2hD54s5xCf7qxtthHhqfmNGtwWWhhrRmgnj8Bfua0ByEKMVmg6iS2BlaVwreZ+Gc7h/qQ+qEUWsBEdAtQv6v1VbgW12oOd+dCjhboeeL9GN9NOm/ldT/rnOTg=", new Location(p.getWorld(), 3.5, 151.06250, -35.5), false, p);
							 NPC npc6 = NpcAPI.createNPC("§8[NPC] mqh12p5", "eyJ0aW1lc3RhbXAiOjE1NjYyNjA5MzI5MDUsInByb2ZpbGVJZCI6ImVkY2I2NzNhNjUxZDRmYjg5NzgyNDc4MmM4MTJmYzdmIiwicHJvZmlsZU5hbWUiOiJTbGlvbiIsInNpZ25hdHVyZVJlcXVpcmVkIjp0cnVlLCJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNGEyNzY0YjljNjMwZjdkMTc0ZWU2NGQ2ZmU4YWQ0ZmY0NjZhNWIyYTZmY2U0MzNjMTIyZDM0YzNlYzhlNzVmNyIsIm1ldGFkYXRhIjp7Im1vZGVsIjoic2xpbSJ9fX19", "ZYbzAv+mdIApGRNtqQAIZQtp28dLxGkJjgPwU/wnd5MGSMFzgSgsIAnNI+W2XXFiIJLuoP6dTUkknOZxnABOHUVQHoanzboW+5y3VeHngWZ3d/Jn3sNFVddw0PU9We8ydPgmd0ETj/Xi7a+p313rwiw6G5rm5zVFdgCciIi91DkPJRmJuD8flRSYmOtDpza5yRN57ny9kR2L39xQ7a0nYUWs/4uiwPg8K2xO9q7rO1y0wBwMhhW1Fe6IrWU8HyodoRiFmS+20KMjKF53rGBhoORAYIikfozq9nV0aGt2dY0QnGw+m3D1QtBkXFOFsumQOEpZh3epb5R1lUnpJRYDXe/H6NyGd4Yk6NRVUd0mfVwuUMAiIhg4bf/JdJm24hFzHpPmkB5y23OBZpdOL0kn4CpTInWw98NH3WBSQHOGx+wp/A/2548WS3H0Gm0NdJIzeNChfqlzyLPtzJGFEVM3xWHRHIXvCzHp7LZRhweC7e2x/J8sGPfcLwG7ikRd1Y1tvI9v8qnuqWeSzhT4cdSt5OSM/az7/xiXiM13/dMkBxsWaQLBiBUcgsZF2bIHdKVmQO0NEQ6R4hHEktK9R0THhqfaglP36YQ9fTCcPafadVS8g4dgQ+cOVYtbqq5sVTIpxuvD2C0FuApkA+Mrp4DdWQDcuVhLHCviELXTjXdSO9I=", new Location(p.getWorld(), 6.5, 151.06250, -34.5), false, p);
							 
							 ArrayList<Integer> ids = new ArrayList<Integer>();
							 ids.add(npc.getID().intValue());
							 ids.add(npc2.getID().intValue());
							 ids.add(npc3.getID().intValue());
							 ids.add(npc4.getID().intValue());
							 ids.add(npc5.getID().intValue());
							 ids.add(npc6.getID().intValue());
							 Main.plugin.npc.remove(p.getName());
							 Main.plugin.npc.put(p.getName(), ids);
							 Bukkit.getScheduler().scheduleSyncDelayedTask(Main.plugin, new Runnable(){
									public void run(){
										npc.setAction(Action.REMOVE_PLAYER);
										npc2.setAction(Action.REMOVE_PLAYER);
										npc3.setAction(Action.REMOVE_PLAYER);
										npc4.setAction(Action.REMOVE_PLAYER);
										npc5.setAction(Action.REMOVE_PLAYER);
										npc6.setAction(Action.REMOVE_PLAYER);
									}
							 }, 4L);
						 }
					 }
				}
		 }, 5L);
	 }
	 
	 
	public void updatelanguage(Player p, String lang) {
		p.closeInventory();
		if(lang == null){
			lang="FR";
		}
		if(lang.equals("null") || lang.equals("nostring")){
			lang="FR";
		}
		INVENTORYAPI INVAPI = new INVENTORYAPI();
		ItemStack n = new ItemStack(Material.COMPASS, 1);
		ItemMeta m = n.getItemMeta();
        if (lang.equals("FR")){
        	p.getInventory().setItem(8, INVAPI.creatcustomitem(Material.NETHER_STAR, false, Enchantment.ARROW_FIRE, "§cHubs §8• §7(Clic-droit)"));
        	p.getInventory().setItem(7, INVAPI.createItemhead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYmY3NWQxYjc4NWQxOGQ0N2IzZWE4ZjBhN2UwZmQ0YTFmYWU5ZTdkMzIzY2YzYjEzOGM4Yzc4Y2ZlMjRlZTU5In19fQ==", "§eBoutique §8• §7(Clic-droit)", " "));
        	sendHeaderFooter(p, "§7• §3§lTOLARIA§f§lMC §7•\n§fBienvenue sur le serveur §eTolariaMC\n", "§0 \n§7• §3§lGUIDES§r §fet §9§lMODS§r §fsont §bdisponibles §fsi besoin... §7•");
			m.setDisplayName("§bMenu principal §8• §7(Clic-droit)");
			p.getInventory().setItem(1, INVAPI.Itemlanguage("GIVE", p, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNTEyNjlhMDY3ZWUzN2U2MzYzNWNhMWU3MjNiNjc2ZjEzOWRjMmRiZGRmZjk2YmJmZWY5OWQ4YjM1Yzk5NmJjIn19fQ==", lang, "GIVE", "GIVE", "GIVE"));
        }else if (lang.equals("EN")){
        	p.getInventory().setItem(8, INVAPI.creatcustomitem(Material.NETHER_STAR, false, Enchantment.ARROW_FIRE, "§cHubs §8• §7(Right-click)"));
        	p.getInventory().setItem(7, INVAPI.createItemhead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYmY3NWQxYjc4NWQxOGQ0N2IzZWE4ZjBhN2UwZmQ0YTFmYWU5ZTdkMzIzY2YzYjEzOGM4Yzc4Y2ZlMjRlZTU5In19fQ==", "§eShop §8• §7(Right-click)", " "));
        	sendHeaderFooter(p, "§7• §3§lTOLARIA§f§lMC §7•\n§fWelcome to the server §eTolariaMC\n", "§0 \n§7• §3§lHELPERS§r §fand §9§lMODS§r §fare §bavailable §fif necessary... §7•");
			m.setDisplayName("§bMain menu §8• §7(Right-click)");
			p.getInventory().setItem(1, INVAPI.Itemlanguage("GIVE", p, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvODc5ZDk5ZDljNDY0NzRlMjcxM2E3ZTg0YTk1ZTRjZTdlOGZmOGVhNGQxNjQ0MTNhNTkyZTQ0MzVkMmM2ZjlkYyJ9fX0=", lang, "GIVE", "GIVE", "GIVE"));
        }else if (lang.equals("ES")){
        	p.getInventory().setItem(8, INVAPI.creatcustomitem(Material.NETHER_STAR, false, Enchantment.ARROW_FIRE, "§cHubs §8• §7(Haga-clic)"));
        	p.getInventory().setItem(7, INVAPI.createItemhead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYmY3NWQxYjc4NWQxOGQ0N2IzZWE4ZjBhN2UwZmQ0YTFmYWU5ZTdkMzIzY2YzYjEzOGM4Yzc4Y2ZlMjRlZTU5In19fQ==", "§eTienda §8• §7(Haga-clic)", " "));
			sendHeaderFooter(p, "§7• §3§lTOLARIA§f§lMC §7•\n§fBienvenido al servidor de §eTolariaMC\n", "§0 \n§7• §3§lGUIAS§r §fy §9§lMODS§r §festán §bdisponibles §fsi es necesario... §7•");
			m.setDisplayName("§bMenú principal §8• §7(Haga-clic)");
			p.getInventory().setItem(1, INVAPI.Itemlanguage("GIVE", p, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMzJiZDQ1MjE5ODMzMDllMGFkNzZjMWVlMjk4NzQyODc5NTdlYzNkOTZmOGQ4ODkzMjRkYThjODg3ZTQ4NWVhOCJ9fX0=", lang, "GIVE", "GIVE", "GIVE"));
        }
		n.setItemMeta(m);
		p.getInventory().setItem(0, n);
		String langcurrent = null;
		
		if(Main.plugin.dataPlayers.containsKey(p.getName())){
			langcurrent = Main.plugin.dataPlayers.get(p.getName()).getLang();
		}
		if(lang != null || !lang.equals("FR")){
			Main.plugin.dataPlayers.get(p.getName()).setLang(lang);
		}
		if (Main.plugin.boards.containsKey(p)){
			String COLORSCORE = returngrade(p.getName());
			ScoreboardSign scoreboard = Main.plugin.boards.get(p);
			if(lang.equals("FR")){
				scoreboard.setLine(6, "§7Pseudo: §b" + p.getName());
				scoreboard.setLine(7, "§7Grade §8➥ " + COLORSCORE);
				scoreboard.setLine(11, "§7Joueurs: §e" + Bukkit.getOnlinePlayers().size() +"§6/50");
    		}else if(lang.equals("EN")){
    			scoreboard.setLine(6, "§7Pseudo: §b" + p.getName());
    			scoreboard.setLine(7, "§7Rank §8➥ " + COLORSCORE);
    			scoreboard.setLine(11, "§7Players: §e" + Bukkit.getOnlinePlayers().size() +"§6/50");
    		}else if(lang.equals("ES")){
    			scoreboard.setLine(6, "§7Seudónimo: §b" + p.getName());
    			scoreboard.setLine(7, "§7Rango §8➥ " + COLORSCORE);
    			scoreboard.setLine(11, "§7Jugadores: §e" + Bukkit.getOnlinePlayers().size() +"§6/50");
    		}
		}
		if(!langcurrent.equals(lang)){
				String getinfo = Mysqlgetinfo(p.getName(), "Langage", "lange");
				if(!(getinfo.equals("nostring") || getinfo.equals("null") || getinfo.isEmpty())){
					Mysqlupdate(p.getName(), "Langage", "lange", lang);
				}else{
					try{
						final Connection connection = DatabaseManager.playersinfo.getDatabaseAccess().getConnection();
						final PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO Langage (name, lange) VALUES (?,?)");
						preparedStatement.setString(1, p.getName());
						preparedStatement.setString(2, lang);
						preparedStatement.executeUpdate();
						connection.close();
					}catch(SQLException e){
						e.printStackTrace();
					}
				}
			}
		
		}

	
	public void stoparcher(Player p){
		if(Main.plugin.GAMEARCHER){
			if(Main.plugin.ARCHER.containsKey(p.getName())){
				p.getInventory().clear();
				Main.plugin.GAMEARCHER = false;
				p.sendMessage(" ");
				p.sendMessage("§f§m--§8§m-----§7- §f§lRECAPITULATIF -§8§m-----§f§m--");
				p.sendMessage(" ");
				p.sendMessage("§7• §fCible(s) Validée(s) §8▎ §eTolariaCoin(s) §7+§6"+(Main.plugin.ARCHER.get(p.getName())*2)+" §6✞");
				p.sendMessage("§7• §fCible(s) Touchée(s) §8▎ §c#"+Main.plugin.ARCHER.get(p.getName()));
				p.sendMessage(" ");
				p.sendMessage("§f§m--§8§m-----§7- §f§lRECAPITULATIF -§8§m-----§f§m--");
				p.sendMessage(" ");
				if (Main.plugin.ARCHER.get(p.getName()) > 0){
					if(Main.plugin.dataPlayers.containsKey(p.getName())){
						int coins = Integer.parseInt(Main.plugin.dataPlayers.get(p.getName()).getCoins()) + (Main.plugin.ARCHER.get(p.getName())*2);
						Main.plugin.dataPlayers.get(p.getName()).setCoins(coins + "");
						String c = Main.plugin.dataPlayers.get(p.getName()).getCoins();
						editlinecoreboard(p, 9, "§eⓉ Coins §7▎ §f" + c +".0 ✞", "§eⓉ Coins §7▎ §f" + c +".0 ✞", "§eⓉ Coins §7▎ §f" + c +".0 ✞");
						Mysqlupdate(p.getName(), "playerinfo", "coins", Main.plugin.dataPlayers.get(p.getName()).getCoins()+ "");
						
					}	
						
				}
				Main.plugin.ARCHER.remove(p.getName());
				for(Player online : Bukkit.getOnlinePlayers()){
				     p.showPlayer(online);
				}
				p.playSound(p.getLocation(), Sound.WOOD_CLICK, 2, 1);
				p.teleport(new Location(Bukkit.getWorld("world_"), 0.5, 153, 0.5, 180, 0));
				ItemStack n = new ItemStack(Material.COMPASS, 1);
				ItemMeta m = n.getItemMeta();
				String lang = Main.plugin.dataPlayers.get(p.getName()).getLang();
				if(lang == null){
					lang = "FR";
				}
		        if (lang.equals("FR")){
		        	p.getInventory().setItem(8, INVAPI.creatcustomitem(Material.NETHER_STAR, false, Enchantment.ARROW_FIRE, "§cHubs §8• §7(Clic-droit)"));
		        	p.getInventory().setItem(7, INVAPI.createItemhead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYmY3NWQxYjc4NWQxOGQ0N2IzZWE4ZjBhN2UwZmQ0YTFmYWU5ZTdkMzIzY2YzYjEzOGM4Yzc4Y2ZlMjRlZTU5In19fQ==", "§eBoutique §8• §7(Clic-droit)", " "));
		        }else if (lang.equals("EN")){
		        	p.getInventory().setItem(8, INVAPI.creatcustomitem(Material.NETHER_STAR, false, Enchantment.ARROW_FIRE, "§cHubs §8• §7(Right-click)"));
		        	p.getInventory().setItem(7, INVAPI.createItemhead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYmY3NWQxYjc4NWQxOGQ0N2IzZWE4ZjBhN2UwZmQ0YTFmYWU5ZTdkMzIzY2YzYjEzOGM4Yzc4Y2ZlMjRlZTU5In19fQ==", "§eShop §8• §7(Right-click)", " "));
		        }else if (lang.equals("ES")){
		        	p.getInventory().setItem(8, INVAPI.creatcustomitem(Material.NETHER_STAR, false, Enchantment.ARROW_FIRE, "§cHubs §8• §7(Haga-clic)"));
		        	p.getInventory().setItem(7, INVAPI.createItemhead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYmY3NWQxYjc4NWQxOGQ0N2IzZWE4ZjBhN2UwZmQ0YTFmYWU5ZTdkMzIzY2YzYjEzOGM4Yzc4Y2ZlMjRlZTU5In19fQ==", "§eTienda §8• §7(Haga-clic)", " "));
		        }
		        m.setDisplayName("§bMenu principal §8• §7(Clic-droit)");
		        p.getInventory().setItem(1, INVAPI.Itemlanguage("GIVE", p, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNTEyNjlhMDY3ZWUzN2U2MzYzNWNhMWU3MjNiNjc2ZjEzOWRjMmRiZGRmZjk2YmJmZWY5OWQ4YjM1Yzk5NmJjIn19fQ==", lang, "GIVE", "GIVE", "GIVE"));
				if(Main.plugin.dataPlayers.containsKey(p.getName())){
					if (Main.plugin.dataPlayers.get(p.getName()).getLang().equals("EN")){
						m.setDisplayName("§bMain menu §8• §7(Right-click)");
						p.getInventory().setItem(1, INVAPI.Itemlanguage("GIVE", p, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvODc5ZDk5ZDljNDY0NzRlMjcxM2E3ZTg0YTk1ZTRjZTdlOGZmOGVhNGQxNjQ0MTNhNTkyZTQ0MzVkMmM2ZjlkYyJ9fX0=", lang, "GIVE", "GIVE", "GIVE"));
					}else if (Main.plugin.dataPlayers.get(p.getName()).getLang().equals("ES")){
						m.setDisplayName("§bMenú principal §8• §7(Haga-clic)");
						p.getInventory().setItem(1, INVAPI.Itemlanguage("GIVE", p, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMzJiZDQ1MjE5ODMzMDllMGFkNzZjMWVlMjk4NzQyODc5NTdlYzNkOTZmOGQ4ODkzMjRkYThjODg3ZTQ4NWVhOCJ9fX0=", lang, "GIVE", "GIVE", "GIVE"));
					}
				}
				n.setItemMeta(m);
				p.getInventory().setItem(0, n);
				
			}else{
				return;
			}
		}
		return;
	}
	
	public void startarcher(Player p) {
		if(Main.plugin.GAMEARCHER){
			p.sendMessage("§6(Stand) §7➠ §cUne partie est déjà en cours...");
			p.playSound(p.getLocation(), Sound.WOLF_GROWL, 5, 1);
			return;
			
		}else{
			Main.plugin.GAMEARCHER = true;
			Main.plugin.ARCHER.put(p.getName(), 0);
			p.getInventory().clear();
			p.teleport(new Location(p.getWorld(), 17.5, 186, -64.5));
			p.playSound(p.getLocation(), Sound.NOTE_PLING, 2, 1);
			p.sendMessage(" ");
			p.sendMessage("              §7➢ §f§lTIR A L'ARC");
			p.sendMessage(" ");
			p.sendMessage("§7➠ §7Votre objectif: Toucher un maximum de cibles,");
			p.sendMessage("§7➠§7à l'aide de votre arc qui vous est donné.");
			p.sendMessage(" ");
			p.sendMessage("§c§o(N'oubliez pas de bien regarder autour de vous)");
			p.sendMessage(" ");
			p.setItemInHand(INVAPI.createItem(Material.STICK, 1, 0, "§eArc §8• §7(Clic-droit)", " "));
			int slot = p.getInventory().getHeldItemSlot();
			if(slot==8){
				slot=7;
			}else{
				slot++;
			}
			p.getInventory().setItem(slot, INVAPI.createItem(Material.ARROW, 10, 0, "§aMunition(s) §8• §7(Clic-droit)", " "));
			if(!Main.plugin.GAMEARCHER){
				return;
			}
			if(!Main.plugin.ARCHER.containsKey(p.getName())){
				return;
			}
			Bukkit.getScheduler().scheduleSyncDelayedTask(Main.plugin, new Runnable(){
				public void run(){
					if(!Main.plugin.GAMEARCHER){
						return;
					}
					if(!Main.plugin.ARCHER.containsKey(p.getName())){
						return;
					}
					p.sendTitle("§a③", " ");
					p.playSound(p.getLocation(), Sound.NOTE_PLING, 2, 1);
					Bukkit.getScheduler().scheduleSyncDelayedTask(Main.plugin, new Runnable(){
						public void run(){
							if(!Main.plugin.GAMEARCHER){
								return;
							}
							if(!Main.plugin.ARCHER.containsKey(p.getName())){
								return;
							}
							p.sendTitle("§e②", " ");
							p.playSound(p.getLocation(), Sound.NOTE_PLING, 2, 1);
							Bukkit.getScheduler().scheduleSyncDelayedTask(Main.plugin, new Runnable(){
								public void run(){
									if(!Main.plugin.GAMEARCHER){
										return;
									}
									if(!Main.plugin.ARCHER.containsKey(p.getName())){
										return;
									}
									p.sendTitle("§c①", " ");
									p.playSound(p.getLocation(), Sound.NOTE_PLING, 2, 1);
									RepeatingTask repeatingTask = new RepeatingTask(Main.plugin, 10, 30) {
										int i = 0;
										Location loc = new Location(p.getWorld(), 17.5, 186, -64.5);
										@Override
							            public void run() {
											if(as != null){
												if(!as.isDead()){
													as.remove();
												}
											}
											if(!Main.plugin.GAMEARCHER){
												canncel();
												return;
											}
											if(!Main.plugin.ARCHER.containsKey(p.getName())){
												canncel();
												return;
											}
											if(i>=10){
												stoparcher(p);
												canncel();
												return;
											}
											loc = new Location(p.getWorld(), 17.5, 186, -64.5);
											i++;
											Random r = new Random();
											int rand = r.nextInt(3);
											if(rand==0){
												loc.setY(loc.getY()+6);
											}else if(rand==1){
												loc.setY(loc.getY()+2);
											}else{
												loc.setY(loc.getY()+5);
												loc.setX(loc.getX()+3);
											}
											rand = r.nextInt(4);
											if(rand==0){
												loc.setZ(loc.getZ()+6);
											}else if(rand==1){
												loc.setZ(loc.getZ()-6);
											}else if(rand==2){
												loc.setZ(loc.getZ()+2);
												loc.setX(loc.getX()+5);
											}else{
												loc.setZ(loc.getZ()-2);
												loc.setX(loc.getX()+6);
											}
											as = (ArmorStand) p.getWorld().spawn(loc, ArmorStand.class);
											as.setHelmet(INVAPI.createItemhead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvODZmY2FlZmExOTY2OWQ4YmUwMmNmNWJhOWE3ZjJjZjZkMjdlNjM2NDEwNDk2ZmZjZmE2MmIwM2RjZWI5ZDM3OCJ9fX0=", "§eTIR", " "));
											as.setVisible(false);
											as.setBasePlate(false);
											as.setGravity(false);									
											as.setCanPickupItems(false);
											as.setCustomNameVisible(true);
											as.setCustomName("§eTIR");
										}
									};
									
								}
							},20L);
						}
					},20L);
				}
			},65L);
			
			
			
		}
		
	}
		
}
	
