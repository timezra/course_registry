package timezra.course_registry

import grails.test.mixin.Mock
import grails.test.mixin.TestFor

import org.junit.Test

@TestFor(CourseController)
@Mock([Course, Teacher])
class CourseControllerTests {

	def populateValidParams(final params) {
		assert params != null
		params["name"] = 'Testing 101'
		params["description"] = 'Testing 101 Description'
		params["credits"] = 3

		def teacher = new Teacher(name: "John Doe", email: "john.doe@rutgers.edu", password: "1234567")
		teacher.save()

		params["teacher.id"] = teacher.id
	}

	@Test
	void indexRedirectsToCourseList() {
		controller.index()
		assert "/course/list" == response.redirectedUrl
	}

	@Test
	void listStartsWithNoElements() {

		def model = controller.list()

		assert model.courseInstanceList.size() == 0
		assert model.courseInstanceTotal == 0
	}

	@Test
	void creatingACourseMakesANewInstanceAvailableFromTheModel() {
		def model = controller.create()

		assert model.courseInstance != null
	}

	@Test
	void afterACourseIsSavedItIsShown() {
		controller.save()

		assert model.courseInstance != null
		assert view == '/course/create'

		response.reset()

		populateValidParams(params)
		controller.save()

		assert response.redirectedUrl == '/course/show/1'
		assert controller.flash.message != null
		assert Course.count() == 1
	}

	@Test
	void theIdParamDeterminesTheCourseThatIsShown() {
		controller.show()

		assert flash.message != null
		assert response.redirectedUrl == '/course/list'


		populateValidParams(params)
		def course = new Course(params)

		assert course.save() != null

		params.id = course.id

		def model = controller.show()

		assert model.courseInstance == course
	}

	@Test
	void theIdParamDeterminesTheCourseThatIsEdited() {
		controller.edit()

		assert flash.message != null
		assert response.redirectedUrl == '/course/list'


		populateValidParams(params)
		def course = new Course(params)

		assert course.save() != null

		params.id = course.id

		def model = controller.edit()

		assert model.courseInstance == course
	}

	@Test
	void updatingWithoutAnyParamsRedirectsToTheListScreen() {
		controller.update()

		assert flash.message != null
		assert response.redirectedUrl == '/course/list'
	}

	@Test
	void invalidParamsInUpdateRedirectsBackToTheEditPage() {
		populateValidParams(params)
		def course = new Course(params)
		course.save()

		params["id"] = course.id
		params["name"] = '     '
		params["description"] = ' '
		params["credits"] = -1

		controller.update()

		assert view == "/course/edit"
		assert model.courseInstance != null
	}

	@Test
	void updatingRedirectsToTheShowCourseScreen() {
		populateValidParams(params)
		def course = new Course(params)
		course.save()

		params["id"] = course.id
		params["name"] = 'Testing 401'
		params["description"] = 'Testing 401 description'
		params["credits"] = 4
		params["teacher.id"] = course.teacher.id
		controller.update()

		assert response.redirectedUrl == "/course/show/$course.id"
		assert flash.message != null
	}

	@Test
	void theVersionCannotBeOutdated() {
		populateValidParams(params)
		def course = new Course(params)
		course.save()

		params["id"] = course.id
		params["name"] = 'Testing 401'
		params["description"] = 'Testing 401 description'
		params["credits"] = 4
		params["teacher.id"] = course.teacher.id
		controller.update()

		response.reset()

		params["id"] = course.id
		params["version"] = -1
		controller.update()

		assert view == "/course/edit"
		assert model.courseInstance != null
		assert model.courseInstance.errors.getFieldError('version')
		assert flash.message != null
	}

	@Test
	void deletingACourseRedirectsToTheListScreen() {
		controller.delete()
		assert flash.message != null
		assert response.redirectedUrl == '/course/list'

		response.reset()

		populateValidParams(params)
		def course = new Course(params)

		assert course.save() != null
		assert Course.count() == 1

		params.id = course.id

		controller.delete()

		assert Course.count() == 0
		assert Course.get(course.id) == null
		assert response.redirectedUrl == '/course/list'
	}
}
