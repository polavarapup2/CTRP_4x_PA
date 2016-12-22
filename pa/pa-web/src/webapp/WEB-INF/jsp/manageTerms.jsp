<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<title><fmt:message key="manageterms.page.title" /></title>
<s:head />
<script type="text/javascript" language="javascript">
	jQuery(document).ready(function() {

		jQuery('#nav li').hover(function() {
			//show its submenu
			jQuery('ul', this).slideDown(100);

		}, function() {
			//hide its submenu
			jQuery('ul', this).slideUp(100);
		});

	});

	function submitForm(btnSelected) {
		var form = document.forms[0];
		if (btnSelected != 'accept') {
			form.action = "logout.action";
		}
		form.submit();
	}
</script>
</head>
<body>
	<c:set var="topic" scope="request" value="manageterms" />
	<h1>
		<fmt:message key="manageterms.page.title" />
	</h1>
	<pa:sucessMessage />
	<pa:failureMessage />
	<table
		style="vertical-align: center; margin-top: 80px; margin-left: 100px">
		<tr>
			<td style="width: 300px"><ul id="nav">
					<li class='root'><span class="btn_img">Manage NCIt
							Intervention Terms &nabla;</span>
						<ul>
							<li class="action"><a
								href="manageTermscreateIntervention.action"> <fmt:message
										key="manageTerms.button.create" />
							</a></li>
							<li  class="action" ><a
								href="manageTermssearchIntervention.action?searchStart=true">
									<fmt:message key="manageTerms.button.import" />
							</a></li>
							<li  class="action"><a
								href="http://ncitermform.nci.nih.gov/ncitermform/"
								target="_blank"><fmt:message
										key="manageTerms.button.requestForm" /> </a></li>
						</ul>
					</li>
				</ul></td>
			<td style="width: 300px">
				<ul id="nav">
					<li  class='root'><span class="btn_img">Manage NCIt
							Disease Terms &nabla;</span>
						<ul>
							<li class="action"><a
								href="manageTermscreateDisease.action"> <fmt:message
										key="manageTerms.button.create" />
							</a></li>
							<li class="action"><a
								href="manageTermssearchDisease.action?searchStart=true"> <fmt:message
										key="manageTerms.button.import" />
							</a></li>
							<li class="action"><a
								href="http://ncitermform.nci.nih.gov/ncitermform/"
								target="_blank"><fmt:message
										key="manageTerms.button.requestForm" /> </a></li>
						</ul>
					</li>
				</ul>
			</td>
		</tr>
	</table>
</body>
</html>