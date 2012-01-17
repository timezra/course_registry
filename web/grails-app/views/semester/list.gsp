
<%@ page import="timezra.course_registry.Semester" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'semester.label', default: 'Semester')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-semester" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="list-semester" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
				<thead>
					<tr>
					
						<g:sortableColumn property="year" title="${message(code: 'semester.year.label', default: 'Year')}" />
					
						<g:sortableColumn property="season" title="${message(code: 'semester.season.label', default: 'Season')}" />
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${semesterInstanceList}" status="i" var="semesterInstance">
				    <g:set var="link_location" value="${createLink(action: 'show', id: semesterInstance.id)}"/>
					<tr class="linked ${(i % 2) == 0 ? 'even' : 'odd'}" onclick="location.href='${link_location}'">
					
						<td><g:link action="show" id="${semesterInstance.id}">${semesterInstance.year}</g:link></td>
					
						<td><g:link action="show" id="${semesterInstance.id}">${fieldValue(bean: semesterInstance, field: "season")}</g:link></td>
					
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${semesterInstanceTotal}" />
			</div>
		</div>
	</body>
</html>
