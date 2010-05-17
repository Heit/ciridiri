package ru.ciridiri.test

import ru.circumflex.core._
import test._
import ru.ciridiri._
import org.specs._
import org.specs.runner.JUnit4
import java.io.File
import org.apache.commons.io.FileUtils._
import org.mortbay.jetty.testing.HttpTester

class SpecsTest extends JUnit4(CiridiriSpec)

object CiridiriSpec extends Specification {
  val tempRoot = "src/test/tmp"
  var app = MockApp

  doBeforeSpec {
    Circumflex("cx.router") = classOf[CiriDiri]
    Circumflex("ciridiri.contentRoot") = tempRoot //seems like not working
    Circumflex("ciridiri.rememberMe") = false //that's too
    Page.contentDir = tempRoot
    Page.rememberMe = false
    forceMkdir(new File(tempRoot))
    app.start
  }

  doAfterSpec {
    app.stop
    forceDelete(new File(tempRoot))
  }

  "Ciridiri" should {
    "redirect from root to the index.html" in {
      checkRedirect(app.get("/").execute, "/index.html")
    }
    "redirect to an edit form if the corresponding page doesnt exist" in {
      checkRedirect(app.get("/nonexistent.html").execute, "/nonexistent.html.e")
    }
    "show an empty edit form" in {
      var r = app.get("/foo.html.e").execute
      r.getContent must include("<textarea")
    }
    "show an existent page" in {
      var page = new Page("bar", "##a-title\na-body")
      page.save

      var r = app.get("/bar.html").execute
      r.getStatus must_==200
      r.getContent must include("a-body")
      r.getContent must include("a-title")
    }
    "show a page source" in {
      var page = new Page("baz", "##a-title\na-body")
      page.save

      var r = app.get("/baz.md").execute
      r.getStatus must_==200
      r.getContent must include("##a-title")
    }
    "create a new page" in {
      var r = app.post("/new-page.html").setContent("content=piuuuu&password=pass").execute

      checkRedirect(r, "/new-page.html")

      var r2 = app.get("/new-page.html").execute
      r2.getStatus must_==200
      r2.getContent must include("piuuuu")
    }
    "update an existent page" in {
      var p = new Page("index", "index.html")
      p.save

      var r = app.post("/index.html").setContent("content=new_content&password=pass").execute
      checkRedirect(r, "/index.html")

      var r2 = app.get("/index.html").execute
      r2.getStatus must_== 200
      r2.getContent must include("new_content")
    }
    "return 403 if passwords mismatch" in {
      var p = new Page("index", "index")
      p.save

      var r = app.post("/index.html").setContent("content=fff&password=hax0r").execute
      r.getStatus must_== 403
    }
  }

  def checkRedirect(req: HttpTester, location: String) = {
    req.getStatus must_==302
    req.getHeader("Location") must_== "http://localhost" + location
  }
}
