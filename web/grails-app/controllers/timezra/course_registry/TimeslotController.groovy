package timezra.course_registry

import org.springframework.dao.DataIntegrityViolationException

class TimeslotController {

	static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

	def index() {
		redirect(action: "list", params: params)
	}

	def list() {
		params.max = Math.min(params.max ? params.int('max') : 10, 100)
		[timeslotInstanceList: Timeslot.list(params), timeslotInstanceTotal: Timeslot.count()]
	}

	def create() {
		[timeslotInstance: new Timeslot(params)]
	}

	def save() {
		def timeslotInstance = new Timeslot(params)
		if (!timeslotInstance.save(flush: true)) {
			render(view: "create", model: [timeslotInstance: timeslotInstance])
			return
		}
		flash.message = message(code: 'default.created.message', args: [
			message(code: 'timeslot.label', default: 'Timeslot'),
			timeslotInstance.id
		])
		if(params.returnPage) {
			def timeslotParam = "timeslots=${timeslotInstance.id}"
			def returnPage = "${params.returnPage}${params.returnPage.contains('?') ? '&' : '?'}${timeslotParam}"
			redirect uri: returnPage
		}
		else {
			redirect action: "show", id: timeslotInstance.id
		}
	}

	def show() {
		def timeslotInstance = Timeslot.get(params.id)
		if (!timeslotInstance) {
			flash.message = message(code: 'default.not.found.message', args: [
				message(code: 'timeslot.label', default: 'Timeslot'),
				params.id
			])
			redirect(action: "list")
			return
		}

		[timeslotInstance: timeslotInstance]
	}

	def edit() {
		def timeslotInstance = Timeslot.get(params.id)
		if (!timeslotInstance) {
			flash.message = message(code: 'default.not.found.message', args: [
				message(code: 'timeslot.label', default: 'Timeslot'),
				params.id
			])
			redirect(action: "list")
			return
		}

		[timeslotInstance: timeslotInstance]
	}

	def update() {
		def timeslotInstance = Timeslot.get(params.id)
		if (!timeslotInstance) {
			flash.message = message(code: 'default.not.found.message', args: [
				message(code: 'timeslot.label', default: 'Timeslot'),
				params.id
			])
			redirect(action: "list")
			return
		}

		if (params.version) {
			def version = params.version.toLong()
			if (timeslotInstance.version > version) {
				timeslotInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
						[
							message(code: 'timeslot.label', default: 'Timeslot')]
						as Object[],
						"Another user has updated this Timeslot while you were editing")
				render(view: "edit", model: [timeslotInstance: timeslotInstance])
				return
			}
		}

		timeslotInstance.properties = params

		if (!timeslotInstance.save(flush: true)) {
			render(view: "edit", model: [timeslotInstance: timeslotInstance])
			return
		}

		flash.message = message(code: 'default.updated.message', args: [
			message(code: 'timeslot.label', default: 'Timeslot'),
			timeslotInstance.id
		])
		redirect(action: "show", id: timeslotInstance.id)
	}

	def delete() {
		def timeslotInstance = Timeslot.get(params.id)
		if (!timeslotInstance) {
			flash.message = message(code: 'default.not.found.message', args: [
				message(code: 'timeslot.label', default: 'Timeslot'),
				params.id
			])
			redirect(action: "list")
			return
		}

		try {
			timeslotInstance.delete(flush: true)
			flash.message = message(code: 'default.deleted.message', args: [
				message(code: 'timeslot.label', default: 'Timeslot'),
				params.id
			])
			redirect(action: "list")
		}
		catch (DataIntegrityViolationException e) {
			flash.message = message(code: 'default.not.deleted.message', args: [
				message(code: 'timeslot.label', default: 'Timeslot'),
				params.id
			])
			redirect(action: "show", id: params.id)
		}
	}
}
