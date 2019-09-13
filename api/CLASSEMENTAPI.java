package mlg.byneox.tc.api;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import mlg.byneox.tc.Main;
import mlg.byneox.tc.Utils.CLASSEMENT;
import mlg.byneox.tc.Utils.FileUtils;
import mlg.byneox.tc.Utils.ProfileSerializationManager;

public class CLASSEMENTAPI {
	
	
	public int checktimeofplayer(String name, String timeparsed){
		File saveDir = new File(Main.plugin.getDataFolder(), "/Classement/");
		final File file = new File(saveDir, "Classement.json");
		
		if(file.exists()){
			final ProfileSerializationManager profileSerializationManager = Main.plugin.getProfileSerializationManager();
			final String json = FileUtils.loadcontent(file);
			final CLASSEMENT profile = profileSerializationManager.deserializeclassement(json);
			classementupdate(profile, timeparsed, name);
			List<String> Name2 = profile.getName();
			int place = 0;
			if(Name2.contains(name)){
				if(Name2.get(0).equals(name)){
					place = 1;
				}else if(Name2.get(1).equals(name)){
					place = 2;
				}else if(Name2.get(2).equals(name)){
					place = 3;
				}else if(Name2.get(3).equals(name)){
					place = 4;
				}else if(Name2.get(4).equals(name)){
					place = 5;
				}
			}
			return place;
		}else{
			Bukkit.broadcastMessage("§3(TolariaMC) §7➠ §cAction impossible... §7(No_classement/Creation)");
			final ProfileSerializationManager profileSerializationManager = Main.plugin.getProfileSerializationManager();
			ArrayList<String> namelore = new ArrayList<String>();
			namelore.add("Aucun...");
			namelore.add("Aucun...");
			namelore.add("Aucun...");
			namelore.add("Aucun...");
			namelore.add("Aucun...");
			ArrayList<String> timelore = new ArrayList<String>();
			timelore.add("97.00");
			timelore.add("97.30");
			timelore.add("98.00");
			timelore.add("98.30");
			timelore.add("99.00");
			final CLASSEMENT profile = CLASSEMENT.createProfile(namelore, timelore);
			final String json = profileSerializationManager.CLASSEMENTserialize(profile);
			FileUtils.save(file, json);
			
		}
		return 0;
	}
	
