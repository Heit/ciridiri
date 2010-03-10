package ru.ciridiri

import java.io.File
import org.apache.commons.io.FileUtils._
import org.apache.commons.io.FilenameUtils
import ru.circumflex.core._
import java.util.regex.{Pattern, Matcher}

class Page(val uri : String, var content : String) {
  val path = Page.pathFromUri(uri)
  val title = Page.findTitle(content)

  def save() = {
    val file = new File(path)
    if (!file.exists) forceMkdir(new File(FilenameUtils.getFullPath(path))) 
    writeStringToFile(file, content, "UTF-8")
  }

}

object Page {
  var contentDir = Circumflex.cfg("pages.root").getOrElse("src/main/webapp/pages").toString
  val sourceExt = ".md"
  val mdTitle = Pattern.compile("^#{1,6}(.*)(#{1,6})?$", Pattern.MULTILINE)

  def pathFromUri(uri : String) = FilenameUtils.concat(contentDir, (FilenameUtils.separatorsToSystem(uri) + sourceExt).replaceAll("^/", ""))

  def uriFromPath(path : String) = FilenameUtils.separatorsToUnix(new File(path).getAbsolutePath.replaceAll("^" + new File(contentDir).getAbsolutePath, "").replaceAll(sourceExt + "$", ""))

  def findByUri(uri : String) : Option[Page] = {
    val file = new File(pathFromUri(uri))
      if (file.exists) {
        return Some(new Page(uri, readFileToString(file, "UTF-8")))
      } else {
        return None
      }
  }

  def findByPath(path : String) : Option[Page] = findByUri(uriFromPath(path))

  def findByUriOrEmpty(uri : String) = findByUri(uri).getOrElse(new Page(uri, ""))

  def findTitle(text : String) = {
    val m = mdTitle.matcher(text)
    if (m.find) m.group(1)
    else ""
  }

}
