package fr.buildyourstory.bys_quidditch.entity.render;


import fr.buildyourstory.bys_quidditch.BYS_Quidditch;
import fr.buildyourstory.bys_quidditch.client.entity.ModelBalais;

import fr.buildyourstory.bys_quidditch.entity.EntityBalais;
import fr.buildyourstory.bys_quidditch.utils.References;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelHorse;
import net.minecraft.client.model.ModelPig;
import net.minecraft.client.model.ModelWolf;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

@SideOnly(Side.CLIENT)
public class RenderBalais extends Render<EntityBalais>
{

    private static final ResourceLocation texture = new ResourceLocation(References.MODID, "textures/entity/robotpig/texture.png");

    protected ModelBase modelBase = new ModelBalais();

    public RenderBalais(RenderManager renderManager, double shadowSize)
    {
        super(Minecraft.getMinecraft().getRenderManager());
        this.shadowSize = 0.2f;
    }



    private ResourceLocation getEntityTextureLocation()
    {
        return texture;
    }


    @Nullable
    @Override
    protected ResourceLocation getEntityTexture(EntityBalais entity) {
        return getEntityTextureLocation();
    }
}




