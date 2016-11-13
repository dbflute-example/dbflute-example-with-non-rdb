<c:import url="${viewPrefix}/common/default_layout.jsp">
<c:param name="contents">
<!-- <main> start main content -->
<div class="contents">
	<h2 class="content-title"><la:caption key="labels.esproduct.detail.title"/></h2>
	<section class="product-result-box">
		<h3 class="content-title-second">Data Form</h3>
		<table>
			<tr>
				<td><label>product</label></td>
				<td>${product.productName}</td>
			</tr>
			<tr>
				<td><label>category</label></td>
				<td>${product.categoryName}</td>
			</tr>
		</table>
	</section>
	<section class="product-search-box">
		<h3 class="content-title-second">Delete Data</h3>
		<la:form method="post" styleClass="product-search-form" action="/esproduct/delete/">
			<la:hidden property="productId"/>
			<la:submit value="Delete"/>
		</la:form>
	</section>
</div>
<!-- </main> end of main content -->
</c:param>
</c:import>
