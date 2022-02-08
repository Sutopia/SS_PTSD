package org.sutopia.starsector.mod.ptsd;

import java.awt.Color;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.combat.BaseHullMod;
import com.fs.starfarer.api.combat.ShipAPI;
import com.fs.starfarer.api.combat.ShipAPI.HullSize;
import com.fs.starfarer.api.impl.campaign.ids.HullMods;
import com.fs.starfarer.api.impl.campaign.ids.Stats;
import com.fs.starfarer.api.impl.combat.PhaseCloakStats;
import com.fs.starfarer.api.ui.Alignment;
import com.fs.starfarer.api.ui.TooltipMakerAPI;

public class TemporalShunt extends BaseHullMod {
	@Override
	public String getDescriptionParam(int index, HullSize hullSize) {
		switch (index) {
			//case 0: return String.format("+%d%%", Math.round(PhaseCloakStatsForTemporalShunt.MANEUVERABILITY_BONUS));
			case 0:	return String.format("%d%%", Math.round(PhaseCloakStatsForTemporalShunt.PTSD_NO_PENALTY_FLUX_LEVEL * 100f));
			case 1:	return String.format("%d%%", Math.round(
						(PhaseCloakStatsForTemporalShunt.PTSD_NO_PENALTY_FLUX_LEVEL + PhaseCloakStats.BASE_FLUX_LEVEL_FOR_MIN_SPEED)
						* 100f));
		}
		return "You should not see this";
	}
	
	@Override
	public void addPostDescriptionSection(TooltipMakerAPI tooltip, HullSize hullSize, ShipAPI ship, float width, boolean isForModSpec) {
		if (ship == null || isForModSpec) return;
		
		tooltip.addSpacer(6f);
		tooltip.addSectionHeading("Current Phase Coil Stress Model", Alignment.MID, 0);
		tooltip.addSpacer(6f);
		if (ship.getVariant().hasHullMod(HullMods.SAFETYOVERRIDES)) {
			tooltip.addPara("No penalty regardless of flux level due to %s", 0f, Color.ORANGE, 
					Global.getSettings().getHullModSpec(HullMods.SAFETYOVERRIDES).getDisplayName());
		} else {
			tooltip.addPara("No penalty below %s hard flux level", 0f, Color.ORANGE, 
					String.format("%d%%", Math.round(PhaseCloakStatsForTemporalShunt.PTSD_NO_PENALTY_FLUX_LEVEL * 100f)));
			tooltip.addPara("Zero flux speed bonus is nullified at %s hard flux level", 0f, Color.ORANGE, 
					String.format("%d%%", Math.round(
							(PhaseCloakStatsForTemporalShunt.PTSD_NO_PENALTY_FLUX_LEVEL +
							ship.getMutableStats().getDynamic().getMod(
								Stats.PHASE_CLOAK_FLUX_LEVEL_FOR_MIN_SPEED_MOD).computeEffective(PhaseCloakStats.BASE_FLUX_LEVEL_FOR_MIN_SPEED)) 
							* 100f)));
		}
	}
}
