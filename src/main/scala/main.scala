package ru.ciridiri

import ru.circumflex.core._
import ru.circumflex.freemarker.FreemarkerHelper

class CiriDiri extends RequestRouter
    with FreemarkerHelper {

  get("/") = redirect("/index.html")

  get("*.html") {
    case uri(Page.ByUri(page)) =>
      'ciripage := page
      ftl("/ciridiri/page.ftl")
    case _ =>
      redirect(uri + ".e")
  }

  get("*.md") {
    case uri(Page.ByUri(page)) =>
      contentType("text/plain; charset=utf-8")
      page.content
    case _ =>
      error(404, "Page not found")
  }

  get("*.html.e") {
    case uri(Page.ByUriOrEmpty(page)) =>
      'ciripage := page
      ftl("/ciridiri/edit.ftl")
  }

  post("*.html") {
    case uri(Page.ByUriOrEmpty(page)) =>
      if (Page.password == param('password).get) {
        page.content = param('content).get
        page.save
        redirect("" + uri)
      }
      else
        error(403, "Forbidden: password mismatch")
  }

}
