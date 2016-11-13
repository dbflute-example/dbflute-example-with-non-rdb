<c:import url="${viewPrefix}/common/default_layout.jsp">
<c:param name="contents">
<!-- <main> start main content -->
<div class="contents">
	<h2 class="content-title"><la:caption key="labels.esproduct.list.title"/></h2>
	<c:if test="${totalCount == 0}">
	<section class="product-search-box">
		<h3 class="content-title-second">Import Data From DB</h3>
		<la:form method="post" styleClass="product-search-form" action="/esproduct/import/">
			<la:submit value="labels.import"/>
		</la:form>
	</section>
	</c:if>
	<section class="product-search-box">
		<h3 class="content-title-second">Search Condition</h3>
		<la:form method="GET" styleClass="product-search-form" action="/esproduct/list/">
			<la:errors/>
			<ul class="product-search-condition-list">
				<li><span>Name</span><la:text property="productName"/></li>
				<li><span>Purchased Member</span><la:text property="purchaseMemberName"/></li>
				<li>
					<span><la:caption key="labels.esproductStatus"/></span> 
					<la:select property="productStatus">
						<la:option value="" key="labels.listbox.caption.tell"/>
						<la:optionCls name="ProductStatus"/>
					</la:select>
				</li>
			</ul>
			<la:submit value="labels.search"/>
		</la:form>
	</section>
	<section class="product-result-box">
		<h3 class="content-title-second">Search Results</h3>
		<table class="product-list-tbl">
			<thead>
				<tr>
					<th>ID</th>
					<th>Product Name</th>
					<th>Status</th>
					<th>Category</th>
					<th>Price</th>
					<th>Latest Purchase Date</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="bean" items="${beans}">
				<tr>
					<td>${f:h(bean.productId)}</td>
					<td><la:link href="/esproduct/detail/${f:h(bean.productId)}">${f:h(bean.productName)}</la:link></td>
					<td>${f:h(bean.productStatus)}</td>
					<td>${f:h(bean.productCategory)}</td>
					<td>${f:h(bean.regularPrice)}</td>
					<td>${f:h(bean.latestPurchaseDate)}</td>
				</tr>
				</c:forEach>
			</tbody>
		</table>
		<la:link href="/esproduct/add/">Create New</la:link>
		<section class="product-list-paging-box">
			<c:import url="${viewPrefix}/common/paging_navi.jsp"/>
		</section>
	</section>
</div>
<!-- </main> end of main content -->
</c:param>
</c:import>
