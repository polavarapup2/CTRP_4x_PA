<%@ include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<table>
	<tr>
		<td>
			<s:textfield label="First Name"
				name="selectedSponsor.name.part[0].value" size="30" readonly="true"
				cssClass="readonly" cssStyle="width:200px" />
		</td>
		<td class="value">
			<ul style="margin-top: -5px;">
				<li style="padding-left: 0">
					<a href="javascript:void(0)" class="btn" onclick="lookup4sponsor();"
						title="Opens a popup form to select Sponsor" /> 
						<span class="btn_img"><span class="organization">Look Up Sponsor</span>
					</span>
					</a>
				</li>
			</ul>
		</td>
	</tr>
</table>
<span class="alert-danger"> <s:fielderror>
		<s:param>SponsorNotSelected</s:param>
	</s:fielderror> </span>