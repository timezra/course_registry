package timezra.course_registry


class User {

	String name
	String email
	String password
	static hasMany = [courses: Course]

	static constraints = {
		name(blank: false)
		email(email: true, blank:false, unique: true)
		password(blank: false, password: true)
	}

	@Override String toString() {
		name
	}
}
