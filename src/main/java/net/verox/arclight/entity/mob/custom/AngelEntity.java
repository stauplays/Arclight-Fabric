package net.verox.arclight.entity.mob.custom;

import net.minecraft.entity.*;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.ai.control.MoveControl;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.boss.BossBar;
import net.minecraft.entity.boss.ServerBossBar;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.*;
import net.minecraft.entity.passive.CatEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ShulkerBulletEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.*;
import net.verox.arclight.entity.mob.EntityTypes;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class AngelEntity extends FlyingEntity implements IAnimatable, Monster {

    static {
        ATTACK = DataTracker.registerData(AngelEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    }

    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(ATTACK, false);
    }

    public boolean isAttacking() {
        return (Boolean)this.dataTracker.get(ATTACK);
    }

    public void setAttack(boolean attack) {
        this.dataTracker.set(ATTACK, attack);
    }

    private static final TrackedData<Boolean> ATTACK;

    private final ServerBossBar bossBar;
    
    public static final float field_30475 = 7.448451F;
    public static final int field_28641 = MathHelper.ceil(24.166098F);
    Vec3d targetPosition;
    BlockPos circlingCenter;
    AngelEntity.AngelMovementType movementType;
    
    public AnimationFactory factory = new AnimationFactory(this);

    public AngelEntity(EntityType<? extends FlyingEntity> entityType, World world) {
        super(entityType, world);
        this.bossBar = (ServerBossBar)(new ServerBossBar(this.getDisplayName(), BossBar.Color.YELLOW, BossBar.Style.PROGRESS)).setDarkenSky(true);
        this.movementType = AngelEntity.AngelMovementType.CIRCLE;
        this.targetPosition = Vec3d.ZERO;
        this.circlingCenter = BlockPos.ORIGIN;
        this.experiencePoints = 5000;
        this.moveControl = new AngelEntity.AngelMoveControl(this);
    }

    private AngelEntity( World level ){
        this(EntityTypes.ANGEL, level );
    }

    public static AngelEntity createAngelEntity( World level ){
        return new AngelEntity( level );
    }

    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        if (this.hasCustomName()) {
            this.bossBar.setName(this.getDisplayName());
        }

    }

    public void addFlapEffects() {
        if (this.world.isClient && !this.isSilent()) {
            this.world.playSound(this.getX(), this.getY(), this.getZ(), SoundEvents.ENTITY_ENDER_DRAGON_FLAP, this.getSoundCategory(), 2.0F, 1.2F + this.random.nextFloat() * 0.3F, false);
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
        this.bossBar.setPercent(this.getHealth() / this.getMaxHealth());
    }

    public EntityGroup getGroup() {
        return EntityGroup.DEFAULT;
    }

    private static enum AngelMovementType {
        CIRCLE,
        SWOOP;
        private AngelMovementType() {
        }
    }

    class AngelMoveControl extends MoveControl {
        private float targetSpeed = 1.0F;

        public AngelMoveControl(MobEntity owner) {
            super(owner);
        }

        public void tick() {
            if (AngelEntity.this.horizontalCollision) {
                AngelEntity.this.setYaw(AngelEntity.this.getYaw() + 180.0F);
                this.targetSpeed = 1.5F;
            }

            double d = AngelEntity.this.targetPosition.x - AngelEntity.this.getX();
            double e = AngelEntity.this.targetPosition.y - AngelEntity.this.getY();
            double f = AngelEntity.this.targetPosition.z - AngelEntity.this.getZ();
            double g = Math.sqrt(d * d + f * f);
            if (Math.abs(g) > 9.999999747378752E-6) {
                double h = 1.0 - Math.abs(e * 0.699999988079071) / g;
                d *= h;
                f *= h;
                g = Math.sqrt(d * d + f * f);
                double i = Math.sqrt(d * d + f * f + e * e);
                float j = AngelEntity.this.getYaw();
                float k = (float)MathHelper.atan2(f, d);
                float l = MathHelper.wrapDegrees(AngelEntity.this.getYaw() + 90.0F);
                float m = MathHelper.wrapDegrees(k * 57.295776F);
                AngelEntity.this.setYaw(MathHelper.stepUnwrappedAngleTowards(l, m, 4.0F) - 90.0F);
                AngelEntity.this.bodyYaw = AngelEntity.this.getYaw();
                if (MathHelper.angleBetween(j, AngelEntity.this.getYaw()) < 3.0F) {
                    this.targetSpeed = MathHelper.stepTowards(this.targetSpeed, 1.0F, 0.005F * (3.5F / this.targetSpeed));
                } else {
                    this.targetSpeed = MathHelper.stepTowards(this.targetSpeed, 1.3F, 0.025F);
                }

                float n = (float)(-(MathHelper.atan2(-e, g) * 57.2957763671875));
                AngelEntity.this.setPitch(n);
                float o = AngelEntity.this.getYaw() + 90.0F;
                double p = (double)(this.targetSpeed * MathHelper.cos(o * 0.017453292F)) * Math.abs(d / i);
                double q = (double)(this.targetSpeed * MathHelper.sin(o * 0.017453292F)) * Math.abs(f / i);
                double r = (double)(this.targetSpeed * MathHelper.sin(n * 0.017453292F)) * Math.abs(e / i);
                Vec3d vec3d = AngelEntity.this.getVelocity();
                AngelEntity.this.setVelocity(vec3d.add((new Vec3d(p, r, q)).subtract(vec3d).multiply(0.2)));
            }

        }
    }
    public static DefaultAttributeContainer.Builder setAttributes() {
        return FlyingEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 500.0D)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 65.0f)
                .add(EntityAttributes.GENERIC_ATTACK_SPEED, 3.0f)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 3.5f);
    }
    protected void initGoals() {
        super.initGoals();
        this.goalSelector.add(1, new AngelEntity.StartAttackGoal());
        this.goalSelector.add(2, new AngelEntity.SwoopMovementGoal());
        this.goalSelector.add(3, new AngelEntity.CircleMovementGoal());
        this.targetSelector.add(0, new AngelEntity.FindTargetGoal());
    }

    private <E extends IAnimatable> PlayState predicate1(AnimationEvent<E> event) {
        if (event.isMoving() && !AngelEntity.this.isAttacking()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("move", true));
            return PlayState.CONTINUE;
        }

        if (!event.isMoving()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("idle", true));
            return PlayState.CONTINUE;
        }
        return PlayState.STOP;
    }

    private <E extends IAnimatable> PlayState predicate2(AnimationEvent<E> event) {
        if (AngelEntity.this.isAttacking()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("attack", true));
            return PlayState.CONTINUE;
        }
        return PlayState.STOP;
    }


    @Override
    public void registerControllers(AnimationData animationData) {
        AnimationController<AngelEntity> controller = new AnimationController(this, "controller",
                5, this::predicate1);
        AnimationController<AngelEntity> attack = new AnimationController(this, "attack",
                5, this::predicate2);
        animationData.addAnimationController(controller);
        animationData.addAnimationController(attack);

    }

    private class SwoopMovementGoal extends AngelEntity.MovementGoal {
        public int attacks;
        private static final int CAT_CHECK_INTERVAL = 20;
        private boolean catsNearby;
        private int nextCatCheckAge;

        SwoopMovementGoal() {
            super();
        }

        public boolean canStart() {
            return AngelEntity.this.getTarget() != null && AngelEntity.this.movementType == AngelEntity.AngelMovementType.SWOOP;
        }


        public boolean shouldContinue() {
            LivingEntity livingEntity = AngelEntity.this.getTarget();
            if (livingEntity == null) {
                return false;
            } else if (!livingEntity.isAlive()) {
                return false;
            } else {
                if (livingEntity instanceof PlayerEntity) {
                    PlayerEntity playerEntity = (PlayerEntity)livingEntity;
                    if (livingEntity.isSpectator() || playerEntity.isCreative()) {
                        return false;
                    }
                }

                if (!this.canStart()) {
                    return false;
                } else {
                    if (AngelEntity.this.age > this.nextCatCheckAge) {
                        this.nextCatCheckAge = AngelEntity.this.age + 20;
                        List<CatEntity> list = AngelEntity.this.world.getEntitiesByClass(CatEntity.class, AngelEntity.
                                this.getBoundingBox().expand(16.0), EntityPredicates.VALID_ENTITY);
                        Iterator var3 = list.iterator();

                        while(var3.hasNext()) {
                            CatEntity catEntity = (CatEntity)var3.next();
                            catEntity.hiss();
                        }

                        this.catsNearby = !list.isEmpty();
                    }

                    return !this.catsNearby;
                }
            }
        }

        public void start() {
            this.attacks = 0;
            this.attacks = this.getTickCount(10);
        }

        public void stop() {
            AngelEntity.this.setTarget((LivingEntity)null);
            AngelEntity.this.movementType = AngelEntity.AngelMovementType.CIRCLE;
            if (this.attacks > 35) {
                AngelEntity.this.setAttack(false);
            }
        }

        public void tick() {
            LivingEntity livingEntity = AngelEntity.this.getTarget();
            if (livingEntity != null) {
                AngelEntity.this.targetPosition = new Vec3d(livingEntity.getX(), livingEntity.getBodyY(0.5), livingEntity.getZ());
                ++this.attacks;
                if (AngelEntity.this.getBoundingBox().expand(2.20000000298023224).intersects(livingEntity.getBoundingBox())) {
                    AngelEntity.this.tryAttack(livingEntity);
                    AngelEntity.this.movementType = AngelEntity.AngelMovementType.CIRCLE;
                    if (!AngelEntity.this.isSilent()) {
                        AngelEntity.this.world.syncWorldEvent(1039, AngelEntity.this.getBlockPos(), 0);
                    }
                } else if (AngelEntity.this.horizontalCollision || AngelEntity.this.hurtTime > 0) {
                    AngelEntity.this.movementType = AngelEntity.AngelMovementType.CIRCLE;
                }
                AngelEntity.this.setAttack(this.attacks > 25);

            }
        }
    }

    class StartAttackGoal extends Goal {
        private int cooldown;

        StartAttackGoal() {
        }

        public boolean canStart() {
            LivingEntity livingEntity = AngelEntity.this.getTarget();
            if (livingEntity != null && livingEntity.isAlive()) {
                return AngelEntity.this.world.getDifficulty() != Difficulty.PEACEFUL;
            } else {
                return livingEntity != null ? AngelEntity.this.isTarget(livingEntity, TargetPredicate.DEFAULT) : false;
            }
        }

        public void start() {
            this.cooldown = this.getTickCount(5);
            //this.cooldown = 0;
            AngelEntity.this.movementType = AngelEntity.AngelMovementType.CIRCLE;
            this.startSwoop();
        }

        public void stop() {
            AngelEntity.this.circlingCenter = AngelEntity.this.world.getTopPosition(Heightmap.Type.MOTION_BLOCKING, AngelEntity.this.circlingCenter).up(10 + AngelEntity.this.random.nextInt(20));
        }

        public void tick() {
            if (AngelEntity.this.movementType == AngelEntity.AngelMovementType.CIRCLE) {
                --this.cooldown;
                if (this.cooldown <= 0) {
                    AngelEntity.this.movementType = AngelEntity.AngelMovementType.SWOOP;
                    this.startSwoop();
                    this.cooldown = this.getTickCount((8 + AngelEntity.this.random.nextInt(4)) * 20);
                    AngelEntity.this.playSound(SoundEvents.ENTITY_WARDEN_ATTACK_IMPACT, 10.0F, 0.95F + AngelEntity.this.random.nextFloat() * 0.1F);
                } else if (this.cooldown <= 3) {
                    AngelEntity.this.world.spawnEntity(new ShulkerBulletEntity(AngelEntity.this.world, AngelEntity.this, getTarget(),
                            AngelEntity.this.getMovementDirection().getAxis()));
                    AngelEntity.this.playSound(SoundEvents.ENTITY_WITHER_SHOOT, 2.0F, (AngelEntity.this.random.nextFloat()
                            - AngelEntity.this.random.nextFloat()) * 0.2F + 1.0F);
                }
            }

        }

        private void startSwoop() {
            AngelEntity.this.circlingCenter = AngelEntity.this.getTarget().getBlockPos().up(20 + AngelEntity.this.random.nextInt(20));
            if (AngelEntity.this.circlingCenter.getY() < AngelEntity.this.world.getSeaLevel()) {
                AngelEntity.this.circlingCenter = new BlockPos(AngelEntity.this.circlingCenter.getX(), AngelEntity.this.world.getSeaLevel() + 1, AngelEntity.this.circlingCenter.getZ());
            }

        }
    }

    class CircleMovementGoal extends AngelEntity.MovementGoal {
        private float angle;
        private float radius;
        private float yOffset;
        private float circlingDirection;

        CircleMovementGoal() {
            super();
        }

        public boolean canStart() {
            return AngelEntity.this.getTarget() == null || AngelEntity.this.movementType == AngelEntity.AngelMovementType.CIRCLE;
        }

        public void start() {
            this.radius = 5.0F + AngelEntity.this.random.nextFloat() * 10.0F;
            this.yOffset = -4.0F + AngelEntity.this.random.nextFloat() * 9.0F;
            this.circlingDirection = AngelEntity.this.random.nextBoolean() ? 1.0F : -1.0F;
            this.adjustDirection();
        }

        public void tick() {
            if (AngelEntity.this.random.nextInt(this.getTickCount(350)) == 0) {
                this.yOffset = -4.0F + AngelEntity.this.random.nextFloat() * 9.0F;
            }

            if (AngelEntity.this.random.nextInt(this.getTickCount(250)) == 0) {
                ++this.radius;
                if (this.radius > 15.0F) {
                    this.radius = 5.0F;
                    this.circlingDirection = -this.circlingDirection;
                }
            }

            if (AngelEntity.this.random.nextInt(this.getTickCount(450)) == 0) {
                this.angle = AngelEntity.this.random.nextFloat() * 2.0F * 3.1415927F;
                this.adjustDirection();
            }

            if (this.isNearTarget()) {
                this.adjustDirection();
            }

            if (AngelEntity.this.targetPosition.y < AngelEntity.this.getY() && !AngelEntity.this.world.isAir(AngelEntity.this.getBlockPos().down(1))) {
                this.yOffset = Math.max(1.0F, this.yOffset);
                this.adjustDirection();
            }

            if (AngelEntity.this.targetPosition.y > AngelEntity.this.getY() && !AngelEntity.this.world.isAir(AngelEntity.this.getBlockPos().up(1))) {
                this.yOffset = Math.min(-1.0F, this.yOffset);
                this.adjustDirection();
            }

        }

        private void adjustDirection() {
            if (BlockPos.ORIGIN.equals(AngelEntity.this.circlingCenter)) {
                AngelEntity.this.circlingCenter = AngelEntity.this.getBlockPos();
            }

            this.angle += this.circlingDirection * 15.0F * 0.017453292F;
            AngelEntity.this.targetPosition = Vec3d.of(AngelEntity.this.circlingCenter).add((double)(this.radius * MathHelper.cos(this.angle)), (double)(-4.0F + this.yOffset), (double)(this.radius * MathHelper.sin(this.angle)));
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
                List<PlayerEntity> list = AngelEntity.this.world.getPlayers(this.PLAYERS_IN_RANGE_PREDICATE, AngelEntity.this, AngelEntity.this.getBoundingBox().expand(16.0, 64.0, 16.0));
                if (!list.isEmpty()) {
                    list.sort(Comparator.comparing(Entity::getY).reversed());
                    Iterator var2 = list.iterator();
    
                    while(var2.hasNext()) {
                        PlayerEntity playerEntity = (PlayerEntity)var2.next();
                        if (AngelEntity.this.isTarget(playerEntity, TargetPredicate.DEFAULT)) {
                            AngelEntity.this.setTarget(playerEntity);
                            return true;
                        }
                    }
                }
    
                return false;
            }
        }
    
        public boolean shouldContinue() {
            LivingEntity livingEntity = AngelEntity.this.getTarget();
            return livingEntity != null ? AngelEntity.this.isTarget(livingEntity, TargetPredicate.DEFAULT) : false;
        }
    }
    
    abstract class MovementGoal extends Goal {
        public MovementGoal() {
            this.setControls(EnumSet.of(Control.MOVE));
        }

        protected boolean isNearTarget() {
            return AngelEntity.this.targetPosition.squaredDistanceTo(AngelEntity.this.getX(), AngelEntity.this.getY(), AngelEntity.this.getZ()) < 4.0;
        }
    }

    @Override
    public AnimationFactory getFactory() {
        return factory;
    }

    protected boolean isDisallowedInPeaceful() {
        return true;
    }

    protected SoundEvent getAmbientSound() { return SoundEvents.ENTITY_WITHER_AMBIENT; }
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) { return SoundEvents.ENTITY_WITHER_HURT; }
    protected SoundEvent getDeathSound() { return SoundEvents.ENTITY_WITHER_DEATH; }
    protected SoundEvent getStepSound() { return SoundEvents.ENTITY_ENDER_DRAGON_FLAP; }
    protected float getSoundValue() { return 1.0F; }

}
