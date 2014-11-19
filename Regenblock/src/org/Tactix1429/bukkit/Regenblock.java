package org.Tactix1429.bukkit;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;













import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockBurnEvent;
import org.bukkit.event.block.BlockExpEvent;
import org.bukkit.event.block.BlockPhysicsEvent;
import org.bukkit.event.block.LeavesDecayEvent;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.World;

//import com.sk89q.worldedit.bukkit.WorldEditAPI;
//import com.sk89q.worldedit.bukkit.WorldEditPlugin;


public class Regenblock extends JavaPlugin implements Listener {
	
	final List<Location> l = new ArrayList<Location>();
	List<Integer> zl = new ArrayList<Integer>();
	List<Integer> ml = new ArrayList<Integer>();
	List<Integer> y = new ArrayList<Integer>();
	List<Integer> x = new ArrayList<Integer>();
	final List<Material> m = new ArrayList<Material>();
	List<Byte> d = new ArrayList<Byte>();
	List<String> w = new ArrayList<String>();
	final List<Block> b = new ArrayList<Block>();
	final List<Location> l1 = new ArrayList<Location>();
	final List<Material> m1 = new ArrayList<Material>();
	final List<Byte> d1 = new ArrayList<Byte>();
	final List<String> w1 = new ArrayList<String>();
	List<Integer> z1 = new ArrayList<Integer>();
	List<Integer> y1 = new ArrayList<Integer>();
	List<Integer> x1 = new ArrayList<Integer>();
	List<Integer> m2 = new ArrayList<Integer>();
	public boolean edit = true;
	
	
	
	
	int task;
	int task1;
	int task2;
	int counter ;
	int counter1  ;
	int s = 0;
	boolean inArbeit = false;
	public int alt;
	public int c = 0;
	public boolean breakable = true;
	public boolean Arbeit = false;
	public int z =0;
	public int task3;
	public int zBlock = 0;
	Player p = null;
	
	 File BlockFile = new File(this.getDataFolder(), "Blocks.yml"); 
	 File MessageFile = new File(this.getDataFolder(), "Messages.yml");
	 FileConfiguration BlockConfig;
	 FileConfiguration MessagesConfig;
	
	   
	
	
	
	
	
	
	
	
	
	
	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {
		Messages(label,args,sender);
		
		//if(label.equalsIgnoreCase("location"))
		//{
		//	this.getConfig().set(""+args[0]+".Point1",weAPI.getClass(). );
		//}
		if(label.equalsIgnoreCase("save")&&sender instanceof Player&&sender.hasPermission("Regen.Save"))
		{
			if(args.length != 1)
			{
				p = (Player) sender;
				p.sendMessage("/save"+ChatColor.YELLOW+" on|off");
			}
			else
			{
			if(args[0].equalsIgnoreCase("on"))
			{
			this.getConfig().set("Save", true);
			
			p = (Player) sender;
			
			p.sendMessage(""+ChatColor.YELLOW+"Save-Mode"+ChatColor.BLUE+" ist aktiviert");
			this.saveConfig();
			}
			else if(args[0].equalsIgnoreCase("off") )
			{
				p = (Player) sender;
				this.getConfig().set("Save", false);
				p.sendMessage(ChatColor.YELLOW+"Save-Mode"+ChatColor.BLUE+" ist deaktiviert");
				this.saveConfig();
			}
			
		}
		}
		
	     if(label.equalsIgnoreCase("save")&&sender instanceof Player&!(sender.hasPermission("Regen.Save")))
	     {
	    	 p = (Player) sender;
	    	 p.sendMessage(ChatColor.RED+"Du benötigst die Berechtigung: Regen.Save");
	     }
		
		 this.saveConfig();
		 if(label.equalsIgnoreCase("edit")&&sender instanceof Player&&sender.hasPermission("Regen.edit"))
			{
				if(args.length != 1)
				{
					p = (Player) sender;
					p.sendMessage("/edit"+ChatColor.YELLOW+" on|off");
				}
				else
				{
				if(args[0].equalsIgnoreCase("on"))
				{
				this.getConfig().set("Edit", true);
				
				p = (Player) sender;
				
				p.sendMessage(""+ChatColor.YELLOW+"Edit-Mode"+ChatColor.BLUE+" ist aktiviert");
				this.saveConfig();
				}
				else if(args[0].equalsIgnoreCase("off") )
				{
					p = (Player) sender;
					this.getConfig().set("Edit", false);
					p.sendMessage(ChatColor.YELLOW+"Edit-Mode"+ChatColor.BLUE+" ist deaktiviert");
					this.saveConfig();
				}
				
			}
			}
			
		     if(label.equalsIgnoreCase("Edit")&&sender instanceof Player&!(sender.hasPermission("Regen.Edit")))
		     {
		    	 p = (Player) sender;
		    	 p.sendMessage(ChatColor.RED+"Du benötigst die Berechtigung: Regen.Edit");
		     }
			
			 this.saveConfig();
		
		return super.onCommand(sender, command, label, args);
	}

