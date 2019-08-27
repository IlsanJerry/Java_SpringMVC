<%@page import="java.util.List"%>
<%@page import="vo.NewsVO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<style type="text/css">
body {
text-align: center;
width : 100%;
margin: 0 auto;
}
header, section, nav {
	width: 80%;
	margin: 10px auto;
	text-align: center;
}

table {margin: 10px auto;}
th {
	border: 3px solid gray;
	text-align: center;
	background: #ddd;
	color: purple;
	text-shadow: 1px 1px 1px #fff;
}

td {
	border-bottom: 1px dotted black;
	text-align: center;
}
td:nth-child(5n+1), th:nth-child(5n+1) {width: 60px;}
td:nth-child(5n+2), th:nth-child(5n+2) {width: 300px;}
td:nth-child(5n+3), th:nth-child(5n+3) {width: 100px;}
td:nth-child(5n+4), th:nth-child(5n+4) {width: 100px;}
td:nth-child(5n+5), th:nth-child(5n+5) {width: 60px;}
.insert {display: none;}
input, textarea {margin: 3px;}
a {text-decoration: underline;}
a:link, a:active, a:visited {color: black;}
a:hover {font-weight: bold;}
h1 {
	text-align: center;
	color: purple;
}
input {text-align: center;}
id {width: 500px;}
.showLine {margin-left: 70%;}
#test_1{width: 500px;}
</style>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<%
		List<NewsVO> list = (List<NewsVO>) request.getAttribute("list");
		NewsVO vo1 = (NewsVO) request.getAttribute("vo");
		List<NewsVO> r_writer = (List<NewsVO>) request.getAttribute("r_writer");
		String action = request.getParameter("action");
	%>
	<h1>뉴스 게시판 =ㅅ=</h1> 
	<table>
		<tr>
			<td>번호</td>
			<td>제목</td>
			<td>작성자</td>
			<td>작성일</td>
			<td>조회수</td>
		</tr>
		<tr>
			<%	//첫 화면 전체 게시판 리스트를 보여주는애 
				if (list != null) {
					for (NewsVO vo : list) {
			%>
		
			<td><%= vo.getId() %></td>
			<td onclick="displayUpdateForm('<%=vo.getId()%>')"><%=vo.getTitle()%></td>
			<td onclick="displaywriter('<%=vo.getWriter()%>')"><%=vo.getWriter()%></td>
			<td class='<%=vo.getId()%>'><%=vo.getWritedate()%></td>
			<td><%=vo.getCnt()%></td>

		</tr>
		<%
			}
		}
		%>
	</table>
	<%
		if (request.getAttribute("msg") != null) {
	%>
	<script>
		alert('${msg}');
	</script>
	<%
		}
	%>
	
		<%
			if (request.getAttribute("readVO") != null) {
				NewsVO vo = (NewsVO) request.getAttribute("readVO");
		%>
		<h2> 제목누르면 나오는 이하 탭들임.</h2>
		<div id="contentForm" style="display:'block'; text-align:center">
			<form method="POST" action="/springnews/newsmain">
				<input type="hidden" name="action" value="update">
				<input type="hidden" name="newsidd" value=<%=vo.getId()%>>
				
				<input id="writer" type="text" name="writer" style="width:300px" value= <%= vo.getWriter() %>><br>
				<input id="title" type="text" name="title" style="width:300px" value= <%= vo.getTitle() %>><br>
				<input id="writedate" type="datetime-local" name="writedate" style="width:300px" value= <%= vo.getWritedate() %>><br>
				<textarea id="content" rows="15" cols="40" name="content"><%= vo.getContent() %></textarea>
				<br> 
				<input type="button" value="확인" onclick="displayContentForm('2')">
				<input type="submit" value="수정">
				<input type="button" onclick="location.href = '/springnews/newsmain?action=delete&newsid=' + <%= vo.getId() %>" value="삭제">
			</form>	
		</div>
		<%
			}
		%>
	
	<!-- http://70.12.113.165:8000
	/springnews/newsmain?action=find & option=title & search=TEST_title
	 -->
	 <!-- http://localhost:8000
	 /springnews/newsmain?action=find&searchType=title&search=TEST_title
	 
	 http://70.12.113.165:8000
	 /springnews/newsmain?action=search&searchType=title&key=TEST_title
	  --><form method="get" action="/springnews/newsmain" style="text-align:center">
		<input type="hidden" name="action" value="search"> 
		<select name="searchType">
			<option value="title">제목</option>
			<option value="content">내용</option>
			<option value="writer">작성자</option>
		</select> 
		<input type="text" name="key"> 
		<input type="submit" value="뉴스검색">
	</form>
	<br>
	<button onclick="location.href='http://localhost:8181/springnews/newsmain'">화면
		홈버튼</button>
	<button id='b' onclick="displayDiv(1)">뉴스 작성</button>
	<script>
		function displayDiv(type) {
			if (type == 1) {
				document.getElementById("write").style.display = "block";
				document.getElementById("update").style.display = "none";
			} else if (type == 2) {
				document.getElementById("update").style.display = "none";
			}
		}
		//function displayUpdateForm(cv) {
		//	location.href = 'news?read=' + cv;
		//}
		function displayUpdateForm(cv) {
			location.href = 'newsmain?action=listOne&read=' + cv;
		}
		
		function deleteNews(cv) {
			location.href = 'newsmain?action=delete&id=' + cv;
		}
		function displaywriter(cv) {
			location.href = 'newsmain?action=search_writer&writer=' + cv;
		}
	</script>
	<%
		if (vo1 != null) {
	%>
	<div id="update">
		<form method="post" action="/springnews/newsmain">
			<input type="hidden" name="action" value="update"> 
			<input style="width: 200px;"type="hidden" name="id" 
					value="<%=vo1.getId()%>"> 
			<input style="width: 200px; height: 30px;" id="writer" type="text" name="writer" 
					value='<%=vo1.getWriter()%>'><br>
			<input style="width: 200px; height: 30px;" id="title" type="text" name="title"
					value='<%=vo1.getTitle()%>'><br>
			<textarea style="width: 200px;  height: 200px;" id="context" name="content">
						<%=vo1.getContent()%></textarea>
			<br> 
			<input id='b' onclick="displayDiv(2)" type="reset" value="확인"> 
			<input id='b' type="submit" value="수정">
			<input id='b' onclick="deleteNews('<%=vo1.getId()%>')" type="reset"
				value="삭제">
		</form>
	</div>
	<%
		}
	%>

	<div id="write" style="display: none">
		<form method="post" action="/springnews/newsmain">
			<input type="hidden" name="action" value="insert"> 
			<input style="width: 200px;"id="writer" type="text" name="writer" 
					placeholder="작성자명을 입력해주세요."><br>
			<input style="width: 200px;" id="title" type="text" name="title" 
					placeholder="제목을 입력해주세요"><br>
			<input id="writedate" type="datetime-local" name="writedate" placeholder="작성일을 입력하세요" style="width:200px"><br>
			<textarea style="width: 200px;" id="context" name="content" 
					placeholder="내용을 입력해주세요"></textarea>
			<br> 
			<input id='b' type="submit" value="저장"> 
			<input id='b' type="reset" value="재작성"> 
			<input id='b' onclick="displayDiv(3)" type="reset" value="취소">
		</form>
	</div>
</body>
</html>