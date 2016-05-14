<%@ page language="java" contentType="text/html; charset=UTF-8"
         import="com.lagou.platform.forbid.controller.RichTextEditorController"
         pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>
<%
    request.setCharacterEncoding( "utf-8" );
    response.setHeader("Content-Type" , "text/html");
    out.write( RichTextEditorController.uEditorUpload(request) );
%>