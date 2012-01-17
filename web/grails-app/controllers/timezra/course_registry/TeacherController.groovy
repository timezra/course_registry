package timezra.course_registry

import org.springframework.dao.DataIntegrityViolationException

class TeacherController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list() {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [teacherInstanceList: Teacher.list(params), teacherInstanceTotal: Teacher.count()]
    }

    def create() {
        [teacherInstance: new Teacher(params)]
    }

    def save() {
        def teacherInstance = new Teacher(params)
        if (!teacherInstance.save(flush: true)) {
            render(view: "create", model: [teacherInstance: teacherInstance])
            return
        }

		flash.message = message(code: 'default.created.message', args: [message(code: 'teacher.label', default: 'Teacher'), teacherInstance.id])
        redirect(action: "show", id: teacherInstance.id)
    }

    def show() {
        def teacherInstance = Teacher.get(params.id)
        if (!teacherInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'teacher.label', default: 'Teacher'), params.id])
            redirect(action: "list")
            return
        }

        [teacherInstance: teacherInstance]
    }

    def edit() {
        def teacherInstance = Teacher.get(params.id)
        if (!teacherInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'teacher.label', default: 'Teacher'), params.id])
            redirect(action: "list")
            return
        }

        [teacherInstance: teacherInstance]
    }

    def update() {
        def teacherInstance = Teacher.get(params.id)
        if (!teacherInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'teacher.label', default: 'Teacher'), params.id])
            redirect(action: "list")
            return
        }

        if (params.version) {
            def version = params.version.toLong()
            if (teacherInstance.version > version) {
                teacherInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'teacher.label', default: 'Teacher')] as Object[],
                          "Another user has updated this Teacher while you were editing")
                render(view: "edit", model: [teacherInstance: teacherInstance])
                return
            }
        }

        teacherInstance.properties = params

        if (!teacherInstance.save(flush: true)) {
            render(view: "edit", model: [teacherInstance: teacherInstance])
            return
        }

		flash.message = message(code: 'default.updated.message', args: [message(code: 'teacher.label', default: 'Teacher'), teacherInstance.id])
        redirect(action: "show", id: teacherInstance.id)
    }

    def delete() {
        def teacherInstance = Teacher.get(params.id)
        if (!teacherInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'teacher.label', default: 'Teacher'), params.id])
            redirect(action: "list")
            return
        }

        try {
            teacherInstance.delete(flush: true)
			flash.message = message(code: 'default.deleted.message', args: [message(code: 'teacher.label', default: 'Teacher'), params.id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
			flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'teacher.label', default: 'Teacher'), params.id])
            redirect(action: "show", id: params.id)
        }
    }
}
