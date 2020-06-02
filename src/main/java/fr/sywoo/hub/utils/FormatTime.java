package fr.sywoo.hub.utils;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class FormatTime {
	
	private int sec, min, hour, day, month;
	
	public FormatTime(int seconds) {
		for(long i = 0; i < seconds; i++) {
			sec++;
			if(sec == 60) {
				sec = 0;
				min++;
			}
			if(min == 60) {
				hour++;
				min = 0;
			}
			if(hour == 24) {
				day++;
				hour = 0;
			}
			if(day == 30) {
				month++;
				day = 0;
			}
		}
	}
	
	public FormatTime(long seconds) {
		for(long i = 0; i < seconds; i++) {
			sec++;
			if(sec == 60) {
				sec = 0;
				min++;
			}
			if(min == 60) {
				hour++;
				min = 0;
			}
			if(hour == 24) {
				day++;
				hour = 0;
			}
			if(day == 30) {
				month++;
				day = 0;
			}
		}
	}
	
	
	public FormatTime(Date date) {
		if(date == null) {
			sec = 0;
			min = 0;
			hour = 0;
			day = 0;
			return;
		}
		LocalDateTime d = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
		Duration period = Duration.between(new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime(), d);
		System.out.println("Period = " + period.getSeconds());
		for(long i = 0; i < period.getSeconds(); i++) {
			sec++;
			if(sec == 60) {
				sec = 0;
				min++;
			}
			if(min == 60) {
				hour++;
				min = 0;
			}
			if(hour == 24) {
				day++;
				hour = 0;
			}
			if(day == 30) {
				month++;
				day = 0;
			}
		}
		
	}
	
	public FormatTime(Date date, Date date2) {
		if(date == null) {
			sec = 0;
			min = 0;
			hour = 0;
			day = 0;
			return;
		}
		LocalDateTime d = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
		Duration period = Duration.between(date2.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime(), d);
		System.out.println("Period = " + period.getSeconds());
		for(long i = 0; i < period.getSeconds(); i++) {
			sec++;
			if(sec == 60) {
				sec = 0;
				min++;
			}
			if(min == 60) {
				hour++;
				min = 0;
			}
			if(hour == 24) {
				day++;
				hour = 0;
			}
			if(day == 30) {
				month++;
				day = 0;
			}
		}
		
	}
	
	public FormatTime(int min, int sec) {
		this.sec = sec;
		this.min = min;
	}
	
	public String toString() {
		String output = "";
		
		if(month >= 1) { output += format(month) + ":"; }
		if(day >= 1) { output += format(day) + ":"; }
		if(hour >= 1) { output += format(hour) + ":"; }

		output += format(min) + ":";
		output += format(sec);

		return output;
	}
	
	public String toMDString() {
		String output = "";
		
		if(month >= 1) { output += format(month) + " Mois "; }
		if(day >= 1) { output += format(day) + " Jour(s) "; }

		if(min == 0 && sec == 0 && day == 0 && month == 0) {
			return "Jamais";
		}
		
		return output;
	}
	
	public String toCutString() {
		String output = "";
		
		int totalHour = hour;
		
		day += month*30;
		totalHour += day*24;
		
		output = format(totalHour) + "h" + format(min) + "min";
		return output;
	}
	
	public String toMSString() {
		String output = "";
		
		if(min >= 1) { output += format(day) + " Minute(s) "; }
		if(sec >= 1) { output += format(hour) + "Seconde(s) "; }

		if(min == 0 && sec == 0 && day == 0 && month == 0) {
			return "Jamais";
		}
		
		return output;	
	}
	
	public String toMDHString() {
		String output = "";
		
		if(month >= 1) { output += format(month) + " Mois "; }
		if(day >= 1) { output += format(day) + " Jour(s) "; }
		if(hour >= 1) { output += format(hour) + "Heure(s) "; }

		if(min == 0 && sec == 0 && day == 0 && month == 0) {
			return "Jamais";
		}
		
		return output;
	}
	
	public String toFormatString() {
		String output = "";
		
		if(month >= 1) { output += format(month) + " Mois "; }
		if(day >= 1) { output += format(day) + " Jour(s) "; }
		if(hour >= 1) { output += format(hour) + "Heure(s) "; }

		output += format(min) + " Minute(s) et ";
		output += format(sec) + " Seconde(s)";

		if(min == 0 && sec == 0 && day == 0 && month == 0) {
			return "Jamais";
		}
		
		return output;
	}
	
	private String format(int in) {
		String out = String.valueOf(in);
		if(in < 10) {
			out = "0" + String.valueOf(in);
		}
		
		return out;
	}

	public int getHour() {
		return hour;
	}

	public void setHour(int hour) {
		this.hour = hour;
	}

	public int getMin() {
		return min;
	}

	public void setMin(int min) {
		this.min = min;
	}

	public int getSec() {
		return sec;
	}

	public void setSec(int sec) {
		this.sec = sec;
	}

	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public int getDay() {
		return day;
	}

	public void setDay(int day) {
		this.day = day;
	}
	
}
