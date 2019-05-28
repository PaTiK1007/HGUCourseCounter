package edu.handong.analysis.datamodel;

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

	
	public Course(String line){
		
		studentId = line.trim().split(", ")[0];
		yearMonthGraduated = line.trim().split(", ")[1];
		firstMajor = line.trim().split(", ")[2];
		secondMajor = line.trim().split(", ")[3];
		courseCode = line.trim().split(", ")[4];
		courseName = line.trim().split(", ")[5];
		courseCredit = line.trim().split(", ")[6];
		yearTaken = Integer.parseInt(line.trim().split(", ")[7]);
		semesterCourseTaken = Integer.parseInt(line.trim().split(", ")[8]);
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
