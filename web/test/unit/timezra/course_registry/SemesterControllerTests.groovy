package timezra.course_registry

import grails.test.mixin.Mock
import grails.test.mixin.TestFor

import org.junit.Test

@TestFor(SemesterController)
@Mock(Semester)
class SemesterControllerTests {

	def populateValidParams(final params) {
		assert params != null
		params["year"] = Calendar.instance.get(Calendar.YEAR) + 1
		params["season"] = Season.Winter
	}

	@Test
	void indexRedirectsToSemesterList() {
		controller.index()
		assert "/semester/list" == response.redirectedUrl
	}

	@Test
	void listStartsWithNoElements() {

		def model = controller.list()

		assert model.semesterInstanceList.size() == 0
		assert model.semesterInstanceTotal == 0
	}

	@Test
	void creatingASemesterMakesANewInstanceAvailableFromTheModel() {
		def model = controller.create()

		assert model.semesterInstance != null
	}

	@Test
	void afterASemesterIsSavedItIsShown() {
		controller.save()

		assert model.semesterInstance != null
		assert view == '/semester/create'

		response.reset()

		populateValidParams(params)
		controller.save()

		assert response.redirectedUrl == '/semester/show/1'
		assert controller.flash.message != null
		assert Semester.count() == 1
	}

	@Test
	void theIdParamDeterminesTheSemesterThatIsShown() {
		controller.show()

		assert flash.message != null
		assert response.redirectedUrl == '/semester/list'


		populateValidParams(params)
		def semester = new Semester(params)

		assert semester.save() != null

		params.id = semester.id

		def model = controller.show()

		assert model.semesterInstance == semester
	}

	@Test
	void theIdParamDeterminesTheSemesterThatIsEdited() {
		controller.edit()

		assert flash.message != null
		assert response.redirectedUrl == '/semester/list'


		populateValidParams(params)
		def semester = new Semester(params)

		assert semester.save() != null

		params.id = semester.id

		def model = controller.edit()

		assert model.semesterInstance == semester
	}

	@Test
	void updatingWithoutAnyParamsRedirectsToTheListScreen() {
		controller.update()

		assert flash.message != null
		assert response.redirectedUrl == '/semester/list'
	}

	@Test
	void invalidParamsInUpdateRedirectsBackToTheEditPage() {
		populateValidParams(params)
		def semester = new Semester(params)
		semester.save()

		// test invalid parameters in update
		params["id"] = semester.id
		params["year"] = Calendar.instance.get(Calendar.YEAR) - 1
		params["season"] = null

		controller.update()

		assert view == "/semester/edit"
		assert model.semesterInstance != null
	}

	@Test
	void updatingRedirectsToTheShowSemesterScreen() {
		populateValidParams(params)
		def semester = new Semester(params)
		semester.save()

		params["id"] = semester.id
		params["year"] = Calendar.instance.get(Calendar.YEAR) + 2
		params["season"] = Season.Spring
		controller.update()

		assert response.redirectedUrl == "/semester/show/$semester.id"
		assert flash.message != null
	}

	@Test
	void theVersionCannotBeOutdated() {
		populateValidParams(params)
		def semester = new Semester(params)
		semester.save()

		params["id"] = semester.id
		params["year"] = Calendar.instance.get(Calendar.YEAR) + 2
		params["season"] = Season.Spring
		controller.update()

		response.reset()

		populateValidParams(params)
		params["id"] = semester.id
		params["version"] = -1
		controller.update()

		assert view == "/semester/edit"
		assert model.semesterInstance != null
		assert model.semesterInstance.errors.getFieldError('version')
		assert flash.message != null
	}

	@Test
	void deletingASemesterRedirectsToTheListScreen() {
		controller.delete()
		assert flash.message != null
		assert response.redirectedUrl == '/semester/list'

		response.reset()

		populateValidParams(params)
		def semester = new Semester(params)

		assert semester.save() != null
		assert Semester.count() == 1

		params.id = semester.id

		controller.delete()

		assert Semester.count() == 0
		assert Semester.get(semester.id) == null
		assert response.redirectedUrl == '/semester/list'
	}
}
