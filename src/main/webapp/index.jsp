<script type="text/JavaScript">

var xmlhttp;

function execute($method, $url, $data)
{
	xmlhttp = new XMLHttpRequest();
  xmlhttp.open($method, $url, true);
  xmlhttp.send($data);
}

</script>

<h3>taxinvoicenumber application is working</h3>
----------------------------------------------------------------
<!-- <br/>
<a href="javascript:execute('PUT', '/services/collections/docs/testdoc', 'Here is some PUT data');">PUT Resource</a>
<br/>
----------------------------------------------------------------
<br/>
<a href="/services/collections/docs/testdoc/?id=2">GET Resource</a>
<br/>
----------------------------------------------------------------
<br/>
<a href="/services/customer/?id=2">GET customer</a>
<br/>
----------------------------------------------------------------
<form name="postdataform" action="/rest1c2ebs/services/collections/docs/testdoc" method="post">
  POST Resource Data: <input type="text" name="ResourceData"> <input type="submit" value="Update">
</form>
----------------------------------------------------------------
<br/>
<a href="javascript:execute('DELETE', '/services/collections/docs/testdoc', null);">DELETE Resource</a> -->
