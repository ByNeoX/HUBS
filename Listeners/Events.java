package mlg.byneox.tc.Listeners;


import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import mlg.byneox.tc.Main;
import mlg.byneox.tc.api.INVENTORYAPI;
import mlg.byneox.tc.api.TCAPI;

public class Events implements Listener {
	
	TCAPI API = new TCAPI();
	INVENTORYAPI INVAPI = new INVENTORYAPI();
	Location loc = new Location(Bukkit.getWorld("world_"), 0, 170, -21, 180, 0);
	
	@EventHandler
	public void onarrow(ProjectileHitEvent e){
		if(e.getEntityType() == EntityType.ARROW){
			e.getEntity().remove();
		}
		
	}
	
	@EventHandler
    public void on(PlayerInteractEntityEvent e) {
        if(e.getRightClicked() instanceof Villager) {
        	e.setCancelled(true);
            API.startarcher(e.getPlayer());
            return;
        }
    }
	
	
	
    @EventHandler
    public void onanymove(PlayerMoveEvent e){
    	if(Main.plugin.ARCHER.containsKey(e.getPlayer().getName())){
    		if(e.getPlayer().getLocation().distance(new Location(e.getPlayer().getWorld(), 17.5, 186, -64.5)) > 0.5){
    			e.getPlayer().teleport(new Location(e.getPlayer().getWorld(), 17.5, 186, -64.5));
    			return;
    		}
    	}else{
	    	if(!Main.plugin.INJUMP.containsKey(e.getPlayer().getName())){
	    		if(e.getPlayer().getLocation().getY() < 105 || e.getPlayer().getLocation().getY() > 240){
	    			e.getPlayer().teleport(new Location(Bukkit.getWorld("world_"), 0.5, 153, 0.5, 180, 0));
	    			return;
	    		}
	    		if(e.getPlayer().getLocation().getZ() <-105){
	    			e.getPlayer().teleport(new Location(Bukkit.getWorld("world_"), 0.5, 151.2, 0.5, 180, 0));
	    		}else if(e.getPlayer().getLocation().getZ() > 58){
	    			e.getPlayer().teleport(new Location(Bukkit.getWorld("world_"), 0.5, 151.2, 0.5, 180, 0));
	    		}
	    		if(e.getPlayer().getLocation().getX() < -79){
	    			e.getPlayer().teleport(new Location(Bukkit.getWorld("world_"), 0.5, 151.2, 0.5, 180, 0));
	    		}else if(e.getPlayer().getLocation().getX() > 83){
	    			e.getPlayer().teleport(new Location(Bukkit.getWorld("world_"), 0.5, 151.2, 0.5, 180, 0));
	    		}
	    	}
    	}
    }
    
    @EventHandler
    public void oninvenotryclose(InventoryCloseEvent e){
		if(Main.plugin.COMMANDEEXECUTE.containsKey(e.getPlayer().getName())){
			return;
		}
    	for(ItemStack items: e.getPlayer().getInventory().getContents()){
    		if(items == null){
    			return;
    		}
    		if(items == (new ItemStack(Material.AIR))){
    			return;
    		}
    		if(items.getType() == Material.COMPASS || items.getType() == Material.BANNER || items.getType() == Material.BED  || items.getType() == Material.SKULL || items.getType() == Material.SKULL_ITEM){
    			return;
    		}else{
    			e.getPlayer().getInventory().remove(items.getType());
    		}
    		
    	}
    }
    

    
	@EventHandler
	public void oncommand(PlayerCommandPreprocessEvent e){
		boolean DOROIT = false;
		if (!(e.getMessage().contains("/report") || e.getMessage().contains("/Commande") || e.getMessage().contains("/grade") || e.getMessage().contains("/spawn") || e.getMessage().contains("/lobby") || e.getMessage().contains("/temps") || e.getMessage().contains("/reportlist") || e.getMessage().contains("/hub") || e.getMessage().contains("/moveplayeronserver") || e.getMessage().contains("/commande") || e.getMessage().equalsIgnoreCase("/msg") || e.getMessage().equalsIgnoreCase("/r"))) {
			if(!Main.plugin.COMMANDEEXECUTE.containsKey(e.getPlayer().getName())){
				DOROIT = false;
			}else if(Main.plugin.COMMANDEEXECUTE.containsKey(e.getPlayer().getName())){
				if(Main.plugin.COMMANDEEXECUTE.get(e.getPlayer().getName()).equals("off")){
					DOROIT = false;
				}else if(Main.plugin.COMMANDEEXECUTE.get(e.getPlayer().getName()).equals("on")){
					DOROIT = true;
				}
			}
			if (DOROIT == false){
				e.setCancelled(true);
				e.getPlayer().sendMessage("§7➠ §fCommande inconnue... :x");
				return;
			}
		}

	}
    
