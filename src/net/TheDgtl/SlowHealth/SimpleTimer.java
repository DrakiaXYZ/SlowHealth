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
 */

package net.TheDgtl.SlowHealth;

import java.util.TimerTask;
        
public class SimpleTimer extends TimerTask
{
  private SlowHealth m_Plugin;
  
  SimpleTimer(SlowHealth plugin)
  {
    m_Plugin = plugin;
  }

  @Override
  public void run()
  {
    m_Plugin.handleHealth();
  }

}

