<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'test.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<link href="CSS/bootstrap.css" rel="stylesheet">
	<script src="<%=basePath%>script/jquery-2.2.3.js"></script>
	<script src="<%=basePath%>script/jquery.form.js"></script>
	<script src="<%=basePath%>script/bootstrap.js"></script>

  </head>
  
  <script type="text/javascript">
   $(function(){
         $('#myTabs li:eq(0) a').click(function (e) {
         e.preventDefault();
         $(this).tab('show');
        });
        
        $('#myTabs li:eq(1) a').click(function(e){
          e.preventDefault();
          $(this).tab('show');
        });
        
         $('#myTabs li:eq(2) a').click(function(e){
          e.preventDefault();
          $(this).tab('show');
          
          $('#myTabs li:eq(3) a').click(function(e){
          e.preventDefault();
          $(this).tab('show');
        });
          
        });
        

   });
</script>
  
  <body>
  
     <div class="book-comment">  <!-- comment -->
    
     <ul class="nav nav-tabs" role="tablist" id="myTabs">
    <li role="presentation" class="active"><a href="#all" aria-controls="all" role="tab" data-toggle="tab">全部评论</a></li>
    <li role="presentation"><a href="#good" aria-controls="good" role="tab" data-toggle="tab">好评</a></li>
    <li role="presentation"><a href="#general" aria-controls="general" role="tab" data-toggle="tab">中评</a></li>
    <li role="presentation"><a href="#poor" aria-controls="poor" role="tab" data-toggle="tab">差评</a></li>
    </ul>
    

  <!-- Tab panes -->
  <div class="tab-content">
    <div role="tabpanel" class="tab-pane fade in active" id="all">
      <table>
       
      </table>
    </div>
    <div role="tabpanel" class="tab-pane fade" id="good">BB</div>
    <div role="tabpanel" class="tab-pane fade" id="general">CC</div>
    <div role="tabpanel" class="tab-pane fade" id="poor">DD</div>
  </div>
         
</div> <!-- comment end -->



  </body>
</html>
