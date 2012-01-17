<%@ page import="timezra.course_registry.Course" %>



<div class="fieldcontain ${hasErrors(bean: courseInstance, field: 'name', 'error')} required">
	<label for="name">
		<g:message code="course.name.label" default="Name" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="name" required="" value="${courseInstance?.name}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: courseInstance, field: 'description', 'error')} required">
	<label for="description">
		<g:message code="course.description.label" default="Description" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="description" required="" value="${courseInstance?.description}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: courseInstance, field: 'credits', 'error')} required">
	<label for="credits">
		<g:message code="course.credits.label" default="Credits" />
		<span class="required-indicator">*</span>
	</label>
	<g:field type="number" name="credits" min="1" max="5" required="" value="${fieldValue(bean: courseInstance, field: 'credits')}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: courseInstance, field: 'semester', 'error')} ">
	<label for="semester">
		<g:message code="course.semester.label" default="Semester" />
		
	</label>
	<g:select id="semester" name="semester.id" from="${timezra.course_registry.Semester.list()}" optionKey="id" value="${courseInstance?.semester?.id}" class="many-to-one" noSelection="['null': '']"/>
</div>

<div class="fieldcontain ${hasErrors(bean: courseInstance, field: 'timeslots', 'error')} ">
	<label for="timeslots">
		<g:message code="course.timeslots.label" default="Timeslots" />
		
	</label>
	<g:select name="timeslots" from="${timezra.course_registry.Timeslot.list()}" multiple="multiple" optionKey="id" size="5" value="${courseInstance?.timeslots*.id}" class="many-to-many"/>
</div>

<div class="fieldcontain ${hasErrors(bean: courseInstance, field: 'prerequisites', 'error')} ">
	<label for="prerequisites">
		<g:message code="course.prerequisites.label" default="Prerequisites" />
		
	</label>
	<g:select name="prerequisites" from="${timezra.course_registry.Course.list()}" multiple="multiple" optionKey="id" size="5" value="${courseInstance?.prerequisites*.id}" class="many-to-many"/>
</div>

<div class="fieldcontain ${hasErrors(bean: courseInstance, field: 'teacher', 'error')} required">
	<label for="teacher">
		<g:message code="course.teacher.label" default="Teacher" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="teacher" name="teacher.id" from="${timezra.course_registry.Teacher.list()}" optionKey="id" required="" value="${courseInstance?.teacher?.id}" class="many-to-one"/>
</div>

