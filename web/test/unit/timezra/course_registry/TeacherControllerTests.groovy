package timezra.course_registry

import grails.test.mixin.Mock
import grails.test.mixin.TestFor

import org.junit.Test

@TestFor(TeacherController)
@Mock(Teacher)
class TeacherControllerTests {

	def populateValidParams(final params) {
		assert params != null
		params["name"] = 'John Doe'
		params["email"] = 'john.doe@rutgers.edu'
		params["password"] = '1234567'
	}

	@Test
	void indexRedirectsToTeacherList() {
		controller.index()
		assert "/teacher/list" == response.redirectedUrl
	}

	@Test
	void listStartsWithNoElements() {

		def model = controller.list()

		assert model.teacherInstanceList.size() == 0
		assert model.teacherInstanceTotal == 0
	}

	@Test
	void creatingATeacherMakesANewInstanceAvailableFromTheModel() {
		def model = controller.create()

		assert model.teacherInstance != null
	}

	@Test
	void afterATeacherIsSavedItIsShown() {
		controller.save()

		assert model.teacherInstance != null
		assert view == '/teacher/create'

		response.reset()

		populateValidParams(params)
		controller.save()

		assert response.redirectedUrl == '/teacher/show/1'
		assert controller.flash.message != null
		assert Teacher.count() == 1
	}

	@Test
	void theIdParamDeterminesTheTeacherWhoIsShown() {
		controller.show()

		assert flash.message != null
		assert response.redirectedUrl == '/teacher/list'


		populateValidParams(params)
		def teacher = new Teacher(params)

		assert teacher.save() != null

		params.id = teacher.id

		def model = controller.show()

		assert model.teacherInstance == teacher
	}

	@Test
	void theIdParamDeterminesTheTeacherWhoIsEdited() {
		controller.edit()

		assert flash.message != null
		assert response.redirectedUrl == '/teacher/list'


		populateValidParams(params)
		def teacher = new Teacher(params)

		assert teacher.save() != null

		params.id = teacher.id

		def model = controller.edit()

		assert model.teacherInstance == teacher
	}

	@Test
	void updatingWithoutAnyParamsRedirectsToTheListScreen() {
		controller.update()

		assert flash.message != null
		assert response.redirectedUrl == '/teacher/list'
	}

	@Test
	void invalidParamsInUpdateRedirectsBackToTheEditPage() {
		populateValidParams(params)
		def teacher = new Teacher(params)
		teacher.save()

		params["id"] = teacher.id
		params["name"] = '    '
		params["email"] = 'john'
		params["password"] = ''

		controller.update()

		assert view == "/teacher/edit"
		assert model.teacherInstance != null
	}

	@Test
	void updatingRedirectsToTheShowTeacherScreen() {
		populateValidParams(params)
		def teacher = new Teacher(params)
		teacher.save()

		params["id"] = teacher.id
		params["name"] = 'jane.doe'
		params["email"] = 'jane.doe@rutgers.edu'
		params["password"] = '7654321'

		controller.update()

		assert response.redirectedUrl == "/teacher/show/$teacher.id"
		assert flash.message != null
	}

	@Test
	void theVersionCannotBeOutdated() {
		populateValidParams(params)
		def teacher = new Teacher(params)
		teacher.save()

		params["id"] = teacher.id
		params["name"] = 'jane.doe'
		params["email"] = 'jane.doe@rutgers.edu'
		params["password"] = '7654321'

		controller.update()

		response.reset()

		populateValidParams(params)
		params["id"] = teacher.id
		params["version"] = -1
		controller.update()

		assert view == "/teacher/edit"
		assert model.teacherInstance != null
		assert model.teacherInstance.errors.getFieldError('version')
		assert flash.message != null
	}

	@Test
	void deletingATeacherRedirectsToTheListScreen() {
		controller.delete()
		assert flash.message != null
		assert response.redirectedUrl == '/teacher/list'

		response.reset()

		populateValidParams(params)
		def teacher = new Teacher(params)

		assert teacher.save() != null
		assert Teacher.count() == 1

		params.id = teacher.id

		controller.delete()

		assert Teacher.count() == 0
		assert Teacher.get(teacher.id) == null
		assert response.redirectedUrl == '/teacher/list'
	}
}
