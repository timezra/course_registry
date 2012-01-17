package timezra.course_registry.pages

import groovy.transform.InheritConstructors

import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement

@InheritConstructors
abstract class CreateUser extends CourseRegistryPage {
	WebElement name
	WebElement email
	WebElement password
	WebElement create

	CreateUser(WebDriver driver) {
		super(driver)
	}

	protected <T> T register(name, email, password, Class<T> nextPage) {
		type_name name
		type_email email
		type_password password
		click_create nextPage
	}
}
