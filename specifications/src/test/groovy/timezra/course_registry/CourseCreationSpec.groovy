package timezra.course_registry

import org.openqa.selenium.By

import timezra.course_registry.pages.Semester
import timezra.course_registry.pages.Timeslot

class CourseCreationSpec extends CourseRegistrySpec {

	def "a teacher can add a course"() {
		when:
		def showCourse = createCourse COURSE_NAME, COURSE_DESCRIPTION, COURSE_CREDITS

		then:
		showCourse.message.text ==~ /Course \d+ created/

		where:
		browser << browsers()
	}

	def "courses occur in a semester"() {
		when:
		def semester = new Semester(season: SEMESTER_SEASON, year: SEMESTER_YEAR)
		def showCourse = createCourseWithSemester COURSE_NAME, COURSE_DESCRIPTION, COURSE_CREDITS, semester

		then:
		showCourse.semester.text == semester.toString()

		where:
		browser << browsers()
	}

	def "courses occur in timeslots"() {
		when:
		def timeslot1 = new Timeslot(day: 'Monday', start: '8am', end: '10am')
		def timeslot2 = new Timeslot(day: 'Wednesday', start: '1pm', end: '3pm')
		def timeslot3 = new Timeslot(day: 'Friday', start: '10am', end: '12m')
		def showCourse = createCourseWithTimeslots COURSE_NAME, COURSE_DESCRIPTION, COURSE_CREDITS, timeslot1, timeslot2, timeslot3
		then:
		showCourse.timeslots.findElement(By.linkText(timeslot1.toString())).text == timeslot1.toString()
		showCourse.timeslots.findElement(By.linkText(timeslot2.toString())).text == timeslot2.toString()
		showCourse.timeslots.findElement(By.linkText(timeslot3.toString())).text == timeslot3.toString()

		where:
		browser << browsers()
	}

	def "courses can have prerequisites"() {
		when:
		def courseName1 = randomString()
		def courseName2 = randomString()
		def courseName3 = randomString()
		createCourse courseName1, "${courseName1} Description", '3'
		createCourse courseName2, "${courseName2} Description", '3'
		createCourse courseName3, "${courseName3} Description", '3'
		def showCourse = createCourseWithPrerequisites COURSE_NAME, COURSE_DESCRIPTION, COURSE_CREDITS, courseName1, courseName2, courseName3
		then:
		showCourse.prerequisites.findElement(By.linkText(courseName1)).text == courseName1
		showCourse.prerequisites.findElement(By.linkText(courseName2)).text == courseName2
		showCourse.prerequisites.findElement(By.linkText(courseName3)).text == courseName3

		where:
		browser << browsers()
	}
}
