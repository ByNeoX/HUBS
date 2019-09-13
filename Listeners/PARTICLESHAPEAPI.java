package mlg.byneox.tc.Listeners;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Effect;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.FireworkMeta;

import mlg.byneox.tc.Main;
import mlg.byneox.tc.Utils.ParticleEffect;
import mlg.byneox.tc.Utils.RepeatingTask;
import mlg.byneox.tc.Utils.ParticleEffect.OrdinaryColor;

public class PARTICLESHAPEAPI {

	
	public void displaypart(Player p){
		RepeatingTask repeatingTask = new RepeatingTask(Main.plugin, 0, 7) {
			int Firework34=0;
			@Override
            public void run() {
				if(p.isOnline()){
					if (Main.plugin.dataPlayers.containsKey(p.getName())){
						if (Main.plugin.dataPlayers.get(p.getName()).getPart() == null){
							Firework34=0;
							canncel();
							return;
						}else{
							if(Main.plugin.dataPlayers.get(p.getName()).getPart().equals("Firework")){
								Firework34=Firework34+1;
								if(Firework34==11){
									Fireworks(p);
									Firework34=0;
								}
							}
							if (Main.plugin.dataPlayers.get(p.getName()).getPart().equals("Cercle")){
								launchcircle(p);
							}else if (Main.plugin.dataPlayers.get(p.getName()).getPart().equals("Multicolor")){
								drawhelix(p, Effect.COLOURED_DUST);
							}else if (Main.plugin.dataPlayers.get(p.getName()).getPart().equals("Sphere")){
								drawball(p, ParticleEffect.REDSTONE, 0.5f);
							}else if (Main.plugin.dataPlayers.get(p.getName()).getPart().equals("Neige")){
								Location loc = p.getLocation();
								loc.setY(loc.getY()+2);
								ParticleEffect.FIREWORKS_SPARK.display(0, 0, 0, 1, 5, loc, 30);
							}else if (Main.plugin.dataPlayers.get(p.getName()).getPart().equals("Coeur")){
								heart(p);
							}else if (Main.plugin.dataPlayers.get(p.getName()).getPart().equals("Magma")){
								magma(p);
							}else if (Main.plugin.dataPlayers.get(p.getName()).getPart().equals("Nuage")){
								cloud(p);
							}	
						}
					}else{
						Firework34=0;
						canncel();
					}
            }else{
            	Firework34=0;
				canncel();
            }
          }
       };
	}
	
	
	public void drawhelix(Player p, Effect ps){
		Location location1 = p.getLocation();
		Bukkit.getScheduler().scheduleSyncDelayedTask(Main.plugin, new Runnable() {
            public void run() {
            	Location location2 = p.getLocation();
            	if(location1.distance(location2) < 0.2){
    				double phi = 0;
    				phi = phi + Math.PI/8;                                 
                    double x, y, z;                
                   
                    for (double t = 0; t <= 2*Math.PI; t = t + Math.PI/16){
                            for (double i = 0; i <= 1; i = i + 1){
                                    x = 0.4*(2*Math.PI-t)*0.5*Math.cos(t + phi + i*Math.PI);
                                    y = 0.5*t;
                                    z = 0.4*(2*Math.PI-t)*0.5*Math.sin(t + phi + i*Math.PI);
                                    location1.add(x, y, z);
                                    ParticleEffect.REDSTONE.display(0, 0, 0, 1, 1, location1, 30);
                                    location1.subtract(x,y,z);
                            }
                           
                    }              
                   
                    if(phi > 10*Math.PI){                                          
                    	phi =0;
                    	return;
                    }
            	}else{
            		ParticleEffect.REDSTONE.display(0.5F, 0.3F, 0.5F, 1, 10, location1, 30);
            	}
            }
		}, 3L);
	}
	
	
	public void cloud(Player p){
		Location location1 = p.getLocation();
		Bukkit.getScheduler().scheduleSyncDelayedTask(Main.plugin, new Runnable() {
            public void run() {
            	Location location2 = p.getLocation();
            	if(location1.distance(location2) < 0.2){
					Location loc = p.getLocation();
					loc.setY(loc.getY()+2.5);
					ParticleEffect.CLOUD.display(0.5F, 0.3F, 0.5F, 0, 6, loc, 25);
					loc.setX(loc.getX()+0.3);
					ParticleEffect.CLOUD.display(0.5F, 0.3F, 0.5F, 0, 6, loc, 25);
					loc.setX(loc.getX()-0.6);
					ParticleEffect.CLOUD.display(0.5F, 0.3F, 0.5F, 0, 6, loc, 25);
					loc.setX(loc.getX()+0.3);
					loc.setZ(loc.getZ()+0.3);
					ParticleEffect.CLOUD.display(0.5F, 0.3F, 0.5F, 0, 6, loc, 25);
					loc.setZ(loc.getZ()-0.6);
					ParticleEffect.CLOUD.display(0.5F, 0.3F, 0.5F, 0, 6, loc, 25);
            	}else{
            		ParticleEffect.CLOUD.display(1, 0.3F, 1, 0, 10, location1, 4);
            }
           }
        }, 3L); 
	}
	
	
	public void launchcircle(Player p){
		Location location1 = p.getLocation();
		Bukkit.getScheduler().scheduleSyncDelayedTask(Main.plugin, new Runnable() {
            public void run() {
				Location loc = p.getLocation();
		    	if(loc.distance(location1) < 0.2){
					for(int i = 1; i < 4; i++){
						if (i==1){
							Bukkit.getScheduler().scheduleSyncDelayedTask(Main.plugin, new Runnable() {
								public void run() {
									circle(loc, 1, p);
								}
							}, 1L);
						}
						if (i==2){
							Bukkit.getScheduler().scheduleSyncDelayedTask(Main.plugin, new Runnable() {
								public void run() {
									circle(loc, 2, p);
								}
							}, 2L);
						}
					}
		    	}else{
		    		OrdinaryColor d = new OrdinaryColor(0, 204, 204);
		    		loc.setX(loc.getX()-0.5F);
		    		ParticleEffect.REDSTONE.display(d, loc, 30);
		    		loc.setX(loc.getX()-0.5F);
		    		ParticleEffect.REDSTONE.display(d, loc, 30);
		    		loc.setX(loc.getX()+1F);
		    		ParticleEffect.REDSTONE.display(d, loc, 30);
		    		loc.setX(loc.getX()+0.5F);
		    		ParticleEffect.REDSTONE.display(d, loc, 30);
		    		loc.setX(loc.getX()-0.5F);
		    		loc.setZ(loc.getZ()+0.5F);
		    		ParticleEffect.REDSTONE.display(d, loc, 30);
		    		loc.setX(loc.getX()-0.5F);
		    		ParticleEffect.REDSTONE.display(d, loc, 30);
		    		loc.setX(loc.getX()+1F);
		    		loc.setZ(loc.getZ()-1F);
		    		ParticleEffect.REDSTONE.display(d, loc, 30);
		    	}
            }
		},3L);
	}
	