	public void classementupdate(CLASSEMENT profile, String timeparsed, String name){
		List<String> Name = profile.getName();
		List<String> Time = profile.getTime();
		File saveDir = new File(Main.plugin.getDataFolder(), "/Classement/");
		final ProfileSerializationManager profileSerializationManager = Main.plugin.getProfileSerializationManager();
		final File file = new File(saveDir, "Classement.json");
		String secondofplayer = timeparsed.toString().substring(3);
		String minutesofplayer = timeparsed.substring(0,2);
		long secplayer = TimeUnit.SECONDS.toSeconds(Long.parseLong(secondofplayer));
		long minplayer = TimeUnit.MINUTES.toSeconds(Long.parseLong(minutesofplayer));
		long timeofplayer = (secplayer+minplayer);
		ArrayList<Long> time = new ArrayList<Long>();
		for(int i=0; i<5; i++){
			String time1 = Time.get(i);
			String timesecondes1 = time1.toString().substring(3);
			String timeminutes1 = time1.substring(0,2);
			long sec1= TimeUnit.SECONDS.toSeconds(Long.parseLong(timesecondes1));
			long min1= TimeUnit.MINUTES.toSeconds(Long.parseLong(timeminutes1));
			time.add((sec1+min1));
		}
		if(timeofplayer <= time.get(0)){//1er
			if(Name.contains(name)){
				if(!(Name.get(0).equals(name))){
					if(!Name.get(3).equals(name)){
						Name.set(4, Name.get(3));
						Time.set(4, Time.get(3));
					}
					if(!Name.get(2).equals(name)){
						Name.set(3, Name.get(2));
						Time.set(3, Time.get(2));
					}
					if(!Name.get(1).equals(name)){
						Name.set(2, Name.get(1));
						Time.set(2, Time.get(1));
					}
					if(!Name.get(0).equals(name)){
						Name.set(1, Name.get(0));
						Time.set(1, Time.get(0));
					}
					Name.set(0, name);
					Time.set(0, timeparsed);
				}
			}
			if(Name.get(0).equals(name)){
				Time.set(0, timeparsed);
			}else if(!Name.contains(name)){
				Name.set(4, Name.get(3));
				Time.set(4, Time.get(3));
				Name.set(3, Name.get(2));
				Time.set(3, Time.get(2));
				Name.set(2, Name.get(1));
				Time.set(2, Time.get(1));
				Name.set(1, Name.get(0));
				Time.set(1, Time.get(0));
				Name.set(0, name);
				Time.set(0, timeparsed);
			}
		}else if(timeofplayer <= time.get(1)){//2ème
			if(Name.contains(name)){
				if(!(Name.get(0).equals(name) || Name.get(1).equals(name))){
					if(!Name.get(3).equals(name)){
						Name.set(4, Name.get(3));
						Time.set(4, Time.get(3));
					}
					if(!Name.get(2).equals(name)){
						Name.set(3, Name.get(2));
						Time.set(3, Time.get(2));
					}
					if(!Name.get(1).equals(name)){
						Name.set(2, Name.get(1));
						Time.set(2, Time.get(1));
					}
					Name.set(1, name);
					Time.set(1, timeparsed);
				}else if (!Name.get(1).equals(name)){
					return;
				}
			}
			if(Name.get(1).equals(name)){
				Time.set(1, timeparsed);
			}else if(!Name.contains(name)){
				Name.set(4, Name.get(3));
				Time.set(4, Time.get(3));
				Name.set(3, Name.get(2));
				Time.set(3, Time.get(2));
				Name.set(2, Name.get(1));
				Time.set(2, Time.get(1));
				Name.set(1, name);
				Time.set(1, timeparsed);
			}
		}else if(timeofplayer <= time.get(2)){//3ème
			if(Name.contains(name)){
				if(!(Name.get(0).equals(name) || Name.get(1).equals(name) || Name.get(2).equals(name))){
					if(!Name.get(3).equals(name)){
						Name.set(4, Name.get(3));
						Time.set(4, Time.get(3));
					}
					if(!Name.get(2).equals(name)){
						Name.set(3, Name.get(2));
						Time.set(3, Time.get(2));
					}
					Name.set(2, name);
					Time.set(2, timeparsed);
				}else if (!Name.get(2).equals(name)){
					return;
				}
			}
			if(Name.get(2).equals(name)){
				Time.set(2, timeparsed);
			}else if(!Name.contains(name)){
				Name.set(4, Name.get(3));
				Time.set(4, Time.get(3));
				Name.set(3, Name.get(2));
				Time.set(3, Time.get(2));
				Name.set(2, name);
				Time.set(2, timeparsed);
			}
		}else if(timeofplayer <= time.get(3)){//4ème
			if(Name.contains(name)){
				if(!(Name.get(0).equals(name) || Name.get(1).equals(name) || Name.get(2).equals(name) || Name.get(3).equals(name))){
					if(!Name.get(3).equals(name)){
						Name.set(4, Name.get(3));
						Time.set(4, Time.get(3));
					}
					Name.set(3, name);
					Time.set(3, timeparsed);
				}else if (!Name.get(3).equals(name)){
					return;
				}
			}
			if(Name.get(3).equals(name)){
				Time.set(3, timeparsed);
			}else if(!Name.contains(name)){
				Name.set(4, Name.get(3));
				Time.set(4, Time.get(3));
				Name.set(3, name);
				Time.set(3, timeparsed);
			}
		}else if(timeofplayer <= time.get(4)){//5ème
			Name.set(4, name);
			Time.set(4, timeparsed);
		}
		final CLASSEMENT profile1 = CLASSEMENT.createProfile(Name, Time);
		final String json = profileSerializationManager.CLASSEMENTserialize(profile1);
		FileUtils.save(file, json);	
		ArrayList<String> Colors = new ArrayList<String>();
		Colors.add("§c");
		Colors.add("§6");
		Colors.add("§e");
		Colors.add("§e");
		Colors.add("§e");
		for(int i =0; i<5; i++){
			String t = Time.get(i);
			if(t.equals("97.00") || t.equals("97.30") || t.equals("98.00") || t.equals("98.30") || t.equals("99.00")){
				t="00.00";
			}
			Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "holo setline classement2 "+(4+i)+" §3#"+(i+1)+" §7• "+Colors.get(i)+Name.get(i)+" §f§l-§r §7Temps: §b("+t+")");
		}
		return;
		
	}

}
