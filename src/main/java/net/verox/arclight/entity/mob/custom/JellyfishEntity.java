package net.verox.arclight.entity.mob.custom;

import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.ai.control.MoveControl;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.boss.ServerBossBar;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.SmallFireballEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import java.util.Comparator;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.List;

public class JellyfishEntity extends FlyingEntity implements IAnimatable,Monster {

    private AnimationFactory factory = new AnimationFactory(this);

    Vec3d targetPosition;
    BlockPos circlingCenter;
    JellyfishEntity.JellyMovementType movementType;

    public JellyfishEntity(EntityType<? extends FlyingEntity> entityType, World world) {
        super(entityType, world);
        this.movementType = JellyfishEntity.JellyMovementType.CIRCLE;
        this.targetPosition = Vec3d.ZERO;
        this.circlingCenter = BlockPos.ORIGIN;
        this.experiencePoints = 11;
        this.moveControl = new JellyfishEntity.JellyMoveControl(this);
    }

    private static final TrackedData<Integer> DARK_TICKS_REMAINING;

    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(DARK_TICKS_REMAINING, 0);
    }

    private static enum JellyMovementType {
        CIRCLE;
        private JellyMovementType() {
        }
    }

    class JellyMoveControl extends MoveControl {
        private float targetSpeed = 0.1F;

        public JellyMoveControl(MobEntity owner) {
            super(owner);
        }

        public void tick() {
            if (JellyfishEntity.this.horizontalCollision) {
                JellyfishEntity.this.setYaw(JellyfishEntity.this.getYaw() + 180.0F);
                this.targetSpeed = 0.3F;
            }

            double d = JellyfishEntity.this.targetPosition.x - JellyfishEntity.this.getX();
            double e = JellyfishEntity.this.targetPosition.y - JellyfishEntity.this.getY();
            double f = JellyfishEntity.this.targetPosition.z - JellyfishEntity.this.getZ();
            double g = Math.sqrt(d * d + f * f);
            if (Math.abs(g) > 9.999999747378752E-6) {
                double h = 1.0 - Math.abs(e * 0.699999988079071) / g;
                d *= h;
                f *= h;
                g = Math.sqrt(d * d + f * f);
                double i = Math.sqrt(d * d + f * f + e * e);
                float j = JellyfishEntity.this.getYaw();
                float k = (float)MathHelper.atan2(f, d);
                float l = MathHelper.wrapDegrees(JellyfishEntity.this.getYaw() + 90.0F);
                float m = MathHelper.wrapDegrees(k * 57.295776F);
                JellyfishEntity.this.setYaw(MathHelper.stepUnwrappedAngleTowards(l, m, 4.0F) - 90.0F);
                JellyfishEntity.this.bodyYaw = JellyfishEntity.this.getYaw();
                if (MathHelper.angleBetween(j, JellyfishEntity.this.getYaw()) < 3.0F) {
                    this.targetSpeed = MathHelper.stepTowards(this.targetSpeed, 0.2F, 0.005F * (0.3F / this.targetSpeed));
                } else {
                    this.targetSpeed = MathHelper.stepTowards(this.targetSpeed, 0.3F, 0.025F);
                }

                float n = (float)(-(MathHelper.atan2(-e, g) * 57.2957763671875));
                JellyfishEntity.this.setPitch(n);
                float o = JellyfishEntity.this.getYaw() + 90.0F;
                double p = (double)(this.targetSpeed * MathHelper.cos(o * 0.017453292F)) * Math.abs(d / i);
                double q = (double)(this.targetSpeed * MathHelper.sin(o * 0.017453292F)) * Math.abs(f / i);
                double r = (double)(this.targetSpeed * MathHelper.sin(n * 0.017453292F)) * Math.abs(e / i);
                Vec3d vec3d = JellyfishEntity.this.getVelocity();
                JellyfishEntity.this.setVelocity(vec3d.add((new Vec3d(p, r, q)).subtract(vec3d).multiply(0.2)));
            }

        }
    }

    
    
    abstract class MovementGoal extends Goal {
        public MovementGoal() {
            this.setControls(EnumSet.of(Control.MOVE));
        }

        protected boolean isNearTarget() {
            return JellyfishEntity.this.targetPosition.squaredDistanceTo(JellyfishEntity.this.getX(), JellyfishEntity.this.getY(), JellyfishEntity.this.getZ()) < 4.0;
        }
    }

    public static DefaultAttributeContainer.Builder setAttributes() {
        return FlyingEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 6.0D)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 5.0f)
                .add(EntityAttributes.GENERIC_ATTACK_SPEED, 1.0f)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.3f);
    }

    protected void initGoals() {
        this.goalSelector.add(5, new JellyfishEntity.CircleMovementGoal());
        this.goalSelector.add(3, new LookAroundGoal(this));
        this.goalSelector.add(4, new LookAtEntityGoal(this, PlayerEntity.class, 8.0f));
        this.targetSelector.add(0, new JellyfishEntity.FindTargetGoal());
        //this.goalSelector.add(1, new JellyfishEntity.ShootFireballGoal(this));
    }

    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        if (event.isMoving()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.moonlight_jelly.move", true));
            return PlayState.CONTINUE;
        }

        event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.moonlight_jelly.idle", true));
        return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(AnimationData animationData) {
        animationData.addAnimationController(new AnimationController(this, "controller",
                0, this::predicate));
    }

    private static class ShootFireballGoal extends Goal {
        private final JellyfishEntity jellyfish;
        private int fireballsFired;
        private int fireballCooldown;
        private int targetNotVisibleTicks;

        public ShootFireballGoal(JellyfishEntity jellyfish) {
            this.jellyfish = jellyfish;
            this.setControls(EnumSet.of(Control.MOVE, Control.LOOK));
        }

        public boolean canStart() {
            LivingEntity livingEntity = this.jellyfish.getTarget();
            return livingEntity != null && livingEntity.isAlive() && this.jellyfish.canTarget(livingEntity);
        }

        public void start() {
            this.fireballsFired = 0;
        }

        public void stop() {
            this.targetNotVisibleTicks = 0;
        }

        public boolean shouldRunEveryTick() {
            return true;
        }

        public void tick() {
            --this.fireballCooldown;
            LivingEntity livingEntity = this.jellyfish.getTarget();
            if (livingEntity != null) {
                boolean bl = this.jellyfish.getVisibilityCache().canSee(livingEntity);
                if (bl) {
                    this.targetNotVisibleTicks = 0;
                } else {
                    ++this.targetNotVisibleTicks;
                }

                double d = this.jellyfish.squaredDistanceTo(livingEntity);
                if (d < 4.0) {
                    if (!bl) {
                        return;
                    }

                    if (this.fireballCooldown <= 0) {
                        this.fireballCooldown = 20;
                        this.jellyfish.tryAttack(livingEntity);
                    }

                    this.jellyfish.getMoveControl().moveTo(livingEntity.getX(), livingEntity.getY(), livingEntity.getZ(), 1.0);
                } else if (d < this.getFollowRange() * this.getFollowRange() && bl) {
                    double e = livingEntity.getX() - this.jellyfish.getX();
                    double f = livingEntity.getBodyY(0.5) - this.jellyfish.getBodyY(0.5);
                    double g = livingEntity.getZ() - this.jellyfish.getZ();
                    if (this.fireballCooldown <= 0) {
                        ++this.fireballsFired;
                        if (this.fireballsFired == 1) {
                            this.fireballCooldown = 60;
                        } else if (this.fireballsFired <= 4) {
                            this.fireballCooldown = 6;
                        } else {
                            this.fireballCooldown = 100;
                            this.fireballsFired = 0;
                        }

                        if (this.fireballsFired > 1) {
                            double h = Math.sqrt(Math.sqrt(d)) * 0.5;
                            if (!this.jellyfish.isSilent()) {
                                this.jellyfish.world.syncWorldEvent((PlayerEntity)null, 1018, this.jellyfish.getBlockPos(), 0);
                            }

                            for(int i = 0; i < 1; ++i) {
                                SmallFireballEntity smallFireballEntity = new SmallFireballEntity(this.jellyfish.world, this.jellyfish, this.jellyfish.getRandom().nextTriangular(e, 2.297 * h), f, this.jellyfish.getRandom().nextTriangular(g, 2.297 * h));
                                smallFireballEntity.setPosition(smallFireballEntity.getX(), this.jellyfish.getBodyY(0.5) + 0.5, smallFireballEntity.getZ());
                                this.jellyfish.world.spawnEntity(smallFireballEntity);
                            }
                        }
                    }

                    this.jellyfish.getLookControl().lookAt(livingEntity, 10.0F, 10.0F);
                } else if (this.targetNotVisibleTicks < 5) {
                    this.jellyfish.getMoveControl().moveTo(livingEntity.getX(), livingEntity.getY(), livingEntity.getZ(), 1.0);
                }

                super.tick();
            }
        }

        private double getFollowRange() {
            return this.jellyfish.getAttributeValue(EntityAttributes.GENERIC_FOLLOW_RANGE);
        }
    }

    class FindTargetGoal extends Goal {
        private final TargetPredicate PLAYERS_IN_RANGE_PREDICATE = TargetPredicate.createAttackable().setBaseMaxDistance(64.0);
        private int delay = toGoalTicks(20);

        FindTargetGoal() {
        }

        public boolean canStart() {
            if (this.delay > 0) {
                --this.delay;
                return false;
            } else {
                this.delay = toGoalTicks(60);
                List<PlayerEntity> list = JellyfishEntity.this.world.getPlayers(this.PLAYERS_IN_RANGE_PREDICATE, JellyfishEntity.this, JellyfishEntity.this.getBoundingBox().expand(16.0, 64.0, 16.0));
                if (!list.isEmpty()) {
                    list.sort(Comparator.comparing(Entity::getY).reversed());
                    Iterator var2 = list.iterator();

                    while(var2.hasNext()) {
                        PlayerEntity playerEntity = (PlayerEntity)var2.next();
                        if (JellyfishEntity.this.isTarget(playerEntity, TargetPredicate.DEFAULT)) {
                            JellyfishEntity.this.setTarget(playerEntity);
                            return true;
                        }
                    }
                }

                return false;
            }
        }

        public boolean shouldContinue() {
            LivingEntity livingEntity = JellyfishEntity.this.getTarget();
            return livingEntity != null ? JellyfishEntity.this.isTarget(livingEntity, TargetPredicate.DEFAULT) : false;
        }
    }

    class CircleMovementGoal extends JellyfishEntity.MovementGoal {
        private float angle;
        private float radius;
        private float yOffset;
        private float circlingDirection;

        CircleMovementGoal() {
            super();
        }

        public boolean canStart() {
            return JellyfishEntity.this.getTarget() == null || JellyfishEntity.this.movementType == JellyfishEntity.JellyMovementType.CIRCLE;
        }

        public void start() {
            this.radius = 5.0F + JellyfishEntity.this.random.nextFloat() * 10.0F;
            this.yOffset = -4.0F + JellyfishEntity.this.random.nextFloat() * 9.0F;
            this.circlingDirection = JellyfishEntity.this.random.nextBoolean() ? 1.0F : -1.0F;
            this.adjustDirection();
        }

        public void tick() {
            if (JellyfishEntity.this.random.nextInt(this.getTickCount(350)) == 0) {
                this.yOffset = -4.0F + JellyfishEntity.this.random.nextFloat() * 9.0F;
            }

            if (JellyfishEntity.this.random.nextInt(this.getTickCount(250)) == 0) {
                ++this.radius;
                if (this.radius > 15.0F) {
                    this.radius = 5.0F;
                    this.circlingDirection = -this.circlingDirection;
                }
            }

            if (JellyfishEntity.this.random.nextInt(this.getTickCount(450)) == 0) {
                this.angle = JellyfishEntity.this.random.nextFloat() * 2.0F * 3.1415927F;
                this.adjustDirection();
            }

            if (this.isNearTarget()) {
                this.adjustDirection();
            }

            if (JellyfishEntity.this.targetPosition.y < JellyfishEntity.this.getY() && !JellyfishEntity.this.world.isAir(JellyfishEntity.this.getBlockPos().down(1))) {
                this.yOffset = Math.max(1.0F, this.yOffset);
                this.adjustDirection();
            }

            if (JellyfishEntity.this.targetPosition.y > JellyfishEntity.this.getY() && !JellyfishEntity.this.world.isAir(JellyfishEntity.this.getBlockPos().up(1))) {
                this.yOffset = Math.min(-1.0F, this.yOffset);
                this.adjustDirection();
            }

        }

        private void adjustDirection() {
            if (BlockPos.ORIGIN.equals(JellyfishEntity.this.circlingCenter)) {
                JellyfishEntity.this.circlingCenter = JellyfishEntity.this.getBlockPos();
            }

            this.angle += this.circlingDirection * 15.0F * 0.017453292F;
            JellyfishEntity.this.targetPosition = Vec3d.of(JellyfishEntity.this.circlingCenter).add((double)(this.radius
                    * MathHelper.cos(this.angle)), (double)(-4.0F + this.yOffset), (double)(this.radius * MathHelper.sin(this.angle)));
        }
    }

    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putInt("DarkTicksRemaining", this.getDarkTicksRemaining());
    }

    public void tickMovement() {
        super.tickMovement();
        int i = this.getDarkTicksRemaining();
        if (i > 0) {
            this.setDarkTicksRemaining(i - 1);
        }

        this.world.addParticle(ParticleTypes.GLOW, this.getParticleX(0.6), this.getRandomBodyY(),
                this.getParticleZ(0.6), 0.0, 0.0, 0.0);
    }

    public int getDarkTicksRemaining() {
        return (Integer)this.dataTracker.get(DARK_TICKS_REMAINING);
    }
    private void setDarkTicksRemaining(int ticks) {
        this.dataTracker.set(DARK_TICKS_REMAINING, ticks);
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.ENTITY_AXOLOTL_IDLE_AIR;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.ENTITY_AXOLOTL_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_SQUID_DEATH;
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState state) {
        this.playSound(SoundEvents.ENTITY_SQUID_SQUIRT, 0.15f, 1.0f);
    }

    static {
        DARK_TICKS_REMAINING = DataTracker.registerData(JellyfishEntity.class, TrackedDataHandlerRegistry.INTEGER);
    }
    
    @Override
    public AnimationFactory getFactory() {
        return factory;
    }
}
