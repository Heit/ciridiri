package ru.ciridiri

import _root_.ru.circumflex.core.RequestRouter
import _root_.ru.circumflex.freemarker.FreemarkerHelper
import _root_.org.slf4j.LoggerFactory

class CiriDiri extends RequestRouter
    with FreemarkerHelper {

  val log = LoggerFactory.getLogger("ru.ciridiri")

  get("/") = redirect("/index.html")

  get("(.*)\\.html") = Page.findByUri(param("uri$1").get) match {
    case Some(page) =>
      ctx += "ciripage" -> page
      ftl("/ciridiri/page.ftl")
    case None =>
      redirect(ctx.uri + ".e")
  }

  get("(.*)\\.md") = Page.findByUri(param("uri$1").get) match {
    case Some(page) =>
      contentType("text/plain; charset=utf-8")
      page.content
    case None =>
      error(404, "Page not found")
  }

  get("(.*)\\.html.e") = {
    ctx += "ciripage" -> Page.findByUriOrEmpty(param("uri$1").get)
    ftl("/ciridiri/edit.ftl")
  }

  post("(.*)\\.html") = if (Page.password != param("password").get)
    error(403, "Forbidden: password mismatch")
  else {
    var page = Page.findByUriOrEmpty(param("uri$1").get)
    page.content = param("content").get
    page.save
    redirect(ctx.uri)
  }

}
