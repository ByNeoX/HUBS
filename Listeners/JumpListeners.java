package mlg.byneox.tc.Listeners;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.Vector;

import mlg.byneox.tc.Main;
import mlg.byneox.tc.Utils.RepeatingTask;
import mlg.byneox.tc.api.ActionBar;
import mlg.byneox.tc.api.CLASSEMENTAPI;
import mlg.byneox.tc.api.INVENTORYAPI;
import mlg.byneox.tc.api.TCAPI;

public class JumpListeners implements Listener{
	TCAPI API = new TCAPI();
	INVENTORYAPI INVAPI = new INVENTORYAPI();
	Location loc1 = new Location(Bukkit.getWorld("world_"), 0.5, 134, -68.5, -35, 17);
	Location loc2 = new Location(Bukkit.getWorld("world_"), -4.5, 121, -26.5, -38, 1);
	Location jump = new Location(Bukkit.getWorld("world_"), -28.746, 148, -46.0, 180, 0);
	public HashMap<String, String> timejump = new HashMap<String, String>();
	public HashMap<String, String> checkpoint = new HashMap<String, String>();
	
	
	@EventHandler
	public void onleavejump(PlayerQuitEvent e){
		Player p =e.getPlayer();
		if(Main.plugin.INJUMP.containsKey(p.getName())){
			Main.plugin.INJUMP.remove(p.getName());
			this.checkpoint.remove(e.getPlayer().getName());
		}
		if(timejump.containsKey(p.getName())){
			timejump.remove(p.getName());
		}
	}
	
