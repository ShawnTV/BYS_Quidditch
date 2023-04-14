package fr.buildyourstory.bys_quidditch.entity;

import fr.buildyourstory.bys_quidditch.proxy.ClientProxy;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.item.EntityBoat;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.IEntityAdditionalSpawnData;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class EntityBalais extends Entity implements IEntityAdditionalSpawnData {

    public boolean prevControlled = false;

    public boolean inputForward = false;
    public boolean inputRight = false;
    public boolean inputBack = false;
    public boolean inputLeft = false;
    public boolean inputUp = false;
    public boolean inputDown = false;
    public float yaw = 0.0F;
    public float pitch = 0.0F;
    public float partialTicks = 0.0F;
    private float deltaRotation;

    private int debug = 0;

    public EntityBalais(World worldIn) {
        super(worldIn);
        setSize(1.0f, 1.0f);
        isImmuneToFire = true;
    }

    @Override
    protected void entityInit() {

    }

    @Override
    public boolean canBeCollidedWith() {
        return !this.isDead;
    }

    @Override
    public boolean canBePushed() {
        return true;
    }

    @Override
    protected void readEntityFromNBT(NBTTagCompound compound) {

    }

    @Override
    protected void writeEntityToNBT(NBTTagCompound compound) {

    }

    @Override
    protected boolean canBeRidden(Entity entityIn) {
        return true;
    }

    @Override
    public boolean canRiderInteract() {
        return true;
    }

    @Override
    public boolean shouldDismountInWater(Entity rider) {
        return false;
    }

    @Override
    public double getMountedYOffset() {
        return 0.0d;
    }

    @Override
    public boolean canPassengerSteer() {
        return getControllingPassenger() instanceof EntityPlayer;
    }

    @Override
    public boolean processInitialInteract(EntityPlayer player, EnumHand hand) {
        if (!this.isBeingRidden()) {
            if (!this.world.isRemote) {
                player.rotationYaw = this.rotationYaw;
                player.rotationPitch = this.rotationPitch;
                player.startRiding(this);
            }
            return true;
        }
        return false;
    }

    @Override
    public AxisAlignedBB getCollisionBox(Entity entityIn) {
        return getEntityBoundingBox();
    }

    @Override
    public boolean attackEntityFrom(DamageSource source, float amount) {
        if (source.getTrueSource() instanceof EntityPlayer) {
            this.setDead();
            return true;
        }
        return false;
    }

    @Override
    public Entity getControllingPassenger() {
        List<Entity> list = this.getPassengers();
        return list.isEmpty() ? null : (Entity) list.get(0);
    }

    @Override
    public AxisAlignedBB getCollisionBoundingBox() {
        return null;
    }

    @SideOnly(Side.CLIENT)
    public void updateControls() {
        Minecraft mc = Minecraft.getMinecraft();

        if (this.getControllingPassenger() == mc.player) {
            GameSettings settings = mc.gameSettings;

            this.inputForward = settings.keyBindForward.isKeyDown();
            this.inputRight = settings.keyBindRight.isKeyDown();
            this.inputBack = settings.keyBindBack.isKeyDown();
            this.inputLeft = settings.keyBindLeft.isKeyDown();
            this.inputUp = settings.keyBindJump.isKeyDown();
            this.inputDown = ClientProxy.keyBindBalaisDescend.isKeyDown();

            this.prevControlled = true;


        } else if (this.prevControlled) {
            this.inputForward = false;
            this.inputRight = false;
            this.inputBack = false;
            this.inputLeft = false;
            this.inputUp = false;
            this.inputDown = false;
            this.inputDown = false;

            this.prevControlled = false;


        }
    }

    @Override
    public void onUpdate() {
        super.onUpdate();

        this.onGround = false;


        if (this.world.isRemote) {
            this.updateControls();
        }

        this.updateMotion();

        this.move(MoverType.SELF, this.motionX, this.motionY, this.motionZ);
    }


    @SideOnly(Side.CLIENT)
    public void updateRotation() {
        Minecraft mc = Minecraft.getMinecraft();
        if (this.getControllingPassenger() == mc.player) {
            float f = mc.getRenderPartialTicks() - this.partialTicks;
            if (f <= 0.0F) {
                f++;
            }


            double d = mc.gameSettings.mouseSensitivity * 0.6D + 0.2D;
            double d1 = d * d * d * 8.0D;
            double deltaX = mc.mouseHelper.deltaX;
            double deltaY = -(double) mc.mouseHelper.deltaY;
            double d2 = MathHelper.clamp(deltaX * d1 * 0.06D, -24.0D * f, 24.0D * f);
            double d3 = MathHelper.clamp(deltaY * d1 * 0.06D, -24.0D * f, 24.0D * f);
            if (mc.gameSettings.invertMouse) {
                d3 *= -1.0D;
            }

            this.rotationYaw += (float) d2 * 0.5F;
            this.rotationPitch += (float) d3 * 0.5F;
            this.yaw += (float) d2 * 0.5F;
            this.pitch += (float) d3 * 0.5F;


            if (Math.abs(this.yaw) > 0.01F) {
                this.rotationYaw += this.yaw * 0.5F * f;
                this.yaw *= 1.0F - 0.2F * f;
            } else {
                this.yaw = 0.0F;
            }
            if (Math.abs(this.pitch) > 0.01F) {
                this.rotationPitch += this.pitch * 0.5F * f;
                this.pitch *= 1.0F - 0.2F * f;
            } else {
                this.pitch = 0.0F;
            }

            // this.rotationYaw = MathHelper.wrapDegrees(this.rotationYaw);
            this.rotationPitch = MathHelper.clamp(this.rotationPitch, -90.0F, 90.0F);
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void applyOrientationToEntity(Entity entityToUpdate) {
        this.updateRotation();

        entityToUpdate.prevRotationYaw = this.rotationYaw;
        entityToUpdate.rotationYaw = this.rotationYaw;
        entityToUpdate.prevRotationPitch = this.rotationPitch;
        entityToUpdate.rotationPitch = this.rotationPitch;
        if (entityToUpdate instanceof EntityLivingBase) {
            EntityLivingBase entity = (EntityLivingBase) entityToUpdate;
            entity.prevRotationYawHead = this.rotationYaw;
            entity.rotationYawHead = this.rotationYaw;
            entity.prevRenderYawOffset = this.rotationYaw;
            entity.renderYawOffset = this.rotationYaw;
        }
    }


    /*public void updateMotion() {

        this.motionX *= 0.95D;
        this.motionY *= 0.95D;
        this.motionZ *= 0.95D;


        if (Math.abs(this.motionX) < 0.001D) {
            this.motionX = 0.0D;
        }
        if (Math.abs(this.motionY) < 0.001D) {
            this.motionY = 0.0D;
        }
        if (Math.abs(this.motionZ) < 0.001D) {
            this.motionZ = 0.0D;
        }


        if (this.getControllingPassenger() instanceof EntityPlayer) {
            if (this.world.isRemote) {


                if (this.isBeingRidden()) {

                    double d = 0;

                    float rotationYaw = this.rotationYaw;
                    float rotationPitch = this.rotationPitch;

                    float strafe = 0.0F;
                    float forward = 0.0F;
                    float up = 0.0F;

                    if (this.inputForward) {
                        forward += 1.0F;
                    }
                    if (this.inputBack) {
                        forward -= 1.0F;
                    }
                    if (this.inputRight) {
                        strafe -= 1.0F;
                    }
                    if (this.inputLeft) {
                        strafe += 1.0F;
                    }
                    if (this.inputUp) {
                        up += 1.0F;
                    }
                    if (this.inputDown) {
                        up -= 1.0F;
                    }

                    if (this.inputForward && !this.inputBack) {
                        if (this.inputUp && !this.inputDown) {
                            rotationPitch = (rotationPitch - 90.0F) / 2.0F;
                            up = 0.0F;
                        } else if (this.inputDown && !this.inputUp) {
                            rotationPitch = (rotationPitch + 90.0F) / 2.0F;
                            up = 0.0F;
                        }
                    } else if (this.inputBack && !this.inputForward) {
                        if (this.inputUp && !this.inputDown) {
                            rotationPitch = (rotationPitch + 90.0F) / 2.0F;
                            up = 0.0F;
                        } else if (this.inputDown && !this.inputUp) {
                            rotationPitch = (rotationPitch - 90.0F) / 2.0F;
                            up = 0.0F;
                        }
                        forward *= 0.45F;
                    }

                    move3D(this, strafe, up, forward, d, rotationYaw, rotationPitch);
                }
            }

        }
    }*/


    public void updateMotion() {
        if (this.isBeingRidden())
        {
            float f = 0.0F;
            float up = 0.0f;

            if (this.inputLeft)
            {
                this.deltaRotation += -1.0F;
            }

            if (this.inputRight)
            {
                ++this.deltaRotation;
            }

            if (this.inputRight != this.inputLeft && !this.inputForward && !this.inputBack)
            {
                f += 0.005F;
            }

            this.rotationYaw += this.deltaRotation;

            if (this.inputForward)
            {
                f += 0.04F;
            }

            if (this.inputBack)
            {
                f -= 0.005F;
            }

            if(this.inputUp)
            {
                up += 0.2;
            }

            if(this.inputDown)
            {
                up -= 0.2;
            }


            this.motionX += (double)(MathHelper.sin(-this.rotationYaw * 0.017453292F) * f);
            this.motionZ += (double)(MathHelper.cos(this.rotationYaw * 0.017453292F) * f);
            this.motionY += (double) (MathHelper.cos(1)*up);
        }
    }


        /*double rotX  = -MathHelper.sin(this.rotationYaw * 0.017453292F);
        double rotZ  =  MathHelper.cos(this.rotationYaw * 0.017453292F);
        float fakeRotationPitch = this.rotationPitch;

        double speed = 0.02d;

        if (inputDown && !inputUp) {
            if (inputForward) {
                fakeRotationPitch = (fakeRotationPitch + 90f) / 2f;
            } else if (inputBack) {
                fakeRotationPitch = (fakeRotationPitch - 90f) / 2f;
            } else {
                this.motionY -= speed;
            }
        }

        if (inputUp && !inputDown) {
            if (inputForward) {
                fakeRotationPitch = (fakeRotationPitch - 90f) / 2f;
            } else if (inputBack) {
                fakeRotationPitch = (fakeRotationPitch + 90f) / 2f;
            } else {
                this.motionY += speed;
            }
        }

        double lookVecX = rotX * MathHelper.cos(fakeRotationPitch * 0.017453292F);
        double lookVecY = -MathHelper.sin(fakeRotationPitch * 0.017453292F);
        double lookVecZ = rotZ * MathHelper.cos(fakeRotationPitch * 0.017453292F);

        if (inputForward) {
            this.motionX += speed * lookVecX;
            this.motionY += speed * lookVecY;
            this.motionZ += speed * lookVecZ;
        }
        if (inputBack) {
            this.motionX -= speed * lookVecX;
            this.motionY -= speed * lookVecY;
            this.motionZ -= speed * lookVecZ;
        }
        if (inputLeft) {
            this.motionX += speed * rotZ;
            this.motionZ += speed * -rotX;
        }
        if (inputRight) {
            this.motionX += speed * -rotZ;
            this.motionZ += speed * rotZ;
        }

        this.motionX *= 0.90d;
        if (this.motionX < 0.0001d) this.motionX = 0;
        this.motionY *= 0.90d;
        if (this.motionY < 0.0001d) this.motionY = 0;
        this.motionZ *= 0.90d;
        if (this.motionZ < 0.0001d) this.motionZ = 0;

    }*/

    /*public static void move3D(Entity entity, double strafe, double up, double forward, double speed, double yaw, double pitch) {
        double d = strafe * strafe + up * up + forward * forward;
        if (d >= 1.0E-4D) {
            d = Math.sqrt(d);
            if (d < 1.0D) {
                d = 1.0D;
            }
            d = speed / d;

            strafe *= d;
            up *= d;
            forward *= d;

            double d1 = Math.sin(yaw * 0.017453292D);
            double d2 = Math.cos(yaw * 0.017453292D);
            double d3 = Math.sin(pitch * 0.017453292D);
            double d4 = Math.cos(pitch * 0.017453292D);

            entity.motionX += strafe * d2 - forward * d1 * d4;
            entity.motionY += up - forward * d3;
            entity.motionZ += forward * d2 * d4 + strafe * d1;





        }
    }*/

    @Override
    public void writeSpawnData(ByteBuf buffer) {

    }

    @Override
    public void readSpawnData(ByteBuf additionalData) {

    }
}