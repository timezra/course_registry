package timezra.course_registry.pages

import groovy.transform.InheritConstructors

import java.util.List

import org.openqa.selenium.By
import org.openqa.selenium.NoSuchElementException
import org.openqa.selenium.SearchContext
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement

@InheritConstructors
class CreateCourse extends CourseRegistryPage {

	WebElement name
	WebElement description
	WebElement credits
	WebElement semester
	WebElement timeslots
	WebElement prerequisites
	WebElement add_semester_link
	WebElement add_timeslot_link
	WebElement create

	CreateCourse(WebDriver driver) {
		super(driver)
	}

	ShowCourse doCreate(name, description, credits) {
		type_name name
		type_description description
		type_credits credits
		click_create ShowCourse
	}

	ShowCourse createWithSemester(name, description, credits, semester) {
		addSemesterIfNecessary semester
		choose_semester semester.toString()
		doCreate name, description, credits
	}

	ShowCourse createWithTimeslots(name, description, credits, Timeslot...timeslots) {
		timeslots.each { addTimeslotIfNecessary it }
		timeslots.each { choose_timeslots it.toString() }
		doCreate name, description, credits
	}

	ShowCourse createWithSemesterAndTimeslots(name, description, credits, semester, Timeslot...timeslots) {
		addSemesterIfNecessary semester
		timeslots.each { addTimeslotIfNecessary it }
		choose_semester semester.toString()
		timeslots.each { choose_timeslots it.toString() }
		doCreate name, description, credits
	}

	ShowCourse createWithPrerequisites(name, description, credits, String...prerequisites) {
		prerequisites.each { choose_prerequisites it.toString() }
		doCreate name, description, credits
	}

	private void addTimeslotIfNecessary(timeslot) {
		try {
			timeslots.findElement(new ByOption(regex: toWhitespaceAgnosticRegex(timeslot.toString())))
		} catch (NoSuchElementException e) {
			def createTimeslot = click_add_timeslot_link CreateTimeslot
			createTimeslot.doCreate timeslot.day, timeslot.start, timeslot.end, CreateCourse
		}
	}

	private void addSemesterIfNecessary(semester) {
		try {
			this.semester.findElement(new ByOption(regex: toWhitespaceAgnosticRegex(semester.toString())))
		} catch (NoSuchElementException e) {
			def createSemester = click_add_semester_link CreateSemester
			createSemester.doCreate semester, CreateCourse
		}
	}

	// Unfortunately, we need to do this for the ChromeDriver
	private String toWhitespaceAgnosticRegex(s) {
		s.replaceAll(/\s+/, "\\\\s+")
	}

	private static final class ByOption extends By {
		String regex
		@Override List<WebElement> findElements(SearchContext context) {
			List options = context.findElements By.tagName("option")
			for (def option : options) {
				if((option.text =~ regex)) {
					return [option]
				}
			}
			[]
		}

		String toString() {
			"option with text '${regex}'"
		}
	}
}
