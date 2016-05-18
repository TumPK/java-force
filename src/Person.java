
public class Person {
	String name;
	String startDate;
	String startTime;
	String endDate;
	String endTime;
	
	public Person(){
		
	}
	public Person(String name,String startDate,String startTime,String endDate,String endTime){
		this.name = name;
		this.startDate = startDate;
		this.startTime = startTime;
		this.endDate = endDate;
		this.endTime = endTime;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	
	public String getStartDateTime(){
		return this.getStartDate()+" "+this.getStartTime();
	}
	
	public String getEndDateTime(){
		return this.getEndDate()+" "+this.getEndTime();
	}
}
