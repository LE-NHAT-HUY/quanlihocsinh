<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<h2>Chỉnh sửa Cohort</h2>
<form:form method="post" modelAttribute="cohort">
    <form:hidden path="cohortID"/>
    <label>CohortName:</label>
    <form:input path="cohortName"/><br/>
    <label>StartYear:</label>
    <form:input path="startYear"/><br/>
    <label>EndYear:</label>
    <form:input path="endYear"/><br/>
    <label>IsActive:</label>
    <form:checkbox path="isActive"/><br/>
    <input type="submit" value="Update"/>
</form:form>
