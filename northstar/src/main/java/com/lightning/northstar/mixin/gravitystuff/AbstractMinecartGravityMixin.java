package com.lightning.northstar.mixin.gravitystuff;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.lightning.northstar.world.dimension.NorthstarDimensions;

import net.minecraft.world.entity.vehicle.AbstractMinecart;
import net.minecraft.world.phys.Vec3;

@Mixin(AbstractMinecart.class)
public class AbstractMinecartGravityMixin {
    @Unique
    private static final double CONSTANT = 0.08;
    private static final double EARTH_GRAV = 1;
    private static final double MOON_GRAV = 0.64;
    private static final double MARS_GRAV = 0.65;
    private static final double VENUS_GRAV = 0.92;
    private static final double MERCURY_GRAV = 0.65;
    private static final double ORBIT_GRAV = 0.65;
    double PLANET_GRAV = 1;
    
   
    	//help

    @Inject(method = "tick", at = @At("TAIL"))
    public void northstar$travel(CallbackInfo ci) {
        AbstractMinecart entity = (AbstractMinecart) (Object) this;
        Vec3 velocity = entity.getDeltaMovement();
        if (entity.getLevel().dimension() == NorthstarDimensions.MARS_DIM_KEY)
        {PLANET_GRAV = MARS_GRAV;}else 
        if (entity.getLevel().dimension() == NorthstarDimensions.MOON_DIM_KEY)
        {PLANET_GRAV = MOON_GRAV;}else 
        if (entity.getLevel().dimension() == NorthstarDimensions.VENUS_DIM_KEY)
        {PLANET_GRAV = VENUS_GRAV;}else 
        if (entity.getLevel().dimension() == NorthstarDimensions.MERCURY_DIM_KEY)
        {PLANET_GRAV = MERCURY_GRAV;}else 
        if (entity.getLevel().dimension() == NorthstarDimensions.EARTH_ORBIT_DIM_KEY)
        {PLANET_GRAV = ORBIT_GRAV;}else 
        {PLANET_GRAV = EARTH_GRAV;}
        if (!entity.isNoGravity() && !entity.isInWater() && !entity.isInLava()) {
        	double newGrav = CONSTANT * PLANET_GRAV;
            entity.setDeltaMovement(velocity.x(), velocity.y() + CONSTANT - newGrav, velocity.z());
        }
    }
}