package timezra.course_registry.pages

import static org.apache.commons.lang.StringUtils.splitByCharacterTypeCamelCase

import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.FindBy
import org.openqa.selenium.support.PageFactory
import org.openqa.selenium.support.ui.Select

abstract class CourseRegistryPage {

	WebDriver driver

	@FindBy(className = "message")
	WebElement message

	static <T extends CourseRegistryPage> T goTo(String address, WebDriver driver, Class<T> page) {
		driver.get address
		PageFactory.initElements driver, page
	}

	CourseRegistryPage(WebDriver driver) {
		this.driver = driver
		String title = splitByCharacterTypeCamelCase(getClass().simpleName).join ' '
		if(!title.equals(driver.title)) {
			throw new IllegalStateException("Should be on page '${title}' but was on page '${driver.title}' instead")
		}
	}

	def methodMissing(String name, args) {
		def m
		if((m = name =~ /click_(\w+)/)) {
			def webElement = this."${m[0][1]}"
			webElement.click()
			PageFactory.initElements driver, args[0]
		}
		else if((m = name =~ /type_(\w+)/)) {
			def webElement = this."${m[0][1]}"
			webElement.clear()
			webElement.sendKeys args[0]
		}
		else if((m = name =~ /choose_(\w+)/)) {
			def webElement = this."${m[0][1]}"
			def select = new Select(webElement)
			select.selectByVisibleText args[0]
		}
		else {
			throw new MissingMethodException(name, getClass(), args)
		}
	}
}
