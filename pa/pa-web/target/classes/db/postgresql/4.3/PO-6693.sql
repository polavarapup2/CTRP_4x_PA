UPDATE pa_properties SET value = '<table border="0">
<tr>
  <td align="left" style="width:30%"><b>NCI ID</b></td>
  <td align="left" style="width:30%"><b>Lead Organization</b></td>
  <td align="left" style="width:30%"><b>Lead Organization Trial ID</b></td>
</tr>
</table> 
<hr>
<table border="0">
 ${tableRows}
</table> ' WHERE name = 'verifyDataCTRO.email.body';