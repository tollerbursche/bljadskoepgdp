
public class Date {
private	int day;
private	int month;
private	int year;
	
public int getDay() {
		return day;
	}		
public int getMonth() {
		return month;
	}
public int getYear() {
		return year;
	}

	Date(int day, int month, int year){
		this.day=day;
		this.month=month;
		this.year=year;
		}
	public Date() {
		day=Terminal.TODAYS_DAY;
		month=Terminal.TODAYS_MONTH;
		year=Terminal.TODAYS_YEAR;
		
	}
	
	public String toString() {
		return "Today is" + this.day + "." + this.month + "." + this.year;
	}
}