	public void stopjump(Player p){
		if(Main.plugin.INJUMP.containsKey(p.getName())){
			Main.plugin.INJUMP.remove(p.getName());
			this.checkpoint.remove(p.getName());
		}
		if(timejump.containsKey(p.getName())){
			timejump.remove(p.getName());
		}
		p.getInventory().clear();
		p.teleport(new Location(Bukkit.getWorld("world_"), 0.5, 153, 0.5, 180, 0));
		Bukkit.getScheduler().scheduleSyncDelayedTask(Main.plugin, new Runnable()
		{
			public void run(){
				ActionBar bar = new ActionBar("§4⚠ §7▎ §cFin du jump...");
				bar.sendToPlayer(p);
				
				if(Main.plugin.dataPlayers.containsKey(p.getName())){
					if(!(Main.plugin.dataPlayers.get(p.getName()).getGrade().equals("Joueur") || Main.plugin.dataPlayers.get(p.getName()).getGrade().equals("VIP"))){
						p.setAllowFlight(true);
					}
				}
				ItemStack n = new ItemStack(Material.COMPASS, 1);
				ItemMeta m = n.getItemMeta();
				String lang = "FR";
				if(Main.plugin.dataPlayers.containsKey(p.getName())){
					lang = Main.plugin.dataPlayers.get(p.getName()).getLang();
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
				if (lang.equals("EN")){
					m.setDisplayName("§bMain menu §8• §7(Right-click)");
					p.getInventory().setItem(1, INVAPI.Itemlanguage("GIVE", p , "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvODc5ZDk5ZDljNDY0NzRlMjcxM2E3ZTg0YTk1ZTRjZTdlOGZmOGVhNGQxNjQ0MTNhNTkyZTQ0MzVkMmM2ZjlkYyJ9fX0=", lang, "GIVE", "GIVE", "GIVE"));
				}else if (lang.equals("ES")){
					m.setDisplayName("§bMenú principal §8• §7(Haga-clic)");
					p.getInventory().setItem(1, INVAPI.Itemlanguage("GIVE", p, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMzJiZDQ1MjE5ODMzMDllMGFkNzZjMWVlMjk4NzQyODc5NTdlYzNkOTZmOGQ4ODkzMjRkYThjODg3ZTQ4NWVhOCJ9fX0=", lang, "GIVE", "GIVE", "GIVE"));
				}else{
					m.setDisplayName("§bMenu principal §8• §7(Clic-droit)");
					p.getInventory().setItem(1, INVAPI.Itemlanguage("GIVE", p , "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNTEyNjlhMDY3ZWUzN2U2MzYzNWNhMWU3MjNiNjc2ZjEzOWRjMmRiZGRmZjk2YmJmZWY5OWQ4YjM1Yzk5NmJjIn19fQ==", lang, "GIVE", "GIVE", "GIVE"));
				}
				n.setItemMeta(m);
				p.getInventory().setItem(0, n);
			}
		}, 5L);
	}
	
	
	public boolean checklife(int life, Player p){
		if(life==1){
			stopjump(p);
			return false;
			
		}
		return true;
	}
	
	
	@EventHandler
	public void onpressureplate(PlayerInteractEvent  e){
		if(e.getAction().equals(Action.PHYSICAL)){
			if(e.getClickedBlock().getType() == Material.GOLD_PLATE){
				if(!API.isspec(e.getPlayer().getName())){
					if(e.getPlayer().getLocation().getY() > 147.0){
						if(Main.plugin.INJUMP.containsKey(e.getPlayer().getName())){
							return;
						}else{
							e.getPlayer().sendMessage("§3(TolariaMC) §7➠ §aDémarrage du jump...");
							e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.valueOf("LEVEL_UP"), 2, 5);
							this.checkpoint.put(e.getPlayer().getName(), "JUMP");
							Main.plugin.INJUMP.put(e.getPlayer().getName(), 3);
							timejump.put(e.getPlayer().getName(), "00.00");
							e.getPlayer().setAllowFlight(false);
							if(e.getPlayer().isFlying()){
								e.getPlayer().setFlying(false);
							}
							e.getPlayer().setGameMode(GameMode.ADVENTURE);
							e.getPlayer().getInventory().clear();
							
							if(Main.plugin.dataPlayers.containsKey(e.getPlayer().getName())){
								if(Main.plugin.dataPlayers.get(e.getPlayer().getName()).getLang().equals("FR")){
									e.getPlayer().getInventory().setItem(8, INVAPI.createItem(Material.BED, 1, 0, "§cRetourner au HUB §8• §7(Clic-droit)", ""));
								}else if(Main.plugin.dataPlayers.get(e.getPlayer().getName()).getLang().equals("EN")){
									e.getPlayer().getInventory().setItem(8, INVAPI.createItem(Material.BED, 1, 0, "§cBack to Lobby §8• §7(Right-click)", ""));
								}else if(Main.plugin.dataPlayers.get(e.getPlayer().getName()).getLang().equals("ES")){
									e.getPlayer().getInventory().setItem(8, INVAPI.createItem(Material.BED, 1, 0, "§cVolver al lobby §8• §7(Haga-clic)", ""));
								}
							}else{
								e.getPlayer().getInventory().setItem(8, INVAPI.createItem(Material.BED, 1, 0, "§cRetourner au HUB §8• §7(Clic-droit)", ""));
							}
							RepeatingTask repeatingTask = new RepeatingTask(Main.plugin, 0, 20) {
								int secondes = 0;
								int minutes = 0;
								String health = API.gethealth(Main.plugin.INJUMP.get(e.getPlayer().getName()));
								@Override
					            public void run() {
									if(e.getPlayer().isOnline()){
										if(Main.plugin.INJUMP.containsKey(e.getPlayer().getName())){
											if (secondes != 59){
												secondes = secondes+1;
											}else{
												secondes=0;
												minutes=minutes+1;
											}
											health = API.gethealth(Main.plugin.INJUMP.get(e.getPlayer().getName()));
											ActionBar bar = new ActionBar("§4Vie(s) §7▎§c " + health);
											if(minutes> 15){
												stopjump(e.getPlayer());
												canncel();
												return;
											}
											if (minutes < 10){
												if (secondes < 10){
													bar = new ActionBar("§f0"+minutes+".0"+secondes+" §8• §4Vie(s) §7▎§c "+ health);
													timejump.replace(e.getPlayer().getName(), "0"+minutes+".0"+secondes);
												}else{
													bar = new ActionBar("§f0"+minutes+"."+secondes+" §8• §4Vie(s) §7▎§c "+ health);
													timejump.replace(e.getPlayer().getName(), "0"+minutes+"."+secondes);
												}
											}else{
												if (secondes < 10){
													bar = new ActionBar("§f"+minutes+".0"+secondes+" §8• §4Vie(s) §7▎§c "+ health);
													timejump.replace(e.getPlayer().getName(), minutes+".0"+secondes);
												}else{
													bar = new ActionBar("§f"+minutes+"."+secondes+" §8• §4Vie(s) §7▎§c "+ health);
													timejump.replace(e.getPlayer().getName(), minutes+"."+secondes);
												}
											}
											bar.sendToPlayer(e.getPlayer());
										}else{
											canncel();
										}
									}else{
										canncel();
									}
								}
							};
						}
					}else{
						if(e.getPlayer().getLocation().getY() < 122){
							if(Main.plugin.INJUMP.containsKey(e.getPlayer().getName())){
								if(timejump.containsKey(e.getPlayer().getName())){
									if(this.checkpoint.get(e.getPlayer().getName()).equals("2")){
										CLASSEMENTAPI API = new CLASSEMENTAPI();
										int place = API.checktimeofplayer(e.getPlayer().getName(), timejump.get(e.getPlayer().getName()));
										Date now = new Date();
										SimpleDateFormat format = new SimpleDateFormat("MM/yyyy");
										e.getPlayer().sendMessage(" ");
										e.getPlayer().sendMessage("              §7➢ §f§lJUMP");
										e.getPlayer().sendMessage(" ");
										e.getPlayer().sendMessage(" §7➠ §eTemps: §a§l"+timejump.get(e.getPlayer().getName())+" §7(s)");
										e.getPlayer().sendMessage(" §7➠ §eClassement: §c#"+place+" §7("+format.format(now).toString()+")");
										e.getPlayer().sendMessage(" ");
										Bukkit.getScheduler().scheduleSyncDelayedTask(Main.plugin, new Runnable()
										{
										public void run(){
											e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.valueOf("LEVEL_UP"), 2, 5);
											}
										},4L);
									}else{
										return;
									}
								}
								stopjump(e.getPlayer());
								return;
							}else{
								return;
							}
						}
					}
				}			
			}else if(e.getClickedBlock().getType() == Material.IRON_PLATE){
				if(!API.isspec(e.getPlayer().getName())){
					if(Main.plugin.INJUMP.containsKey(e.getPlayer().getName())){
						if(e.getPlayer().getLocation().getY() > 136){
							return;
						}
						int checkpoint = 1;
						if(e.getPlayer().getLocation().getY() < 133){
							checkpoint = 2;
						}
						if(checkpoint == 2){
							if(!this.checkpoint.get(e.getPlayer().getName()).equals("1")){
								return;
							}
							
						}
						if(this.checkpoint.get(e.getPlayer().getName()).equals(checkpoint+"")){
							return;
						}else{
							this.checkpoint.replace(e.getPlayer().getName(), checkpoint+"");
							e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.valueOf("ORB_PICKUP"), 2, 5);
							e.getPlayer().sendTitle(" ", "§7("+checkpoint+"/2)");
						}
						
					return;
					}
				}
			}
		}else if(!e.getAction().equals(Action.PHYSICAL)){
			if(e.getAction() != null){
				if(e.getPlayer().getItemInHand().getType() == null){
					return;
				}
				if(e.getPlayer().getItemInHand().getType() != Material.AIR){
					if (e.getItem().getType() != null){
						if (e.getItem().getType() == Material.AIR){
							return;
						}else if(e.getItem().getType() == Material.BED){
							stopjump(e.getPlayer());
							return;
						}
					}
				}
			}
		}
	}
	
