package random.facades;

import net.minecraft.server.management.ServerConfigurationManager;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.world.Teleporter;

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

    public void transferPlayerToDimension(EntityPlayerMP par1EntityPlayerMP, int par2)
    {
        transferPlayerToDimension(par1EntityPlayerMP, par2, ForgeMultiMod.getWorldServer(par1EntityPlayerMP, par2).getDefaultTeleporter());
    }
    public void transferPlayerToDimension(EntityPlayerMP par1EntityPlayerMP, int par2, Teleporter teleporter)
    {
        int j = par1EntityPlayerMP.dimension;
        WorldServer worldserver = ForgeMultiMod.getWorldServer(par1EntityPlayerMP, par1EntityPlayerMP.dimension);
        par1EntityPlayerMP.dimension = par2;
        WorldServer worldserver1 = ForgeMultiMod.getWorldServer(par1EntityPlayerMP, par1EntityPlayerMP.dimension);
        par1EntityPlayerMP.playerNetServerHandler.sendPacketToPlayer(new Packet9Respawn(par1EntityPlayerMP.dimension, (byte)par1EntityPlayerMP.worldObj.difficultySetting, worldserver1.getWorldInfo().getTerrainType(), worldserver1.getHeight(), par1EntityPlayerMP.theItemInWorldManager.getGameType()));
        worldserver.removePlayerEntityDangerously(par1EntityPlayerMP);
        par1EntityPlayerMP.isDead = false;
        this.transferEntityToWorld(par1EntityPlayerMP, j, worldserver, worldserver1, teleporter);
        this.func_72375_a(par1EntityPlayerMP, worldserver);
        par1EntityPlayerMP.playerNetServerHandler.setPlayerLocation(par1EntityPlayerMP.posX, par1EntityPlayerMP.posY, par1EntityPlayerMP.posZ, par1EntityPlayerMP.rotationYaw, par1EntityPlayerMP.rotationPitch);
        par1EntityPlayerMP.theItemInWorldManager.setWorld(worldserver1);
        this.updateTimeAndWeatherForPlayer(par1EntityPlayerMP, worldserver1);
        this.syncPlayerInventory(par1EntityPlayerMP);
        Iterator iterator = par1EntityPlayerMP.getActivePotionEffects().iterator();

        while (iterator.hasNext())
        {
            PotionEffect potioneffect = (PotionEffect)iterator.next();
            par1EntityPlayerMP.playerNetServerHandler.sendPacketToPlayer(new Packet41EntityEffect(par1EntityPlayerMP.entityId, potioneffect));
        }

        GameRegistry.onPlayerChangedDimension(par1EntityPlayerMP);
    }
}