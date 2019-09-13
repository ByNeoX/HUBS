package mlg.byneox.tc.Listeners;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.messaging.PluginMessageRecipient;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;

import mlg.byneox.tc.Main;
import mlg.byneox.tc.Mysql.DatabaseManager;
import mlg.byneox.tc.Mysql.PlayerData;
import mlg.byneox.tc.api.INVENTORYAPI;
import mlg.byneox.tc.api.TCAPI;


public class GUI implements Listener{
	
	TCAPI API = new TCAPI();
	INVENTORYAPI INVAPI = new INVENTORYAPI();
	private String[] jokes = new String[] {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10" };
	private static Random random = new Random();

	public String getRandomJoke() {
		return jokes[random.nextInt(jokes.length)];
		}
	
	public void PARTCLICK(String Part, Player p, String NEWTASK){
		PARTICLESHAPEAPI API = new PARTICLESHAPEAPI();
		if (!Part.equals("null")){
			if(NEWTASK.equals("oui")){
				API.displaypart(p);
			}
		}
	}
	
	
	public void Mysql(String Part, Player p, String NEWTASK)
	  {
		String result = API.Mysqlgetinfo(p.getName(), "NetworkCos", "selectedpart");
		if (!(result.equals("nostring") || result.isEmpty())) {
			API.Mysqlupdate(p.getName(), "NetworkCos", "selectedpart", Part);
			String reset = "null";
			if(Part.equals("null")){
				Part = "selectedpart";
				reset = "true";
			}
			if(reset.equals("true")){
				API.Mysqlupdate(p.getName(), "NetworkCos", Part, "null");
			}else{
				API.Mysqlupdate(p.getName(), "NetworkCos", Part, "true");
			}
		}else{
			String Cercle = "null";
			String Sphere = "null";
			String Multicolor = "null";
			String Coeur = "null";
			String Magma = "null";
			String Nuage = "null";
			String Firework = "null";
			try{
				final Connection connection = DatabaseManager.playersinfo.getDatabaseAccess().getConnection();
				final PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO NetworkCos (name, selectedpart, Cercle, Sphere, Multicolor, Coeur, Magma, Nuage, Firework) VALUES (?,?,?,?,?,?,?,?,?)");
				preparedStatement.setString(1, p.getName());
				preparedStatement.setString(2, Part);
				preparedStatement.setString(3, Cercle);
				preparedStatement.setString(4, Sphere);
				preparedStatement.setString(5, Multicolor);
				preparedStatement.setString(6, Coeur);
				preparedStatement.setString(7, Magma);
				preparedStatement.setString(8, Nuage);
				preparedStatement.setString(9, Firework);
				preparedStatement.executeUpdate();
				connection.close();
			}catch(SQLException e){
				e.printStackTrace();
				return;
			}
			String reset = "null";
			if(Part.equals("null")){
				Part = "selectedpart";
				reset = "true";
			}
			if(reset.equals("true")){
				API.Mysqlupdate(p.getName(), "NetworkCos", Part, "null");
			}else{
				API.Mysqlupdate(p.getName(), "NetworkCos", Part, "true");
			}
		}


	  }
	
	
	public void supprpart(Player p){
		Bukkit.getScheduler().scheduleSyncDelayedTask(Main.plugin, new Runnable()
		{
			public void run(){
				if(Main.plugin.dataPlayers.containsKey(p.getName())){
					Main.plugin.dataPlayers.get(p.getName()).setPart(null);
				}
				Mysql("null", p, "oui");
				p.sendMessage("§e(Boutique) §7➠ §cDésélection effectuée... §7(No_particule)");
				
				p.playSound(p.getLocation(), Sound.NOTE_BASS, 5, 1);
				p.closeInventory();
			}
		},4L);
	}
	
	
	
	public void checkpart(Player p, String part){
		Mysql(part, p, "oui");
		p.playSound(p.getLocation(), Sound.ORB_PICKUP, 5, 1);
		p.sendMessage("§e(Boutique) §7➠ §aSélection effectuée... §7(§f"+part+"§7 ✔)");
		if(Main.plugin.dataPlayers.containsKey(p.getName())){
			if(Main.plugin.dataPlayers.get(p.getName()).getPart() == null){
				Main.plugin.dataPlayers.get(p.getName()).setPart(part);
				Bukkit.getScheduler().scheduleSyncDelayedTask(Main.plugin, new Runnable()
				{
					public void run(){
						PARTCLICK(part, p, "oui");
					}
				}, 5L);
				return;
			}
			Main.plugin.dataPlayers.get(p.getName()).setPart(part);
			return;
		}else{
			PlayerData data = new PlayerData();
			data.setPart(part);
			Main.plugin.dataPlayers.put(p.getName(), data);
			Bukkit.getScheduler().scheduleSyncDelayedTask(Main.plugin, new Runnable()
			{
				public void run(){
					PARTCLICK(part, p, "oui");
				}
			}, 5L);
		}
	}
	
	public void replacepart(Player p, String part, String type){
		Bukkit.getScheduler().scheduleSyncDelayedTask(Main.plugin, new Runnable()
		{
			public void run(){
				Mysql(part, p, "oui");
				p.playSound(p.getLocation(), Sound.ORB_PICKUP, 5, 1);
				p.sendMessage("§e(Boutique) §7➠ §aAchat effectuée... §7(§f"+part+"§7 ✔)");
				if(Main.plugin.dataPlayers.containsKey(p.getName())){
					if(Main.plugin.dataPlayers.get(p.getName()).getPart() == null){
						Main.plugin.dataPlayers.get(p.getName()).setPart(part);
						Bukkit.getScheduler().scheduleSyncDelayedTask(Main.plugin, new Runnable()
						{
							public void run(){
								PARTCLICK("Cercle", p, "oui");
							}
						}, 5L);
						return;
					}
					Main.plugin.dataPlayers.get(p.getName()).setPart(part);
					return;
				}else{
					PlayerData data = new PlayerData();
					data.setPart(part);
					Main.plugin.dataPlayers.put(p.getName(), data);
					Bukkit.getScheduler().scheduleSyncDelayedTask(Main.plugin, new Runnable()
					{
						public void run(){
							PARTCLICK("Cercle" , p, "oui");
						}
					}, 5L);
				}
			}
		}, 3L);
	}
	
	public ItemStack item(String Name, Material mat, boolean enchant, boolean has, String coins){
		ItemStack i = new ItemStack(mat);
		if(has){
			i = new ItemStack(mat, 1, (short)13);
		}else{
			i = new ItemStack(mat, 1, (short)14);
		}
		ItemMeta met = i.getItemMeta();
		met.setDisplayName(Name);
		ArrayList<String> lore2 = new ArrayList<String>();
		if(enchant){
			met.addEnchant(Enchantment.DURABILITY, 1, true);
			met.addItemFlags(ItemFlag.HIDE_ENCHANTS);
			lore2.add("§8Type: Particule");
			lore2.add(" ");
			lore2.add("§8•§7 Acheté: §2✔");
			lore2.add(" ");
			lore2.add("§8•§7 Prix d'achat: §e"+coins+" ✞");
			lore2.add("§8•§7 Statut: §a§lOBTENU");
			lore2.add("§8•§7 Sélectioné: §a§lOUI");
			lore2.add(" ");
			lore2.add("§8■ §fClic-droit §7» §a(Désélectionner)");
		}else{
			if(has){
				lore2.add("§8Type: Particule");
				lore2.add(" ");
				lore2.add("§8•§7 Acheté: §2✔");
				lore2.add(" ");
				lore2.add("§8•§7 Prix d'achat: §e"+coins+" ✞");
				lore2.add("§8•§7 Statut: §a§lOBTENU");
				lore2.add("§8•§7 Sélectioné: §c§lNON");
				lore2.add(" ");
				lore2.add("§8■ §fClic-gauche §7» §a(Sélectionner)");
			}else{
				lore2.add("§8Type: Particule");
				lore2.add(" ");
				lore2.add("§8•§7 Acheté: §4✖");
				lore2.add(" ");
				lore2.add("§8•§7 Prix d'achat: §e"+coins+" ✞");
				lore2.add("§8•§7 Statut: §c§lNON-OBTENU");
				lore2.add(" ");
				lore2.add("§8■ §fClic-gauche §7» §a(Acheter)");
			}
		}
		met.setLore(lore2);
		i.setItemMeta(met);
		
		return i;
		
	}
	
	@EventHandler
	public void onclick(InventoryClickEvent e){
		if (e.getClickedInventory() == null){
			return;
		}
		if(e.getClickedInventory() == e.getWhoClicked().getInventory()){
			if(!Main.plugin.COMMANDEEXECUTE.containsKey(e.getWhoClicked().getName())){
				e.setCancelled(true);
			}
			return;
		}
		if (e.getClickedInventory().getName().equals("§8[Report] » Liste")){
			e.setCancelled(true);
			if(e.getCurrentItem().getType() == Material.PAPER){
				int id = (e.getSlot());
				try{
					final Connection connection = DatabaseManager.playersinfo.getDatabaseAccess().getConnection();
					final PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM reports WHERE idreports = ?");
					preparedStatement.setString(1, Main.plugin.idreports.get(id));
					preparedStatement.executeUpdate();
					connection.close();
				}catch(SQLException e3){
					e3.printStackTrace();
				}
				e.getWhoClicked().closeInventory();
				return;
			}else{
				return;
			}
		}else if (e.getClickedInventory().getName().contains("§8[Hubs] » ")){
			e.setCancelled(true);
			if(!(e.getSlot() == 22 || e.getSlot() == 24)){
				return;
			}else if (e.getSlot() == 22){
				ByteArrayDataOutput out = ByteStreams.newDataOutput();
				out.writeUTF("Connect");
				out.writeUTF("HUB_02");
				((PluginMessageRecipient) e.getWhoClicked()).sendPluginMessage(Main.plugin, "BungeeCord", out.toByteArray());
			}else{
				ByteArrayDataOutput out = ByteStreams.newDataOutput();
				out.writeUTF("Connect");
				out.writeUTF("HUB_03");
				((PluginMessageRecipient) e.getWhoClicked()).sendPluginMessage(Main.plugin, "BungeeCord", out.toByteArray());
			}
		}else if (e.getClickedInventory().getName().contains("§8[Report] » ")){
			e.setCancelled(true);
			String reported = e.getClickedInventory().getName();
			reported = reported.substring(13);
			if(e.getSlot() == 11 || e.getSlot() == 12 || e.getSlot() == 13 || e.getSlot() == 14 || e.getSlot() == 15){
				e.getWhoClicked().sendMessage("§3(Report) §7➠ §aReport effectué avec succès... §7("+reported+"§7)");
				String MOTIF = "AUTRE";
				if(e.getCurrentItem().getType() == Material.BOOK_AND_QUILL){
					MOTIF= "CHAT";
				}else if(e.getCurrentItem().getType() == Material.IRON_AXE){
					MOTIF= "COMBAT";
				}else if(e.getCurrentItem().getType() == Material.LAVA_BUCKET){
					MOTIF= "ANTI-JEU";
				}else if(e.getCurrentItem().getType() == Material.FEATHER){
					MOTIF= "MOUVEMENT";
				}
				String code = null;
				String r = null;
				for(int i = 0; i<10; i++){
					r =getRandomJoke();
					if(code==null){
						code = r;
					}else{
						code= code+""+r;
					}
				}
				Date now = new Date();
				now.setHours(now.getHours()+2);
				SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
				try{
					final Connection connection = DatabaseManager.playersinfo.getDatabaseAccess().getConnection();
					final PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO reports (namesender, nametarget, motif, hours, server, idreports) VALUES (?,?,?,?,?,?)");
					preparedStatement.setString(1, e.getWhoClicked().getName());
					preparedStatement.setString(2, reported);
					preparedStatement.setString(3, MOTIF);
					preparedStatement.setString(4, format.format(now).toString());
					preparedStatement.setString(5, "TNT_HUB");
					preparedStatement.setString(6, code);
					preparedStatement.executeUpdate();
					connection.close();
				}catch(SQLException e3){
					e3.printStackTrace();
				}
				e.getWhoClicked().closeInventory();
			}
		}else if (e.getClickedInventory().getName().equals("§8[Langage] » Sélection") || e.getClickedInventory().getName().equals("§8[Language] » Selection") || e.getClickedInventory().getName().equals("§8[Idioma] » Selección")){
			e.setCancelled(true);
			if(e.getSlot() == 20){
				API.updatelanguage((Player) e.getWhoClicked(), "ES");
			}else if(e.getSlot() == 22){
				API.updatelanguage((Player) e.getWhoClicked(), "FR");
			}else if(e.getSlot() == 24){
				API.updatelanguage((Player) e.getWhoClicked(), "EN");
			}
		}else if (e.getClickedInventory().getName().equals("§8[Menu Principal] » Accueil") || e.getClickedInventory().getName().equals("§8[Main Menu] » Home") || e.getClickedInventory().getName().equals("§8[Menú principal] » Bienvenida")){
			e.setCancelled(true);
			if(e.getClick().isRightClick()){
				return;
			}else{
				if(e.getSlot() == 48){
					e.getWhoClicked().closeInventory();
					e.getWhoClicked().sendMessage("§b(Twitter) §7➠ §fTwitter.com/TolariaV2");
				}else if(e.getSlot() == 49){
					e.getWhoClicked().closeInventory();
					e.getWhoClicked().sendMessage("§9(Discord) §7➠ §fDiscord.io/TolariaMC");
				}else if(e.getSlot() == 50){
					e.getWhoClicked().closeInventory();
					e.getWhoClicked().sendMessage("§d(Instagram) §7➠ §fAucun");
				}else if(e.getSlot() == 21 || e.getSlot() ==  20){
					e.getWhoClicked().closeInventory();
					INVENTORYAPI INVAPI = new INVENTORYAPI();
					if(e.getSlot() == 21){
						INVAPI.load((Player) e.getWhoClicked(), Main.plugin.dataPlayers.get(e.getWhoClicked().getName()).getLang(), "MLG_HUB");
					}else if(e.getSlot() ==  20){
						INVAPI.load((Player) e.getWhoClicked(), Main.plugin.dataPlayers.get(e.getWhoClicked().getName()).getLang(), "TNT_HUB");
					}
				}
			}
		}else if (e.getClickedInventory().getName().contains("§8[Grade] » ")){
			e.setCancelled(true);
			if(e.getCurrentItem().getType() == Material.AIR || e.getCurrentItem().getType() == Material.STAINED_GLASS_PANE){
				return;
			}
			String f = e.getClickedInventory().getName().substring(12);
			if(f!=null){
				Player d = Bukkit.getPlayerExact(f);
				if (d.isOnline()){
					String Grade = "Joueur";
					if(e.getSlot() == 4){
						Grade = "Admin";
					}else if(e.getSlot() == 5){
						Grade = "Owner";
					}else if(e.getSlot() == 10){
						Grade = "Resp";
					}else if(e.getSlot() == 11){
						Grade = "Staff";
					}else if(e.getSlot() == 12){
						Grade = "Mod";
					}else if(e.getSlot() == 13){
						Grade = "Guide";
					}else if(e.getSlot() == 14){
						Grade = "Famous";
					}else if(e.getSlot() == 15){
						Grade = "VIP+";
					}else if(e.getSlot() == 16){
						Grade = "VIP";
					}
					
					if(Main.plugin.dataPlayers.containsKey(f)){
						Main.plugin.dataPlayers.get(f).setGrade(Grade);
					}else{
						PlayerData data = new PlayerData();
						data.setGrade(Grade);
					}
					
					if(e.getSlot() == 22){
						if(Main.plugin.dataPlayers.containsKey(f)){
							Main.plugin.dataPlayers.get(f).setGrade(null);
						}
					}
					API.editlinecoreboard((Player) e.getWhoClicked(), 7, "§7Grade §8➥ " + API.returngrade(e.getWhoClicked().getName()), "§7Rank §8➥ " + API.returngrade(e.getWhoClicked().getName()), "§7Rango §8➥ " + API.returngrade(e.getWhoClicked().getName()));
					e.getWhoClicked().closeInventory();
					String COLORSCORE = Main.plugin.dataPlayers.get(f).getGrade();
					if (COLORSCORE == null){
						COLORSCORE = "Joueur";
					}
					API.Mysqlupdate(f, "playerinfo", "grade", COLORSCORE);
				}
			}
		}else if (e.getClickedInventory().getName().equals("§8[Boutique] » Menu") || e.getClickedInventory().getName().equals("§8[Shop] » Home") || e.getClickedInventory().getName().equals("§8[Tienda] » Bienvenida")){
			if (e.getWhoClicked() == null){
				return;
			}
			if (e.getAction() == null){
				return;
			}
			e.setCancelled(true);
			if(e.getSlot()==49){
				if(e.getCurrentItem().getType() == Material.BARRIER){
					if(e.getCurrentItem().getItemMeta().getDisplayName().equals("§5§cRevenir §8• §7(Clic-droit)")){
						INVAPI.openBoutiqueMenu((Player) e.getWhoClicked(), true, e.getClickedInventory());
					}
				}
			}
			if(e.getSlot() == 20 || e.getSlot() == 24){
				if(e.getCurrentItem().getType() == Material.NETHER_STAR){
					e.getWhoClicked().closeInventory();
					e.getWhoClicked().sendMessage("§e(Boutique) §7➠ §fwww.TolariaMC.fr/boutique");
				}
			}
			if(e.getSlot() == 22){
				if(e.getCurrentItem().getType() == Material.NETHER_STAR){
					e.getClickedInventory().setItem(12, new ItemStack(Material.AIR));
					e.getClickedInventory().setItem(13, new ItemStack(Material.AIR));
					e.getClickedInventory().setItem(14, new ItemStack(Material.AIR));
					e.getClickedInventory().setItem(21, new ItemStack(Material.AIR));
					e.getClickedInventory().setItem(22, new ItemStack(Material.AIR));
					e.getClickedInventory().setItem(23, new ItemStack(Material.AIR));
					e.getClickedInventory().setItem(49, INVAPI.creatcustomitem(Material.BARRIER, true, Enchantment.DURABILITY, "§5§cRevenir §8• §7(Clic-droit)"));
					e.getClickedInventory().setItem(50, INVAPI.createItem(Material.DOUBLE_PLANT, 1, 0, "§6§nVotre Monnaie", "§7• §e"+Main.plugin.dataPlayers.get(e.getWhoClicked().getName()).getCoins()+".0 ✞"));
					e.getClickedInventory().setItem(20, INVAPI.createItem(Material.NETHER_STAR, 1, 0, "§3Grade: §e§lVIP", "§8Avantages Network/HUB:##§8• §fPréfixe §3» §e§lVIP§r §eXenore_87#§8• §fPseudo §3» §eJaune#§8• §fDurée §3» §cPermanent##§8• §fSlots réservés §3» §a✔#§8• §fAnnonce de connexion §3» §a✔##§8• §fSlot Amis §3» §e15##§8Avantages Jeux:##§8• §fPersonnage §3» §eGold §7(TNTRUN)#§8• §fKits §3» §a✔ §7(Skyblock, PVPBOX)#§8• §fCommandes §3» §e/craft §7(Skyblock)#§8• §fBooster §3» §6+30% §7(Coins)#§8• §fAide serveur §3» §6✯✯✯§7✯✯#§c§l...##§8• §7Prix d'achat: §e11.00 €"));
					e.getClickedInventory().setItem(24, INVAPI.createItem(Material.NETHER_STAR, 1, 0, "§3Grade: §b§lVIP+", "§8Avantages Network/HUB:##§8• §fPréfixe §3» §b§lVIP+§r §bXenore_87#§8• §fPseudo §3» §bBleu#§8• §fDurée §3» §cPermanent##§8• §fSlots réservés §3» §a✔#§8• §fAnnonce de connexion §3» §a✔##§8• §fSlot Amis §3» §e30#§8• §fCapacité de Vol §3» §a✔##§8Avantages Jeux:##§8• §fPersonnage §3» §eGold §7(TNTRUN)#§8• §fKits §3» §a✔ §7(Skyblock, PVPBOX)#§8• §fWorldedit/Voxel §3» §a✔ §7(Créatif)#§8• §fCommandes §3» §e/craft, /hat & /ec §7(Skyblock)#§8• §fBooster §3» §6+50% §7(Coins)#§8• §fAide serveur §3» §6✯✯✯✯✯#§c§l...##§8• §7Prix d'achat: §e20.00 €"));
				}
			}
			if(e.getCurrentItem().getType() == Material.POTION){
				e.setCancelled(true);
				if(e.getCurrentItem().getItemMeta().getDisplayName().contains("Particules")){
					e.getClickedInventory().setItem(49, INVAPI.creatcustomitem(Material.BARRIER, true, Enchantment.DURABILITY, "§2§cRevenir §8• §7(Clic-droit)"));
					e.getClickedInventory().setItem(50, INVAPI.createItem(Material.DOUBLE_PLANT, 1, 0, "§6§nVotre Monnaie", "§7• §e"+Main.plugin.dataPlayers.get(e.getWhoClicked().getName()).getCoins()+".0 ✞"));
					e.getClickedInventory().setItem(14, new ItemStack(Material.AIR));
					e.getClickedInventory().setItem(13, new ItemStack(Material.AIR));
					e.getClickedInventory().setItem(12, new ItemStack(Material.AIR));
					e.getClickedInventory().setItem(22, new ItemStack(Material.AIR));
					for(int i = 19; i<26; i++){
						e.getClickedInventory().setItem(i, INVAPI.createItem(Material.STAINED_GLASS_PANE, 1, (short)0, "§f§lChargement...", " "));
					}
					Bukkit.getScheduler().scheduleSyncDelayedTask(Main.plugin, new Runnable()
					{
						String select = "nostring";
						String Cercle = "nostring";
						String Sphere = "nostring";
						String Multicolor = "nostring";
						String Coeur = "nostring";
						String Magma = "nostring";
						String Nuage = "nostring";
						String Firework = "nostring";
						String Partstring = "Cercle";
						String Partvalue = "nostring";
						public void run(){
							try{
								final Connection connection = DatabaseManager.playersinfo.getDatabaseAccess().getConnection();
								final PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM NetworkCos WHERE name = ?");
								preparedStatement.setString(1, e.getWhoClicked().getName());
								
								preparedStatement.executeQuery();
								
								final ResultSet rs = preparedStatement.getResultSet();
								if(rs.next()){
									select = rs.getString("selectedpart");
									Cercle = rs.getString("Cercle");
									Sphere = rs.getString("Sphere");
									Multicolor = rs.getString("Multicolor");
									Coeur = rs.getString("Coeur");
									Magma = rs.getString("Magma");
									Nuage = rs.getString("Nuage");
									Firework = rs.getString("Firework");
								}
								connection.close();
							}catch(SQLException e1){
								e1.printStackTrace();
								return;
							}
							e.getClickedInventory().setItem(49, INVAPI.creatcustomitem(Material.BARRIER, true, Enchantment.DURABILITY, "§5§cRevenir §8• §7(Clic-droit)"));
							Partvalue = Cercle;
							for(int i = 19; i<26; i++){
								if(i == 20){
									Partstring = "Sphere";
									Partvalue = Sphere;
								}else if(i == 21){
									Partstring = "Multicolor";
									Partvalue = Multicolor;
								}else if(i == 22){
									Partstring = "Coeur";
									Partvalue = Coeur;
								}else if(i == 23){
									Partstring = "Magma";
									Partvalue = Magma;
								}else if(i == 24){
									Partstring = "Nuage";
									Partvalue = Nuage;
								}else if(i == 25){
									Partstring = "Firework";
									Partvalue = Firework;
								}
								if(Partvalue.equals("true")){
									if(select.equals(Partstring)){
										e.getClickedInventory().setItem(i, item("§b"+Partstring, Material.STAINED_GLASS_PANE, true, true, "1000"));
									}else{
										e.getClickedInventory().setItem(i, item("§b"+Partstring, Material.STAINED_GLASS_PANE, false, true, "1000"));
									}
								}else{
									e.getClickedInventory().setItem(i, item("§b"+Partstring, Material.STAINED_GLASS_PANE, false, false, "1000"));
								}
							}
							
						}
						
					}, 14L); 
					
				}else{
					return;
				}
			}
			ItemStack item = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short)13);
			ItemStack red = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short)14);
			if(e.getCurrentItem().getType() == Material.STAINED_GLASS_PANE){
				if (e.getCurrentItem().getData().equals(item.getData())){
					e.getWhoClicked().closeInventory();
					if(Main.plugin.dataPlayers.containsKey(e.getWhoClicked().getName())){
						if(e.getSlot() == 19){
							if(e.getCurrentItem().getItemMeta().getDisplayName().contains("Cercle")){
								if(Main.plugin.dataPlayers.get(e.getWhoClicked().getName()).getPart() != null){
									if(Main.plugin.dataPlayers.get(e.getWhoClicked().getName()).getPart().equals("Cercle")){
										supprpart((Player) e.getWhoClicked());
										return;
									}
								}
								checkpart((Player) e.getWhoClicked(), "Cercle");
							}else if(e.getCurrentItem().getItemMeta().getDisplayName().contains("Neige")){
								Bukkit.getScheduler().scheduleSyncDelayedTask(Main.plugin, new Runnable()
								{
									public void run(){
									if(Main.plugin.dataPlayers.get(e.getWhoClicked().getName()).getPart() != null){
										if(Main.plugin.dataPlayers.get(e.getWhoClicked().getName()).getPart().equals("Neige")){
											Bukkit.getScheduler().scheduleSyncDelayedTask(Main.plugin, new Runnable()
											{
												public void run(){
													supprpart((Player) e.getWhoClicked());
													return;
													}
												}, 2L); 
											}
										}
										checkpart((Player) e.getWhoClicked(), "Neige");
									}
								}, 4L); 
							}
						}else if(e.getSlot() == 20){
							if(Main.plugin.dataPlayers.get(e.getWhoClicked().getName()).getPart() != null){
								if(Main.plugin.dataPlayers.get(e.getWhoClicked().getName()).getPart().equals("Sphere")){
									supprpart((Player) e.getWhoClicked());
									return;
								}
							}
							checkpart((Player) e.getWhoClicked(), "Sphere");
						}else if(e.getSlot() == 21){
							if(Main.plugin.dataPlayers.get(e.getWhoClicked().getName()).getPart() != null){
								if(Main.plugin.dataPlayers.get(e.getWhoClicked().getName()).getPart().equals("Multicolor")){
									supprpart((Player) e.getWhoClicked());
									return;
								}
							}
							checkpart((Player) e.getWhoClicked(), "Multicolor");
						}else if(e.getSlot() == 22){
							if(Main.plugin.dataPlayers.get(e.getWhoClicked().getName()).getPart() != null){
								if(Main.plugin.dataPlayers.get(e.getWhoClicked().getName()).getPart().equals("Coeur")){
									supprpart((Player) e.getWhoClicked());
									return;
								}
							}
							checkpart((Player) e.getWhoClicked(), "Coeur");
						}else if(e.getSlot() == 23){
							if(Main.plugin.dataPlayers.get(e.getWhoClicked().getName()).getPart() != null){
								if(Main.plugin.dataPlayers.get(e.getWhoClicked().getName()).getPart().equals("Magma")){
									supprpart((Player) e.getWhoClicked());
									return;
								}
							}
							checkpart((Player) e.getWhoClicked(), "Magma");
						}else if(e.getSlot() == 24){
							if(Main.plugin.dataPlayers.get(e.getWhoClicked().getName()).getPart() != null){
								if(Main.plugin.dataPlayers.get(e.getWhoClicked().getName()).getPart().equals("Nuage")){
									supprpart((Player) e.getWhoClicked());
									return;
								}
							}
							checkpart((Player) e.getWhoClicked(), "Nuage");
						}else if(e.getSlot() == 25){
							if(Main.plugin.dataPlayers.get(e.getWhoClicked().getName()).getPart() != null){
								if(Main.plugin.dataPlayers.get(e.getWhoClicked().getName()).getPart().equals("Firework")){
									supprpart((Player) e.getWhoClicked());
									return;
								}
							}
							
							PARTICLESHAPEAPI API = new PARTICLESHAPEAPI();
							API.Fireworkslaunchstart((Player) e.getWhoClicked());
							checkpart((Player) e.getWhoClicked(), "Firework");
						}
					}
				}else if (e.getCurrentItem().getData().equals(red.getData())){
					e.getWhoClicked().closeInventory();
					Bukkit.getScheduler().scheduleSyncDelayedTask(Main.plugin, new Runnable()
					{
						public void run(){
							String result = "nostring";
							if(Main.plugin.dataPlayers.containsKey(e.getWhoClicked().getName())){
								result = Main.plugin.dataPlayers.get(e.getWhoClicked().getName()).getCoins();

							}else{
								result = API.Mysqlgetinfo(e.getWhoClicked().getName(), "playerinfo", "coins");
							}
							if (!(result.equals("nostring") || result.equals("null") || result.isEmpty())){
								int d = Integer.parseInt(result);
								if(d >= 1000){
									d=d-1000;
									if(Main.plugin.dataPlayers.containsKey(e.getWhoClicked().getName())){
									Main.plugin.dataPlayers.get(e.getWhoClicked().getName()).setCoins(d + "");
									}
									API.editlinecoreboard((Player) e.getWhoClicked(), 9, "§eⓉ Coins §7▎ §f" + Main.plugin.dataPlayers.get(e.getWhoClicked().getName()).getCoins() +".0 ✞", "§eⓉ Coins §7▎ §f" + Main.plugin.dataPlayers.get(e.getWhoClicked().getName()).getCoins() +".0 ✞", "§eⓉ Coins §7▎ §f" + Main.plugin.dataPlayers.get(e.getWhoClicked().getName()).getCoins() +".0 ✞");
									API.Mysqlupdate(e.getWhoClicked().getName(), "playerinfo", "coins", d+ "");
									if(e.getSlot() == 19){
										if(e.getCurrentItem().getItemMeta().getDisplayName().contains("Cercle")){
											replacepart((Player) e.getWhoClicked(), "Cercle", "replace");
										}else if(e.getCurrentItem().getItemMeta().getDisplayName().contains("Neige")){
											replacepart((Player) e.getWhoClicked(), "Neige", "replace");
										}
									}else if(e.getSlot() == 20){
										replacepart((Player) e.getWhoClicked(), "Sphere", "replace");
									}else if(e.getSlot() == 21){
										replacepart((Player) e.getWhoClicked(), "Multicolor", "replace");
									}else if(e.getSlot() == 22){
										replacepart((Player) e.getWhoClicked(), "Coeur", "replace");
									}else if(e.getSlot() == 23){
										replacepart((Player) e.getWhoClicked(), "Magma", "replace"); 
									}else if(e.getSlot() == 24){
										replacepart((Player) e.getWhoClicked(), "Nuage", "replace");
									}else if(e.getSlot() == 25){
										replacepart((Player) e.getWhoClicked(), "Firework", "replace");
										PARTICLESHAPEAPI API = new PARTICLESHAPEAPI();
										API.Fireworkslaunchstart((Player) e.getWhoClicked());
									}
								}else{
									e.getWhoClicked().sendMessage("§e(Boutique) §7➠ §cVous n'avez pas assez d'argent... §8▎ §7(1000.0 ✞)");
									((Player) e.getWhoClicked()).playSound(e.getWhoClicked().getLocation(), Sound.WOLF_GROWL, 5, 1);
									return;
									
								}
							}
						}
					}, 3L);
					
				}
			}
		}
	}

}
