package random.facades;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.common.DimensionManager;

public class ForgeMultiCommand extends CommandBase
{

	@Override
	public String getCommandName()
	{
		return "fmc";
	}

	@Override
	public String getCommandUsage(ICommandSender icommandsender)
	{
		return "fmc <options>";
	}

	@Override
	public void processCommand(ICommandSender icommandsender, String[] astring)
	{
		EntityPlayerMP plyr = null;
		if(icommandsender instanceof EntityPlayerMP)
		{
			plyr = (EntityPlayerMP)icommandsender;
			if(astring.length == 0)
			{
				plyr.addChatMessage("Dimension: "+plyr.dimension);
			}
			if(astring.length == 2 && isInteger(astring[1]))
			{
				if(astring[0].equals("tp"))
				{
					int dim = Integer.parseInt(astring[1]);
					System.out.println("fmc tp "+ dim);
					plyr.mcServer.getConfigurationManager().transferPlayerToDimension(plyr, dim);
					plyr.timeUntilPortal = plyr.getPortalCooldown();
				}
			} else if(astring.length == 3 && isInteger(astring[1]) && isInteger(astring[2]))
			{
				 if(astring[0].equals("create"))
				{
					int a = Integer.parseInt(astring[1]), b = Integer.parseInt(astring[2]);
					System.out.println("fmc create "+ a +" " + b);
					DimensionManager.registerDimension(a, b);
				}
			}
		}
	}
	
	public static boolean isInteger(String str) {
		if (str == null) {
			return false;
		}
		int length = str.length();
		if (length == 0) {
			return false;
		}
		int i = 0;
		if (str.charAt(0) == '-') {
			if (length == 1) {
				return false;
			}
			i = 1;
		}
		for (; i < length; i++) {
			char c = str.charAt(i);
			if (c <= '/' || c >= ':') {
				return false;
			}
		}
		return true;
	}
}
