import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Time;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class force_demo {
	
	public static void main(String[] args) {
		readFileAndPrint("d:\\1.working_time.log");
	}
	
	public static void readFileAndPrint(String fileName) {
		FileReader fileReader = null;
		BufferedReader bufferedReader = null;
		int count=0;
		try {
			fileReader = new FileReader(fileName);
			bufferedReader = new BufferedReader(new FileReader(fileName));
			String line;
			while ((line = bufferedReader.readLine()) != null) {
				String data = line;
				String[] splitData = data.split("\\|");
				
				Person a = new Person();
				a.name = splitData[0];
				a.startDate = splitData[1];
				
				if(splitData[2].length() == 0)a.startTime = null;
				else a.startTime = splitData[2];
				
				a.endDate = splitData[3];
				try{
					a.endTime = splitData[4];
				}catch(ArrayIndexOutOfBoundsException e){
					a.endTime = null;
				}
				
				System.out.println(++count+". "+a.getName());
				
				if(a.getStartTime()==null||a.getStartTime()==null){
					System.out.print("àÃÔèÁ·Ó§Ò¹ ");
					System.out.println(a.getStartDateTime());
					//System.out.println("äÁèÁÕ¢éÍÁÙÅàÇÅÒàÃÔèÁ§Ò¹ ");
					System.out.print("ÍÍ¡¨Ò¡§Ò¹ ");
					System.out.println(a.getEndDateTime());
					//System.out.println("äÁèÁÕ¢éÍÁÙÅàÇÅÒÍÍ¡¨Ò¡§Ò¹ ");
					System.out.println("äÁè·ÃÒºàÇÅÒ¡ÒÃ·Ó§Ò¹");
				}else{
					showTime(a.getStartDateTime(),a.getEndDateTime(),a);
				}
				
				System.out.println();
				
				
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (bufferedReader != null) {
				try {
					bufferedReader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (fileReader != null) {
				try {
					fileReader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
	}
	
	public static void showTime(String sDate,String eDate,Person p){
		SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		Date sdate = null,edate = null;
		try {
			sdate = df.parse(sDate);
			edate = df.parse(eDate);
			System.out.println("àÃÔèÁ·Ó§Ò¹ "+sDate+" ¹.");
			System.out.println("ÍÍ¡¨Ò¡§Ò¹ "+eDate+" ¹.");
			} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}
		lateTime(p,sdate,edate);
	}
	
	public static void lateTime(Person p,Date sdate,Date edate){
		SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		String limitTime = p.startDate+" 8:05";
		String endTime = p.startDate+" 17:00";
		String OTTime = p.startDate+" 17:30";
		//System.out.println("-->>>>"+limitTime);
		Date sTime = null,eTime = null,otTime = null;
		double force;
		long ot;
		try {
			sTime = df.parse(limitTime);
			eTime = df.parse(endTime);
			otTime = df.parse(OTTime);
			} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}
		if(holiday(sdate) == 1){
			System.out.println("ÇÑ¹ËÂØ´ ");
			force = 295 * 1.5;
		}else{
			System.out.println("ÇÑ¹»¡µÔ");
			force = 295;
		}
		
		int checkTimeoff;
		double otWork,total,work,worklate;
		DecimalFormat fn = new DecimalFormat("0.00");
		checkTimeoff = timeOff(sdate,edate,eTime,otTime);
		if(sdate.compareTo(sTime) > 0){
			System.out.println(p.startTime+" ÊÒÂ");
			worklate = diffDate(sTime,sdate);
			if(checkTimeoff == 1){
				if(worklate/60.0 > 8.0){
					System.out.println("·Ó§Ò¹ 0 ªÑèÇâÁ§ ¤èÒáÃ§ 0.00 ºÒ·");
				}else{
					work = 480 - worklate;
					total = work*force/480;
					System.out.println("·Ó§Ò¹ "+fn.format(work/60.0)+" ªÑèÇâÁ§ ¤èÒáÃ§ "+fn.format(total)+" ºÒ·");
				}
			}else if(checkTimeoff == 2){
				work = 480 - worklate;
				total = work*force/480;
				System.out.println("·Ó§Ò¹ "+fn.format(work/60.0)+" ªÑèÇâÁ§ ¤èÒáÃ§ "+fn.format(total)+" ºÒ·");
				
				ot = diffDate(otTime,edate);
				otWork = (force/480.0)*ot;
				System.out.println("·Ó§Ò¹¹Í¡àÇÅÒ(OT) "+fn.format(ot/60.0)+" ªÑèÇâÁ§ ¤èÒáÃ§ "+fn.format(otWork)+" ºÒ·");
				total = total+otWork;
				System.out.println("ÃÇÁà»ç¹à§Ô¹ "+fn.format(total)+" ºÒ·");
			}else if(checkTimeoff == 0){
				work = diffDate(edate,eTime);
				work = 480 - worklate - work;
				total = work*force/480;
				System.out.println("·Ó§Ò¹ "+fn.format(work/60.0)+" ªÑèÇâÁ§ ¤èÒáÃ§ "+fn.format(total)+" ºÒ·");
				
			}else{
				ot = diffDate(otTime,edate);
				otWork = (force/480.0)*ot;
				System.out.println("·Ó§Ò¹¹Í¡àÇÅÒ(OT) "+fn.format(ot/60.0)+" ªÑèÇâÁ§ ¤èÒáÃ§ "+fn.format(otWork)+" ºÒ·");
			}
		}else{
			//System.out.println(p.startTime+" äÁèÊÒÂ");
			if(checkTimeoff == 1){
				System.out.println("·Ó§Ò¹ 8 ªÑèÇâÁ§ ¤èÒáÃ§ "+force+" ºÒ·");
				//System.out.println("ÍÍ¡¨Ò¡§Ò¹»¡µÔ ¤èÒáÃ§ "+force+" ºÒ·");
			}else if(checkTimeoff == 2){
				System.out.print("·Ó§Ò¹ 8 ªÑèÇâÁ§");
				System.out.println(" ¤èÒáÃ§ "+force+" ºÒ·");
				ot = diffDate(otTime,edate);
				otWork = (force/480.0)*ot;
				System.out.println("·Ó§Ò¹¹Í¡àÇÅÒ(OT) "+fn.format(ot/60.0)+" ªÑèÇâÁ§ ¤èÒáÃ§ "+fn.format(otWork)+" ºÒ·");
				total = force+otWork;
				System.out.println("ÃÇÁà»ç¹à§Ô¹ "+fn.format(total)+" ºÒ·");
			}else if(checkTimeoff == 0){
				work = diffDate(sTime,edate);
				total = force - (work*force/480.0);
				System.out.println("ÍÍ¡¡èÍ¹àÇÅÒ ·Ó§Ò¹ "+fn.format(work/60.0)+" ªÑèÇâÁ§ ¤èÒáÃ§ "+fn.format(total)+" ºÒ·");
			}else{
				ot = diffDate(otTime,edate);
				otWork = (force/480.0)*ot;
				System.out.println("·Ó§Ò¹¹Í¡àÇÅÒ(OT) "+fn.format(ot/60.0)+" ªÑèÇâÁ§ ¤èÒáÃ§ "+fn.format(otWork)+" ºÒ·");
			}
		}
	}
	
	public static long diffDate(Date sdate,Date edate){
		long diff = edate.getTime()-sdate.getTime();
		diff = diff/(60*1000);
		return diff;
	}
	
	public static int timeOff(Date sdate,Date edate,Date eTime,Date otTime){
		if(edate.compareTo(otTime) >= 0&&sdate.compareTo(eTime) <= 0){
			return 2;
		}else if(edate.compareTo(eTime) >= 0&&sdate.compareTo(eTime) <= 0){
			return 1;
		}else if(edate.compareTo(otTime) >= 0){
			return -1;
		}else{
			return 0;
		}
	}
	
	public static int holiday(Date sdate){
		//System.out.println(sdate.getDay());
		if(sdate.getDay()==6||sdate.getDay()==7){
			return 1;
		}else{
			return 0;
		}
	}
	
}
