package mlg.byneox.tc.Tasks;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import mlg.byneox.tc.Main;
import mlg.byneox.tc.Mysql.DatabaseManager;
import mlg.byneox.tc.Mysql.PlayerData;
import mlg.byneox.tc.api.TABLISTAPI;
import mlg.byneox.tc.api.TCAPI;

public class LoadTask extends BukkitRunnable{
	
	private String name;
	private Player p;
	TCAPI API = new TCAPI();
	TABLISTAPI TABLSIT = new TABLISTAPI();
	List<String> info = new ArrayList<String>();
	
	public LoadTask(String name, Player p){
		this.name = name;
		this.p = p;
	}
	
	public void run(){
		if(p != null){
			if(!p.isOnline()){
				return;
			}else{
				PlayerData data = new PlayerData();
				Main.plugin.mysqlload.put(name, "o");
				info.add("grade");
				info.add("coins");
				info = API.Mysqlgetallinfo(name, "playerinfo", info);
				String result = info.get(0);
				String coins = info.get(1);
				if(result.equals("nostring") || result == null){
					result = "Joueur";
					try{
						if(p == null){
							return;
						}
						if(!p.isOnline()){
							return;
						}
						final Connection connection = DatabaseManager.playersinfo.getDatabaseAccess().getConnection();
						final PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO playerinfo (name, grade, coins, ip, uuid) VALUES (?,?,?,?,?)");
						preparedStatement.setString(1, name);
						preparedStatement.setString(2, "Joueur");
						preparedStatement.setString(3, "0");
						preparedStatement.setString(4, p.getAddress().getAddress().toString());
						preparedStatement.setString(5, p.getUniqueId().toString());
						preparedStatement.executeUpdate();
						connection.close();
					}catch(SQLException e){
						e.printStackTrace();
					}
				}
				if(p == null){
					return;
				}
				if(!p.isOnline()){
					return;
				}
				if(!(result.equals("nostring") || result == null)){
					final String rank = result;
					Bukkit.getScheduler().scheduleSyncDelayedTask(Main.plugin, new Runnable()
					{
						public void run(){
							TABLSIT.SetPrefix(p, rank);
						}
					}, (long) 0.7);
				}
				String Particle = API.Mysqlgetinfo(name, "NetworkCos", "selectedpart");
				String Langage = API.Mysqlgetinfo(name, "Langage", "lange");
				if(!result.equals("Joueur")){
					data.setGrade(result);
					API.gradebc(p, Langage);
				}
				if (!(coins.equals("nostring") || coins.equals("null") || coins.isEmpty())) {
					data.setCoins(coins);
				}
				if (!(Langage.equals("nostring") || Langage.equals("null") || Langage.isEmpty())) {
					data.setLang(Langage);
				}
				if (!(Particle.equals("nostring") || Particle.equals("null") || Particle.isEmpty())) {
					data.setPart(Particle);
				}
				if(Main.plugin.dataPlayers.containsKey(p.getName())){
					if(Main.plugin.dataPlayers.get(p.getName()).getSpec() == true){
						data.setSpec(Main.plugin.dataPlayers.get(p.getName()).getSpecplayer());
					}
				}
				Main.plugin.dataPlayers.put(p.getName(), data);
				API.InitConnection(p);
			}
		}
	}
	
}
