package ru.ciridiri.test

import ru.circumflex.core._
import ru.ciridiri._
import org.specs._
import org.specs.runner.JUnit4

import java.io.File
import org.apache.commons.io.FileUtils._
import org.apache.commons.io.FilenameUtils


class CiriridiPageSpecsTest extends JUnit4(CiridiriPageSpec)

object CiridiriPageSpec extends Specification {
  val tempRoot = "src/test/tmp"
  val mockPage = new Page("index", "##awesome title\nawesome contents")

  doBeforeSpec {
    Circumflex("ciridiri.contentRoot") = tempRoot //seems like not working
    Page.contentDir = tempRoot
    forceMkdir(new File(tempRoot))
  }

  doAfterSpec {
    forceDelete(new File(tempRoot))
  }

  "Ciridiri's Page" should {
    "find markdown-like title" in {
      mockPage.title must_== "awesome title"
    }
    "save contents to file" in {
      val targetPath = tempRoot + "/index.md"
      mockPage.path must_== targetPath
      mockPage.save

      new File(targetPath).exists must beTrue
      new File(targetPath).length must be_!=(0)
    }
    "update contents" in {
      mockPage.save
      mockPage.content = "new contents"
      mockPage.save

      readFileToString(new File(mockPage.path), "UTF-8") must_== "new contents"
    }
    "find page by uri" in {
      var p = new Page("secret-uri", "secret text")
      p.save

      var p2 = Page.findByUri("secret-uri")
      p2 must not beNone
      var p2g = p2.get
      p2g.content must_== "secret text"
    }
    "return None if page is not found" in {
      Page.findByUri("non-existent") must beNone
    }
    "return empty page if needed" in {
      var p2 = Page.findByUriOrEmpty("non-existent").get
      p2.content must_==""
    }
    "respect uri hierarchy" in {
      var p = new Page("about/team/boris", "some description")
      p.save

      var targetPath = tempRoot + "/about/team/boris.md"
      new File(targetPath).exists must beTrue
      p.path must_== targetPath
    }
    "cache contents" in {
      mockPage.cache_!

      var cachePath = tempRoot + "/index.md.html"
      new File(cachePath).exists must beTrue
      new File(cachePath).length must be_!=(0)
    }
    "sweep the cached fragment" in {
      mockPage.cache_!

      var cachePath = tempRoot + "/index.md.html"
      mockPage.sweep_!
      new File(cachePath).exists must beFalse
    }
    "respond to HTML" in {
      mockPage.content = "<h2>header</h2>"
      mockPage.save //trigger recaching
      mockPage.toHtml must_== "<h2>header</h2>"
    }
  }
}
