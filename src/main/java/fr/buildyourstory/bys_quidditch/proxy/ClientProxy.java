package fr.buildyourstory.bys_quidditch.proxy;

import fr.buildyourstory.bys_quidditch.client.entity.ModelBalais;
import fr.buildyourstory.bys_quidditch.init.ModEntities;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

public class ClientProxy extends CommonProxy{

    @Override
    public void preInit() {
        super.preInit();
    }

    @Override
    public void init() {
        super.init();
    }

    @Override
    public void postInit() {
        super.postInit();

        ModEntities.registerEntitiesRenders();

    }
}
