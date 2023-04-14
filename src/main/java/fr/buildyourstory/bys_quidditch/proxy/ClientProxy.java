package fr.buildyourstory.bys_quidditch.proxy;

import fr.buildyourstory.bys_quidditch.client.entity.ModelBalais;
import fr.buildyourstory.bys_quidditch.init.ModEntities;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import org.lwjgl.input.Keyboard;

public class ClientProxy extends CommonProxy{

    public static KeyBinding keyBindBalaisDescend = new KeyBinding("Balais descend", Keyboard.KEY_C, "Balais");

    @Override
    public void preInit() {
        super.preInit();
    }

    @Override
    public void init() {
        super.init();
        ClientRegistry.registerKeyBinding(ClientProxy.keyBindBalaisDescend);
    }

    @Override
    public void postInit() {
        super.postInit();

        ModEntities.registerEntitiesRenders();

    }
}
