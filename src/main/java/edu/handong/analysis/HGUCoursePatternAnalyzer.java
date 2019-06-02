package edu.handong.analysis;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.csv.*;

import edu.handong.analysis.datamodel.Course;
import edu.handong.analysis.datamodel.Student;
import edu.handong.analysis.utils.Utils;


public class HGUCoursePatternAnalyzer {

	private HashMap<String,Student> students = new HashMap<String, Student>();
	
	private String input;
	private String output;
	private String code;
	private String stYear;
	private String enYear;
	private String optA;
	private boolean help;
	private boolean c2 = false;
	
	
	
	
	/**
	 * This method runs our analysis logic to save the number courses taken by each student per semester in a result file.
	 * Run method must not be changed!!
	 * @param args
	 */
	public void run(String[] args) throws IOException{
		Options options = createOptions();
		ArrayList<String> linesToBeSaved = new ArrayList<String>();
		
		
		if(parseOptions(options, args)){
			if (help){
				printHelp(options);
				return;
			}
		}
		
		
		
		if(input != null && output !=null) {
			 
		CSVParser lines = Utils.getLines(input, true);
		
		students = loadStudentCourseRecords(lines);  // 학생들의 기록을 해쉬맵에 저장 KEY는 ID, VALUE는 학생의 기록-> COURSE에서 받은 것
		
		// To sort HashMap entries by key values so that we can save the results by student ids in ascending order.
		Map<String, Student> sortedStudents = new TreeMap<String,Student>(students); // 각 학생을 학기별로 정렬
		
		// Generate result lines to be saved.
		if(c2 = false) {
			
			linesToBeSaved = option1(sortedStudents); // 저장될 정보를 라인 단위로 받기
		}else {
			
			linesToBeSaved = option2(sortedStudents);
			
		}
		
		// Write a file (named like the value of resultPath) with linesTobeSaved.
		Utils.writeAFile(linesToBeSaved, output); // 저장되야하는 정보와 저장 경로를 받아서 저장시키기
		 
		 
		}
	}
	
