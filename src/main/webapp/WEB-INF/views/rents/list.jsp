<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<!DOCTYPE html>
<html>
<%@include file="/WEB-INF/views/common/head.jsp"%>
<body class="hold-transition skin-blue sidebar-mini">
<div class="wrapper">

    <%@ include file="/WEB-INF/views/common/header.jsp" %>
    <!-- Left side column. contains the logo and sidebar -->
    <%@ include file="/WEB-INF/views/common/sidebar.jsp" %>

    <!-- Content Wrapper. Contains page content -->
    <div class="content-wrapper">
        <!-- Content Header (Page header) -->
        <section class="content-header">
            <h1>
                Reservations
                <a class="btn btn-primary" href="${pageContext.request.contextPath}/reservation/create">Ajouter</a>
            </h1>
        </section>

        <!-- Main content -->
        <section class="content">
            <div class="row">
                <div class="col-md-12">
                    <div class="box">
                        <div class="box-body no-padding">
                            <table class="table table-striped">
                                <tr>
                                    <th style="width: 10px">#</th>
                                    <th>Voiture</th>
                                    <th>Client</th>
                                    <th>Debut</th>
                                    <th>Fin</th>
                                    <th>Action</th>
                                </tr>
                                <tr>

                                <c:forEach items="${reservation}" var="reservation" varStatus="loop">
                                    <td>${reservation.id}.</td>
                                    <td>${vehicule[loop.index].constructeur} ${vehicule[loop.index].modele}</td>
                                    <td>${client[loop.index].prenom} ${client[loop.index].nom}</td>
                                    <td>${reservation.debut}</td>
                                    <td>${reservation.fin}</td>
                                    <td>
                                        <a class="btn btn-primary" href="${pageContext.request.contextPath}/reservation/details?id=${reservation.id}">
                                            <i class="fa fa-play"></i>
                                        </a>
                                        <a class="btn btn-success" href="${pageContext.request.contextPath}/reservation/edit?id=${reservation.id}">
                                            <i class="fa fa-edit"></i>
                                        </a>
                                        <a class="btn btn-danger" href="${pageContext.request.contextPath}/reservation/delete?id=${reservation.id}" onclick="return confirmDelete();">
                                            <i class="fa fa-trash"></i>
                                        </a>
                                    </td>
                                </tr>
                                </c:forEach>
                            </table>
                        </div>
                        <!-- /.box-body -->
                    </div>
                    <!-- /.box -->
                </div>
                <!-- /.col -->
            </div>
        </section>
        <!-- /.content -->
        <script>
            function confirmDelete() {
                return confirm("Etes-vous sur de vouloir supprimer cette reservation ?");
            }
        </script>
    </div>

    <%@ include file="/WEB-INF/views/common/footer.jsp" %>
</div>
<!-- ./wrapper -->

<%@ include file="/WEB-INF/views/common/js_imports.jsp" %>
</body>
</html>
