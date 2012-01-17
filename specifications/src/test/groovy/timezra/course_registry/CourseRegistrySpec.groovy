package timezra.course_registry

import static java.util.concurrent.TimeUnit.SECONDS
import groovy.lang.Closure

import java.util.concurrent.TimeUnit

import org.openqa.selenium.WebDriver
import org.openqa.selenium.firefox.FirefoxDriver
import org.openqa.selenium.htmlunit.HtmlUnitDriver

import spock.lang.Shared
import spock.lang.Specification
import timezra.course_registry.pages.CourseRegistryHome
import timezra.course_registry.pages.CourseRegistryPage
import timezra.course_registry.pages.CreateCourse
import timezra.course_registry.pages.CreateStudent
import timezra.course_registry.pages.CreateTeacher
import timezra.course_registry.pages.Timeslot

abstract class CourseRegistrySpec extends Specification {

	static final def HOME = 'http://localhost:8080/course_registry'

	static final String TEACHER_NAME = 'Test Teacher'
	static final String TEACHER_PASSWORD = 'test_password'
	static final def STUDENT_NAME = 'Test Student'
	static final def STUDENT_PASSWORD = 'test_password'
	static final def COURSE_NAME = 'Test Course'
	static final def COURSE_DESCRIPTION = 'Test Course Description'
	static final def COURSE_CREDITS = '3'
	static final def SEMESTER_SEASON = 'Spring'
	static final def SEMESTER_YEAR = "${Calendar.instance.get(Calendar.YEAR)+1}"

	@Shared
	WebDriver driver

	def setup() {
		driver.manage().timeouts().implicitlyWait 10, SECONDS
	}

	def cleanup() {
		driver.quit()
	}

	protected def randomEmail() {
		"${randomString()}@test.com"
	}

	protected def randomString() {
		UUID.randomUUID().toString()
	}

	protected def goHome() {
		CourseRegistryPage.goTo HOME, driver, CourseRegistryHome
	}

	protected def createTeacher(name, email, password) {
		def createTeacher = goHome().click_teacher_link CreateTeacher
		createTeacher.register name, email, password
	}

	protected def createStudent(name, email, password) {
		def createStudent = goHome().click_student_link CreateStudent
		createStudent.register name, email, password
	}

	protected def createCourse(courseName, description, credits) {
		createCourse { createCourse -> createCourse.doCreate courseName, description, credits }
	}

	protected def createCourseWithSemester(courseName, description, credits, semester) {
		createCourse { createCourse -> createCourse.createWithSemester courseName, description, credits, semester }
	}

	protected def createCourseWithTimeslots(courseName, description, credits, Timeslot...timeslots) {
		createCourse { createCourse -> createCourse.createWithTimeslots courseName, description, credits, timeslots }
	}

	protected def createCourseWithSemesterAndTimeslots(courseName, description, credits, semester, Timeslot...timeslots) {
		createCourse { createCourse -> createCourse.createWithSemesterAndTimeslots courseName, description, credits, semester, timeslots }
	}

	protected def createCourseWithPrerequisites(courseName, description, credits, String...prerequisites) {
		createCourse { createCourse -> createCourse.createWithPrerequisites courseName, description, credits, prerequisites }
	}

	protected def createCourse(Closure strategy) {
		def showTeacher = createTeacher TEACHER_NAME, randomEmail(), TEACHER_PASSWORD
		def createCourse = showTeacher.click_add_course_link CreateCourse
		strategy createCourse
	}

	protected def browsers() {
		//		def capabilities = DesiredCapabilities.internetExplorer()
		//		capabilities.setCapability("version", "7")
		//		capabilities.setCapability("platform", Platform.XP)
		//		capabilities.setCapability("name", "Testing Course Registry in Sauce")
		//		def sauceDriver = new RemoteWebDriver(
		//				new URL(
		//				"http://<username>:<apiKey>@ondemand.saucelabs.com:80/wd/hub"),
		//				capabilities)
		//
		//		System.setProperty("webdriver.chrome.driver", "/path/to/bin/chromedriver")
		def drivers = [
			new HtmlUnitDriver(),
			new FirefoxDriver(),
			//			new ChromeDriver(),
			//			sauceDriver
		]
		new Browsers(spec: this, delegate: drivers.iterator())
	}

	private static final class Browsers {
		@Delegate Iterator<WebDriver> delegate
		CourseRegistrySpec spec

		@Override WebDriver next() {
			spec.driver = delegate.next()
		}
	}
}
