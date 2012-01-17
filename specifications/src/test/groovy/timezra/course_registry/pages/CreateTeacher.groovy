package timezra.course_registry.pages

import groovy.transform.InheritConstructors

import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement

@InheritConstructors
class CreateTeacher extends CreateUser {

	CreateTeacher(WebDriver driver) {
		super(driver)
	}

	ShowTeacher register(name, email, password) {
		register name, email, password, ShowTeacher
	}
}
