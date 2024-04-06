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
                            <h3 class="box-title">Modifier les informations du client</h3>
                        </div>
                        <form role="form" action="${pageContext.request.contextPath}/client/list" method="post">
                            <div class="box-body">
                                <div class="form-group">
                                    <label for="prenom">Prenom</label>
                                    <input type="text" class="form-control" id="prenom" name="prenom" value="${client.prenom}">
                                </div>
                                <div class="form-group">
                                    <label for="nom">Nom</label>
                                    <input type="text" class="form-control" id="nom" name="nom" value="${client.nom}">
                                </div>
                                <div class="form-group">
                                    <label for="email">Email</label>
                                    <input type="email" class="form-control" id="email" name="email" value="${client.email}">
                                </div>
                                <div class="form-group">
                                    <label for="naissance">Naissance</label>
                                    <input type="date" class="form-control" id="naissance" name="naissance" value="${client.naissance}">
                                </div>
                            </div>
                            <div class="box-footer">
                                <button type="submit" class="btn btn-primary">Enregistrer</button>
                                <a href="${pageContext.request.contextPath}/client/list" class="btn btn-default">Annuler</a>
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
