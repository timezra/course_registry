<html>
	<head>
		<title><g:message code="home.title" /></title>
		<meta name="layout" content="main" />
		<style type="text/css" media="screen">
			#pageBody {
			   margin-left: 280px;
			   margin-right: 20px;
			 }
		</style>
	</head>
	<body>
	   <div id="pageBody" class="dialog">
	     <p id="greeting">
	       <g:message code="home.welcome.message" />
	     </p>
         <p>
           <g:message code="home.question.prefix" />
           <g:link elementId="student_link" controller="student" action="create"><g:message code="home.student.label" /></g:link>
           <g:message code="home.choice.label" />
           <g:link elementId="teacher_link" controller="teacher" action="create"><g:message code="home.teacher.label" /></g:link>
           <g:message code="home.question.mark" />
         </p>
        <br />
        <p>
            <g:message code="home.otherwise" /> <g:link elementId="login_link" controller="user" action="login"><g:message code="home.login" /></g:link>.
        </p>
	   </div>
	</body>
</html>