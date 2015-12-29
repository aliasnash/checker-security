<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://www.joda.org/joda/time/tags" prefix="joda"%>

<tiles:insertDefinition name="defaultTemplate">
	<tiles:putAttribute name="title" value="Checker Task Recreate" />
	<tiles:putAttribute name="body">
		<div class="panel panel-default">
			<div class="panel-heading">Задачи на переработку</div>
			<div class="panel-body">
				<table class="table">
					<thead>
						<tr>
							<th style="vertical-align: middle" class="col-md-1"># (IDTA)</th>
							<th style="vertical-align: middle" class="col-md-2">Пользователь</th>
							<th style="vertical-align: middle" class="col-md-3">Наименование</th>
							<th style="vertical-align: middle" class="col-md-3">Описание</th>
							<th style="vertical-align: middle" class="col-md-2">Дата</th>
							<th style="vertical-align: middle" class="col-md-1">Статус</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${taskFailList}" var="taskFail" varStatus="status">
							<tr>
								<td>${status.index + 1}&nbsp;(${taskFail.idTasksArticle})</td>
								<td>${taskFail.taskArticle.tasks.user.title}</td>
								<td>${taskFail.taskArticle.article.caption}</td>
								<td>${taskFail.description}</td>
								<td><joda:format value="${taskFail.dateAdded}" style="SM" /></td>
								<td>${taskFail.taskArticle.taskStatus.status}</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</div>
	</tiles:putAttribute>
</tiles:insertDefinition>



