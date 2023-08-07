package net.pmkjun.pyrofishinghelper.mixin;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MovementType;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.FishingBobberEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import net.minecraft.entity.ItemEntity;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import net.minecraft.client.network.ClientPlayNetworkHandler;

@Mixin(FishingBobberEntity.class)
public abstract class FishingBobberSilentMixin extends ProjectileEntity {
    public FishingBobberSilentMixin(EntityType<? extends ProjectileEntity> entityType, World world) {
        super(entityType, world);
    }

    @Shadow
    @Final
    private static TrackedData<Integer> HOOK_ENTITY_ID;
    @Override
    protected void initDataTracker() {
        this.getDataTracker().startTracking(HOOK_ENTITY_ID, 0);
        this.getDataTracker().startTracking(CAUGHT_FISH, false);
    }

    @Override
    public boolean cannotBeSilenced() {
        return super.cannotBeSilenced();
    }

    @Shadow
    private int hookCountdown;
    @Shadow
    private int waitCountdown;
    @Shadow
    private int fishTravelCountdown;
    @Shadow
    @Final
    private static TrackedData<Boolean> CAUGHT_FISH;
    @Shadow
    private float fishAngle;
    @Shadow
    @Final
    private int lureLevel;


    @Shadow @Nullable public abstract PlayerEntity getPlayerOwner();

    /**
     * @author
     * @reason
     */
    @Overwrite
    private void tickFishingLogic(BlockPos pos) {
        ServerWorld serverWorld = (ServerWorld)this.getWorld();
        int i = 1;
        BlockPos blockPos = pos.up();
        if (this.random.nextFloat() < 0.25F && this.getWorld().hasRain(blockPos)) {
            ++i;
        }

        if (this.random.nextFloat() < 0.5F && !this.getWorld().isSkyVisible(blockPos)) {
            --i;
        }

        if (this.hookCountdown > 0) {
            --this.hookCountdown;
            if (this.hookCountdown <= 0) {
                this.waitCountdown = 0;
                this.fishTravelCountdown = 0;
                this.getDataTracker().set(CAUGHT_FISH, false);
            }
        } else {
            float f;
            float g;
            float h;
            double d;
            double e;
            double j;
            BlockState blockState;
            if (this.fishTravelCountdown > 0) {
                this.fishTravelCountdown -= i;
                if (this.fishTravelCountdown > 0) {
                    this.fishAngle += (float)this.random.nextTriangular(0.0, 9.188);
                    f = this.fishAngle * 0.017453292F;
                    g = MathHelper.sin(f);
                    h = MathHelper.cos(f);
                    d = this.getX() + (double)(g * (float)this.fishTravelCountdown * 0.1F);
                    e = (double)((float)MathHelper.floor(this.getY()) + 1.0F);
                    j = this.getZ() + (double)(h * (float)this.fishTravelCountdown * 0.1F);
                    blockState = serverWorld.getBlockState(BlockPos.ofFloored(d, e - 1.0, j));
                    if (blockState.isOf(Blocks.WATER)) {
                        if (this.random.nextFloat() < 0.15F) {
                            serverWorld.spawnParticles(ParticleTypes.BUBBLE, d, e - 0.10000000149011612, j, 1, (double)g, 0.1, (double)h, 0.0);
                        }

                        float k = g * 0.04F;
                        float l = h * 0.04F;
                        serverWorld.spawnParticles(ParticleTypes.FISHING, d, e, j, 0, (double)l, 0.01, (double)(-k), 1.0);
                        serverWorld.spawnParticles(ParticleTypes.FISHING, d, e, j, 0, (double)(-l), 0.01, (double)k, 1.0);
                    }
                } else {
                    System.out.println("lure : "+lureLevel+", owner : "+this.getPlayerOwner());
                    //this.playSound(SoundEvents.ENTITY_FISHING_BOBBER_SPLASH, 0.25F, 1.0F + (this.random.nextFloat() - this.random.nextFloat()) * 0.4F);
                    double m = this.getY() + 0.5;
                    serverWorld.spawnParticles(ParticleTypes.BUBBLE, this.getX(), m, this.getZ(), (int)(1.0F + this.getWidth() * 20.0F), (double)this.getWidth(), 0.0, (double)this.getWidth(), 0.20000000298023224);
                    serverWorld.spawnParticles(ParticleTypes.FISHING, this.getX(), m, this.getZ(), (int)(1.0F + this.getWidth() * 20.0F), (double)this.getWidth(), 0.0, (double)this.getWidth(), 0.20000000298023224);
                    this.hookCountdown = MathHelper.nextInt(this.random, 20, 40);
                    this.getDataTracker().set(CAUGHT_FISH, true);
                }
            } else if (this.waitCountdown > 0) {
                this.waitCountdown -= i;
                f = 0.15F;
                if (this.waitCountdown < 20) {
                    f += (float)(20 - this.waitCountdown) * 0.05F;
                } else if (this.waitCountdown < 40) {
                    f += (float)(40 - this.waitCountdown) * 0.02F;
                } else if (this.waitCountdown < 60) {
                    f += (float)(60 - this.waitCountdown) * 0.01F;
                }

                if (this.random.nextFloat() < f) {
                    g = MathHelper.nextFloat(this.random, 0.0F, 360.0F) * 0.017453292F;
                    h = MathHelper.nextFloat(this.random, 25.0F, 60.0F);
                    d = this.getX() + (double)(MathHelper.sin(g) * h) * 0.1;
                    e = (double)((float)MathHelper.floor(this.getY()) + 1.0F);
                    j = this.getZ() + (double)(MathHelper.cos(g) * h) * 0.1;
                    blockState = serverWorld.getBlockState(BlockPos.ofFloored(d, e - 1.0, j));
                    if (blockState.isOf(Blocks.WATER)) {
                        serverWorld.spawnParticles(ParticleTypes.SPLASH, d, e, j, 2 + this.random.nextInt(2), 0.10000000149011612, 0.0, 0.10000000149011612, 0.0);
                    }
                }

                if (this.waitCountdown <= 0) {
                    this.fishAngle = MathHelper.nextFloat(this.random, 0.0F, 360.0F);
                    this.fishTravelCountdown = MathHelper.nextInt(this.random, 20, 80);
                }
            } else {
                this.waitCountdown = MathHelper.nextInt(this.random, 100, 600);
                this.waitCountdown -= this.lureLevel * 20 * 5;
            }
        }

    }

}
