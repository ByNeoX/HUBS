package mlg.byneox.tc.Mysql;

import java.util.Map.Entry;

import org.bukkit.entity.Player;

import mlg.byneox.tc.Main;
import mlg.byneox.tc.Utils.ScoreboardSign;

public class PlayerData {

	private String Grade;
	private String Coins;
	private String Lang;
	private String Part;
	private String Spec;
	
	public String getGrade() {
		if(Grade == null){
			return "Joueur";
		}else{
			return Grade;
		}
	}
	public void setGrade(String grade) {
		Grade = grade;
	}
	public String getLang() {
		if(Lang == null){
			return "FR";
		}else{
			return Lang;
		}
	}
	public void setLang(String lang) {
		Lang = lang;
	}
	public String getCoins() {
		if(Coins == null){
			return "0";
		}else{
			return Coins;
		}
	}
	public void setCoins(String coins) {
		Coins = coins;
	}
	public String getPart() {
		return Part;
	}
	public void setPart(String part) {
		Part = part;
	}
	public boolean getSpec() {
		if(Spec == null){
			return false;
		}else{
			return true;
		}
	}
	public String getSpecplayer() {
		if(Spec == null){
			return "no";
		}else{
			return Spec;
		}
	}
	
	
	public void setSpec(String spec) {
		Spec = spec;
	}
	
	
	
	
}
