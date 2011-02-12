/*
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 */

/*
 * Author: ACTruncale <adam@truncale.net>
 * Author: Drakia <Drakia@TheDgtl.net> 
 */
package net.TheDgtl.SlowHealth;

import java.io.File;
import java.io.IOException;
import java.util.Timer;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.io.FileInputStream;
import org.bukkit.entity.Player;
import org.bukkit.Server;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginLoader;
import org.bukkit.plugin.java.JavaPlugin;

public class SlowHealth extends JavaPlugin
{
  private Logger log;
  private Timer m_Timer = new Timer(true);
  private int HP = 0;
  private double rate = 1;
  private int healAmount = 1;
  private int maxHeal = 20;
  private int maxHurt = 0;
  
  public SlowHealth(PluginLoader pluginLoader, Server instance, PluginDescriptionFile desc, File folder, File plugin, ClassLoader cLoader)
  {
    super(pluginLoader, instance, desc, folder, plugin, cLoader);
    log = Logger.getLogger("Minecraft");
  }

  public void onEnable()
  {
    PluginDescriptionFile pdfFile = this.getDescription();
    
    Properties props = new Properties();
	try {
		props.load(new FileInputStream("server.properties"));
		if (props.containsKey("regen-rate")) 
			rate = Double.parseDouble(props.getProperty("regen-rate"));
		if (props.containsKey("regen-amount")) 
			healAmount = Integer.parseInt(props.getProperty("regen-amount"));
		if (props.containsKey("regen-max")) 
			maxHeal = Integer.parseInt(props.getProperty("regen-max"));
		if (props.containsKey("regen-min")) 
			maxHurt = Integer.parseInt(props.getProperty("regen-min"));
	} catch (IOException ioe) {
		log.log(Level.SEVERE, "[SlowHealth] Can't find server.properties");
	}
	log.info( pdfFile.getName() + " " + pdfFile.getVersion() + " enabled! Rate: " + rate + "s | Amount: " + healAmount );
	
    m_Timer.schedule(new SimpleTimer(this), 0, (long)(rate*1000));
  }

  public void onDisable()
  {
    log.info(this.getDescription().getName() + " disabled.");
    m_Timer.cancel();
  }

  public void handleHealth()
  {
	  for ( Player player : getServer().getOnlinePlayers() )
	  {
		  HP = player.getHealth();
		  if(HP < 0)
			  player.setHealth(0);
    	
		  if(HP < maxHeal && HP > 0 && healAmount > 0)
		  {
			  player.setHealth(HP + healAmount);
			  if(HP > 20)
				  player.setHealth(20);
		  }
		  else if(HP <= 20 && HP > maxHurt && healAmount < 0)
		  {
			  player.setHealth(HP + healAmount);
		  }
    	}
    }
      
  }