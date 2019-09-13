package mlg.byneox.tc.Utils;

import java.util.List;

public class CLASSEMENT {
	private List<String> Name;
	private List<String> time;
	
	
	public CLASSEMENT(List<String> name, List<String> time){
		this.Name = name;
		this.time = time;
	}
	
	
	public List<String> getName(){
		return Name;
	}
	public List<String> getTime(){
		return time;
	}
	
	public static CLASSEMENT createProfile(List<String> Name, List<String> Time) {
		
		return new CLASSEMENT(Name, Time);
	}

}
