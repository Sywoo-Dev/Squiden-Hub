package fr.sywoo.hub.nightclub;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.Location;

import fr.sywoo.hub.nightclub.effects.BoumEffect;
import fr.sywoo.hub.nightclub.effects.CircleEffect;
import fr.sywoo.hub.nightclub.effects.CylinderEffect;
import fr.sywoo.hub.nightclub.effects.DanceFloorEffect;
import fr.sywoo.hub.nightclub.effects.Effect;
import fr.sywoo.hub.nightclub.effects.ParticleEffect;
import fr.sywoo.hub.nightclub.effects.SkyEffect;
import fr.sywoo.hub.nightclub.effects.projectors.Projector;
import fr.sywoo.hub.nightclub.effects.projectors.ProjectorEffect;
import fr.sywoo.hub.nightclub.effects.projectors.RandomEffect;
import fr.sywoo.hub.nightclub.effects.projectors.RandomSmoothEffect;
import fr.sywoo.hub.utils.Cuboid;
import net.minecraft.server.v1_8_R3.EnumParticle;

public class NightClub {

	public List<Projector> projectors = new ArrayList<>();
	public List<Location> pillars = new ArrayList<>();
	public List<Location> projectorsLocation = new ArrayList<>();
	//GENERAL
	public Set<Effect> effects = new HashSet<>();
	public Set<Effect> enabledEffects = new HashSet<>();
	//PROJECTEURS
	public Set<ProjectorEffect> projectorsEffects = new HashSet<>();
	public Set<ProjectorEffect> enabledProjectorsEffects = new HashSet<>();
	//EFFETS
	public Set<Effect> effectS = new HashSet<>();
	public Set<Effect> enabledEffectS = new HashSet<>();

	public Location button;

	public NightClub() {
		button = new Location(Bukkit.getWorld("world"), 32, 41, 56);

		this.projectorsLocation.add(new Location(Bukkit.getWorld("world"), 42.5D, 44.0D, 36.5D));
		this.projectorsLocation.add(new Location(Bukkit.getWorld("world"), 22.5D, 44.0D, 36.5D));
		this.projectorsLocation.add(new Location(Bukkit.getWorld("world"), 36.5D, 44.0D, 50.5D));
		this.projectorsLocation.add(new Location(Bukkit.getWorld("world"), 28.5D, 44.0D, 50.5D));
		this.projectorsLocation.add(new Location(Bukkit.getWorld("world"), 42.5D, 44.0D, 50.5D));
		this.projectorsLocation.add(new Location(Bukkit.getWorld("world"), 42.5D, 44.0D, 43.5D));
		this.projectorsLocation.add(new Location(Bukkit.getWorld("world"), 22.5D, 44.0D, 43.5D));
		this.projectorsLocation.add(new Location(Bukkit.getWorld("world"), 22.5D, 44.0D, 50.5D));
		for (Location loc : projectorsLocation) {
			this.projectors.add(new Projector(loc));
		}
		
		pillars.add(new Location(button.getWorld(), 39.5D, 34.0D, 38.5D));
		pillars.add(new Location(button.getWorld(), 39.5D, 34.0D, 42.5D));
		pillars.add(new Location(button.getWorld(), 39.5D, 34.0D, 47.5D));
		pillars.add(new Location(button.getWorld(), 34.5D, 34.0D, 47.5D));
		pillars.add(new Location(button.getWorld(), 30.5D, 34.0D, 47.5D));
		pillars.add(new Location(button.getWorld(), 25.5D, 34.0D, 47.5D));
		pillars.add(new Location(button.getWorld(), 25.5D, 34.0D, 42.5D));
		pillars.add(new Location(button.getWorld(), 25.5D, 34.0D, 38.5D));
		
		SkyEffect skyEffect = new SkyEffect(this);
		ParticleEffect particleEffect = new ParticleEffect(this, EnumParticle.CLOUD);
		CylinderEffect cylinderEffect = new CylinderEffect(this, pillars);
		CircleEffect circleEffect = new CircleEffect(this, 10, getCenter(), EnumParticle.FLAME);
		BoumEffect boumEffect = new BoumEffect(this);
		RandomEffect randomEffect = new RandomEffect(this);
		DanceFloorEffect danceFloorEffect = new DanceFloorEffect(this);
		RandomSmoothEffect randomSmoothEffect = new RandomSmoothEffect(this);
		
		effects.add(skyEffect);
		effects.add(particleEffect);
		effects.add(cylinderEffect);
		effects.add(circleEffect);
		effects.add(boumEffect);
		effects.add(danceFloorEffect);
		effects.add(randomEffect);
		effects.add(randomSmoothEffect);
		
		effectS.add(skyEffect);
		effectS.add(particleEffect);
		effectS.add(cylinderEffect);
		effectS.add(circleEffect);
		effectS.add(danceFloorEffect);
		effectS.add(boumEffect);
		

		projectorsEffects.add(randomEffect);
		projectorsEffects.add(randomSmoothEffect);
	}

	
	public Location getCenter() {
		return new Location(Bukkit.getWorld("world"), 32.5, 34.0, 40.5);
	}

	public Cuboid getPiste() {
		return new Cuboid(new Location(Bukkit.getWorld("world"), 38, 38, 46),
				new Location(Bukkit.getWorld("world"), 26, 34, 34));
	}


	public Set<Effect> getEffects() {
		return effects;
	}
	
	public Set<Effect> getEnabledEffects() {
		return enabledEffects;
	}
	
	public Set<Effect> getEffectS() {
		return effectS;
	}
	
	public Set<Effect> getEnabledEffectS() {
		return enabledEffectS;
	}
	
	public Set<ProjectorEffect> getProjectorEffect() {
		return projectorsEffects;
	}
	
	public Set<ProjectorEffect> getEnabledProjectorEffect() {
		return enabledProjectorsEffects;
	}
	

	public Location getButton() {
		return button;
	}

	public List<Projector> getProjectors() {
		return projectors;
	}

	public void enableEffect(Effect effect) {
		this.enabledEffects.add(effect);
		if(this.projectorsEffects.contains(effect)) {
			this.enabledProjectorsEffects.add((ProjectorEffect) effect);
		}else if(this.effectS.contains(effect)) {
			this.enabledEffectS.add(effect);
		}
	}

	public void disableEffect(Effect effect) {
		this.enabledEffects.remove(effect);
		if(this.projectorsEffects.contains(effect)) {
			this.enabledProjectorsEffects.remove((ProjectorEffect) effect);
			checkProjectors();
		}else if(this.effectS.contains(effect)) {
			this.enabledEffectS.remove(effect);
		}
	}
	
	public void checkProjectors() {
		if(this.enabledProjectorsEffects.size() == 0) {
			for(Projector proj : projectors) {
				proj.reset();
			}
		}
	}

	public boolean isEffectEnabled(Effect effect) {
		return enabledEffects.contains(effect);
	}
}
