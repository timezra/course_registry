package timezra.course_registry.pages

import groovy.transform.InheritConstructors

import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement

@InheritConstructors
class CreateSemester extends CourseRegistryPage {

	WebElement year
	WebElement season
	WebElement create

	CreateSemester(WebDriver driver) {
		super(driver)
	}

	public <T> T doCreate(semester, Class<T> nextPage) {
		choose_season semester.season
		type_year semester.year
		click_create nextPage
	}
}
