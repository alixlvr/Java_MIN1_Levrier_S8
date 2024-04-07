<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<%@include file="/WEB-INF/views/common/head.jsp"%>
<body class="hold-transition skin-blue sidebar-mini">
<div class="wrapper">
    <%@ include file="/WEB-INF/views/common/header.jsp" %>
    <%@ include file="/WEB-INF/views/common/sidebar.jsp" %>
    <div class="content-wrapper">
        <section class="content">
            <div class="row">
                <div class="col-md-12">
                    <div class="box box-primary">
                        <div class="box-header with-border">
                            <h3 class="box-title">Modifier les informations de la reservation</h3>
                        </div>
                        <form role="form" method="post">
                            <div class="box-body">
                                <div class="form-group">
                                    <label for="client_id">Id du client</label>
                                    <input type="number" class="form-control" id="client_id" name="client_id" value="${reservation.client_id}">
                                </div>
                                <div class="form-group">
                                    <label for="vehicule_id">Id du vehicule</label>
                                    <input type="number" class="form-control" id="vehicule_id" name="vehicule_id" value="${reservation.vehicule_id}" >
                                </div>
                                <div class="form-group">
                                    <label for="debut">Date de debut de la reservation</label>
                                    <input type="date" class="form-control" id="debut" name="debut" value="${reservation.debut}">
                                </div>
                                <div class="form-group">
                                    <label for="fin">Date de fin de la reservation</label>
                                    <input type="date" class="form-control" id="fin" name="fin" value="${reservation.fin}">
                                </div>
                            </div>
                            <div class="box-footer">
                                <button type="submit" class="btn btn-primary" href="${pageContext.request.contextPath}/reservation/list">Enregistrer</button>
                                <a href="${pageContext.request.contextPath}/reservation/list" class="btn btn-default">Annuler</a>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </section>
    </div>
    <%@ include file="/WEB-INF/views/common/footer.jsp" %>
</div>
<%@ include file="/WEB-INF/views/common/js_imports.jsp" %>
</body>
</html>
