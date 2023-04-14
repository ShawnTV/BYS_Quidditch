package fr.buildyourstory.bys_quidditch.init;

import fr.buildyourstory.bys_quidditch.BYS_Quidditch;
import fr.buildyourstory.bys_quidditch.client.entity.ModelBalais;
import fr.buildyourstory.bys_quidditch.entity.EntityBalais;
import fr.buildyourstory.bys_quidditch.entity.render.RenderBalais;
import fr.buildyourstory.bys_quidditch.utils.References;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBed;
import net.minecraft.client.model.ModelBoat;
import net.minecraft.client.model.ModelHorse;
import net.minecraft.client.model.ModelWolf;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ModEntities {

    private static int mobID = 0;

    public static void registerEntities(){

        EntityRegistry.registerModEntity(new ResourceLocation(References.MODID, "balais"), EntityBalais.class, "balais", mobID++, BYS_Quidditch.instance, 60, 1, true);

    }


    @SideOnly(Side.CLIENT)
    public static void registerEntitiesRenders(){

        RenderingRegistry.registerEntityRenderingHandler(EntityBalais.class, new RenderBalais(new ModelBalais(), 0.6f));

    }
}
