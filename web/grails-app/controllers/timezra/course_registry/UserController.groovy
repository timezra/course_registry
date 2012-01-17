package timezra.course_registry

import static org.apache.commons.lang.StringUtils.uncapitalize

class UserController {
	def login = {}

	def authenticate = {
		def user = User.findByEmailAndPassword params.email, params.password
		if(user) {
			session.user = user
			flash.message = "${message code: 'user.greeting', args: [user.name]}"
			redirect controller: uncapitalize(user.class.simpleName), action:"show", id: user.id
		} else {
			flash.message = "${message code: 'user.find.failed', args: [params.email]}"
			redirect action:"login", params: params
		}
	}

	def logout = {
		flash.message = "${message code: 'user.farewell', args: [session.user?.name]}"
		session.user = null
		redirect uri: "/"
	}
}