	@Override
	public void onDisable() {
		BlockConfig.set("Counter", counter);
		
		
		try {
			BlockConfig.save(BlockFile);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
		

	@Override
	public void onEnable() {
		this.getConfig().addDefault("Edit", true);
			this.getConfig().addDefault("Save", false);
		this.getConfig().addDefault("ExpRegen", true);
		this.getConfig().addDefault("FireRegen", true);
		this.getConfig().addDefault("RegenSpeed", 0.05);
		this.getConfig().addDefault("RegenStart", 5);
		this.getConfig().addDefault("LeaveDecay", false);
		this.getConfig().addDefault("Physics", true);
		this.getConfig().addDefault("Messages", true);
		
		FileConfiguration cfg = this.getConfig();
		FileConfiguration messages = this.getConfig();
		messages.options().copyDefaults(true);
		messages.addDefault("UseAsPlayer", "Use this command as a Player");
		messages.options().copyDefaults(true);
		cfg.options().copyDefaults(true);
		if(this.getConfig().getBoolean("Save")== false)
		{
			BlockFile.delete();
		}
		try {
			messages.save(MessageFile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
		this.saveConfig();
		
		this.getServer().getPluginManager().registerEvents(this, this);
		
		this.saveConfig();
		BlockConfig = YamlConfiguration.loadConfiguration(BlockFile);
		 int a = 0;
		 int s = 0;
		 int counter = BlockConfig.getInt("Counter");
		 if(this.getConfig().getBoolean("Save")== true&&BlockConfig.getList("Worlds").size()!=0)
		 {
			 breakable = false;
		 
			  x = this.BlockConfig.getIntegerList("LocationX");
			  y = this.BlockConfig.getIntegerList("LocationY");
			  zl = this.BlockConfig.getIntegerList("LocationZ");
			  ml = this.BlockConfig.getIntegerList("Material");
			  d = this.BlockConfig.getByteList("Data");
			  w = this.BlockConfig.getStringList("Worlds");
			 
			  
			  while(BlockConfig.getList("Worlds").size() > s)
				 {
				  
				 World world = Bukkit.getServer().getWorld(w.get(s));
				
					Location Normal = new Location(world,x.get(s),y.get(s),zl.get(s));
				 l.add(Normal);
				 Material theMaterial = Material.getMaterial(ml.get(s));
			
				 
				 
				
				 
				
				 if(theMaterial.getId()!=46)
				 {
				
				 Bukkit.getWorld("world").getBlockAt(Normal).setType(theMaterial);
				 Bukkit.getWorld(w.get(s)).getBlockAt(Normal).setData(d.get(s));
					
				 }
				 
				
				 
				 s++;
				 
			 
			 }
			  
			 BlockConfig.getList("LocationX").clear();
			  BlockConfig.getList("LocationY").clear();
			  BlockConfig.getList("LocationZ").clear();
			  BlockConfig.getList("Material").clear();
			  BlockConfig.getList("Data").clear();
			  BlockConfig.getList("Worlds").clear();
			  x.clear();
				 y.clear();
				 zl.clear();
				 ml.clear();
				 d.clear();
				 w.clear();
				 m.clear();
				 l.clear();
			  try {
				BlockConfig.save(BlockFile);
			} catch (IOException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			
			
			BlockFile.delete();
			this.saveConfig();
			
		 Bukkit.broadcastMessage(ChatColor.YELLOW+"Es wurden "+ChatColor.RED+s+ChatColor.YELLOW+" Blöcke gefunden und wiederhergestellt");
		 counter = 0;
		 a = 0;
		 s = 0;
		
		 
		 
		 BlockConfig.set("Counter", counter);
		 try {
				BlockConfig.save(BlockFile);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		 
		 
		
		 this.saveDefaultConfig();
		 this.saveConfig();
		 
		
	    
	              
	    BlockConfig = YamlConfiguration.loadConfiguration(BlockFile);
		if(BlockFile. exists()== false)
		{
			try {
				BlockFile.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}}
	}
	@EventHandler
	public void onExplode(EntityExplodeEvent e)
	{
		if(this.getConfig().getBoolean("ExpRegen")==true&&this.getConfig().getBoolean("Edit")==false)
		{
		counter = 0;
		
		alt = alt + e.blockList().size();
		int i = 0;
		BlockFile.delete();
		Bukkit.broadcastMessage(ChatColor.YELLOW+"Es werden "+ChatColor.RED+alt+ChatColor.YELLOW+" Blöcke wieder hergestellt");
		while(i<e.blockList().size())
		{
			if(e.blockList().get(i).getType() != Material.TNT|e.blockList().get(i).getType() != Material.AIR)
			{
				breakable = false;
			
		l.add(e.blockList().get(i).getLocation());
		m.add(e.blockList().get(i).getType());
		d.add(e.blockList().get(i).getData());
		w.add(e.blockList().get(i).getWorld().getName());
		x.add(e.blockList().get(i).getX());
		y.add(e.blockList().get(i).getY());
		zl.add(e.blockList().get(i).getZ());
		ml.add(e.blockList().get(i).getTypeId());
		if(this.getConfig().getBoolean("Save")== true)
		{
		
			BlockConfig.set("Worlds", w);
			BlockConfig.set("Material", ml);
			BlockConfig.set("Data", d);
			BlockConfig.set("LocationX", x);
			BlockConfig.set("LocationY", y);
			BlockConfig.set("LocationZ", zl);
			counter++;
			
			
			
		}
		
		
		i++;
			}
			else
			{
				i++;
			}
		
		}
		try {
			BlockConfig.save(BlockFile);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		if(inArbeit == false)
		{
			inArbeit = true;
			breakable = false;
		int a = 0;
		if(a<l.size())
		{
		task1 =	Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable()
			{
				public void run() 
				{
					if(s==l.size())
					{
						Bukkit.getScheduler().cancelTask(task1);
						inArbeit = false;
						breakable = true;
						l.clear();
						w.clear();
						d.clear();
						m.clear();
						BlockConfig.getList("LocationX").clear();
						BlockConfig.getList("LocationY").clear();
						BlockConfig.getList("LocationZ").clear();
						BlockConfig.getList("Worlds").clear();
						BlockConfig.getList("Data").clear();
						BlockConfig.getList("Material").clear();
						alt = 0;
						try {
							BlockConfig.save(BlockFile);
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
					else
					{
					
				Bukkit.getWorld(w.get(s)).getBlockAt(l.get(s)).setType(m.get(s));
				Bukkit.getWorld(w.get(s)).getBlockAt(l.get(s)).setData(d.get(s));
				
				
				
				
				Location loc = l.get(s);
				Location locnew = new Location(loc.getWorld(),loc.getX(),loc.getY()+1,loc.getZ());
				locnew.getWorld().playSound(loc, Sound.ITEM_PICKUP, 1, 1);
				locnew.getWorld().playEffect(locnew, Effect.SMOKE, 1);
				locnew.getWorld().playEffect(locnew, Effect.SMOKE, 1);
				locnew.getWorld().playEffect(locnew, Effect.SMOKE, 1);
				locnew.getWorld().playEffect(locnew, Effect.SMOKE, 1);
				locnew.getWorld().playEffect(locnew, Effect.SMOKE, 1);
				//deleteConfig(s );
				
				
				s++;
					}
				}
				
			}, this.getConfig().getLong("RegenStart")*20, this.getConfig().getLong("RegenSpeed")*20);
				
			a++;
			
		}
		s = 0;
		
		
	}
		}
	}
	
	
	@EventHandler
	public void LeaveDeacay(LeavesDecayEvent e)
	{
		
		if(breakable == false|this.getConfig().getBoolean("LeaveDecay")==false)
		{
		e.setCancelled(true);
		}
	}
	@EventHandler
	public void Physics(BlockPhysicsEvent e)
	{
		if(this.getConfig().getBoolean("Physics")==false)
		{
			e.setCancelled(true);
		}
	}
	@EventHandler
	public void BlockChange(EntityChangeBlockEvent e)
	{
		if(this.getConfig().getBoolean("Physics")==false)
		{
			e.setCancelled(true);
		}
	}
	@EventHandler
	public void onFire(BlockBurnEvent e)
	{	
		if(this.getConfig().getBoolean("FireRegen")==true&&this.getConfig().getBoolean("Edit")==false)
		{
    
		
		
		
			
		l1.add(e.getBlock().getLocation());
		m1.add(e.getBlock().getType());
		d1.add(e.getBlock().getData());
		w1.add(e.getBlock().getWorld().getName());
		m2.add(e.getBlock().getTypeId());
		x1.add((int) e.getBlock().getLocation().getX());
		y1.add((int) e.getBlock().getLocation().getY());
		z1.add((int) e.getBlock().getLocation().getZ());
		BlockConfig.set("Worlds", w1);
		BlockConfig.set("Data", d1);
		BlockConfig.set("Material", m2);
		BlockConfig.set("LocationX", x1);
		BlockConfig.set("LocationY", y1);
		BlockConfig.set("LocationZ", z1);
		
		
		
		
			
		
		
		if(Arbeit == false)
		{
			Arbeit = true;
			breakable = false;
		int p = 0;
		z = 0;
	
		{
		task2 =	Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable()
			{
				public void run() 
				{
					
					if(z==l1.size())
					{
						Bukkit.getScheduler().cancelTask(task2);
						Arbeit = false;
						breakable = true;
						l1.clear();
						w1.clear();
						d1.clear();
						m1.clear();
						m2.clear();
						x1.clear();
						y1.clear();
						z1.clear();
						
						
						
					}
					else
					{
						
				Bukkit.getWorld(w1.get(z)).getBlockAt(l1.get(z)).setType(m1.get(z));
				Bukkit.getWorld(w1.get(z)).getBlockAt(l1.get(z)).setData(d1.get(z));
				Location loc1 = l1.get(z);
				Location locnew = new Location(loc1.getWorld(),loc1.getX(),loc1.getY()+1,loc1.getZ());
				locnew.getWorld().playSound(loc1, Sound.ITEM_PICKUP, 1, 1);
				locnew.getWorld().playEffect(locnew, Effect.SMOKE, 1);
				locnew.getWorld().playEffect(locnew, Effect.SMOKE, 1);
				locnew.getWorld().playEffect(locnew, Effect.SMOKE, 1);
				locnew.getWorld().playEffect(locnew, Effect.SMOKE, 1);
				locnew.getWorld().playEffect(locnew, Effect.SMOKE, 1);
				z++;
					}
				
				
				
				
					}
				
				
			}, this.getConfig().getLong("RegenStart")*20, this.getConfig().getLong("RegenSpeed")*20);
				
			
			
		}
	
		
		
	}
	}
	}
	@EventHandler
	public void onBlockBreak(BlockBreakEvent e)
	{
		if(this.getConfig().getBoolean("Edit")==false)
		{
    
		
		
		
			
		l1.add(e.getBlock().getLocation());
		m1.add(e.getBlock().getType());
		d1.add(e.getBlock().getData());
		w1.add(e.getBlock().getWorld().getName());
		m2.add(e.getBlock().getTypeId());
		x1.add((int) e.getBlock().getLocation().getX());
		y1.add((int) e.getBlock().getLocation().getY());
		z1.add((int) e.getBlock().getLocation().getZ());
		BlockConfig.set("Worlds", w1);
		BlockConfig.set("Data", d1);
		BlockConfig.set("Material", m2);
		BlockConfig.set("LocationX", x1);
		BlockConfig.set("LocationY", y1);
		BlockConfig.set("LocationZ", z1);
		
		
		
		
			
		
		
		if(Arbeit == false)
		{
			Arbeit = true;
			breakable = false;
		int p = 0;
		zBlock = 0;
	
		{
		task3 =	Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable()
			{
				public void run() 
				{
					
					if(zBlock==l1.size())
					{
						Bukkit.getScheduler().cancelTask(task3);
						Arbeit = false;
						breakable = true;
						l1.clear();
						w1.clear();
						d1.clear();
						m1.clear();
						m2.clear();
						x1.clear();
						y1.clear();
						z1.clear();
						
						
						
					}
					else
					{
						
				Bukkit.getWorld(w1.get(zBlock)).getBlockAt(l1.get(zBlock)).setType(m1.get(zBlock));
				Bukkit.getWorld(w1.get(zBlock)).getBlockAt(l1.get(zBlock)).setData(d1.get(zBlock));
				Location loc1 = l1.get(zBlock);
				Location locnew = new Location(loc1.getWorld(),loc1.getX(),loc1.getY()+1,loc1.getZ());
				locnew.getWorld().playSound(loc1, Sound.ITEM_PICKUP, 1, 1);
				locnew.getWorld().playEffect(locnew, Effect.SMOKE, 1);
				locnew.getWorld().playEffect(locnew, Effect.SMOKE, 1);
				locnew.getWorld().playEffect(locnew, Effect.SMOKE, 1);
				locnew.getWorld().playEffect(locnew, Effect.SMOKE, 1);
				locnew.getWorld().playEffect(locnew, Effect.SMOKE, 1);
				zBlock++;
				
					}
				
				
				
				
					}
				
				
			}, this.getConfig().getLong("RegenStart")*20, this.getConfig().getLong("RegenSpeed")*20);
				
			
			
		}
	
		
		
	}
	}}
	//public WorldEditPlugin getWorldEdit()
	//{
	//Plugin p = Bukkit.getServer().getPluginManager().getPlugin("WorldEdit");
	//if(p instanceof WorldEditPlugin)
	//{
	//	return (WorldEditPlugin) p;}
		//else return null;
	//}
	public void Messages(String s,String[] args,CommandSender sender)
	{
		if(sender instanceof Player)
		{
			p =(Player) sender;
		if(s.equalsIgnoreCase("Messages"))
		{
			if(args[0].equalsIgnoreCase("on"))
			{
				this.getConfig().set("Messages", true);
				p.sendMessage(""+ChatColor.YELLOW+"Messages activated");
				
			}
			this.saveConfig();
			if(args[0].equalsIgnoreCase("off"))
			{
				this.getConfig().set("Messages", false);
				p.sendMessage(""+ChatColor.YELLOW+"Messages deactivated");
				
			}
			this.saveConfig();
		}
		}
		else
		{
			Bukkit.broadcastMessage(MessagesConfig.getString("UseAsPlayer"));
		}
		
	}
	}
	
	
		
	
	
	