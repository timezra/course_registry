package timezra.course_registry

import grails.test.mixin.Mock
import grails.test.mixin.TestFor

import org.junit.Test

@TestFor(TimeslotController)
@Mock(Timeslot)
class TimeslotControllerTests {


	def populateValidParams(final params) {
		assert params != null
		params["dayOfWeek"] = DayOfWeek.Monday
		params["startTime"] = TimeOfDay._8am
		params["endTime"] = TimeOfDay._9am
	}

	@Test
	void indexRedirectsToTimeslotList() {
		controller.index()
		assert "/timeslot/list" == response.redirectedUrl
	}

	@Test
	void listStartsWithNoElements() {

		def model = controller.list()

		assert model.timeslotInstanceList.size() == 0
		assert model.timeslotInstanceTotal == 0
	}

	@Test
	void creatingATimeslotMakesANewInstanceAvailableFromTheModel() {
		def model = controller.create()

		assert model.timeslotInstance != null
	}

	@Test
	void afterATimeslotIsSavedItIsShown() {
		controller.save()

		assert model.timeslotInstance != null
		assert view == '/timeslot/create'

		response.reset()

		populateValidParams(params)
		controller.save()

		assert response.redirectedUrl == '/timeslot/show/1'
		assert controller.flash.message != null
		assert Timeslot.count() == 1
	}

	@Test
	void theIdParamDeterminesTheTimeslotThatIsShown() {
		controller.show()

		assert flash.message != null
		assert response.redirectedUrl == '/timeslot/list'


		populateValidParams(params)
		def timeslot = new Timeslot(params)

		assert timeslot.save() != null

		params.id = timeslot.id

		def model = controller.show()

		assert model.timeslotInstance == timeslot
	}

	@Test
	void theIdParamDeterminesTheTimeslotThatIsEdited() {
		controller.edit()

		assert flash.message != null
		assert response.redirectedUrl == '/timeslot/list'


		populateValidParams(params)
		def timeslot = new Timeslot(params)

		assert timeslot.save() != null

		params.id = timeslot.id

		def model = controller.edit()

		assert model.timeslotInstance == timeslot
	}

	@Test
	void updatingWithoutAnyParamsRedirectsToTheListScreen() {
		controller.update()

		assert flash.message != null
		assert response.redirectedUrl == '/timeslot/list'
	}

	@Test
	void invalidParamsInUpdateRedirectsBackToTheEditPage() {
		populateValidParams(params)
		def timeslot = new Timeslot(params)
		timeslot.save()

		params["id"] = timeslot.id
		params["dayOfWeek"] = null
		params["startTime"] = null
		params["endTime"] = null

		controller.update()

		assert view == "/timeslot/edit"
		assert model.timeslotInstance != null
	}

	@Test
	void updatingRedirectsToTheShowCourseScreen() {
		populateValidParams(params)
		def timeslot = new Timeslot(params)
		timeslot.save()

		params["id"] = timeslot.id
		params["dayOfWeek"] = DayOfWeek.Tuesday
		params["startTime"] = TimeOfDay._10am
		params["endTime"] = TimeOfDay._11am
		controller.update()

		assert response.redirectedUrl == "/timeslot/show/$timeslot.id"
		assert flash.message != null
	}

	@Test
	void theVersionCannotBeOutdated() {
		populateValidParams(params)
		def timeslot = new Timeslot(params)
		timeslot.save()

		params["id"] = timeslot.id
		params["dayOfWeek"] = DayOfWeek.Tuesday
		params["startTime"] = TimeOfDay._10am
		params["endTime"] = TimeOfDay._11am
		controller.update()

		response.reset()

		populateValidParams(params)
		params["id"] = timeslot.id
		params["version"] = -1
		controller.update()

		assert view == "/timeslot/edit"
		assert model.timeslotInstance != null
		assert model.timeslotInstance.errors.getFieldError('version')
		assert flash.message != null
	}

	@Test
	void deletingATimeslotRedirectsToTheListScreen() {
		controller.delete()
		assert flash.message != null
		assert response.redirectedUrl == '/timeslot/list'

		response.reset()

		populateValidParams(params)
		def timeslot = new Timeslot(params)

		assert timeslot.save() != null
		assert Timeslot.count() == 1

		params.id = timeslot.id

		controller.delete()

		assert Timeslot.count() == 0
		assert Timeslot.get(timeslot.id) == null
		assert response.redirectedUrl == '/timeslot/list'
	}
}
