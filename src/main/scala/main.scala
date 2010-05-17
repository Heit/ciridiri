package ru.ciridiri

import ru.circumflex.core._
import ru.circumflex.freemarker.FTL._

class CiriDiri extends RequestRouter with AuthHelper{

  get("/") = redirect("/index.html")

  get("*.html") = Page.findByUri(uri(1)) match {
    case Some(page) =>
      "ciripage" := page
      ftl("/ciridiri/page.ftl")
    case None =>
      redirect(uri(0) + ".e")
  }

  get("*.md") = Page.findByUri(uri(1)) match {
    case Some(page) =>
      contentType("text/plain; charset=utf-8")
      page.content
    case None =>
      error(404, "Page not found")
  }

  get("*.html.e") = {
    "ciripage" := Page.findByUriOrEmpty(uri(1))
    ftl("/ciridiri/edit.ftl")
  }

  post("*.html") = {
    protected_!
    var page = Page.findByUriOrEmpty(uri(1))
    page.content = param('content)
    page.save
    redirect(uri(0))
  }

}
