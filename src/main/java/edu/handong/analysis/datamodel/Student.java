package edu.handong.analysis.datamodel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class Student {
	
	private String studentId;
	private ArrayList<Course> coursesTaken = new ArrayList<Course>(); // List of courses student has taken
	private HashMap<String,Integer> semestersByYearAndSemester = new HashMap<String,Integer>();
	private int total;
	private int sYear;
	private int eYear;
	
	public Student(String studentId, int startYear, int endYear) {
		this.studentId= studentId;
		this.sYear = startYear;
		this.eYear = endYear;
		
	}
	
	
	public void addCourse(Course newRecord) {
		
		int year = newRecord.getYearTaken();
		
		if(year>=sYear && year<=eYear) {
			coursesTaken.add(newRecord);
		}
		
	}
	
	
	public HashMap<String,Integer> getSemestersByYearAndSemester(){
		
		
		for(Course get: coursesTaken) {
			
			
			int year = get.getYearTaken();
			int semester = get.getSemesterCourseTaken();
			String yearAndSemester = year + "-" + semester;
			
			if(semestersByYearAndSemester.containsKey(yearAndSemester)) {
				
			}else {
				semestersByYearAndSemester.put(yearAndSemester, ++total);
			}
		}
		
		return semestersByYearAndSemester;
		
	}
	



	
	public int getNumCourseInNthSementer(int semester) {
		int courseNum = 0;	
			for(String key : semestersByYearAndSemester.keySet()) {
				if(semestersByYearAndSemester.get(key) == semester) {
					String[] sepa = key.split("-");
					int courseYear = Integer.parseInt(sepa[0]);
					int courseSemester= Integer.parseInt(sepa[1]);
					
					for(Course data:coursesTaken) {
						if(data.getYearTaken() == courseYear && data.getSemesterCourseTaken() == courseSemester) {
							
							courseNum++;
							
						}
				}
			}
		
		}
		
		return courseNum;
	

	}
	
	public int getTotal() {
		
		return total;
		
	}


	public String getStudentId() {
		return studentId;
	}
	
	public ArrayList<Course> getCoursesTaken(){
		return coursesTaken;
	}


	public Map<String, Integer> getSemesterByYearAndSemesterMap() {
		
		Map<String, Integer> semesterByYearAndSemesterMap = new TreeMap<String, Integer>(semestersByYearAndSemester); 
		
		return semesterByYearAndSemesterMap;
	}
	
}
