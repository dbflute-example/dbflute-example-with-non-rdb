<c:import url="${viewPrefix}/common/default_layout.jsp">
<c:param name="contents">
<!-- <main> start main content -->
<div class="contents">
	<h2 class="content-title"><la:caption key="labels.esproduct.detail.title"/></h2>
	<section class="product-result-box">
		<h3 class="content-title-second">Data Form</h3>
		<la:form action="/esproduct/add/add/">
		<table>
			<tr>
				<td><label>Name</label></td>
				<td><la:text property="productName"/></td>
			</tr>
			<tr>
				<td><label>Description</label></td>
				<td><la:text property="productDescription"/></td>
			</tr>
			<tr>
				<td><label>Regular Price</label></td>
				<td><la:text property="regularPrice"/></td>
			</tr>
			<tr>
				<td><label>Handle Code</label></td>
				<td><la:select property="productHandleCode">
						<la:option value="BILLYJOEL-01">BILLYJOEL-01"</la:option>
						<la:option value="BILLYJOEL-02">BILLYJOEL-02"</la:option>
						<la:option value="BILLYJOEL-03">BILLYJOEL-03"</la:option>
					</la:select></td>
			</tr>
			<tr>
				<td><label>Category Code</label></td>
				<td><la:select property="productCategoryCode">
						<la:option value="MCD">MCD</la:option>
						<la:option value="INS">INS</la:option>
						<la:option value="HEB">HEB</la:option>
					</la:select></td>
			</tr>
			<tr>
				<td><label>StatusCode</label></td>
				<td><la:select property="productStatusCode">
						<la:option value="ONS">ONS</la:option>
						<la:option value="PST">PST</la:option>
					</la:select></td>
			</tr>
		</table>
		<la:submit value="Add"/>
		</la:form>
	</section>
</div>
<!-- </main> end of main content -->
</c:param>
</c:import>
