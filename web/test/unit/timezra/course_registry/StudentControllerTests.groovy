package timezra.course_registry

import grails.test.mixin.Mock
import grails.test.mixin.TestFor

import org.junit.Test

@TestFor(StudentController)
@Mock([Student, Course])
class StudentControllerTests {

	def populateValidParams(final params) {
		assert params != null
		params["name"] = 'John Doe'
		params["email"] = 'john.doe@rutgers.edu'
		params["password"] = '1234567'
	}

	@Test
	void indexRedirectsToStudentList() {
		controller.index()
		assert "/student/list" == response.redirectedUrl
	}

	@Test
	void listStartsWithNoElements() {

		def model = controller.list()

		assert model.studentInstanceList.size() == 0
		assert model.studentInstanceTotal == 0
	}

	@Test
	void creatingAStudentMakesANewInstanceAvailableFromTheModel() {
		def model = controller.create()

		assert model.studentInstance != null
	}

	@Test
	void afterAStudentIsSavedItIsShown() {
		controller.save()

		assert model.studentInstance != null
		assert view == '/student/create'

		response.reset()

		populateValidParams(params)
		controller.save()

		assert response.redirectedUrl == '/student/show/1'
		assert controller.flash.message != null
		assert Student.count() == 1
	}

	@Test
	void theIdParamDeterminesTheStudentWhoIsShown() {
		controller.show()

		assert flash.message != null
		assert response.redirectedUrl == '/student/list'


		populateValidParams(params)
		def student = new Student(params)

		assert student.save() != null

		params.id = student.id

		def model = controller.show()

		assert model.studentInstance == student
	}

	@Test
	void theIdParamDeterminesTheStudentWhoIsEdited() {
		controller.edit()

		assert flash.message != null
		assert response.redirectedUrl == '/student/list'


		populateValidParams(params)
		def student = new Student(params)

		assert student.save() != null

		params.id = student.id

		def model = controller.edit()

		assert model.studentInstance == student
	}

	@Test
	void updatingWithoutAnyParamsRedirectsToTheListScreen() {
		controller.update()

		assert flash.message != null
		assert response.redirectedUrl == '/student/list'
	}

	@Test
	void invalidParamsInUpdateRedirectsBackToTheEditPage() {
		populateValidParams(params)
		def student = new Student(params)
		student.save()

		params["id"] = student.id
		params["name"] = '     '
		params["email"] = 'john.doe'
		params["password"] = ''

		controller.update()

		assert view == "/student/edit"
		assert model.studentInstance != null
	}

	@Test
	void updatingRedirectsToTheShowStudentScreen() {
		populateValidParams(params)
		def student = new Student(params)
		student.save()

		params["id"] = student.id
		params["name"] = 'Jane Doe'
		params["email"] = 'jane.doe@rutgers.edu'
		params["password"] = '7654321'
		controller.update()

		assert response.redirectedUrl == "/student/show/$student.id"
		assert flash.message != null
	}

	@Test
	void theVersionCannotBeOutdated() {
		populateValidParams(params)
		def student = new Student(params)
		student.save()

		params["id"] = student.id
		params["name"] = 'Jane Doe'
		params["email"] = 'jane.doe@rutgers.edu'
		params["password"] = '7654321'
		controller.update()

		populateValidParams(params)
		params["id"] = student.id
		params["version"] = -1
		controller.update()

		assert view == "/student/edit"
		assert model.studentInstance != null
		assert model.studentInstance.errors.getFieldError('version')
		assert flash.message != null
	}

	@Test
	void deletingAStudentRedirectsToTheListScreen() {
		controller.delete()
		assert flash.message != null
		assert response.redirectedUrl == '/student/list'

		response.reset()

		populateValidParams(params)
		def student = new Student(params)

		assert student.save() != null
		assert Student.count() == 1

		params.id = student.id

		controller.delete()

		assert Student.count() == 0
		assert Student.get(student.id) == null
		assert response.redirectedUrl == '/student/list'
	}
}
