package edu.handong.analysis.datamodel;

import org.apache.commons.csv.CSVRecord;

public class Course {
	private String studentId;
	private String yearMonthGraduated;
	private String firstMajor;
	private String secondMajor;
	private String courseCode;
	private String courseName;
	private String courseCredit;
	private int yearTaken;
	private int semesterCourseTaken;

	
	public Course(CSVRecord line){
		
		studentId = line.get("StudentID");
		yearMonthGraduated = line.get("YearMonthGraduated");
		firstMajor = line.get("FistMajor");
		secondMajor = line.get("SecondMajor");
		courseCode = line.get("CourseCode");
		courseName = line.get("CourseName");
		courseCredit = line.get("CourseCredit");
		yearTaken = Integer.parseInt(line.get("YearTaken"));
		semesterCourseTaken = Integer.parseInt(line.get("SemesterTaken"));
	}


	public String getStudentId() {
		return studentId;
	}


	public String getYearMonthGraduated() {
		return yearMonthGraduated;
	}


	public String getFirstMajor() {
		return firstMajor;
	}


	public String getSecondMajor() {
		return secondMajor;
	}


	public String getCourseCode() {
		return courseCode;
	}


	public String getCourseName() {
		return courseName;
	}


	public String getCourseCredit() {
		return courseCredit;
	}


	public int getYearTaken() {
		return yearTaken;
	}


	public int getSemesterCourseTaken() {
		return semesterCourseTaken;
	}
	

}
