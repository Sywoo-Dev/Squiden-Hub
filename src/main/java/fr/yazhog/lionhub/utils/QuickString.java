package fr.yazhog.lionhub.utils;

import org.bukkit.ChatColor;

import com.google.common.base.Strings;

public class QuickString {
	
	public String getYesOrNot(boolean bool) {
		String str = "§aOui";
		if(!bool) {
			str = "§cNon";
		}
		return str;
	}
	
	public String getIfActivateER(boolean bool) {
		String str = "§aActiver";
		if(!bool) {
			str = "§cDésactiver";
		}
		return str;
	}
	
	public String getIfActivateEES(boolean bool) {
		String str = "§aActivées";
		if(!bool) {
			str = "§cDésactivées";
		}
		return str;
	}
	
	public String getIfActivateES(boolean bool) {
		String str = "§aActivés";
		if(!bool) {
			str = "§cDésactivés";
		}
		return str;
	}
	
	public String getIfActivateEE(boolean bool) {
		String str = "§aActivée";
		if(!bool) {
			str = "§cDésactivée";
		}
		return str;
	}
	
	public String getIfActivateE(boolean bool) {
		String str = "§aActivé";
		if(!bool) {
			str = "§cDésactivé";
		}
		return str;
	}
	
	  public String getProgressBar(int current, int max, int totalBars, char symbol, ChatColor completedColor,
	            ChatColor notCompletedColor) {
	        float percent = (float) current / max;
	        int progressBars = (int) (totalBars * percent);
	        if(percent > 100) return "";
	        return Strings.repeat("" + completedColor + symbol, progressBars)
	                + Strings.repeat("" + notCompletedColor + symbol, totalBars - progressBars);
	    }


}
