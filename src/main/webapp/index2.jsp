<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
</head>
<body>
	<form action="${pageContext.request.contextPath}/sourceController/myTest">
		<table width="100%" border="1">
			<tr><th colspan="2"><h2>数据维护页面</h2></th></tr>
			<tr>	
				<td colspan="2" align="center">
					 用户：<input type="text" name="username" value="aaa" style="width: 200px"/>
					 密码：<input type="text" name="userpwd" value="ccc"  style="width: 200px"/>
				</td>
			</tr>
			<tr>
				<td width="20%" align="right">数据维护类型:</td>
				<td align="left">
					<input type="radio" name="radio1" value="1" checked="checked">刷新rdies人员时间
					<input type="radio" name="radio1" value="2">刷新rdies人员基础数据
					<input type="radio" name="radio1" value="3">刷新rdies组织部门
					<input type="radio" name="radio1" value="4">添加黑名单
					<input type="radio" name="radio1" value="5">移除黑名单
					
				</td>
			</tr>
			<tr>	
				<td align="right"></td>
				<td align="left"></td>
			</tr>
			<tr>
				<td align="right">人员数据:</td>
				<td align="left">
					<textarea rows="5" cols="50" name="context"></textarea>
				 	<span style="color: red">*多人请用","号隔开</span>
				</td>
			</tr>
			<tr>
				<td colspan="2" align="center">
					<input type="submit" value="提 交"/>
				</td>
			</tr>
		</table>
		
		
	</form>
</body>
</html>
