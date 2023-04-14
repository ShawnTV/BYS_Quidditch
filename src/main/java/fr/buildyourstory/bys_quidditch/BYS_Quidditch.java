package fr.buildyourstory.bys_quidditch;

import fr.buildyourstory.bys_quidditch.proxy.CommonProxy;
import fr.buildyourstory.bys_quidditch.utils.References;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = References.MODID, name = References.NAME, version = References.VERSION)
public class BYS_Quidditch {

    @Mod.Instance(References.MODID)
    public static BYS_Quidditch instance;

    @SidedProxy(clientSide = References.CLIENT_PROXY, serverSide = References.SERVER_PROXY)
    public static CommonProxy proxy;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event){
        proxy.preInit();

    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event){
        proxy.init();

    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event){
        proxy.postInit();

    }

}
