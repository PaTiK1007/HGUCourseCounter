package edu.handong.analysis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import edu.handong.analysis.datamodel.Course;
import edu.handong.analysis.datamodel.Student;
import edu.handong.analysis.utils.NotEnoughArgumentException;
import edu.handong.analysis.utils.Utils;


public class HGUCoursePatternAnalyzer {

	private HashMap<String,Student> students = new HashMap<String, Student>();
	
	/**
	 * This method runs our analysis logic to save the number courses taken by each student per semester in a result file.
	 * Run method must not be changed!!
	 * @param args
	 */
	public void run(String[] args) {
		
		try {
			// when there are not enough arguments from CLI, it throws the NotEnoughArgmentException which must be defined by you.
			if(args.length<2)
				throw new NotEnoughArgumentException();
		} catch (NotEnoughArgumentException e) {
			System.out.println(e.getMessage());
			System.exit(0);
		}
		
		String dataPath = args[0]; // csv file to be analyzed
		String resultPath = args[1]; // the file path where the results are saved.
		ArrayList<String> lines = Utils.getLines(dataPath, true);
		
		students = loadStudentCourseRecords(lines);  // 학생들의 기록을 해쉬맵에 저장 KEY는 ID, VALUE는 학생의 기록-> COURSE에서 받은 것
		
		// To sort HashMap entries by key values so that we can save the results by student ids in ascending order.
		Map<String, Student> sortedStudents = new TreeMap<String,Student>(students); // 각 학생을 학기별로 정렬
		
		// Generate result lines to be saved.
		ArrayList<String> linesToBeSaved = countNumberOfCoursesTakenInEachSemester(sortedStudents); // 저장될 정보를 라인 단위로 받기
		
		// Write a file (named like the value of resultPath) with linesTobeSaved.
		Utils.writeAFile(linesToBeSaved, resultPath); // 저장되야하는 정보와 저장 경로를 받아서 저장시키기
	}
	
	/**
	 * This method create HashMap<String,Student> from the data csv file. Key is a student id and the corresponding object is an instance of Student.
	 * The Student instance have all the Course instances taken by the student.
	 * @param lines
	 * @return
	 */
	private HashMap<String,Student> loadStudentCourseRecords(ArrayList<String> lines) {
		
		
		HashMap<String, Student> hashMap = new HashMap<String, Student>();
		
		
		for(String line : lines) {
			
			Course course = new Course(line);
			Student read = new Student(course.getStudentId());
			
			
			if(hashMap.containsKey(read.getStudentId())) {
				
				hashMap.get(read.getStudentId()).addCourse(course);
				
			}else {
				
			read.addCourse(course);
			hashMap.put(read.getStudentId(),read);
			
			}
			
		}
		
		
		return hashMap; // do not forget to return a proper variable.
	}

	/**
	 * This method generate the number of courses taken by a student in each semester. The result file look like this:
	 * StudentID, TotalNumberOfSemestersRegistered, Semester, NumCoursesTakenInTheSemester
	 * 0001,14,1,9
     * 0001,14,2,8
	 * ....
	 * 
	 * 0001,14,1,9 => this means, 0001 student registered 14 semeters in total. In the first semeter (1), the student took 9 courses.
	 * 
	 * 
	 * @param sortedStudents
	 * @return
	 */
	private ArrayList<String> countNumberOfCoursesTakenInEachSemester(Map<String, Student> sortedStudents) {
		String line = null;
		ArrayList<String> printLine = new ArrayList<String>();
		
		for(String key:sortedStudents.keySet()) {
			sortedStudents.get(key).getSemestersByYearAndSemester();
		}
		
		printLine.add("StudentID, TotalNumberOfSemestersRegistered, Semester, NumCoursesTakenInTheSemester");
		
		
		
		for(String key:sortedStudents.keySet()) {
			for(String command: sortedStudents.get(key).getSemesterByYearAndSemesterMap().keySet()) {
				String id = sortedStudents.get(key).getStudentId();
				int registeredSemester = sortedStudents.get(key).getTotal();
				int semester = sortedStudents.get(key).getSemestersByYearAndSemester().get(command);
				int numCourses = sortedStudents.get(key).getNumCourseInNthSementer(semester);
				
				line = id + "," + registeredSemester + "," + semester + "," + numCourses;
				
				printLine.add(line);
			}
		}
		
		
		return printLine;
	}
}
