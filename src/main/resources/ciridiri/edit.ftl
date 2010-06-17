[#ftl]
[#include "layout.ftl"]
[@page]
<h1>Edit page</h1>
<script type="text/javascript">
window.onload = function() {
  document.getElementById('p-content').focus();
}
</script>

<form action="${ciripage.uri}.html" method="post" onkeypress="ctrlEnterSubmit(event, this)">
<fieldset>
  <div>
    <textarea rows="30" cols="20" id="p-content" name="content" style="width: 100%">${ciripage.content?html}</textarea>
  </div>
  [#if !session.arni??]
  <div>
    <label for="password">Password: </label><input type="password" name="password" id="password" />
  </div>
  [/#if]
</fieldset>
<fieldset class="submits">
    <input type="submit" value="Save" /> or <a href="${ciripage.uri}.html">cancel</a>
</fieldset>
</form>
<form method="post" action="${ciripage.uri}.html">
  <input type="hidden" name="_method" value="delete" />
  <input type="submit" value="Delete" />
</form>
[/@page]
