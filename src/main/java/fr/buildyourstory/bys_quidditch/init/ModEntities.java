package fr.buildyourstory.bys_quidditch.init;

import fr.buildyourstory.bys_quidditch.BYS_Quidditch;
import fr.buildyourstory.bys_quidditch.entity.EntityBroom;
import fr.buildyourstory.bys_quidditch.entity.render.RenderBroom;
import fr.buildyourstory.bys_quidditch.utils.References;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ModEntities {

    private static int mobID = 0;

    public static void registerEntities(){

        EntityRegistry.registerModEntity(new ResourceLocation(References.MODID, "balais"), EntityBalais.class, "balais", mobID++, BYS_Quidditch.instance, 60, 1, true);
        EntityRegistry.registerModEntity(new ResourceLocation(References.MODID, "broom"), EntityBroom.class, "broom", mobID++, BYS_Quidditch.instance, 60, 1, true);


    }


    @SideOnly(Side.CLIENT)
    public static void registerEntitiesRenders(){

        RenderingRegistry.registerEntityRenderingHandler(EntityBroom.class, new RenderBroom(Minecraft.getMinecraft().getRenderManager()));

    }

}
