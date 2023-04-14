package fr.buildyourstory.bys_quidditch.entity.render;


import fr.buildyourstory.bys_quidditch.BYS_Quidditch;
import fr.buildyourstory.bys_quidditch.client.entity.ModelBalais;
import fr.buildyourstory.bys_quidditch.entity.EntityBalais;
import fr.buildyourstory.bys_quidditch.utils.References;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelHorse;
import net.minecraft.client.model.ModelWolf;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderBoat;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;

import javax.annotation.Nullable;

public class RenderBalais extends RenderLiving<EntityBalais>  {

    private ResourceLocation texture = new ResourceLocation(References.MODID, "textures/entity/balais/balais.png");

    public RenderBalais(ModelBalais model, float shadowsizeIn) {
        super(Minecraft.getMinecraft().getRenderManager(), model, shadowsizeIn);
    }

    @Nullable
    @Override
    protected ResourceLocation getEntityTexture(EntityBalais entity) {
        return getEntityResourceLocation();
    }

    private ResourceLocation getEntityResourceLocation(){
        return texture;

    }



}
