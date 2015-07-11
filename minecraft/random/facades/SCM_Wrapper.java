package random.facades;

import net.minecraft.server.management.ServerConfigurationManager;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.world1.Teleporter;

public class SCM_Wrapper extends ServerConfigurationManager
{

    /**
     * Wrapper for MinecraftServer instance of ServerConfigurationManager
     * Need to test every, EVERY, part of this code... like seriously... every part
     */


    public SCM_Wrapper(ServerConfigurationManager master)
    {
        try
        {
            Class clz = master.class;
            Field[] flds = clz.getDeclaredFields();
            for(Field priv : flds)
            {
                priv.setAccessible(true);
                this.getClass().getField(priv.getName()).set(this,priv.get(master));
            }
        } catch( Exception e ) 
        { e.printStackTrace(); }
    }

    public void transferPlayerToDimension(EntityPlayerMP plyr, int dim2)
    {
        transferPlayerToDimension(plyr, dim2, ForgeMultiMod.getWorldServer(plyr, dim2).getDefaultTeleporter());
    }
	
    public void transferPlayerToDimension(EntityPlayerMP plyr, int dim2, Teleporter tp)
    {
        int prevDim = plyr.dimension;
        WorldServer world1 = ForgeMultiMod.getWorldServer(plyr, plyr.dimension);
        plyr.dimension = dim2;
        WorldServer world2 = ForgeMultiMod.getWorldServer(plyr, plyr.dimension);
		
        plyr.playerNetServerHandler.sendPacketToPlayer(new Packet9Respawn(
				plyr.dimension, (byte)plyr.worldObj.difficultySetting, world2.getWorldInfo().getTerrainType(), 
				world2.getHeight(), plyr.theItemInWorldManager.getGameType()));
				
        world1.removePlayerEntityDangerously(plyr);
        plyr.isDead = false;
        this.transferEntityToWorld(plyr, prevDim, world1, world2, tp);
        this.func_72375_a(plyr, world1);
        plyr.playerNetServerHandler.setPlayerLocation(plyr.posX, plyr.posY, plyr.posZ, plyr.rotationYaw, 
													  plyr.rotationPitch);
        plyr.theItemInWorldManager.setWorld(world2);
        this.updateTimeAndWeatherForPlayer(plyr, world2);
        this.syncPlayerInventory(plyr);
        Iterator iterator = plyr.getActivePotionEffects().iterator();

        while (iterator.hasNext())
        {
            PotionEffect potioneffect = (PotionEffect)iterator.next();
            plyr.playerNetServerHandler.sendPacketToPlayer(new Packet41EntityEffect(plyr.entityId, potioneffect));
        }

        GameRegistry.onPlayerChangedDimension(plyr);
    }
}