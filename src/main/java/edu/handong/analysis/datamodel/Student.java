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
	
	public Student(String studentId) {
		this.studentId= studentId;
		
	}
	
	
	public void addCourse(Course newRecord) {
		
		coursesTaken.add(newRecord);
	
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


	public Map<String, Integer> getSemesterByYearAndSemesterMap() {
		
		Map<String, Integer> semesterByYearAndSemesterMap = new TreeMap<String, Integer>(semestersByYearAndSemester); 
		
		return semesterByYearAndSemesterMap;
	}
	
}