	public void Fireworkslaunchstart(Player p){
		Firework fw = (Firework)p.getWorld().spawnEntity(p.getLocation(), EntityType.FIREWORK);
	    FireworkMeta fwm = fw.getFireworkMeta();
	    Random random = new Random();
	    int rt = random.nextInt(5);
	    FireworkEffect.Type type = FireworkEffect.Type.values()[rt];
	    
	    Color c1 = Color.fromRGB(random.nextInt(256), random.nextInt(256), random.nextInt(256));
	    Color c2 = Color.fromRGB(random.nextInt(256), random.nextInt(256), random.nextInt(256));
	    
	    FireworkEffect effect = FireworkEffect.builder().flicker(random.nextBoolean()).withColor(c1).withFade(c2).with(type).trail(random.nextBoolean()).build();
	    
	    fwm.addEffect(effect);
	    
	    fwm.setPower(random.nextInt(1));
	    
	    fw.setFireworkMeta(fwm);
	}
	
	public void Fireworks(Player p){
		Bukkit.getScheduler().scheduleSyncDelayedTask(Main.plugin, new Runnable() {
			public void run() {
				Fireworkslaunchstart(p);
			}
		}, 80L);
	}
	
	
	public void circle(Location loc, float radius, Player p){
		for (int degree = 0; degree < 360; degree++) {
		    double radians = Math.toRadians(degree);
		    double x = (radius * Math.cos(radians));
		    double z = (radius * Math.sin(radians));
		    loc.add(x,0,z);
		    OrdinaryColor d = new OrdinaryColor(0, 204, 204);
		    ParticleEffect.REDSTONE.display(d, loc, 30);
		    loc.subtract(x,0,z);
		    
		}
	}
	
	@SuppressWarnings("static-access")
	public void drawball(Player p, ParticleEffect Effect, double r) {
		Location location1 = p.getLocation();
		Bukkit.getScheduler().scheduleSyncDelayedTask(Main.plugin, new Runnable() {
            public void run() {
            	Location loc = p.getLocation();
            	if(loc.distance(location1) < 0.2){
            		loc.setY(loc.getY()+0.6);
					for (double i = 0; i <= Math.PI; i += Math.PI / 10) {
						   double radius = Math.sin(i) +r-0.25;
						   double y = Math.cos(i) +r-0.1;
						   for (double a = 0; a < Math.PI * 2; a+= Math.PI / 10) {
						      double x = Math.cos(a) * radius;
						      double z = Math.sin(a) * radius;
						      loc.add(x, y, z);
						      Effect.REDSTONE.display(0, 0, 0, 0, 1, loc, 30);
						      loc.subtract(x, y, z);
						   }
						}
            	}else{
            		ParticleEffect.REDSTONE.display(0.5F, 0.3F, 0.5F, 0, 10, loc, 30);
            	}
            }
		},3L);
	}


	public void heart(Player p){
		Location location1 = p.getLocation();
		Bukkit.getScheduler().scheduleSyncDelayedTask(Main.plugin, new Runnable() {
            public void run() {
            	Location location2 = p.getLocation();
            	if(location1.distance(location2) < 0.2){
					Location loc = p.getEyeLocation();
					loc.setY(loc.getY() + 0.5);
					ParticleEffect.HEART.display(0, 0, 0, 0, 1, loc, 30);
            	}else{
            		ParticleEffect.REDSTONE.display(0.5F, 0.3F, 0.5F, 0, 10, location1, 30);
            }
           }
        }, 3L); 
	}
	
	public void magma(Player p){
		Location location1 = p.getLocation();
		Bukkit.getScheduler().scheduleSyncDelayedTask(Main.plugin, new Runnable() {
            public void run() {
            	Location location2 = p.getLocation();
            	if(location1.distance(location2) < 0.2){
					Location loc = p.getLocation();
					ParticleEffect.LAVA.display(1, 1, 1, 0, 5, loc, 30);
					ParticleEffect.EXPLOSION_NORMAL.display(1, 0, 1, 0, 2, loc, 20);
            	}else{
            		ParticleEffect.FLAME.display(0.5F, 0.8F, 0.5F, 0, 15, location1, 15);
            }
           }
        }, 3L); 
	}
	
	
	
}
