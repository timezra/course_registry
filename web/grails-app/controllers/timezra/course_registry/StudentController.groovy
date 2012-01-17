package timezra.course_registry

import org.springframework.dao.DataIntegrityViolationException

class StudentController {

	static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

	def index() {
		redirect(action: "list", params: params)
	}

	def list() {
		params.max = Math.min(params.max ? params.int('max') : 10, 100)
		[studentInstanceList: Student.list(params), studentInstanceTotal: Student.count()]
	}

	def create() {
		[studentInstance: new Student(params)]
	}

	def save() {
		def studentInstance = new Student(params)
		if (!studentInstance.save(flush: true)) {
			render(view: "create", model: [studentInstance: studentInstance])
			return
		}

		flash.message = message(code: 'default.created.message', args: [
			message(code: 'student.label', default: 'Student'),
			studentInstance.id
		])
		redirect(action: "show", id: studentInstance.id)
	}

	def show() {
		def studentInstance = Student.get(params.id)
		if (!studentInstance) {
			flash.message = message(code: 'default.not.found.message', args: [
				message(code: 'student.label', default: 'Student'),
				params.id
			])
			redirect(action: "list")
			return
		}

		[studentInstance: studentInstance]
	}

	def edit() {
		def studentInstance = Student.get(params.id)
		if (!studentInstance) {
			flash.message = message(code: 'default.not.found.message', args: [
				message(code: 'student.label', default: 'Student'),
				params.id
			])
			redirect(action: "list")
			return
		}

		[studentInstance: studentInstance]
	}

	def update() {
		def studentInstance = Student.get(params.id)
		if (!studentInstance) {
			flash.message = message(code: 'default.not.found.message', args: [
				message(code: 'student.label', default: 'Student'),
				params.id
			])
			redirect(action: "list")
			return
		}

		if (params.version) {
			def version = params.version.toLong()
			if (studentInstance.version > version) {
				studentInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
						[
							message(code: 'student.label', default: 'Student')]
						as Object[],
						"Another user has updated this Student while you were editing")
				render(view: "edit", model: [studentInstance: studentInstance])
				return
			}
		}

		// Sad that we have to do this, but Course.getAll([]) does not work in unit tests
		// bug - http://jira.grails.org/browse/GPSPOCK-12
		// List courses = Course.getAll params.list('courses').collect({ Long.parseLong it })
		List courses = params.list('courses').collect({ Course.get Long.parseLong(it) })

		courses.eachWithIndex { course, i ->
			course.prerequisites.findAll({ !courses.contains(it.id) }).each {
				studentInstance.errors.rejectValue("courses", "timezra.course_registry.Student.courses.missingPrerequisite",
						[it, course]as Object[],
						"The prerequisite course ${it} must be taken before ${course}.")
			}
			course.timeslots.each { t ->
				courses.subList(i+1, courses.size()).findAll({ course.semester == it.semester && t.overlaps(it.timeslots as List) }).each { nextCourse ->
					studentInstance.errors.rejectValue("courses", "timezra.course_registry.Student.courses.timeslots.overlap",
							[course, nextCourse]as Object[],
							"The courses ${course} and ${nextCourse} occur at the same time.")
				}
			}
		}
		if(studentInstance.errors.hasErrors()) {
			render(view: "edit", model: [studentInstance: studentInstance])
			return
		}

		studentInstance.properties = params

		if (!studentInstance.save(flush: true)) {
			render(view: "edit", model: [studentInstance: studentInstance])
			return
		}

		flash.message = message(code: 'default.updated.message', args: [
			message(code: 'student.label', default: 'Student'),
			studentInstance.id
		])
		redirect(action: "show", id: studentInstance.id)
	}

	def delete() {
		def studentInstance = Student.get(params.id)
		if (!studentInstance) {
			flash.message = message(code: 'default.not.found.message', args: [
				message(code: 'student.label', default: 'Student'),
				params.id
			])
			redirect(action: "list")
			return
		}

		try {
			studentInstance.delete(flush: true)
			flash.message = message(code: 'default.deleted.message', args: [
				message(code: 'student.label', default: 'Student'),
				params.id
			])
			redirect(action: "list")
		}
		catch (DataIntegrityViolationException e) {
			flash.message = message(code: 'default.not.deleted.message', args: [
				message(code: 'student.label', default: 'Student'),
				params.id
			])
			redirect(action: "show", id: params.id)
		}
	}
}
