[#ftl]
[#include "layout.ftl"]
[@page]
<h1>Edit page</h1>
<form action="/${p.uri}.html" method="post">
<fieldset>
    <textarea rows="30" cols="20" name="content" style="width: 100%">${p.content?html}</textarea>
</fieldset>
<fieldset class="submits">
    <input type="submit" value="Save" /> or <a href="/${p.uri}.html">cancel</a>
</fieldset>
</form>
[/@page]