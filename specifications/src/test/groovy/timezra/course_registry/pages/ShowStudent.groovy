package timezra.course_registry.pages

import groovy.transform.InheritConstructors

import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement

@InheritConstructors
class ShowStudent extends CourseRegistryPage {

	WebElement edit
	WebElement courses

	ShowStudent(WebDriver driver) {
		super(driver)
	}

	public <T> T register_for(courses, Class<T> nextPage) {
		def editStudent = click_edit EditStudent
		courses.each { editStudent.choose_courses it }
		editStudent.click_update nextPage
	}
}
