package mlg.byneox.tc.api;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.block.banner.Pattern;
import org.bukkit.block.banner.PatternType;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BannerMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;

import mlg.byneox.tc.Main;
import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.PacketPlayOutTitle;
import net.minecraft.server.v1_8_R3.PlayerConnection;

public class INVENTORYAPI {

	TCAPI API = new TCAPI();
	
	
	
	public ItemStack creatcustomitem(Material item, boolean boolenchant, Enchantment enchant, String name){
		ItemStack itemstack = new ItemStack(item);
		ItemMeta meta = itemstack.getItemMeta();
		if(boolenchant){
			meta.addEnchant(enchant, 1, true);
			meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		}
		if(!name.equals("null")){
			meta.setDisplayName(name);
		}
		itemstack.setItemMeta(meta);
		return itemstack;
		
	}	
	public void openBoutiqueMenu(Player p, boolean hasmenuopen, Inventory inv){
		if(!hasmenuopen){
			String Menuname = "§8[Boutique] » Menu";
			if (Main.plugin.dataPlayers.get(p.getName()).getLang().equals("EN")){
				Menuname = "§8[Shop] » Home";
			}else if (Main.plugin.dataPlayers.get(p.getName()).getLang().equals("ES")){
				Menuname = "§8[Tienda] » Bienvenida";
			}
			inv = Bukkit.createInventory(p, 54, Menuname);
			for (int i = 36; i < 45; i++) {
				inv.setItem(i, createItem(Material.STAINED_GLASS_PANE, 1, (short)4, " ", " "));
			}
		}else{
			for (int i = 19; i < 26; i++) {
				inv.setItem(i, new ItemStack(Material.AIR));
			}
			inv.setItem(49, new ItemStack(Material.AIR));
			inv.setItem(50, new ItemStack(Material.AIR));
		}
		ItemStack i = createItem(Material.POTION, 1, 3, "§bCatégorie §3» §f§nParticules", " ");
		ItemMeta met = i.getItemMeta();
		met.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		met.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
		i.setItemMeta(met);
		inv.setItem(12, createItem(Material.FISHING_ROD, 1, 0, "§bCatégorie §3» §f§nGadgets", " "));
		inv.setItem(13, i);
		inv.setItem(14, createItem(Material.LEATHER, 1, 0, "§bCatégorie §3» §f§nFamiliers", " "));
		inv.setItem(22, createItem(Material.NETHER_STAR, 1, 0, "§bCatégorie §3» §f§nGrades", " "));
		inv.setItem(21, creatcustomitem(Material.BARRIER, true, Enchantment.DURABILITY, "§c§lSOON"));
		inv.setItem(23, creatcustomitem(Material.BARRIER, true, Enchantment.DURABILITY, "§c§lSOON"));
		if(!hasmenuopen){
			p.openInventory(inv);
		}
		return;
	}
	
