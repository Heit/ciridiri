package ru.ciridiri

import java.io.File
import org.apache.commons.io.FileUtils._
import org.apache.commons.io.FilenameUtils
import ru.circumflex.core._
import java.util.regex.{Pattern, Matcher}

class Page(val uri : String, var content : String) {
  val path = FilenameUtils.concat(Page.contentDir, uri.replaceAll("/", File.separator) + ".md")
  val title = Page.findTitle(content)

  def save() = {
    val file = new File(path)
    if (!file.exists) forceMkdir(new File(FilenameUtils.getFullPath(path))) 
    writeStringToFile(file, content, "UTF-8")
  }

}

object Page {
  var contentDir = Circumflex.cfg("pages.root").getOrElse("src/main/webapp/pages").toString
  val mdTitle = Pattern.compile("^#{1,6}(.*)(#{1,6})?$", Pattern.MULTILINE)

  def pathFromUri(uri : String) = contentDir.replaceAll(File.separator + "$", "") + 
         "/"  + uri.replaceAll("/", File.separator) + ".md"

  def uriFromPath(path : String) = path.replaceAll("\\.md$", "").replaceAll("^" + contentDir, "")
                                       .replaceAll(File.separator, "/")

  def findByUri(uri : String) : Option[Page] = {
    val file = new File(pathFromUri(uri))
      if (file.exists) {
        return Some(new Page(uri, readFileToString(file, "UTF-8")))
      } else {
        return None
      }
  }

  def findByUriOrEmpty(uri : String) = findByUri(uri).getOrElse(new Page(uri, ""))

  def findTitle(text : String) = {
    val m = mdTitle.matcher(text)
    if (m.find) m.group(1)
    else ""
  }

}
