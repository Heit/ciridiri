package ru.ciridiri

import _root_.ru.circumflex.core.RequestRouter
import _root_.ru.circumflex.core.Circumflex
import _root_.ru.circumflex.freemarker.FreemarkerHelper
import _root_.java.text.SimpleDateFormat
import _root_.java.util.Date
import _root_.org.slf4j.LoggerFactory

class CiriDiri extends RequestRouter
    with FreemarkerHelper {

  val log = LoggerFactory.getLogger("ru.ciridiri")
  ctx += "h" -> Helpers 

  get("/") = redirect("/index.html")

  get("(.*)\\.html") = Page.findByUri(param("uri$1").get) match {
    case Some(page) =>
      ctx += "p" -> page
      ftl("page.ftl")
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
    ctx += "p" -> Page.findByUriOrEmpty(param("uri$1").get)
    ftl("edit.ftl")
  }

  post("(.*)\\.html") = {
    if (Circumflex.cfg("pages.password").get != param("password").get) {
      error(403, "Forbidden: password mismatch")
    } else {
      var page = Page.findByUriOrEmpty(param("uri$1").get)
      page.content = param("content").get
      page.save
      ctx += "p" -> page
      redirect(ctx.uri)
    }
  }

}
