package timezra.course_registry.pages

import groovy.transform.InheritConstructors

import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement

@InheritConstructors
class CourseRegistryHome extends CourseRegistryPage {

	WebElement greeting
	WebElement teacher_link
	WebElement student_link
	WebElement login_link

	CourseRegistryHome(WebDriver driver) {
		super(driver)
	}
}
