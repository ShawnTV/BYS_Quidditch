package fr.buildyourstory.bys_quidditch.entity.render;

import fr.buildyourstory.bys_quidditch.client.entity.ModelBalais;
import fr.buildyourstory.bys_quidditch.entity.EntityBroom;
import fr.buildyourstory.bys_quidditch.utils.References;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.client.model.obj.OBJModel;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

import javax.annotation.Nullable;

@SideOnly(Side.CLIENT)
public class RenderBroom extends TileEntitySpecialRenderer {

    protected ModelBase modelBalais = new ModelBalais();

    private static final ResourceLocation texture = new ResourceLocation(References.MODID, "textures/entity/nimbus_2001.png");





    @Override
    public void doRender(EntityBroom entity, double x, double y, double z, float entityYaw, float partialTicks) {
        Minecraft mc = Minecraft.getMinecraft();
        EntityPlayer player = mc.player;

        GL11.glPushMatrix();
        GL11.glTranslated(x, y, z);

        if (player == entity.getControllingPassenger() && mc.gameSettings.thirdPersonView == 0) {
            GL11.glTranslated(0.0D, player.eyeHeight, 0.0D);
            this.setupRotation(entity, partialTicks);
            GL11.glTranslated(0.0D, 0.8125D - player.eyeHeight, -0.32D);
        } else {
            GL11.glTranslated(0.0D, 0.8125D, 0.0D);
            this.setupRotation(entity, partialTicks);
        }

        GL11.glScaled(-1.0D, -1.0D, 1.0D);

        this.bindEntityTexture(entity);
        this.modelBalais.render(entity, partialTicks, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
        GL11.glPopMatrix();

        super.doRender(entity, x, y, z, entityYaw, partialTicks);
    }

    protected void setupRotation(Entity entity, float partialTicks) {
        double yaw = this.interpolateRotation(entity.prevRotationYaw, entity.rotationYaw, partialTicks);
        double pitch = this.interpolateRotation(entity.prevRotationPitch, entity.rotationPitch, partialTicks);
        // interpolated yaw and pitch does NOT work
        GL11.glRotated(entity.rotationPitch, Math.cos(Math.toRadians(entity.rotationYaw)), 0.0D, Math.sin(Math.toRadians(entity.rotationYaw)));
        GL11.glRotated(180.0D - entity.rotationYaw, 0.0D, 1.0D, 0.0D);
    }

    protected double interpolateRotation(double prevRotation, double rotation, double partialTicks) {
        return prevRotation + MathHelper.wrapDegrees(rotation - prevRotation) * partialTicks;
    }





    @Nullable
    @Override
    protected ResourceLocation getEntityTexture(EntityBroom entity) {
        return getEntityTextureLocation();
    }




    private ResourceLocation getEntityTextureLocation() {
        return texture;
    }



}