	public void opennetherstar(Player p){
		String Menuname = "§8[Hubs] » Sélection";
		if (Main.plugin.dataPlayers.get(p.getName()).getLang().equals("EN")){
			Menuname = "§8[Hubs] » Selection";
		}else if (Main.plugin.dataPlayers.get(p.getName()).getLang().equals("ES")){
			Menuname = "§8[Hubs] » Selección";
		}
		Inventory inv = Bukkit.createInventory(p, 54, Menuname);
		for (int i = 36; i < 45; i++) {
			inv.setItem(i, createItem(Material.STAINED_GLASS_PANE, 1, (short)4, " ", " "));
		}

		if(Bukkit.getOnlinePlayers().size()<=15){
			
			if(Main.plugin.dataPlayers.containsKey(p.getName())){
				inv.setItem(20, createItemhead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZmQ4MTJkYjcxZDRlN2JhYzAyMWQ1MzQyOTQ0MGM3Y2I3NTM2MzI3NjYxYjJlOWEzYzZlZGQwOWM4ZTYxM2RlIn19fQ==", "§3HUB #1", "§8Type: Serveur##§8• §fVersion: §b1.8.8 §7(1.8/1.14.1)##§8• §fPrésence: §a§lFAIBLE#§8• §fConnecté(s): §e"+Bukkit.getOnlinePlayers().size()+" §fjoueur(s).##§7➥ §cVous êtes déjà connecté(e)!"));
				if(Main.plugin.dataPlayers.get(p.getName()).getLang().equals("ES")){
					inv.setItem(20, createItemhead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZmQ4MTJkYjcxZDRlN2JhYzAyMWQ1MzQyOTQ0MGM3Y2I3NTM2MzI3NjYxYjJlOWEzYzZlZGQwOWM4ZTYxM2RlIn19fQ==", "§3HUB #1", "§8Tipo: Servidor##§8• §fVersion: §b1.8.8 §7(1.8/1.14.1)##§8• §fPresencia: §a§lBAJA#§8• §fConectado(s): §e"+Bukkit.getOnlinePlayers().size()+" §fjugador(es). ##§7➥ §cYa estás conectado!"));
				}else if(Main.plugin.dataPlayers.get(p.getName()).getLang().equals("EN")){
					inv.setItem(20, createItemhead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZmQ4MTJkYjcxZDRlN2JhYzAyMWQ1MzQyOTQ0MGM3Y2I3NTM2MzI3NjYxYjJlOWEzYzZlZGQwOWM4ZTYxM2RlIn19fQ==", "§3HUB #1", "§8Type: Server##§8• §fVersion: §b1.8.8 §7(1.8/1.14.1)##§8• §fPresence: §a§lLOW#§8• §fLogged(s): §e"+Bukkit.getOnlinePlayers().size()+" §fplayer(s).##§7➥ §cYou are already connected!"));
				}
			}
		}else if(Bukkit.getOnlinePlayers().size()<=35){
			inv.setItem(20, createItemhead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZmQ4MTJkYjcxZDRlN2JhYzAyMWQ1MzQyOTQ0MGM3Y2I3NTM2MzI3NjYxYjJlOWEzYzZlZGQwOWM4ZTYxM2RlIn19fQ==", "§3HUB #1", "§8Type: Serveur##§8• §fVersion: §b1.8.8 §7(1.8/1.14.1)##§8• §fPrésence: §6§lMOYENNE#§8• §fConnecté(s): §e"+Bukkit.getOnlinePlayers().size()+" §fjoueur(s).##§7➥ §cVous êtes déjà connecté(e)!"));
			if(Main.plugin.dataPlayers.get(p.getName()).getLang().equals("ES")){
				inv.setItem(20, createItemhead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZmQ4MTJkYjcxZDRlN2JhYzAyMWQ1MzQyOTQ0MGM3Y2I3NTM2MzI3NjYxYjJlOWEzYzZlZGQwOWM4ZTYxM2RlIn19fQ==", "§3HUB #1", "§8Tipo: Servidor##§8• §fVersion: §b1.8.8 §7(1.8/1.14.1)##§8• §fPresencia: §6§lPROMEDIO#§8• §fConectado(s): §e"+Bukkit.getOnlinePlayers().size()+" §fjugador(es). ##§7➥ §cYa estás conectado!"));
			}else if(Main.plugin.dataPlayers.get(p.getName()).getLang().equals("EN")){
				inv.setItem(20, createItemhead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZmQ4MTJkYjcxZDRlN2JhYzAyMWQ1MzQyOTQ0MGM3Y2I3NTM2MzI3NjYxYjJlOWEzYzZlZGQwOWM4ZTYxM2RlIn19fQ==", "§3HUB #1", "§8Type: Server##§8• §fVersion: §b1.8.8 §7(1.8/1.14.1)##§8• §fPresence: §6§lAVERAGE#§8• §fLogged(s): §e"+Bukkit.getOnlinePlayers().size()+" §fplayer(s).##§7➥ §cYou are already connected!"));
			}
		}else{
			inv.setItem(20, createItemhead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZmQ4MTJkYjcxZDRlN2JhYzAyMWQ1MzQyOTQ0MGM3Y2I3NTM2MzI3NjYxYjJlOWEzYzZlZGQwOWM4ZTYxM2RlIn19fQ==", "§3HUB #1", "§8Type: Serveur##§8• §fVersion: §b1.8.8 §7(1.8/1.14.1)##§8• §fPrésence: §c§lFORTE#§8• §fConnecté(s): §e"+Bukkit.getOnlinePlayers().size()+" §fjoueur(s).##§7➥ §cVous êtes déjà connecté(e)!"));
			if(Main.plugin.dataPlayers.get(p.getName()).getLang().equals("ES")){
				inv.setItem(20, createItemhead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZmQ4MTJkYjcxZDRlN2JhYzAyMWQ1MzQyOTQ0MGM3Y2I3NTM2MzI3NjYxYjJlOWEzYzZlZGQwOWM4ZTYxM2RlIn19fQ==", "§3HUB #1", "§8Tipo: Servidor##§8• §fVersion: §b1.8.8 §7(1.8/1.14.1)##§8• §fPresencia: §c§lFUERTE#§8• §fConectado(s): §e"+Bukkit.getOnlinePlayers().size()+" §fjugador(es). ##§7➥ §cYa estás conectado!"));
			}else if(Main.plugin.dataPlayers.get(p.getName()).getLang().equals("EN")){
				inv.setItem(20, createItemhead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZmQ4MTJkYjcxZDRlN2JhYzAyMWQ1MzQyOTQ0MGM3Y2I3NTM2MzI3NjYxYjJlOWEzYzZlZGQwOWM4ZTYxM2RlIn19fQ==", "§3HUB #1", "§8Type: Server##§8• §fVersion: §b1.8.8 §7(1.8/1.14.1)##§8• §fPresence: §c§lSTRONG#§8• §fLogged(s): §e"+Bukkit.getOnlinePlayers().size()+" §fplayer(s).##§7➥ §cYou are already connected!"));
			}
		}
		int HUB02 = Main.plugin.HUB_02;
		int HUB03 = Main.plugin.HUB_03;
		if(HUB02<=15){
			inv.setItem(22, createItemhead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOTEzMTY1YjViOTg3ZmYwODk3ZmQ4OGY0N2ZiYmVkYWJhOTIyNzk2NmRlYjM2YzhmNGY3NWFmYTE5NGFhY2I5NiJ9fX0=", "§3HUB #2", "§8Type: Serveur##§8• §fVersion: §b1.8.8 §7(1.8/1.14.1)##§8• §fPrésence: §a§lFAIBLE#§8• §fConnecté(s): §e"+HUB02+" §fjoueur(s).##§7➥ §aClique pour y accéder!"));
			if(Main.plugin.dataPlayers.get(p.getName()).getLang().equals("ES")){
				inv.setItem(22, createItemhead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOTEzMTY1YjViOTg3ZmYwODk3ZmQ4OGY0N2ZiYmVkYWJhOTIyNzk2NmRlYjM2YzhmNGY3NWFmYTE5NGFhY2I5NiJ9fX0=", "§3HUB #2", "§8Tipo: Servidor##§8• §fVersion: §b1.8.8 §7(1.8/1.14.1)##§8• §fPresencia: §a§lBAJA#§8• §fConectado(s): §e"+HUB02+" §fjugador(es). ##§7➥ §aHaga clic para acceder!"));
			}else if(Main.plugin.dataPlayers.get(p.getName()).getLang().equals("EN")){
				inv.setItem(22, createItemhead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOTEzMTY1YjViOTg3ZmYwODk3ZmQ4OGY0N2ZiYmVkYWJhOTIyNzk2NmRlYjM2YzhmNGY3NWFmYTE5NGFhY2I5NiJ9fX0=", "§3HUB #2", "§8Type: Server##§8• §fVersion: §b1.8.8 §7(1.8/1.14.1)##§8• §fPresence: §a§lLOW#§8• §fLogged(s): §e"+HUB02+" §fplayer(s).##§7➥ §aClick to access!"));
			}
		}else if(HUB02<=35){
			inv.setItem(22, createItemhead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOTEzMTY1YjViOTg3ZmYwODk3ZmQ4OGY0N2ZiYmVkYWJhOTIyNzk2NmRlYjM2YzhmNGY3NWFmYTE5NGFhY2I5NiJ9fX0=", "§3HUB #2", "§8Type: Serveur##§8• §fVersion: §b1.8.8 §7(1.8/1.14.1)##§8• §fPrésence: §6§lMOYENNE#§8• §fConnecté(s): §e"+HUB02+" §fjoueur(s).##§7➥ §aClique pour y accéder!"));
			if(Main.plugin.dataPlayers.get(p.getName()).getLang().equals("ES")){
				inv.setItem(22, createItemhead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOTEzMTY1YjViOTg3ZmYwODk3ZmQ4OGY0N2ZiYmVkYWJhOTIyNzk2NmRlYjM2YzhmNGY3NWFmYTE5NGFhY2I5NiJ9fX0=", "§3HUB #2", "§8Tipo: Servidor##§8• §fVersion: §b1.8.8 §7(1.8/1.14.1)##§8• §fPresencia: §6§lPROMEDIO#§8• §fConectado(s): §e"+HUB02+" §fjugador(es). ##§7➥ §aHaga clic para acceder!"));
			}else if(Main.plugin.dataPlayers.get(p.getName()).getLang().equals("EN")){
				inv.setItem(22, createItemhead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOTEzMTY1YjViOTg3ZmYwODk3ZmQ4OGY0N2ZiYmVkYWJhOTIyNzk2NmRlYjM2YzhmNGY3NWFmYTE5NGFhY2I5NiJ9fX0=", "§3HUB #2", "§8Type: Server##§8• §fVersion: §b1.8.8 §7(1.8/1.14.1)##§8• §fPresence: §6§lAVERAGE#§8• §fLogged(s): §e"+HUB02+" §fplayer(s).##§7➥ §aClick to access!"));
			}
		}else{
			inv.setItem(22, createItemhead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOTEzMTY1YjViOTg3ZmYwODk3ZmQ4OGY0N2ZiYmVkYWJhOTIyNzk2NmRlYjM2YzhmNGY3NWFmYTE5NGFhY2I5NiJ9fX0=", "§3HUB #2", "§8Type: Serveur##§8• §fVersion: §b1.8.8 §7(1.8/1.14.1)##§8• §fPrésence: §c§lFORTE#§8• §fConnecté(s): §e"+HUB02+" §fjoueur(s).##§7➥ §aClique pour y accéder!"));
			if(Main.plugin.dataPlayers.get(p.getName()).getLang().equals("ES")){
				inv.setItem(22, createItemhead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOTEzMTY1YjViOTg3ZmYwODk3ZmQ4OGY0N2ZiYmVkYWJhOTIyNzk2NmRlYjM2YzhmNGY3NWFmYTE5NGFhY2I5NiJ9fX0=", "§3HUB #2", "§8Tipo: Servidor##§8• §fVersion: §b1.8.8 §7(1.8/1.14.1)##§8• §fPresencia: §c§lFUERTE#§8• §fConectado(s): §e"+HUB02+" §fjugador(es). ##§7➥ §aHaga clic para acceder!"));
			}else if(Main.plugin.dataPlayers.get(p.getName()).getLang().equals("EN")){
				inv.setItem(22, createItemhead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOTEzMTY1YjViOTg3ZmYwODk3ZmQ4OGY0N2ZiYmVkYWJhOTIyNzk2NmRlYjM2YzhmNGY3NWFmYTE5NGFhY2I5NiJ9fX0=", "§3HUB #2", "§8Type: Server##§8• §fVersion: §b1.8.8 §7(1.8/1.14.1)##§8• §fPresence: §c§lSTRONG#§8• §fLogged(s): §e"+HUB02+" §fplayer(s).##§7➥ §aClick to access!"));
			}
		}
		
		if(HUB03<=15){
			inv.setItem(24, createItemhead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOTEzMTY1YjViOTg3ZmYwODk3ZmQ4OGY0N2ZiYmVkYWJhOTIyNzk2NmRlYjM2YzhmNGY3NWFmYTE5NGFhY2I5NiJ9fX0=", "§3HUB #3", "§8Type: Serveur##§8• §fVersion: §b1.8.8 §7(1.8/1.14.1)##§8• §fPrésence: §a§lFAIBLE#§8• §fConnecté(s): §e"+HUB03+" §fjoueur(s).##§7➥ §aClique pour y accéder!"));
			if(Main.plugin.dataPlayers.get(p.getName()).getLang().equals("ES")){
				inv.setItem(24, createItemhead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOTEzMTY1YjViOTg3ZmYwODk3ZmQ4OGY0N2ZiYmVkYWJhOTIyNzk2NmRlYjM2YzhmNGY3NWFmYTE5NGFhY2I5NiJ9fX0=", "§3HUB #3", "§8Tipo: Servidor##§8• §fVersion: §b1.8.8 §7(1.8/1.14.1)##§8• §fPresencia: §a§lBAJA#§8• §fConectado(s): §e"+HUB03+" §fjugador(es). ##§7➥ §aHaga clic para acceder!"));
			}else if(Main.plugin.dataPlayers.get(p.getName()).getLang().equals("EN")){
				inv.setItem(24, createItemhead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOTEzMTY1YjViOTg3ZmYwODk3ZmQ4OGY0N2ZiYmVkYWJhOTIyNzk2NmRlYjM2YzhmNGY3NWFmYTE5NGFhY2I5NiJ9fX0=", "§3HUB #3", "§8Type: Server##§8• §fVersion: §b1.8.8 §7(1.8/1.14.1)##§8• §fPresence: §a§lLOW#§8• §fLogged(s): §e"+HUB03+" §fplayer(s).##§7➥ §aClick to access!"));
			}
		}else if(HUB03<=35){
			inv.setItem(24, createItemhead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOTEzMTY1YjViOTg3ZmYwODk3ZmQ4OGY0N2ZiYmVkYWJhOTIyNzk2NmRlYjM2YzhmNGY3NWFmYTE5NGFhY2I5NiJ9fX0=", "§3HUB #3", "§8Type: Serveur##§8• §fVersion: §b1.8.8 §7(1.8/1.14.1)##§8• §fPrésence: §6§lMOYENNE#§8• §fConnecté(s): §e"+HUB03+" §fjoueur(s).##§7➥ §aClique pour y accéder!"));
			if(Main.plugin.dataPlayers.get(p.getName()).getLang().equals("ES")){
				inv.setItem(24, createItemhead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOTEzMTY1YjViOTg3ZmYwODk3ZmQ4OGY0N2ZiYmVkYWJhOTIyNzk2NmRlYjM2YzhmNGY3NWFmYTE5NGFhY2I5NiJ9fX0=", "§3HUB #3", "§8Tipo: Servidor##§8• §fVersion: §b1.8.8 §7(1.8/1.14.1)##§8• §fPresencia: §6§lPROMEDIO#§8• §fConectado(s): §e"+HUB03+" §fjugador(es). ##§7➥ §aHaga clic para acceder!"));
			}else if(Main.plugin.dataPlayers.get(p.getName()).getLang().equals("EN")){
				inv.setItem(24, createItemhead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOTEzMTY1YjViOTg3ZmYwODk3ZmQ4OGY0N2ZiYmVkYWJhOTIyNzk2NmRlYjM2YzhmNGY3NWFmYTE5NGFhY2I5NiJ9fX0=", "§3HUB #3", "§8Type: Server##§8• §fVersion: §b1.8.8 §7(1.8/1.14.1)##§8• §fPresence: §6§lAVERAGE#§8• §fLogged(s): §e"+HUB03+" §fplayer(s).##§7➥ §aClick to access!"));
			}
		}else{
			inv.setItem(24, createItemhead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOTEzMTY1YjViOTg3ZmYwODk3ZmQ4OGY0N2ZiYmVkYWJhOTIyNzk2NmRlYjM2YzhmNGY3NWFmYTE5NGFhY2I5NiJ9fX0=", "§3HUB #3", "§8Type: Serveur##§8• §fVersion: §b1.8.8 §7(1.8/1.14.1)##§8• §fPrésence: §c§lFORTE#§8• §fConnecté(s): §e"+HUB03+" §fjoueur(s).##§7➥ §aClique pour y accéder!"));
			if(Main.plugin.dataPlayers.get(p.getName()).getLang().equals("ES")){
				inv.setItem(24, createItemhead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOTEzMTY1YjViOTg3ZmYwODk3ZmQ4OGY0N2ZiYmVkYWJhOTIyNzk2NmRlYjM2YzhmNGY3NWFmYTE5NGFhY2I5NiJ9fX0=", "§3HUB #3", "§8Tipo: Servidor##§8• §fVersion: §b1.8.8 §7(1.8/1.14.1)##§8• §fPresencia: §c§lFUERTE#§8• §fConectado(s): §e"+HUB03+" §fjugador(es). ##§7➥ §aHaga clic para acceder!"));
			}else if(Main.plugin.dataPlayers.get(p.getName()).getLang().equals("EN")){
				inv.setItem(24, createItemhead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOTEzMTY1YjViOTg3ZmYwODk3ZmQ4OGY0N2ZiYmVkYWJhOTIyNzk2NmRlYjM2YzhmNGY3NWFmYTE5NGFhY2I5NiJ9fX0=", "§3HUB #3", "§8Type: Server##§8• §fVersion: §b1.8.8 §7(1.8/1.14.1)##§8• §fPresence: §c§lSTRONG#§8• §fLogged(s): §e"+HUB03+" §fplayer(s).##§7➥ §aClick to access!"));
			}
		}
		p.openInventory(inv);
	}
	
	
	
	public void openLangageMenu(Player p){
		String Menuname = "§8[Langage] » Sélection";
		if (Main.plugin.dataPlayers.get(p.getName()).getLang().equals("EN")){
			Menuname = "§8[Language] » Selection";
		}else if (Main.plugin.dataPlayers.get(p.getName()).getLang().equals("ES")){
			Menuname = "§8[Idioma] » Selección";
		}
		Inventory inv = Bukkit.createInventory(p, 54, Menuname);
		for (int i = 36; i < 45; i++) {
			inv.setItem(i, createItem(Material.STAINED_GLASS_PANE, 1, (short)4, " ", " "));
		}
		inv.setItem(22, Itemlanguage("MENU", p, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNTEyNjlhMDY3ZWUzN2U2MzYzNWNhMWU3MjNiNjc2ZjEzOWRjMmRiZGRmZjk2YmJmZWY5OWQ4YjM1Yzk5NmJjIn19fQ==", Main.plugin.dataPlayers.get(p.getName()).getLang(), "Français", "French", "Francés"));
		inv.setItem(24, Itemlanguage("MENU", p, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvODc5ZDk5ZDljNDY0NzRlMjcxM2E3ZTg0YTk1ZTRjZTdlOGZmOGVhNGQxNjQ0MTNhNTkyZTQ0MzVkMmM2ZjlkYyJ9fX0=", Main.plugin.dataPlayers.get(p.getName()).getLang(), "Anglais", "English", "Inglés"));
		inv.setItem(20, Itemlanguage("MENU", p, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMzJiZDQ1MjE5ODMzMDllMGFkNzZjMWVlMjk4NzQyODc5NTdlYzNkOTZmOGQ4ODkzMjRkYThjODg3ZTQ4NWVhOCJ9fX0=", Main.plugin.dataPlayers.get(p.getName()).getLang(), "Espagnol", "Spanish", "Español"));
		p.openInventory(inv);
	}
	
	public ItemStack Itemlanguage(String t, Player p, String value, String lang, String selectFR, String selectEN, String selectES)
	{
		ItemStack i = createItemhead(value, " ", " ");
	    ItemMeta m = i.getItemMeta();
	    if(lang == null){
	    	lang = "FR";
	    }
	    if (t.equals("GIVE")){
	    	m.setDisplayName("§aChanger de langue §8• §7(Clic-droit)"); //Setting the display name to "Awesome banner!"
	    	if(lang.equals("EN")){
	    		m.setDisplayName("§aChange language §8• §7(Right-click)");
	    	}if(lang.equals("ES")){
	    		m.setDisplayName("§aCambiar idioma §8• §7(Haga-clic)");
	    	}
	    }else if (t.equals("MENU")){
	    	m.setDisplayName("§3"+selectFR);
	    	ArrayList<String> Loreitem = new ArrayList<>();
	    	Loreitem.add("§8Type: Langage");
	    	Loreitem.add(" ");
	    	Loreitem.add("§7Changez votre langue en "+selectFR+".");
	    	Loreitem.add(" ");
	    	Loreitem.add("§f§nServeurs disponibles:§r ");
	    	Loreitem.add(" ");
	    	Loreitem.add("§8• §eHUB (all)");
	    	Loreitem.add("§8• §eMLG");
	    	Loreitem.add("§8• §eTNTRUN");
	    	Loreitem.add(" ");
	    	Loreitem.add("§7➥ §aClique pour sélectionner!");
	    	if(lang.equals("EN")){
	    		Loreitem.clear();
	    	   	Loreitem.add("§8Type: Language");
	    	   	Loreitem.add(" ");
	    	   	Loreitem.add("§7Change your language to "+selectEN+".");
	    	   	Loreitem.add(" ");
	    	   	Loreitem.add("§f§nAvailable servers:§r ");
	    	   	Loreitem.add(" ");
	    	   	Loreitem.add("§8• §eHUB (all)");
	    	   	Loreitem.add("§8• §eMLG");
	    	   	Loreitem.add("§8• §eTNTRUN");
	    	   	Loreitem.add(" ");
	    	   	Loreitem.add("§7➥ §aClick to select!");
	    		m.setDisplayName("§3"+selectEN);
	    	}else if(lang.equals("ES")){
	    		Loreitem.clear();
	    	    Loreitem.add("§8Tipo: Lengua");
	    	    Loreitem.add(" ");
	    	    Loreitem.add("§7Cambia tu idioma al "+selectES+".");
	    	    Loreitem.add(" ");
	    	    Loreitem.add("§f§nServidores disponibles:§r ");
	    	    Loreitem.add(" ");
	    	    Loreitem.add("§8• §eHUB (all)");
	    	    Loreitem.add("§8• §eMLG");
	    	    Loreitem.add("§8• §eTNTRUN");
	    	    Loreitem.add(" ");
	    	    Loreitem.add("§7➥ §aHaga clic para seleccionar!");
	    		m.setDisplayName("§3"+selectES);
	    	}
	    	m.setLore(Loreitem);
	    }
	    i.setItemMeta(m);

	    return i;
	}
	
	public void openMainMenu(Player p){
		if(Main.plugin.cooldowns.containsKey(p.getName())) {
            double secondsLeft = ((Main.plugin.cooldowns.get(p.getName())/1000)+Main.plugin.cooldownTimeMENU) - (System.currentTimeMillis()/1000);
            if(secondsLeft>0.0) {
                p.sendMessage("§7➠ §4⚠ §cPatientez 0.5 seconde(s).");
                return;
            }else{
            	Main.plugin.cooldowns.remove(p.getName());
            }
		}
		Main.plugin.cooldowns.put(p.getName(), System.currentTimeMillis());
		String Lang = "FR";
		if(Main.plugin.dataPlayers.containsKey(p.getName())){
			Lang = Main.plugin.dataPlayers.get(p.getName()).getLang();
		}
		Inventory inv = Bukkit.createInventory(p, 54, "§8[Menu Principal] » Accueil");
		if(Lang.equals("EN")){
			inv = Bukkit.createInventory(p, 54, "§8[Main Menu] » Home");
		}else if(Lang.equals("ES")){
			inv = Bukkit.createInventory(p, 54, "§8[Menú principal] » Bienvenida");
		}
		for (int i = 36; i < 45; i++) {
			inv.setItem(i, createItem(Material.STAINED_GLASS_PANE, 1, (short) 4, " ", " "));
		}
		inv.setItem(48, createItemhead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvY2M3NDVhMDZmNTM3YWVhODA1MDU1NTkxNDllYTE2YmQ0YTg0ZDQ0OTFmMTIyMjY4MThjMzg4MWMwOGU4NjBmYyJ9fX0=", "§b§lTwitter §b§l: §b§o@TolariaMC", " "));
		inv.setItem(49, createItemhead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNzg3M2MxMmJmZmI1MjUxYTBiODhkNWFlNzVjNzI0N2NiMzlhNzVmZjFhODFjYmU0YzhhMzliMzExZGRlZGEifX19", "§9§lDiscord §9§l: §9§odiscord.me/tolariamc", " "));
		inv.setItem(50, createItemhead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMjViM2YyY2ZhMDczOWM0ZTgyODMxNmYzOWY5MGIwNWJjMWY0ZWQyN2IxZTM1ODg4NTExZjU1OGQ0Njc1In19fQ==", "§d§lInstagram §d§l: §d§oTolaria.MC", " "));
		inv.setItem(22, creatcustomitem(Material.BARRIER, true, Enchantment.DURABILITY, "§c§lSOON"));
		inv.setItem(23, creatcustomitem(Material.BARRIER, true, Enchantment.DURABILITY, "§c§lSOON"));
		inv.setItem(15, creatcustomitem(Material.BARRIER, true, Enchantment.DURABILITY, "§c§lSOON"));
		inv.setItem(24, creatcustomitem(Material.BARRIER, true, Enchantment.DURABILITY, "§c§lSOON"));
		if(Lang.equals("FR")){
			
			inv.setItem(13, createItemhead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZDM1NzQ0NGFkZTY0ZWM2Y2VhNjQ1ZWM1N2U3NzU4NjRkNjdjNWZhNjIyOTk3ODZlMDM3OTkzMTdlZTRhZCJ9fX0=", "§3CREATIVE", "§8Type: Architecture##§7Re-découvrez un jeu original en#§7faisant preuve de talent ainsi que#§7de réflexion afin de devenir le#§7meilleur des architectes.##§8• §fVersion: §b1.12.2 §7(1.12/1.14.1)#§8• §fEn jeu: §e0 §fjoueur(s).##§7➥ §aClique pour y accéder!"));
			inv.setItem(14, createItemhead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYzdlNmM0MGY2OGI3NzVmMmVmY2Q3YmQ5OTE2YjMyNzg2OWRjZjI3ZTI0Yzg1NWQwYTE4ZTA3YWMwNGZlMSJ9fX0=", "§3PVP BOX", "§8Type: Combat/Tactique##§7Envie de PVP ? Enchainez les meurtres#§7dans une arène optimisée et améliorer#§7votre équipement en vous combattant#§7dans de vrais combats.##§8• §fVersion: §b1.12.2 §7(1.12/1.14.1)#§8• §fEn jeu: §e0 §fjoueur(s).##§7➥ §aClique pour y accéder!"));
			
			inv.setItem(11, createItemhead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMzRmY2JjMjU2ZDBiZTdlNjgzYWY4NGUzOGM0YmNkYjcxYWZiOTM5ODUzOGEyOWFhOTZjYmZhMzE4YjJlYSJ9fX0=", "§3SKYBLOCK", "§8Type: Farming/Création##§7Débutez une aventure dans les airs,#§7seul ou bien en équipe, sur une petite#§7île de départ. Améliorez-là grâce à#§7de nombreuses usines et dominer le#§7monde du ciel.##§8• §fType: §3§lAQUA#§8• §fVersion: §b1.12.2 §7(1.12/1.14.1)#§8• §fEn jeu: §e0 §fjoueur(s).##§7➥ §aClique pour y accéder!"));
			inv.setItem(12, createItemhead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYjY5NjVlNmE1ODY4NGMyNzdkMTg3MTdjZWM5NTlmMjgzM2E3MmRmYTk1NjYxMDE5ZGJjZGYzZGJmNjZiMDQ4In19fQ==", "§3SKYBLOCK", "§8Type: Farming/Création##§7Débutez une aventure dans les airs,#§7seul ou bien en équipe, sur une petite#§7île de départ. Améliorez-là grâce à#§7de nombreuses usines et dominer le#§7monde du ciel.##§8• §fType: §4§lLAVA#§8• §fVersion: §b1.12.2 §7(1.12/1.14.1)#§8• §fEn jeu: §e0 §fjoueur(s).##§7➥ §aClique pour y accéder!"));
			inv.setItem(20, createItemhead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvM2FmNTk3NzZmMmYwMzQxMmM3YjU5NDdhNjNhMGNmMjgzZDUxZmU2NWFjNmRmN2YyZjg4MmUwODM0NDU2NWU5In19fQ==", "§3TNTRUN", "§8Type: Rapidité/Réaction##§7Une petite course ? Courez le#§7plus vite possible, afin de rester#§7envie. Mais attention, les blocs se#§7cassent sous vos pieds.##§8• §fVersion: §b1.8.8 §7(1.8/1.14.1)#§8• §fEn jeu: §e"+Main.plugin.TNTTOTAL+" §fjoueur(s).##§7➥ §aClique pour y accéder!"));
			inv.setItem(21, createItemhead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNDlmMWYwN2UyYjFjMzJiYjY0YTEyOGU1MjlmM2FmMWU1Mjg2ZTUxODU0NGVkZjhjYmFhNmM0MDY1YjQ3NmIifX19", "§3MLG", "§8Type: Rapidité/Réaction ##§7Mode de jeu exclusif, votre seau#§7d'eau à la main, posez le plus de#§7seau et faite vous une renommée#§7en tant que professionel du MLG.##§8• §fVersion: §b1.8.8 §7(1.8/1.14.1)#§8• §fEn jeu: §e"+Main.plugin.MLG_HUB+" §fjoueur(s).##§7➥ §aClique pour y accéder!"));
		}else if(Lang.equals("EN")){
			inv.setItem(13, createItemhead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZDM1NzQ0NGFkZTY0ZWM2Y2VhNjQ1ZWM1N2U3NzU4NjRkNjdjNWZhNjIyOTk3ODZlMDM3OTkzMTdlZTRhZCJ9fX0=", "§3CREATIVE", "§8Type: Architecture##§7Re-discover an original game#§7in showing talent as well as of#§7reflection in order to become#§7the best of architects.##§8• §fVersion: §b1.12.2 §7(1.12/1.14.1)#§8• §fIn game: §e0 §fplayer(s).##§7➥ §aClick to access!"));
			inv.setItem(14, createItemhead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYzdlNmM0MGY2OGI3NzVmMmVmY2Q3YmQ5OTE2YjMyNzg2OWRjZjI3ZTI0Yzg1NWQwYTE4ZTA3YWMwNGZlMSJ9fX0=", "§3PVP BOX", "§8Type: Combat/Tactic##§7PVP Fleet ? Chain the murders in#§7an optimized arena and improve#§7your equipment by fighting you#§7in real fights.##§8• §fVersion: §b1.12.2 §7(1.12/1.14.1)#§8• §fIn game: §e0 §fplayer(s).##§7➥ §aClick to access!"));

			
			inv.setItem(11, createItemhead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMzRmY2JjMjU2ZDBiZTdlNjgzYWY4NGUzOGM0YmNkYjcxYWZiOTM5ODUzOGEyOWFhOTZjYmZhMzE4YjJlYSJ9fX0=", "§3SKYBLOCK", "§8Type: Farming/Creation##§7Start an adventure in the air,#§7on your own or as a team, on a#§7small start island. Improve it#§7with many plants and dominate#§7the sky.##§8• §fType: §3§lAQUA#§8• §fVersion: §b1.12.2 §7(1.12/1.14.1)#§8• §fIn game: §e0 §fplayer(s).##§7➥ §aClick to access!"));
			
			inv.setItem(12, createItemhead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYjY5NjVlNmE1ODY4NGMyNzdkMTg3MTdjZWM5NTlmMjgzM2E3MmRmYTk1NjYxMDE5ZGJjZGYzZGJmNjZiMDQ4In19fQ==", "§3SKYBLOCK", "§8Type: Farming/Creation##§7Start an adventure in the air,#§7on your own or as a team, on a#§7small start island. Improve it#§7with many plants and dominate#§7the sky.##§8• §fType: §4§lLAVA#§8• §fVersion: §b1.12.2 §7(1.12/1.14.1)#§8• §fIn game: §e0 §fplayer(s).##§7➥ §aClick to access!"));
			
			inv.setItem(20, createItemhead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvM2FmNTk3NzZmMmYwMzQxMmM3YjU5NDdhNjNhMGNmMjgzZDUxZmU2NWFjNmRmN2YyZjg4MmUwODM0NDU2NWU5In19fQ==", "§3TNTRUN", "§8Type: Speed/Reaction##§7A little race ? Run as soon as#§7possible, in order to stay envy.#§7But beware, the blocks break#§7under your foot.##§8• §fVersion: §b1.8.8 §7(1.8/1.14.1)#§8• §fIn game: §e"+Main.plugin.TNTTOTAL+" §fplayer(s).##§7➥ §aClick to access!"));
			inv.setItem(21, createItemhead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNDlmMWYwN2UyYjFjMzJiYjY0YTEyOGU1MjlmM2FmMWU1Mjg2ZTUxODU0NGVkZjhjYmFhNmM0MDY1YjQ3NmIifX19", "§3MLG", "§8Type: Speed/Reaction##§7Exclusive game mode, your water#§7bucket in the hand, place the most#§7buckets and make you famous as#§7a MLG professional.##§8• §fVersion: §b1.8.8 §7(1.8/1.14.1)#§8• §fIn game: §e"+Main.plugin.MLG_HUB+" §fplayer(s).##§7➥ §aClick to access!"));
		}else if(Lang.equals("ES")){
			
			inv.setItem(13, createItemhead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZDM1NzQ0NGFkZTY0ZWM2Y2VhNjQ1ZWM1N2U3NzU4NjRkNjdjNWZhNjIyOTk3ODZlMDM3OTkzMTdlZTRhZCJ9fX0=", "§3CREATIVE", "§8Tipo: Arquitectura##§7Re-descubre un juego original con#§7un gran talento y una reflexión para#§7convertirte en el mejor de los#§7arquitectos.##§8• §fVersion: §b1.12.2 §7(1.12/1.14.1)#§8• §fApuesta: §e0 §fjugador(es).##§7➥ §aHaga clic para acceder!"));
			inv.setItem(14, createItemhead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYzdlNmM0MGY2OGI3NzVmMmVmY2Q3YmQ5OTE2YjMyNzg2OWRjZjI3ZTI0Yzg1NWQwYTE4ZTA3YWMwNGZlMSJ9fX0=", "§3PVP BOX", "§8Tipo: Combate/Táctica##§7Te gusta PVP ? Encadena a#§7los homicidios en una arena#§7optimizada y mejora tu equi-#§7pamiento en el combate real.##§8• §fVersion: §b1.12.2 §7(1.12/1.14.1)#§8• §fApuesta: §e0 §fjugador(es).##§7➥ §aHaga clic para acceder!"));

			inv.setItem(11, createItemhead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMzRmY2JjMjU2ZDBiZTdlNjgzYWY4NGUzOGM0YmNkYjcxYWZiOTM5ODUzOGEyOWFhOTZjYmZhMzE4YjJlYSJ9fX0=", "§3SKYBLOCK", "§8Tipo: Farming/Creation##§7Comienza una aventura en el aire,#§7solitario o bien en equipo, en una#§7pequeña isla de salida. Mejorarla#§7gracias a de muchas plantas y#§7dominar el mundo del cielo.##§8• §fTipo: §3§lAQUA#§8• §fVersion: §b1.12.2 §7(1.12/1.14.1)#§8• §fApuesta: §e0 §fjugador(es).##§7➥ §aHaga clic para acceder!"));
			inv.setItem(12, createItemhead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYjY5NjVlNmE1ODY4NGMyNzdkMTg3MTdjZWM5NTlmMjgzM2E3MmRmYTk1NjYxMDE5ZGJjZGYzZGJmNjZiMDQ4In19fQ==", "§3SKYBLOCK", "§8Tipo: Farming/Creation##§7Comienza una aventura en el aire,#§7solitario o bien en equipo, en una#§7pequeña isla de salida. Mejorarla#§7gracias a de muchas plantas y#§7dominar el mundo del cielo.##§8• §fTipo: §4§lLAVA#§8• §fVersion: §b1.12.2 §7(1.12/1.14.1)#§8• §fApuesta: §e0 §fjugador(es).##§7➥ §aHaga clic para acceder!"));
			inv.setItem(20, createItemhead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvM2FmNTk3NzZmMmYwMzQxMmM3YjU5NDdhNjNhMGNmMjgzZDUxZmU2NWFjNmRmN2YyZjg4MmUwODM0NDU2NWU5In19fQ==", "§3TNTRUN", "§8Tipo: Velocidad/Reacción##§7Una pequeña carrera ? Corre el#§7tan pronto como sea posible, para#§7seguir queriendo. Pero cuidado, los#§7bloques se rompen bajo tus pies.##§8• §fVersion: §b1.8.8 §7(1.8/1.14.1)#§8• §fApuesta: §e"+Main.plugin.TNTTOTAL+" §fjugador(es).##§7➥ §aHaga clic para acceder!"));
			inv.setItem(21, createItemhead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNDlmMWYwN2UyYjFjMzJiYjY0YTEyOGU1MjlmM2FmMWU1Mjg2ZTUxODU0NGVkZjhjYmFhNmM0MDY1YjQ3NmIifX19", "§3MLG", "§8Tipo: Velocidad/Reacción##§7Modo de juego exclusivo, tu cubo#§7y agua en la mano, coloca la mayor#§7cantidad de cubos y haz una fama#§7como profesional del MLG.##§8• §fVersion: §b1.8.8 §7(1.8/1.14.1)#§8• §fApuesta: §e"+Main.plugin.MLGTOTAL+" §fjugador(es).##§7➥ §aHaga clic para acceder!"));
		}
		p.openInventory(inv);
	}
	
    public static void sendTitle(Player player, Integer fadeIn, Integer stay, Integer fadeOut, String title, String subtitle)
    {
        PlayerConnection connection = ((CraftPlayer)player).getHandle().playerConnection;
     
        PacketPlayOutTitle packetPlayOutTimes = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TIMES, null, fadeIn.intValue(), stay.intValue(), fadeOut.intValue());
        connection.sendPacket(packetPlayOutTimes);
        if (subtitle != null)
        {
            subtitle = ChatColor.translateAlternateColorCodes('&', subtitle);
            IChatBaseComponent titleSub = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + subtitle + "\"}");
            PacketPlayOutTitle packetPlayOutSubTitle = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.SUBTITLE, titleSub);
            connection.sendPacket(packetPlayOutSubTitle);
        }
        if (title != null)
        {
            title = ChatColor.translateAlternateColorCodes('&', title);
            IChatBaseComponent titleMain = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + title + "\"}");
            PacketPlayOutTitle packetPlayOutTitle = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TITLE, titleMain);
            connection.sendPacket(packetPlayOutTitle);
        }
    }
	
	public ItemStack createItemhead(String value, String displayname, String Lore){
        ItemStack head = new ItemStack(Material.SKULL_ITEM, 1, (short)3);
        SkullMeta meta = (SkullMeta) head.getItemMeta();
        GameProfile profile = new GameProfile(UUID.randomUUID(), "");
        profile.getProperties().put("textures", new Property("textures", value));
        Field profileField = null;
        try {
            profileField = meta.getClass().getDeclaredField("profile");
            profileField.setAccessible(true);
            profileField.set(meta, profile);
        } catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException e) {
            e.printStackTrace();
        }
        meta.setDisplayName(displayname);
        String[] split = Lore.split("#");
        
        ArrayList<String> Loreitem = new ArrayList<>();
        for(int in = 0; in < split.length; in++) {
        	Loreitem.add(split[in]);
        }
        meta.setLore(Loreitem);
		head.setItemMeta(meta);
		return head;
	}
		
	
    public ItemStack createItem(Material material, int amount, int data, String displayname, String string) {
        ItemStack item = new ItemStack(material, amount, (short) data);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(displayname);

        String[] split = string.split("#");
      
        ArrayList<String> Lore = new ArrayList<>();
        for(int in = 0; in < split.length; in++) {
            Lore.add(split[in]);
        }
      
        meta.setLore(Lore);
      
        if(displayname.equals("§c§lSOON")){
        	meta.addEnchant(Enchantment.DURABILITY, 1, false);
        	meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        }
        
        item.setItemMeta(meta);
        return item;
    }
    
	public void load(Player p, String lang, String serv){
		if(Main.plugin.load.containsKey(p.getName())) {
			p.sendMessage("§3(Chargement) §7➠ §cAction impossible... §8▎ §7(Patientez)");
			return;
		}
		Main.plugin.load.put(p.getName(), "load");
		if(lang == null){
			lang = "FR";
		}
		final String lang1 = lang;
		ActionBar bar = new ActionBar("§aChargement du compte...");
		if(lang1.equals("EN")){
			bar =  new ActionBar("§aAccount loading...");
		}else if(lang1.equals("ES")){
			bar =  new ActionBar("§aCarga de cuenta...");
		}
		bar.sendToPlayer(p);
		sendTitle(p, 0, 20, 0, "▁▁▂▃▄▅▆▇██", " ");
		Bukkit.getScheduler().scheduleSyncDelayedTask(Main.plugin, new Runnable()
		{
			public void run(){
				sendTitle(p, 0, 20, 0, "▁▂▃▄▅▆▇███", " ");
			}
		}, 3L);
		Bukkit.getScheduler().scheduleSyncDelayedTask(Main.plugin, new Runnable()
		{
			public void run(){
				sendTitle(p, 0, 20, 0, "▂▃▄▅▆▇███▇", " ");
			}
		}, 6L);
		Bukkit.getScheduler().scheduleSyncDelayedTask(Main.plugin, new Runnable()
		{
			public void run(){
				sendTitle(p, 0, 20, 0, "▃▄▅▆▇███▇▆", " ");
			}
		}, 9L);
		Bukkit.getScheduler().scheduleSyncDelayedTask(Main.plugin, new Runnable()
		{
			public void run(){
				sendTitle(p, 0, 20, 0, "▄▅▆▇███▇▆▅", " ");
			}
		}, 12L);
		Bukkit.getScheduler().scheduleSyncDelayedTask(Main.plugin, new Runnable()
		{
			public void run(){
				sendTitle(p, 0, 20, 0, "▅▆▇███▇▆▅▄", " ");
			}
		}, 15L);
		Bukkit.getScheduler().scheduleSyncDelayedTask(Main.plugin, new Runnable()
		{
			public void run(){
				
				sendTitle(p, 0, 20, 0, "▆▇███▇▆▅▄▂", " ");
				ActionBar bar = new ActionBar("§aChargement du compte..");
				if(lang1.equals("EN")){
					bar =  new ActionBar("§aAccount loading..");
				}else if(lang1.equals("ES")){
					bar =  new ActionBar("§aCarga de cuenta..");
				}
				bar.sendToPlayer(p);
				
			}
		}, 18L);
		Bukkit.getScheduler().scheduleSyncDelayedTask(Main.plugin, new Runnable()
		{
			public void run(){
				sendTitle(p, 0, 20, 0, "▇███▇▆▅▄▂▁", " ");
			}
		}, 21L);
		Bukkit.getScheduler().scheduleSyncDelayedTask(Main.plugin, new Runnable()
		{
			public void run(){
				sendTitle(p, 0, 20, 0, "███▇▆▅▄▃▂▁", " ");
			}
		}, 24L);
		Bukkit.getScheduler().scheduleSyncDelayedTask(Main.plugin, new Runnable()
		{
			public void run(){
				sendTitle(p, 0, 20, 0, "██▇▆▅▄▃▂▁▁", " ");
			}
		}, 27L);
		Bukkit.getScheduler().scheduleSyncDelayedTask(Main.plugin, new Runnable()
		{
			public void run(){
				sendTitle(p, 0, 20, 0, "███▇▆▅▄▃▂▁", " ");
			}
		}, 30L);
		Bukkit.getScheduler().scheduleSyncDelayedTask(Main.plugin, new Runnable()
		{
			public void run(){
				sendTitle(p, 0, 20, 0, "▇███▇▆▅▄▃▂", " ");
			}
		}, 33L);
		Bukkit.getScheduler().scheduleSyncDelayedTask(Main.plugin, new Runnable()
		{
			public void run(){
				sendTitle(p, 0, 20, 0, "▆▇███▇▆▅▄▃", " ");
				ActionBar bar = new ActionBar("§aChargement du compte.");
				if(lang1.equals("EN")){
					bar =  new ActionBar("§aAccount loading.");
				}else if(lang1.equals("ES")){
					bar =  new ActionBar("§aCarga de cuenta.");
				}
				bar.sendToPlayer(p);
			}
		}, 36L);
		Bukkit.getScheduler().scheduleSyncDelayedTask(Main.plugin, new Runnable()
		{
			public void run(){
				sendTitle(p, 0, 20, 0, "▅▆▇███▇▆▅▄", " ");
			}
		}, 39L);
		Bukkit.getScheduler().scheduleSyncDelayedTask(Main.plugin, new Runnable()
		{
			public void run(){
				sendTitle(p, 0, 20, 0, "▄▅▆▇███▇▆▅", " ");
			}
		}, 42L);
		Bukkit.getScheduler().scheduleSyncDelayedTask(Main.plugin, new Runnable()
		{
			public void run(){
				sendTitle(p, 0, 20, 0, "▃▄▅▆▇███▇▆", " ");
			}
		}, 45L);
		Bukkit.getScheduler().scheduleSyncDelayedTask(Main.plugin, new Runnable()
		{
			public void run(){
				sendTitle(p, 0, 20, 0, "▂▃▄▅▆▇███▇", " ");
			}
		}, 48L);
		Bukkit.getScheduler().scheduleSyncDelayedTask(Main.plugin, new Runnable()
		{
			public void run(){
				sendTitle(p, 0, 20, 0, "▁▂▃▄▅▆▇███", " ");
				
			}
		}, 51L);
		Bukkit.getScheduler().scheduleSyncDelayedTask(Main.plugin, new Runnable()
		{
			public void run(){
				sendTitle(p, 0, 20, 0, "▁▁▂▃▄▅▆▇██", " ");
				Main.plugin.load.remove(p.getName());
				ByteArrayDataOutput out = ByteStreams.newDataOutput();
				out.writeUTF("Connect");
				out.writeUTF(serv);
				p.sendPluginMessage(Main.plugin, "BungeeCord", out.toByteArray());
			}
		}, 54L);
	}
	
}
