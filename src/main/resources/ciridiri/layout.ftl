[#ftl]
[#macro page]
<!DOCTYPE html>
<html>
<head>
  <meta http-equiv="content-type" content="text/html; charset=utf-8" />
  <link rel="stylesheet" media="screen, projection" href="/css/base.css" />
  <link rel="stylesheet" media="print" href="/css/print.css" />
  <script src="/js/application.js"></script>
  [#if ciripage?? && !.template_name?ends_with('edit.ftl')]
    <link rel="alternate"
          id="edit-link"
          type="application/x-wiki"
          title="Edit this page"
          href="${ciripage.uri}.html.e" />
  [/#if]
  [#if .template_name?ends_with('edit.ftl')]
  <meta name="robots" content="noindex" />
  [/#if]
  <title>
    [#if ciripage?? && ciripage.title != '']
    ${ciripage.title} / ciridiri
      [#else]
        ciridiri: dead simple wiki engine
    [/#if]
  </title>
</head>
<body>
<div id="wrap">
  <div id="page">
    [#nested/]
  </div>
</div>
</body>
</html>
[/#macro]
