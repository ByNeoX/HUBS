package mlg.byneox.tc.Utils;

import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.bukkit.entity.Player;

public class Profile {
	private UUID uuid;
	private String name;
	private int PARTSELECT;
	private int SKINSELECT;
	private int DJSELECT;
	private List<String> Part;
	private List<String> Skin;
	private List<String> DJ;
	
	
	public Profile(UUID uuid, String name, int PARTSELECT, int SKINSELECT, int DJSELECT, List<String> Part, List<String> Skin, List<String> DJ){
		this.uuid = uuid;
		this.name = name;
		this.PARTSELECT = PARTSELECT;
		this.SKINSELECT = SKINSELECT;
		this.DJSELECT = DJSELECT;
		this.Part = Part;
		this.Skin = Skin;
		this.DJ = DJ;
	}
	
	public UUID getUuid(){
		return uuid;
	}
	
	public String getName(){
		return name;
	}
	
	public int getPartSelected(){
		return PARTSELECT;
	}
	
	public int getSkinSelected(){
		return SKINSELECT;
	}
	public int getDJSelected(){
		return DJSELECT;
	}
	
	public List<String> getDJ(){
		return DJ;
	}
	public List<String> getPart(){
		return Part;
	}

	public List<String> getSkin(){
		return Skin;
	}


	public static Profile createProfile(List<String> DJ, List<String> Part, List<String> Skin, Player p, int PARTSELECT, int SKINSELECT, int DJSELECT) {
		
		return new Profile(p.getUniqueId(), p.getName(), PARTSELECT, SKINSELECT, DJSELECT, Part, Skin, DJ);
	}

}
