package fr.buildyourstory.bys_quidditch.entity;

import fr.buildyourstory.bys_quidditch.proxy.ClientProxy;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.entity.Entity;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class EntityBroom extends Entity {

    public boolean inputForward = false;

    public boolean inputRight = false;

    public boolean inputBack = false;

    public boolean inputLeft = false;

    public boolean inputUp = false;

    public boolean inputDown = false;



    private int debug = 0;



    public EntityBroom(World worldIn) {

        super(worldIn);

        setSize(1.0f, 1.0f);

        isImmuneToFire = true;

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

        if (player.isSneaking()) {

            return false;

        } else {

            if (!this.world.isRemote) {

                player.startRiding(this);


            }

            return true;

        }

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







        } else {

            this.inputForward = false;

            this.inputRight = false;

            this.inputBack = false;

            this.inputLeft = false;

            this.inputUp = false;

            this.inputDown = false;





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

        this.updateRotation();



        this.move(MoverType.SELF, this.motionX, this.motionY, this.motionZ);

    }



    public void updateRotation() {

        if (this.getControllingPassenger() instanceof EntityPlayer) {

            this.rotationYaw = MathHelper.wrapDegrees(this.getControllingPassenger().rotationYaw);

            this.rotationPitch = this.getControllingPassenger().rotationPitch;

        }

    }



    public void updateMotion() {

        double rotX  = -MathHelper.sin(this.rotationYaw * 0.017453292F);

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



        this.motionX *= 0.99d;

        if (Math.abs(this.motionX) < 0.0001d) this.motionX = 0;

        this.motionY *= 0.99d;

        if (Math.abs(this.motionY) < 0.0001d) this.motionY = 0;

        this.motionZ *= 0.99d;

        if (Math.abs(this.motionZ) < 0.0001d) this.motionZ = 0;

    }



    @Override

    protected void entityInit() {



    }



    @Override

    protected void readEntityFromNBT(NBTTagCompound compound) {



    }



    @Override

    protected void writeEntityToNBT(NBTTagCompound compound) {



    }

}