    @EventHandler
    public void ondamage(EntityDamageEvent e){
    	e.setCancelled(true);
    }
    	
    @EventHandler
    public void ondamagee(EntityDamageByEntityEvent e){
		if(!Main.plugin.GAMEARCHER){
			return;
		}else{
	    	if(e.getCause() == DamageCause.PROJECTILE){
	    		if(e.getEntityType() == EntityType.ARMOR_STAND){
	    			if((ArmorStand) e.getEntity() != null){
	    				if(((ArmorStand) e.getEntity()).getHelmet().getType() == Material.SKULL || ((ArmorStand) e.getEntity()).getHelmet().getType() == Material.SKULL_ITEM){
	    					if(((ArmorStand) e.getEntity()).getChestplate().getType() == Material.AIR){
	    						if(((ArmorStand) e.getEntity()).getLeggings().getType() == Material.AIR){
	    							if(((ArmorStand) e.getEntity()).getBoots().getType() == Material.AIR){
	    								Arrow z = (Arrow) e.getDamager();
	    								if(z != null){
	    									Player p = (Player) z.getShooter();
	    									if(p != null){
		        								if(Main.plugin.ARCHER.containsKey(p.getName())){
		        									Main.plugin.ARCHER.replace(p.getName(), Main.plugin.ARCHER.get(p.getName())+1);
		        									p.sendMessage("§6(Stand) §7➠ §aCible touchée avec succés! §f(§f§l"+Main.plugin.ARCHER.get(p.getName())+"§f§l/10§r§f)");
		        									e.getEntity().remove();
		        									p.playSound(p.getLocation(), Sound.ORB_PICKUP, 2, 1);
		        								}
	    									}
	    								}
	    							}
	    						}
	    					}
	    				}
	    			}
	    		}
	    	}
		}
    }
	
    @EventHandler
    public void ondrop(PlayerDropItemEvent e){
    	if(!Main.plugin.COMMANDEEXECUTE.containsKey(e.getPlayer().getName())){
    		e.setCancelled(true);
    	}
    }
    
	@EventHandler
	public void onhunger(FoodLevelChangeEvent e){
		e.setCancelled(true);
	}
	
