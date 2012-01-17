package timezra.course_registry

import org.openqa.selenium.By

import timezra.course_registry.pages.EditStudent
import timezra.course_registry.pages.Login
import timezra.course_registry.pages.Semester
import timezra.course_registry.pages.ShowStudent
import timezra.course_registry.pages.Timeslot


class StudentRegistrationSpec extends CourseRegistrySpec {

	def "a user can register as a student"() {
		when:
		def name = STUDENT_NAME
		def showStudent = createStudent name, randomEmail(), STUDENT_PASSWORD

		then:
		showStudent.message.text ==~ /Student \d+ created/

		where:
		browser << browsers()
	}

	def "an existing student can login"() {
		when:
		def name = STUDENT_NAME
		def email = randomEmail()
		def password = STUDENT_PASSWORD
		createStudent name, email, password
		def login = goHome().click_login_link Login
		def showStudent = login.doLogin email, password, ShowStudent

		then:
		showStudent.message.text == "Welcome back, ${name}!"

		where:
		browser << browsers()
	}

	def "a student can register for a course"() {
		when:
		def courseName = randomString()
		createCourse courseName, "${courseName} description", COURSE_CREDITS
		def showStudent = createStudent STUDENT_NAME, randomEmail(), STUDENT_PASSWORD
		showStudent.register_for([courseName], ShowStudent)

		then:
		showStudent.courses.findElement(By.linkText(courseName)).text == courseName

		where:
		browser << browsers()
	}

	def "a student must take any pre-reqs before registering for a course"() {
		when:
		def prereqName = randomString()
		createCourse prereqName, "${prereqName} description", COURSE_CREDITS
		def courseName = randomString()
		createCourseWithPrerequisites courseName, "${courseName} description", COURSE_CREDITS, prereqName
		def showStudent = createStudent STUDENT_NAME, randomEmail(), STUDENT_PASSWORD
		def editStudent = showStudent.register_for([courseName], EditStudent)

		then:
		editStudent.courses_error.text == "The prerequisite course ${prereqName} must be taken before ${courseName}."

		where:
		browser << browsers()
	}

	def "a student cannot take 2 courses that occur at the same time"() {
		when:
		def semester = new Semester(season: SEMESTER_SEASON, year: SEMESTER_YEAR)
		def courseName1 = randomString()
		createCourseWithSemesterAndTimeslots courseName1, "${courseName1} description", COURSE_CREDITS, semester, new Timeslot(day: 'Monday', start: '8am', end: '10am')
		def courseName2 = randomString()
		createCourseWithSemesterAndTimeslots courseName2, "${courseName2} description", COURSE_CREDITS, semester, new Timeslot(day: 'Monday', start: '9am', end: '11am')
		def showStudent = createStudent STUDENT_NAME, randomEmail(), STUDENT_PASSWORD
		def editStudent = showStudent.register_for([courseName1, courseName2], EditStudent)

		then:
		editStudent.courses_error.text == "The courses ${courseName1} and ${courseName2} occur at the same time."

		where:
		browser << browsers()
	}
}