	/**
	 * This method create HashMap<String,Student> from the data csv file. Key is a student id and the corresponding object is an instance of Student.
	 * The Student instance have all the Course instances taken by the student.
	 * @param lines
	 * @return
	 */
	private HashMap<String,Student> loadStudentCourseRecords(CSVParser lines) {
		
		
		HashMap<String, Student> hashMap = new HashMap<String, Student>();
		
		
		for(CSVRecord line : lines) {
			
			Course course = new Course(line);
			Student read = new Student(course.getStudentId(),Integer.parseInt(stYear),Integer.parseInt(enYear));
			
			
			if(hashMap.containsKey(read.getStudentId())) {
				
				hashMap.get(read.getStudentId()).addCourse(course);
				
			}else {
				
			read.addCourse(course);
			hashMap.put(read.getStudentId(),read);
			
			}
			
		}
		
		
		return hashMap;
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
	
	private ArrayList<String> option1(Map<String, Student> sortedStudents) {
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
	
	
	
	private ArrayList<String> option2(Map<String, Student> sortedStudents) {
		ArrayList<String> printLine = new ArrayList<String>();
		
		String result = null;
		
		for(String key:sortedStudents.keySet()) {
			sortedStudents.get(key).getSemestersByYearAndSemester();
		}
		printLine.add("Year,Semester,CourseCode, CourseName,TotalStudents,StudentsTaken,Rate");
		
		Map<String, Integer> sortedTotalNumStudents = new TreeMap<String, Integer>(getTotalNumStudent(sortedStudents));
	
		Map<String, Integer> sortedStudentsTaken = new TreeMap<String, Integer>(getStudentsTaken(sortedStudents));

		
		String courseName = getCourse(sortedStudents);
		
		for(String oneKey:sortedStudentsTaken.keySet()) {
			if(sortedTotalNumStudents.containsKey(oneKey)) {
				String[] temp = oneKey.split("-");
				int year = Integer.parseInt(temp[0]);
				int semester = Integer.parseInt(temp[1]);
				int totalStudents = getTotalNumStudent(sortedStudents).get(oneKey);
				int studentsTaken = getStudentsTaken(sortedStudents).get(oneKey);
				float rate = (float)studentsTaken / totalStudents;
				
				String percent = String.format("%.1f", rate*100);
				
				result = year + "," + semester + "," + code + "," + courseName + "," + totalStudents + "," + studentsTaken + "," + percent + "%";
				
				printLine.add(result);
			}
		}
		
		return printLine;
	}
	
	
	public String getCourse(Map<String,Student> sortedStudents){
		ArrayList<String> courseName = new ArrayList<String>();
		
		for(String key:sortedStudents.keySet()) {
			for(Course data:sortedStudents.get(key).getCoursesTaken()) {
				String currentCourseCode = data.getCourseCode();
				
				if(currentCourseCode.equals(code)) {
					
					courseName.add(data.getCourseName());
					
				}
			}
		}
		String coursePrint = courseName.get(0);
		for(String name:courseName) {
			if(name.equals(coursePrint) == false){
				System.out.println("Wrong course name");
				System.exit(0);
			}
		}
		return coursePrint;
	}
		
	public HashMap<String,Integer> getTotalNumStudent(Map <String,Student> sortedStudents){
			HashMap<String, Integer> totalStudentNum = new HashMap<String, Integer>();
			
			for(String key:sortedStudents.keySet()) {
				for(String command:sortedStudents.get(key).getSemestersByYearAndSemester().keySet()) {
					if(totalStudentNum.containsKey(command)) {
						
						int x = totalStudentNum.get(command)+1;
						
						totalStudentNum.put(command, x);
						
					}else {
						
						totalStudentNum.put(command, 1);
						
					}
				}
			}
			return totalStudentNum;
		}
	
	
	
	
	public HashMap<String, Integer> getStudentsTaken(Map<String, Student> sortedStudents) {
		HashMap<String, Integer> studentsNum = new HashMap<String, Integer>();
		
		for(String key:sortedStudents.keySet()) {
			for(Course data:sortedStudents.get(key).getCoursesTaken()) {
				String yearSemester = data.getYearTaken() + "-" + data.getSemesterCourseTaken();
				String currentCourseCode = data.getCourseCode();
				
				if(currentCourseCode.equals(code)) {
					
					if(studentsNum.containsKey(yearSemester)) {
						
						int x = studentsNum.get(yearSemester)+1;
						studentsNum.put(yearSemester, x);
						
					}else {
						
						studentsNum.put(yearSemester, 1);
						
					}
				}
			}
		}
		
		return studentsNum;
	}
		
		
	
	
	private boolean parseOptions(Options options, String[] args) {
		CommandLineParser parser = new DefaultParser();

		try {

			CommandLine cmd = parser.parse(options, args);
			
			input = cmd.getOptionValue("i");
			output = cmd.getOptionValue("o");
			optA = cmd.getOptionValue("a");
			code = cmd.getOptionValue("c");
			stYear = cmd.getOptionValue("s");
			enYear = cmd.getOptionValue("e");
			help = cmd.hasOption("h");
			
			if(optA.equals("2")) {
				c2 = true;
			}
			

		} catch (Exception e) {
			printHelp(options);
			return false;
		}

		return true;
	}
	
	private Options createOptions() {
		Options options = new Options();

		options.addOption(Option.builder("i").longOpt("input")
				.desc("Set an input file path")
				.hasArg()
				.argName("Input path")
				.required() 
				.build());
		
		options.addOption(Option.builder("o").longOpt("output")
				.desc("Set an output file path")
				.hasArg()
				.argName("Output path")
				.required() 
				.build());
		
	
		options.addOption(Option.builder("a").longOpt("analysis")
				.desc("1: Count courses per semester, 2: Count per course name and year")
				.hasArg()    
				.argName("Analysis option")
				.required() 
				.build());
		
		
		options.addOption(Option.builder("c").longOpt("coursecode")
				.desc("Course code for '-a 2' option")
				.hasArg()    
				.argName("course code")
				.required(c2)
				.build());
		
		options.addOption(Option.builder("s").longOpt("startyear")
				.desc("Set the start year for analysis e.g., -s 2002")
				.hasArg()    
				.argName("Start year for analysis")
				.required()
				.build());
		
		options.addOption(Option.builder("e").longOpt("endyear")
				.desc("Set the end year for analysis e.g., -e 2005")
				.hasArg()    
				.argName("End year for analysis")
				.required()
				.build());
		
		
		options.addOption(Option.builder("h").longOpt("help")
		        .desc("Show a Help page")
		        .build());

		return options;
	}
	
	private void printHelp(Options options) {
		// automatically generate the help statement
		HelpFormatter formatter = new HelpFormatter();
		String header = "HGU Course Analyzer";
		String footer ="";
		formatter.printHelp("HGUCourseCounter", header, options, footer, true);
	}

}
