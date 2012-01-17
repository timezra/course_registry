<%@ page import="timezra.course_registry.Course" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'course.label', default: 'Course')}" />
		<g:set var="semesterEntityName" value="${message(code: 'semester.label', default: 'Semester')}" />
		<g:set var="timeslotEntityName" value="${message(code: 'timeslot.label', default: 'Timeslot')}" />
		<g:set var="returnPage" value="${createLink(action: actionName, params: ['teacher.id': params.'teacher.id', 'semester.id': params.'semester.id', 'timeslots': params.'timeslots']) - request.contextPath}" />
		<title><g:message code="default.create.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#create-course" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link elementId="add_semester_link" class="create" action="create" controller="semester" params="[returnPage: returnPage]"><g:message code="default.add.label" args="[semesterEntityName]" /></g:link></li>
				<li><g:link elementId="add_timeslot_link" class="create" action="create" controller="timeslot" params="[returnPage: returnPage]"><g:message code="default.add.label" args="[timeslotEntityName]" /></g:link></li>
			</ul>
		</div>
		<div id="create-course" class="content scaffold-create" role="main">
			<h1><g:message code="default.create.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<g:hasErrors bean="${courseInstance}">
			<ul class="errors" role="alert">
				<g:eachError bean="${courseInstance}" var="error">
				<li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
				</g:eachError>
			</ul>
			</g:hasErrors>
			<g:form action="save" >
				<fieldset class="form">
					<g:render template="form"/>
				</fieldset>
				<fieldset class="buttons">
					<g:submitButton name="create" class="save" value="${message(code: 'default.button.create.label', default: 'Create')}" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
