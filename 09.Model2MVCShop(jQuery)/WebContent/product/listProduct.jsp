<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.util.*" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html>
<head>
	<title>상품 목록조회</title>

	<link rel="stylesheet" href="/css/admin.css" type="text/css">
	<script src="http://code.jquery.com/jquery-2.1.4.min.js"></script>
	<script type="text/javascript">

		function fncGetUserList(currentPage){
			
			$("#currentPage").val(currentPage) ;
			$("form").attr("method","POST").attr("action","/product/listProduct?menu=${param.menu}").submit();
			
		}
		
		$(function(){
			
			$( ".ct_btn01:contains('검색')" ).on("click", function(){
				fncGetUserList('1');
			});
			
			$( ".ct_list_pop td:nth-child(3)" ).on("click",function(){
				if($(this).children(".tranCode").val()=='0'){
				if(${param.menu=='manage'}){
					self.location="/product/updateProductView.do?prodNo="+$(this).children().val()+"&menu=${param.menu}";
				}else if(${param.menu=='search'}){
					self.location="/product/getProduct?prodNo="+$(this).children().val()+"&menu=${param.menu}";
				}
				}
			});
			$(".ct_list_pop td:nth-child(9)").on("click",function(){
				var prodNo = $( ".ct_list_pop td:nth-child(3)" ).children().val();
				alert($(this).children(".tranCode").val())
				if($(this).children(".tranCode").val().trim()=='1'){
					alert($(this).children(".tranCode").val())
					self.location="/purchase/updateTranCodeByProd?prodNo="+prodNo+"&tranCode=2";
				}
			})
			
			$( ".ct_list_pop td:nth-child(3)" ).css("color","red");
			$("h7").css("color","red");
			
			$(".ct_list_pop:nth-child(4n+6)" ).css("background-color" , "whitesmoke");
		});

	</script>
</head>

	<body bgcolor="#ffffff" text="#000000">

	<div style="width:98%; margin-left:10px;">

	<form name="detailForm">

<table width="100%" height="37" border="0" cellpadding="0"	cellspacing="0">
<input type="hidden" id="menu" value="${param.menu}"/>
		<tr>
			<td width="15" height="37">
				<img src="/images/ct_ttl_img01.gif" width="15" height="37"/>
			</td>
			<td background="/images/ct_ttl_img02.gif" width="100%" style="padding-left:10px;">
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td width="93%" class="ct_ttl01">
						
							<c:if test="${!empty param.menu}">
								<c:choose>
									<c:when test="${param.menu=='manage'}">
										상품관리
									</c:when>
									<c:when test="${param.menu=='search'}">
										상품목록
									</c:when>
								</c:choose>
							</c:if>
						</td>
					</tr>
				</table>
			</td>
			<td width="12" height="37">
				<img src="/images/ct_ttl_img03.gif" width="12" height="37"/>
			</td>
		</tr>
</table>


<table width="100%" border="0" cellspacing="0" cellpadding="0" style="margin-top:10px;">
	<tr>
		<td align="right">
			<select name="selectCondition" class="ct_input_g" style="width:80px">
				<option value="0" ${! empty search.searchCondition && search.searchCondition==0 ? "selected" : "" }>상품번호</option>
				<option value="1" ${! empty search.searchCondition && search.searchCondition==1 ? "selected" : "" }>상품명</option>
				<option value="2" ${! empty search.searchCondition && search.searchCondition==2 ? "selected" : "" }>상품가격</option>
			</select>
			<input type="text" name="searchKeyword" value="${! empty search.searchKeyword ? search.searchKeyword : ""}" class="ct_input_g" style="width:200px; height:19px" />
	</td>
		<td align="right" width="70">
			<table border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td width="17" height="23">
						<img src="/images/ct_btnbg01.gif" width="17" height="23">
					</td>
					<td background="/images/ct_btnbg02.gif" class="ct_btn01" style="padding-top:3px;">검색</td>
					<td width="14" height="23">
						<img src="/images/ct_btnbg03.gif" width="14" height="23">
					</td>
				</tr>
			</table>
		</td>
	</tr>
</table>


<table width="100%" border="0" cellspacing="0" cellpadding="0" style="margin-top:10px;">
	<tr>
		<td colspan="11" >전체${resultPage.totalCount}, 현재${resultPage.currentPage} 페이지</td>
	</tr>
	<tr>
		<td class="ct_list_b" width="100">No</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b" width="150">상품명</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b" width="150">가격</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b">등록일</td>	
		<td class="ct_line02"></td>
		<td class="ct_list_b">현재상태</td>	
	</tr>
	<tr>
		<td colspan="11" bgcolor="808285" height="1"></td>
	</tr>
	
	<c:forEach var="product" items="${list}">
		<c:set var="i" value="${ i+1 }"/>
		<tr class="ct_list_pop">
		<td align="center">${ i }</td>
		<td></td>
			<c:if test="${param.menu == 'manage'}">
				<td align="left">
						${product.prodName}
				<input type="hidden" name="prodNo" value="${product.prodNo}"/>
				<input type="hidden" class="tranCode" value="${product.proTranCode}"/>
				</td>
			</c:if>
			<c:if test="${param.menu=='search'}">
				<td align="left">	
						${product.prodName}
				<input type="hidden" class="prodNo" value="${product.prodNo}"/>
				<input type="hidden" class="tranCode" value="${product.proTranCode}"/>
				</td>
			</c:if>
		<td></td>
		<td align="left">${product.price }</td>
		<td></td>
		<td align="left">${product.regDate }</td>
		<td></td>
		<td align="left">
		<c:if test="${param.menu == 'manage' }">
			<c:choose>
				<c:when test="${product.proTranCode.charAt(0) == '1'.charAt(0) }">
					구매완료<%-- <a href="/purchase/updateTranCodeByProd?prodNo=${product.prodNo}&tranCode=2">배송하기</a> --%>
					<input type="hidden" class="tranCode" value="${product.proTranCode}">배송하기</span>
				</c:when>
				<c:when test="${product.proTranCode.charAt(0) == '2'.charAt(0) }">배송중</c:when>
				<c:when test="${product.proTranCode.charAt(0) == '3'.charAt(0) }">배송완료</c:when>
				<c:otherwise>
					판매중
				</c:otherwise>
			</c:choose>
		</c:if>
		<c:if test="${param.menu == 'search' }">
			<c:choose>
				<c:when test="${product.proTranCode == '0'}">
					판매중
				</c:when>
				<c:otherwise>
					재고없음
				</c:otherwise>
			</c:choose>
		</c:if>
		</td>
	</tr>
	</c:forEach>
</table>

<table width="100%" border="0" cellspacing="0" cellpadding="0" style="margin-top:10px;">
	<tr>
		<td align="center">
			<input type="hidden" id="currentPage" name="currentPage" value=""/>
			
			<jsp:include page="../common/pageNavigator.jsp"/>

    	</td>
	</tr>
</table>
<!--  페이지 Navigator 끝 -->

</form>

</div>
</body>
</html>
