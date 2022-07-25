package net.verox.arclight.entity.mob.custom;

import com.ibm.icu.impl.units.ConversionRates;
import net.minecraft.block.BlockState;
import net.minecraft.block.FluidBlock;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.ai.pathing.PathNodeType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.boss.BossBar;
import net.minecraft.entity.boss.ServerBossBar;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.Monster;
import net.minecraft.entity.mob.SpiderEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.SmallFireballEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.tag.FluidTags;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class ScorpionEntity extends SpiderEntity implements Monster, IAnimatable{
    private static final TrackedData<Byte> SCORPION_FLAGS;

    private final ServerBossBar bossBar;
    private AnimationFactory factory = new AnimationFactory(this);

    public ScorpionEntity(EntityType<? extends SpiderEntity> entityType, World world) {
        super(entityType, world);
        this.setPathfindingPenalty(PathNodeType.LAVA, 8.0F);
        this.setPathfindingPenalty(PathNodeType.DANGER_FIRE, 0.0F);
        this.setPathfindingPenalty(PathNodeType.DAMAGE_FIRE, 0.0F);
        this.experiencePoints = 5000;
        this.bossBar = (ServerBossBar)(new ServerBossBar(this.getDisplayName(), BossBar.Color.RED, BossBar.Style.PROGRESS)).setDarkenSky(true);
    }

    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(SCORPION_FLAGS, (byte)0);
    }

    public boolean isOnFire() {
        return this.isFireActive();
    }

    private boolean isFireActive() {
        return ((Byte)this.dataTracker.get(SCORPION_FLAGS) & 1) != 0;
    }

    void setFireActive(boolean fireActive) {
        byte b = (Byte)this.dataTracker.get(SCORPION_FLAGS);
        if (fireActive) {
            b = (byte)(b | 1);
        } else {
            b &= -2;
        }

        this.dataTracker.set(SCORPION_FLAGS, b);
    }

    static {
        SCORPION_FLAGS = DataTracker.registerData(ScorpionEntity.class, TrackedDataHandlerRegistry.BYTE);
    }

    public boolean handleFallDamage(float fallDistance, float damageMultiplier, DamageSource damageSource) {
        return false;
    }

    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        if (this.hasCustomName()) {
            this.bossBar.setName(this.getDisplayName());
        }

    }

    public void onSummoned() {
        this.bossBar.setPercent(0.0F);
        this.setHealth(this.getMaxHealth() / 3.0F);
    }

    public void onStartedTrackingBy(ServerPlayerEntity player) {
        super.onStartedTrackingBy(player);
        this.bossBar.addPlayer(player);
    }

    public void onStoppedTrackingBy(ServerPlayerEntity player) {
        super.onStoppedTrackingBy(player);
        this.bossBar.removePlayer(player);
    }

    public void setCustomName(@Nullable Text name) {
        super.setCustomName(name);
        this.bossBar.setName(this.getDisplayName());
    }
    protected void mobTick() {
        this.isFireImmune();
        this.isImmuneToExplosion();
        this.bossBar.setPercent(this.getHealth() / this.getMaxHealth());
    }

    public static DefaultAttributeContainer.Builder setAttributes() {
        return AnimalEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 800.0D)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 60.0f)
                .add(EntityAttributes.GENERIC_ATTACK_SPEED, 0.8f)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.6f);
    }

    protected void initGoals() {
        this.goalSelector.add(1, new SwimGoal(this));
        this.goalSelector.add(3, new PounceAtTargetGoal(this, 0.4F));
        //this.goalSelector.add(4, new AttackGoal(this));
        this.goalSelector.add(5, new WanderAroundFarGoal(this, 0.8));
        this.goalSelector.add(6, new LookAtEntityGoal(this, PlayerEntity.class, 8.0F));
        this.goalSelector.add(6, new LookAroundGoal(this));
        this.targetSelector.add(1, new RevengeGoal(this, new Class[0]));
        this.targetSelector.add(2, new TargetGoal(this, PlayerEntity.class));
        //this.targetSelector.add(3, new TargetGoal(this, IronGolemEntity.class));
        this.goalSelector.add(4, new ScorpionEntity.ShootFireballGoal(this));
    }

    private static class TargetGoal<T extends LivingEntity> extends ActiveTargetGoal<T> {
        public TargetGoal(ScorpionEntity spider, Class<T> targetEntityClass) {
            super(spider, targetEntityClass, true);
        }

        public boolean canStart() {
            float f = this.mob.getBrightnessAtEyes();
            return f >= 0.5F ? false : super.canStart();
        }
    }

    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {

        if (event.isMoving()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("move", true));
            return PlayState.CONTINUE;
        }

        event.getController().setAnimation(new AnimationBuilder().addAnimation("idle", true));
        return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(AnimationData animationData) {
        animationData.addAnimationController(new AnimationController(this, "controller",
                0, this::predicate));
    }

    private class ShootFireballGoal extends Goal {
        private final ScorpionEntity scorpion;
        private int fireballsFired;
        private int fireballCooldown;
        private int targetNotVisibleTicks;
        private LivingEntity target;

        private ShootFireballGoal(ScorpionEntity scorpion) {
            this.scorpion = scorpion;
        }

        public boolean canStart() {
            LivingEntity livingEntity = this.scorpion.getTarget();
            if (livingEntity == null) {
                return false;
            } else {
                this.target = livingEntity;
                return true;
            }
        }

        public boolean shouldContinue() {
            if (!this.target.isAlive()) {
                return false;
            } else if (this.scorpion.squaredDistanceTo(this.target) > 225.0) {
                return false;
            } else {
                return !this.scorpion.getNavigation().isIdle() || this.canStart();
            }
        }

        public void start() {
            this.fireballsFired = 0;
        }

        public void stop() {
            this.target = null;
            this.scorpion.setFireActive(false);
            this.scorpion.getNavigation().stop();
        }

        public boolean shouldRunEveryTick() {
            return true;
        }

        public void tick() {
            --this.fireballCooldown;
            LivingEntity livingEntity = this.scorpion.getTarget();
            if (livingEntity != null) {
                boolean bl = this.scorpion.getVisibilityCache().canSee(livingEntity);
                if (bl) {
                    this.targetNotVisibleTicks = 0;
                } else {
                    ++this.targetNotVisibleTicks;
                }

                double d = this.scorpion.squaredDistanceTo(livingEntity);
                if (d < 4.0) {
                    if (!bl) {
                        return;
                    }

                    if (this.fireballCooldown <= 0) {
                        this.fireballCooldown = 20;
                        if (ScorpionEntity.this.getBoundingBox().expand(3.20000000298023224).intersects(livingEntity.getBoundingBox())) {
                            this.scorpion.tryAttack(livingEntity);
                        }
                    }

                    this.scorpion.getMoveControl().moveTo(livingEntity.getX(), livingEntity.getY(), livingEntity.getZ(), 1.5);
                } else if (d < this.getFollowRange() * this.getFollowRange() && bl) {
                    double e = livingEntity.getX() - this.scorpion.getX();
                    double f = livingEntity.getBodyY(0.5) - this.scorpion.getBodyY(0.5);
                    double g = livingEntity.getZ() - this.scorpion.getZ();
                    if (this.fireballCooldown <= 0) {
                        ++this.fireballsFired;
                        if (this.fireballsFired == 1) {
                            this.fireballCooldown = 60;
                            this.scorpion.getLookControl().lookAt(livingEntity, 30.0F, 30.0F);
                            double j = (double)(this.scorpion.getWidth() * 3.5F * this.scorpion.getWidth() * 3.5F);
                            double k = this.scorpion.squaredDistanceTo(livingEntity.getX(), livingEntity.getY(), livingEntity.getZ());
                            double m = 0.8;
                            if (k > j && k < 16.0) {
                                m = 1.33;
                            } else if (e < 225.0) {
                                m = 0.6;
                            }
                            this.scorpion.getNavigation().startMovingTo(livingEntity, m);
                            this.fireballCooldown = Math.max(this.fireballCooldown - 1, 0);
                            if (ScorpionEntity.this.getBoundingBox().expand(3.20000000298023224).intersects(livingEntity.getBoundingBox())) {
                                this.scorpion.tryAttack(livingEntity);
                            }
                            if (!(k > j)) {
                                if (this.fireballCooldown <= 0) {
                                    this.fireballCooldown = 20;
                                }
                            }
                        } else if (this.fireballsFired <= 4) {
                            this.fireballCooldown = 6;
                            this.scorpion.getLookControl().lookAt(livingEntity, 30.0F, 30.0F);
                            double j = (double)(this.scorpion.getWidth() * 3.5F * this.scorpion.getWidth() * 3.5F);
                            double k = this.scorpion.squaredDistanceTo(livingEntity.getX(), livingEntity.getY(), livingEntity.getZ());
                            double m = 0.8;
                            if (k > j && k < 16.0) {
                                m = 1.33;
                            } else if (e < 225.0) {
                                m = 0.6;
                            }
                            this.scorpion.getNavigation().startMovingTo(livingEntity, m);
                            this.fireballCooldown = Math.max(this.fireballCooldown - 1, 0);
                            if (ScorpionEntity.this.getBoundingBox().expand(3.20000000298023224).intersects(livingEntity.getBoundingBox())) {
                                this.scorpion.tryAttack(livingEntity);
                            }
                            if (!(k > j)) {
                                if (this.fireballCooldown <= 0) {
                                    this.fireballCooldown = 20;
                                }
                            }
                        } else {
                            this.fireballCooldown = 100;
                            this.fireballsFired = 0;
                            this.scorpion.getLookControl().lookAt(livingEntity, 30.0F, 30.0F);
                            double j = (double)(this.scorpion.getWidth() * 3.5F * this.scorpion.getWidth() * 3.5F);
                            double k = this.scorpion.squaredDistanceTo(livingEntity.getX(), livingEntity.getY(), livingEntity.getZ());
                            double m = 0.8;
                            if (k > j && k < 16.0) {
                                m = 1.33;
                            } else if (e < 225.0) {
                                m = 0.6;
                            }
                            this.scorpion.getNavigation().startMovingTo(livingEntity, m);
                            this.fireballCooldown = Math.max(this.fireballCooldown - 1, 0);
                            if (ScorpionEntity.this.getBoundingBox().expand(3.20000000298023224).intersects(livingEntity.getBoundingBox())) {
                                this.scorpion.tryAttack(livingEntity);
                            }
                            if (!(k > j)) {
                                if (this.fireballCooldown <= 0) {
                                    this.fireballCooldown = 20;
                                }
                            }

                        }

                        if (this.fireballsFired > 1) {
                            double h = Math.sqrt(Math.sqrt(d)) * 0.5;
                            if (!this.scorpion.isSilent()) {
                                this.scorpion.world.syncWorldEvent((PlayerEntity)null, 1018, this.scorpion.getBlockPos(), 0);
                            }

                            for(int i = 0; i < 1; ++i) {
                                SmallFireballEntity smallFireballEntity = new SmallFireballEntity(this.scorpion.world, this.scorpion, this.scorpion.getRandom().nextTriangular(e, 2.297 * h), f, this.scorpion.getRandom().nextTriangular(g, 2.297 * h));
                                smallFireballEntity.setPosition(smallFireballEntity.getX(), this.scorpion.getBodyY(0.5) + 0.5, smallFireballEntity.getZ());
                                this.scorpion.world.spawnEntity(smallFireballEntity);
                            }
                        }
                    }

                    this.scorpion.getLookControl().lookAt(livingEntity, 10.0F, 10.0F);
                } else if (this.targetNotVisibleTicks < 5) {
                    this.scorpion.getMoveControl().moveTo(livingEntity.getX(), livingEntity.getY(), livingEntity.getZ(), 1.5);
                }
                super.tick();
            }
        }

        private double getFollowRange() {
            return this.scorpion.getAttributeValue(EntityAttributes.GENERIC_FOLLOW_RANGE);
        }
    }

    private void updateFloating() {
        if (this.isInLava()) {
            ShapeContext shapeContext = ShapeContext.of(this);
            if (shapeContext.isAbove(FluidBlock.COLLISION_SHAPE, this.getBlockPos(), true) && !this.world.getFluidState(this.getBlockPos().up()).isIn(FluidTags.LAVA)) {
                this.onGround = true;
            } else {
                this.setVelocity(this.getVelocity().multiply(0.5).add(0.0, 0.05, 0.0));
            }
        }

    }

    public boolean tryAttack(Entity target) {
        if (!super.tryAttack(target)) {
            return false;
        } else {
            if (target instanceof LivingEntity) {
                ((LivingEntity)target).addStatusEffect(new StatusEffectInstance(StatusEffects.WITHER, 200), this);
            }

            return true;
        }
    }

    public boolean canWalkOnFluid(FluidState state) {
        return state.isIn(FluidTags.LAVA);
    }

    protected ParticleEffect getParticles() {
        return ParticleTypes.FLAME;
    }

    @Override
    public AnimationFactory getFactory() {
        return factory;
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.ENTITY_SPIDER_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.ENTITY_SPIDER_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_SPIDER_DEATH;
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState state) {
        this.playSound(this.isInLava() ? SoundEvents.ENTITY_STRIDER_STEP_LAVA : SoundEvents.ENTITY_SPIDER_STEP, 0.15f, 0.4f);
    }
}