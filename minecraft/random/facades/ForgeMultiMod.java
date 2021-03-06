package random.facades;

import java.io.File;
import java.lang.reflect.Field;

import net.minecraft.command.ServerCommandManager;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.Property;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;

@Mod(modid=ForgeMultiMod.MODID, name=ForgeMultiMod.MODNAME, version=ForgeMultiMod.MODVER) //Tell forge "Oh hey, there's a new mod here to load."
public class ForgeMultiMod
{
    //Set the ID of the mod (Should be lower case).
    public static final String MODID = "forge_multi";
    //Set the "Name" of the mod.
    public static final String MODNAME = "Forge Multiverse Mod";
    //Set the version of the mod.
    public static final String MODVER = "0.0.0";

    @Instance(value = ForgeMultiMod.MODID) //Tell Forge what instance to use.
    public static ForgeMultiMod instance;
       
    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
    	Configuration config = new Configuration(event.getSuggestedConfigurationFile());

    	config.load();
    	File dir;
		try
		{
			Loader ld = Loader.instance();
			Field privie = Loader.class.getDeclaredField("canonicalModsDir");
			privie.setAccessible(true);
			dir = (File)(privie.get(ld));
			for(File ls : dir.listFiles())
			{
				if(ls.getName().startsWith("uni_"))
				{
					// Need to do stuff here
				}
			}
		} catch (Exception e)
		{ e.printStackTrace(); }
		
    	config.save();
    }
       
    @EventHandler
    public void load(FMLInitializationEvent event)
    {
    }
       
    @EventHandler
    public void serverStart(FMLServerStartingEvent event)
    {
		ServerCommandManager abc = (ServerCommandManager)(MinecraftServer.getServer().getCommandManager());
		abc.registerCommand(new ForgeMultiCommand());
		try
		{
			MinecraftServer mc = MinecraftServer.getServer();
			Field privie = MinecraftServer.class.getDeclaredField("serverConfigManager");
			privie.setAccessible(true);
			privie.set(mc,new SCM_Wrapper(privie.get(mc)));
		} catch (Exception e)
		{ e.printStackTrace(); }
    }
	
	public static getWorldServer(EntityPlayerMP plyr, int dimID)
	{
		
	}
}