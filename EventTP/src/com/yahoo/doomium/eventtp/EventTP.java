package com.yahoo.doomium.eventtp;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;
import org.bukkit.plugin.java.JavaPlugin;

public class EventTP extends JavaPlugin{
	
	Location eventLoc = null;
	String broadMess = null;
	
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
    	if(cmd.getName().equalsIgnoreCase("eventstatus")){
    		if (eventLoc != null)
    		{
    			sender.sendMessage(broadMess);
    		}
    	}
    	
    	if(cmd.getName().equalsIgnoreCase("eventjoin")){
    		if (sender instanceof Player)
    		{
    			if ((!sender.isPermissionSet("EventTP.join"))|(sender.hasPermission("EventTP.join")))
    			{
    				if (eventLoc == null)
    				{
    					sender.sendMessage("There is not an active event to join.");
        				return true;
    				}
    				else
    				{
    					((Player) sender).teleport(eventLoc, TeleportCause.PLUGIN);
        				return true;
    				}
    			}
    			else
    			{
    				sender.sendMessage("You are not allowed to join events,  if this is a mistake please contact a moderator.");
    				return true;
    			}
    		}
    		else
    		{
    			sender.sendMessage("Silly Console,  you can't join events.");
				return true;
    		}
    	}
    	
    	if(cmd.getName().equalsIgnoreCase("eventclose")){
    		if (sender instanceof Player)
    		{
    			if ((sender.hasPermission("EventTP.manage")))
    			{
    				eventLoc = null;
    				sender.sendMessage("Closing the open event if it is open.");
    				Bukkit.getServer().broadcastMessage("Active event has been closed.");
    				return true;
    			}
    			else
    			{
    				sender.sendMessage("You are not allowed to manage events.");
    				Bukkit.getServer().broadcastMessage("Active event has been closed.");
    				return true;
    			}
    		}
    		else
    		{
    			eventLoc = null;
    			sender.sendMessage("Closing open event if it is open.");
				return true;
    		}
    	}
    	
    	if(cmd.getName().equalsIgnoreCase("eventopen")){
    		if (sender instanceof Player)
    		{
    			if (((sender.hasPermission("EventTP.manage"))))
    			{
    				if (eventLoc == null)
    				{
    					String mess = "";
    					for(int i = 0; i<args.length;i++)
    					{
    						mess = mess + args[i] +" ";
    					}
    					eventLoc = ((Player) sender).getLocation();
    					Bukkit.getServer().broadcastMessage(mess);
    					broadMess = mess;
        				return true;
    				}
    				else
    				{
    					sender.sendMessage("There is already an active event.");
    				}
    			}
    			else
    			{
    				sender.sendMessage("You are not allowed to start events");
    				return true;
    			}
    		}
    		else
    		{
    			if (args.length > 1)
    			{
					String mess = "";
					for(int i = 1; i<args.length;i++)
					{
						mess = mess + args[i] +" ";
					}
					
    				try {
    					eventLoc = Bukkit.getServer().getPlayer(args[0]).getLocation();
    					Bukkit.getServer().broadcastMessage(mess);
    					broadMess = mess;
    				}
    				finally {
    					sender.sendMessage("Attempted to start a event on player "+args[0]+" but no promises,  server based event creation is odd.");
    				}
    			}
    		}
    	}
		return false;
    }

}
