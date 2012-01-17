package timezra.course_registry

import org.springframework.context.MessageSourceResolvable

enum Season implements MessageSourceResolvable {
	Winter, Spring, Summer, Fall

	@Override Object[] getArguments() {
		[]
	}

	@Override String[] getCodes() {
		[
			"${getClass().name}.${name()}"
		]
	}

	@Override String getDefaultMessage() {
		name()
	}
}
