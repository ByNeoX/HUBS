package mlg.byneox.tc.Mysql;

import java.sql.Connection;
import java.sql.SQLException;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class DatabaseAccess {
	
	private DatabaseCredentials credentials;
	private HikariDataSource hikariDataSource;
	
	
	public DatabaseAccess(DatabaseCredentials credentials){
		this.credentials = credentials;
	}
	
	private void setupHikariCP(){
		final HikariConfig hikariConfig = new HikariConfig();
		
		hikariConfig.setMaximumPoolSize(40);
		hikariConfig.setJdbcUrl(credentials.toURI());
		hikariConfig.setUsername(credentials.getUser());
		hikariConfig.setPassword(credentials.getPass());
		hikariConfig.setMaxLifetime(600000L);
		hikariConfig.setIdleTimeout(300000L);
		hikariConfig.setLeakDetectionThreshold(300000L);
		
		hikariConfig.setConnectionTimeout(10000L);
		
		this.hikariDataSource = new HikariDataSource(hikariConfig);
		
	}
	
	public void initPool(){
		setupHikariCP();
		
	}
	
	public void closePool(){
		
	}
	
	public Connection getConnection() throws SQLException{
		if(this.hikariDataSource == null){
			System.out.println("pas connect�");
			setupHikariCP();
		
		}
		return this.hikariDataSource.getConnection();
		
	}

}
