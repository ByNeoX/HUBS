package mlg.byneox.tc.Mysql;

public enum DatabaseManager {
	
	playersinfo(new DatabaseCredentials("localhost", "phpmyadmin", "DJhtKFSaYlXb5zHg" , "playersinfo", 3306));
	
	
	
	
	private DatabaseAccess databaseAccess;
	
	DatabaseManager(DatabaseCredentials credentials) {
		this.databaseAccess = new DatabaseAccess(credentials);

	}
	
	public DatabaseAccess getDatabaseAccess(){
		return databaseAccess;
	}
	
	public static void initalldatabaseconnections(){
		for(DatabaseManager databaseManager : values()){
			databaseManager.databaseAccess.initPool();
		}
	}

	
	public static void closealldatabaseconnections(){
		for(DatabaseManager databaseManager : values()){
			databaseManager.databaseAccess.closePool();
		}
		
	}
}
