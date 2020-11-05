package com.legacy.aether.entities.hostile;

import com.legacy.aether.entities.EntityTypesAether;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.AxeItem;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.world.World;

public class EntityChestMimic extends HostileEntity {

    public float mouth, legs;

    private float legsDirection = 1;

    public EntityChestMimic(World world) {
        super(EntityTypesAether.CHEST_MIMIC, world);
    }

    @Override
    protected void initGoals() {
        super.initGoals();
        initAttributes();

        this.goalSelector.add(0, new SwimGoal(this));
        this.goalSelector.add(2, new MeleeAttackGoal(this, 1.0D, false));
        this.goalSelector.add(5, new GoToWalkTargetGoal(this, 1.0D));
        this.goalSelector.add(7, new WanderAroundFarGoal(this, 1.0D));
        this.targetSelector.add(1, new RevengeGoal(this));
        this.targetSelector.add(2, new FollowTargetGoal<PlayerEntity>(this, PlayerEntity.class, true));
    }

    protected void initAttributes() {
        this.getAttributeInstance(EntityAttributes.GENERIC_FOLLOW_RANGE).setBaseValue(8.0D);
        this.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED).setBaseValue(0.28000000417232513D);
        this.getAttributeInstance(EntityAttributes.GENERIC_ATTACK_DAMAGE).setBaseValue(3.0D);
        this.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH).setBaseValue(40.0D);
    }

    @Override
    public void tick() {
        super.tick();

        this.mouth = (float) ((Math.cos((float) this.age / 10F * 3.14159265F)) + 1F) * 0.6F;
        this.legs *= 0.9F;

        if (this.prevX - this.getX() != 0 || this.prevZ - this.getZ() != 0) {
            this.legs += legsDirection * 0.2F;

            if (this.legs > 1.0F) {
                this.legsDirection = -1;
            }

            if (this.legs < -1.0F) {
                this.legsDirection = 1;
            }
        } else {
            this.legs = 0.0F;
        }
    }

    @Override
    public boolean damage(DamageSource damageSource, float damage) {
        if (damageSource.getAttacker() instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) damageSource.getAttacker();

            if (player.getMainHandStack().getItem() instanceof AxeItem) {
                damage *= 1.25F;
            }
        }

        return super.damage(damageSource, damage);
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.BLOCK_WOOD_HIT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.BLOCK_CHEST_CLOSE;
    }

}