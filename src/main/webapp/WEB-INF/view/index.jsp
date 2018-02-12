<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html>
<body>
	<h2>Hello World!</h2>
	userId:${user.userId}
	<br> userName:${user.userName}
	<br> userPassword:${user.userPassword}
	<br> userEmail:${user.userEmail}
	<br>
	<form action="test/myTest.do">
		<button>Login</button>
	</form>
</body>
</html>
