<%@ page import="timezra.course_registry.Teacher" %>



<div class="fieldcontain ${hasErrors(bean: teacherInstance, field: 'name', 'error')} required">
	<label for="name">
		<g:message code="teacher.name.label" default="Name" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="name" required="" value="${teacherInstance?.name}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: teacherInstance, field: 'email', 'error')} required">
	<label for="email">
		<g:message code="teacher.email.label" default="Email" />
		<span class="required-indicator">*</span>
	</label>
	<g:field type="email" name="email" required="" value="${teacherInstance?.email}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: teacherInstance, field: 'password', 'error')} required">
	<label for="password">
		<g:message code="teacher.password.label" default="Password" />
		<span class="required-indicator">*</span>
	</label>
	<g:field type="password" name="password" required="" value="${teacherInstance?.password}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: teacherInstance, field: 'courses', 'error')} ">
	<label for="courses">
		<g:message code="teacher.courses.label" default="Courses" />
		
	</label>
	
<ul class="one-to-many">
<g:each in="${teacherInstance?.courses?}" var="c">
    <li><g:link controller="course" action="show" id="${c.id}">${c?.encodeAsHTML()}</g:link></li>
</g:each>
<li class="add">
<g:link controller="course" action="create" params="['teacher.id': teacherInstance?.id]">${message(code: 'default.add.label', args: [message(code: 'course.label', default: 'Course')])}</g:link>
</li>
</ul>

</div>

