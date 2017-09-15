<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset="utf-8">
<link rel="stylesheet"
	href="//netdna.bootstrapcdn.com/bootstrap/3.0.0/css/bootstrap.min.css">
<title>Potwierdzenie zamówienia</title>
</head>
<body>
	<section>
		<div class="jumbotron">
			<div class="container">
				<h1>Zamówienie</h1>
				<p>Potwierdzenie zamówienia</p>
			</div>
		</div>
	</section>
	<div class="container">
		<div class="row">
			<form:form modelAttribute="order" class="form-horizontal">
				<input type="hidden" name="_flowExecutionKey"
					value="${flowExecutionKey}" />
				<div
					class="well col-xs-10 col-sm-10 col-md-6 col-xsoffset-1 col-sm-offset-1 col-md-offset-3">
					<div class="text-center">
						<h1>Paragon</h1>
					</div>
					<div class="row">
						<div class="col-xs-6 col-sm-6 col-md-6">
							<address>
								<strong>Adres do wysyłki</strong> <br>${order.shippingDetail.name}<br>
								<!-- W podobny sposób umieść wartości pozostałych pól obiektu order.shippingDetail. W tym listingu zostały pominięte. -->
							</address>
						</div>
					</div>
					
					<button id="back" class="btn btn-default"
						name="_eventId_backToCollectShippingDetail">wstecz</button>
					<button type="submit" class="btn btn-success"
						name="_eventId_orderConfirmed">
						Zatwierdź <span class="glyphiconglyphiconchevron-right"></span>
					</button>
					<button id="btnCancel" class="btn btn-default"
						name="_eventId_cancel">Anuluj</button>
				</div>
		</div>
		</form:form>
	</div>
	</div>
</body>
</html>