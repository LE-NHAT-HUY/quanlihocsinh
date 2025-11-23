<c:forEach var="item" items="${AdminMenu}">
    <c:if test="${item.ItemLevel == 1}">
        <li class="nav-item">
            <a class="nav-link" href="#">
                <i class="${item.Icon}"></i><span>${item.ItemName}</span>
            </a>

            <ul class="nav-content collapse">
                <c:forEach var="subItem" items="${AdminMenu}">
                    <c:if test="${subItem.ParentLevel == item.AdminMenulD}">
                        <li>
                            <a href="#">
                                <i class="${subItem.Icon}"></i><span>${subItem.ItemName}</span>
                            </a>
                        </li>
                    </c:if>
                </c:forEach>
            </ul>
        </li>
    </c:if>
</c:forEach>