	@EventHandler
	public void onMoveonJump(PlayerMoveEvent e){
		if(Main.plugin.INJUMP.containsKey(e.getPlayer().getName())){
			int health = Main.plugin.INJUMP.get(e.getPlayer().getName());
			Location loc = e.getPlayer().getLocation();
			loc.setY(loc.getY()-0.5);
			if(e.getPlayer().getLocation().getY() > 148){
				if(e.getPlayer().getLocation().getX() > -26.0 && e.getPlayer().getLocation().getZ() > -41.0){
					e.getPlayer().teleport(jump);
					if(checklife(health, e.getPlayer())==true){
						Main.plugin.INJUMP.replace(e.getPlayer().getName(), (health-1));
					}
					return;
				}
			}
			if(!this.checkpoint.containsKey(e.getPlayer().getName())){
				this.checkpoint.put(e.getPlayer().getName(), "JUMP");
			}else{
				if(this.checkpoint.get(e.getPlayer().getName()).equals("JUMP")){
					if(loc.getY() < 129){
						e.getPlayer().teleport(jump);
						if(checklife(health, e.getPlayer())==true){
							Main.plugin.INJUMP.replace(e.getPlayer().getName(), (health-1));
						}
					}
				}else{
					if(loc.getY() < 117){
						if(this.checkpoint.get(e.getPlayer().getName()).equals("1")){
							e.getPlayer().teleport(loc1);
						}else{
							e.getPlayer().teleport(loc2);
						}
						if(checklife(health, e.getPlayer())==true){
							Main.plugin.INJUMP.replace(e.getPlayer().getName(), (health-1));
						}
					}
				}
			}
			Material b = null;
			if(loc.getBlock().getType() != null){
				b = loc.getBlock().getType();
			}else{
				b = Material.AIR;
			}
			if(this.checkpoint.get(e.getPlayer().getName()).equals("JUMP")){
				if(loc.getBlock().getY() < 143.5){
					if (!(b == Material.AIR || b == Material.IRON_PLATE || b == Material.LONG_GRASS || b == Material.WOOD_DOUBLE_STEP || b == Material.WOOD_STEP || b == Material.GRASS || b == Material.WOOD)){
						if(checklife(health, e.getPlayer())==true){
							if(this.checkpoint.get(e.getPlayer().getName()).equals("JUMP")){
								e.getPlayer().teleport(jump);
							}else if(this.checkpoint.get(e.getPlayer().getName()).equals("1")){
								e.getPlayer().teleport(loc1);
							}else{
								e.getPlayer().teleport(loc2);
							}
							Main.plugin.INJUMP.replace(e.getPlayer().getName(), (health-1));
						}
						return;
					}
				}
			}
			
			if (!(b == Material.AIR || b == Material.LONG_GRASS || b == Material.SPRUCE_FENCE || b == Material.DEAD_BUSH || b == Material.STATIONARY_LAVA || b == Material.TORCH || b == Material.FENCE || b == Material.STAINED_CLAY || b == Material.WOOD  || b == Material.LAVA || b == Material.CARPET || b == Material.COBBLE_WALL || b == Material.WEB || b == Material.PACKED_ICE || b == Material.ICE || b == Material.IRON_BLOCK || b == Material.IRON_PLATE || b == Material.GOLD_PLATE || b == Material.STONE || b == Material.STEP || b == Material.DOUBLE_STEP || b == Material.WOOD_DOUBLE_STEP || b == Material.GRASS || b == Material.DIRT || b == Material.SOUL_SAND || b == Material.WOOD_STEP  || b == Material.STONE_SLAB2 || b == Material.DOUBLE_STONE_SLAB2 || b == Material.STAINED_GLASS_PANE || b == Material.GOLD_BLOCK || b == Material.LADDER)){
				if(checklife(health, e.getPlayer())==true){
					if(this.checkpoint.get(e.getPlayer().getName()).equals("JUMP")){
						e.getPlayer().teleport(jump);
					}else if(this.checkpoint.get(e.getPlayer().getName()).equals("1")){
						e.getPlayer().teleport(loc1);
					}else{
						e.getPlayer().teleport(loc2);
					}
					Main.plugin.INJUMP.replace(e.getPlayer().getName(), (health-1));
				}
				return;
			}else{
				if(b==Material.STONE){
					if(loc.getBlock().getY() < 132.0){
						if(checklife(health, e.getPlayer())==true){
							if(this.checkpoint.get(e.getPlayer().getName()).equals("JUMP")){
								e.getPlayer().teleport(jump);
							}else if(this.checkpoint.get(e.getPlayer().getName()).equals("1")){
								e.getPlayer().teleport(loc1);
							}else{
								e.getPlayer().teleport(loc2);
							}
							Main.plugin.INJUMP.replace(e.getPlayer().getName(), (health-1));
						}
						return;
					}
				}
				return;
			}
		}else{
			return;
		}
	}
	

}