	@EventHandler
	public void onbreak(BlockPlaceEvent e){
		if(!Main.plugin.COMMANDEEXECUTE.containsKey(e.getPlayer().getName())){
			e.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onplace(BlockBreakEvent e){
		if(!Main.plugin.COMMANDEEXECUTE.containsKey(e.getPlayer().getName())){
			e.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onWeather(WeatherChangeEvent e){
		e.setCancelled(true);
	}
	
	@EventHandler
    public void onPlayerInteractEntity(PlayerInteractEntityEvent event)
    {
		if(
			!event.isCancelled()
			&& event.getRightClicked() instanceof ItemFrame
			&& !((ItemFrame)event.getRightClicked()).getItem().getType().equals(Material.AIR) //we dont need to prevent put items into the empty item frame (thats out of scope of this plugin)
		)
		{
			event.setCancelled(true);
		}
    }
	
	
	@EventHandler
	public void a(PlayerInteractEvent event){
		if (event.getAction() != Action.RIGHT_CLICK_BLOCK) {
	      return;
	    }
		if (event.getClickedBlock().getType() == Material.BED_BLOCK || event.getClickedBlock().getType() == Material.BED || event.getClickedBlock().getType() == Material.ENDER_CHEST || event.getClickedBlock().getType() == Material.ENCHANTMENT_TABLE || event.getClickedBlock().getType() == Material.ANVIL || event.getClickedBlock().getType() == Material.BREWING_STAND || event.getClickedBlock().getType() == Material.TRAP_DOOR || event.getClickedBlock().getType() == Material.CHEST || event.getClickedBlock().getType() == Material.FENCE_GATE || event.getClickedBlock().getType() == Material.DARK_OAK_FENCE_GATE || event.getClickedBlock().getType() == Material.SPRUCE_FENCE_GATE || event.getClickedBlock().getType() == Material.FENCE) {
			event.setCancelled(true);
		}
		if (event.getClickedBlock().getType() == Material.LEVER) {
			event.setCancelled(true);
		}
	}
	
	//PART MENU
	
	@SuppressWarnings("deprecation")
	public boolean consumeItem(Player player, int count, Material mat) {
	    Map<Integer, ? extends ItemStack> ammo = player.getInventory().all(mat);

	    int found = 0;
	    for (ItemStack stack : ammo.values())
	        found += stack.getAmount();
	    if (count > found)
	        return false;
	    for (Integer index : ammo.keySet()) {
	        ItemStack stack = ammo.get(index);

	        int removed = Math.min(count, stack.getAmount());
	        count -= removed;

	        if (stack.getAmount() == removed)
	            player.getInventory().setItem(index, null);
	        else
	            stack.setAmount(stack.getAmount() - removed);

	        if (count <= 0)
	            break;
	    }

	    player.updateInventory();
	    if(found==1){
	    	player.sendMessage("§6(Stand) §7➠ §cAïe! Il n'y a plus de munition camarade!");
	    	player.sendMessage("§6(Stand) §7➠ §4Votre partie s'arrête ici...");
			API.stoparcher(player);
	    }
	    return true;
	}
	
	@EventHandler
	public void onclick(PlayerInteractEvent e){
		if(e.getAction() != null){
			if(e.getPlayer().getItemInHand().getType() == null){
				return;
			}
			if(e.getPlayer().getItemInHand().getType() != Material.AIR){
				if(e.getAction() != Action.PHYSICAL){
					if (e.getItem().getType() != null){
						if (e.getItem().getType() == Material.AIR){
							return;
						}else if (e.getItem().getType() == Material.NETHER_STAR){
							INVAPI.opennetherstar(e.getPlayer());
						}else if (e.getItem().getType() == Material.COMPASS){
							INVAPI.openMainMenu(e.getPlayer());
						}else if (e.getItem().getType() == Material.SKULL_ITEM){
							if(e.getItem().getItemMeta().getDisplayName().equals("§aChanger de langue §8• §7(Clic-droit)") || e.getItem().getItemMeta().getDisplayName().equals("§aChange language §8• §7(Right-click)") || e.getItem().getItemMeta().getDisplayName().equals("§aCambiar idioma §8• §7(Haga-clic)")){
								INVAPI.openLangageMenu(e.getPlayer());	
							}else{
								INVAPI.openBoutiqueMenu(e.getPlayer(), false, null);	
							}
						}else if (e.getItem().getType() == Material.STICK){
							if(e.getPlayer().getInventory().contains(Material.ARROW)){
								e.getPlayer().launchProjectile(Arrow.class);
								consumeItem(e.getPlayer(), 1, Material.ARROW);
								return;
							}else{
								e.getPlayer().sendMessage("§6(Stand) §7➠ §cAïe! Il n'y a plus de munition camarade!");
								e.getPlayer().sendMessage("§6(Stand) §7➠ §4Votre partie s'arrête ici...");
								API.stoparcher(e.getPlayer());
								return;
							}
						}

					}
				}
			}
		}
	}
}
	
	
	
	
