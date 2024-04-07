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
                            <h3 class="box-title">Modifier les informations du vehicule</h3>
                        </div>
                        <form role="form" method="post">
                            <div class="box-body">
                                <div class="form-group">
                                    <label for="constructeur">Constructeur</label>
                                    <input type="text" class="form-control" id="constructeur" name="constructeur" value="${vehicule.constructeur}">
                                </div>
                                <div class="form-group">
                                    <label for="modele">Modele</label>
                                    <input type="text" class="form-control" id="modele" name="modele" value="${vehicule.modele}">
                                </div>
                                <div class="form-group">
                                    <label for="nb_places">Nombre de places</label>
                                    <input type="number" class="form-control" id="nb_places" name="nb_places" value="${vehicule.nb_places}">
                                </div>
                            </div>
                            <div class="box-footer">
                                <button type="submit" class="btn btn-primary" href="${pageContext.request.contextPath}/vehicules/list">Enregistrer</button>
                                <a href="${pageContext.request.contextPath}/vehicules/list" class="btn btn-default">Annuler</a>
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
