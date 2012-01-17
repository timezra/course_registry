package timezra.course_registry

import org.springframework.dao.DataIntegrityViolationException

class SemesterController {

	static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

	def index() {
		redirect(action: "list", params: params)
	}

	def list() {
		params.max = Math.min(params.max ? params.int('max') : 10, 100)
		[semesterInstanceList: Semester.list(params), semesterInstanceTotal: Semester.count()]
	}

	def create() {
		def semesterInstance = new Semester(params)
		if(!params.year) semesterInstance.year = Calendar.instance.get Calendar.YEAR
		[semesterInstance: semesterInstance]
	}

	def save() {
		def semesterInstance = new Semester(params)
		if (!semesterInstance.save(flush: true)) {
			render(view: "create", model: [semesterInstance: semesterInstance])
			return
		}

		flash.message = message(code: 'default.created.message', args: [
			message(code: 'semester.label', default: 'Semester'),
			semesterInstance.id
		])
		if(params.returnPage) {
			def semesterParam = "semester.id=${semesterInstance.id}"
			def returnPage= (params.returnPage =~ /semester.id=\d*/) ? params.returnPage.replaceAll('semester.id=\\d*', '') : params.returnPage
			returnPage += "${returnPage.contains('?') ? '&' : '?'}${semesterParam}"
			redirect uri: returnPage
		}
		else {
			redirect action: "show", id: semesterInstance.id
		}
	}

	def show() {
		def semesterInstance = Semester.get(params.id)
		if (!semesterInstance) {
			flash.message = message(code: 'default.not.found.message', args: [
				message(code: 'semester.label', default: 'Semester'),
				params.id
			])
			redirect(action: "list")
			return
		}

		[semesterInstance: semesterInstance]
	}

	def edit() {
		def semesterInstance = Semester.get(params.id)
		if (!semesterInstance) {
			flash.message = message(code: 'default.not.found.message', args: [
				message(code: 'semester.label', default: 'Semester'),
				params.id
			])
			redirect(action: "list")
			return
		}

		[semesterInstance: semesterInstance]
	}

	def update() {
		def semesterInstance = Semester.get(params.id)
		if (!semesterInstance) {
			flash.message = message(code: 'default.not.found.message', args: [
				message(code: 'semester.label', default: 'Semester'),
				params.id
			])
			redirect(action: "list")
			return
		}

		if (params.version) {
			def version = params.version.toLong()
			if (semesterInstance.version > version) {
				semesterInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
						[
							message(code: 'semester.label', default: 'Semester')]
						as Object[],
						"Another user has updated this Semester while you were editing")
				render(view: "edit", model: [semesterInstance: semesterInstance])
				return
			}
		}

		semesterInstance.properties = params

		if (!semesterInstance.save(flush: true)) {
			render(view: "edit", model: [semesterInstance: semesterInstance])
			return
		}

		flash.message = message(code: 'default.updated.message', args: [
			message(code: 'semester.label', default: 'Semester'),
			semesterInstance.id
		])
		redirect(action: "show", id: semesterInstance.id)
	}

	def delete() {
		def semesterInstance = Semester.get(params.id)
		if (!semesterInstance) {
			flash.message = message(code: 'default.not.found.message', args: [
				message(code: 'semester.label', default: 'Semester'),
				params.id
			])
			redirect(action: "list")
			return
		}

		try {
			semesterInstance.delete(flush: true)
			flash.message = message(code: 'default.deleted.message', args: [
				message(code: 'semester.label', default: 'Semester'),
				params.id
			])
			redirect(action: "list")
		}
		catch (DataIntegrityViolationException e) {
			flash.message = message(code: 'default.not.deleted.message', args: [
				message(code: 'semester.label', default: 'Semester'),
				params.id
			])
			redirect(action: "show", id: params.id)
		}
	}
}
