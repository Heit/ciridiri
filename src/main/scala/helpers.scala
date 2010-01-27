package ru.ciridiri

import com.petebevin.markdown.MarkdownProcessor;

object Helpers {
  def prettify(text : String) = new MarkdownProcessor().markdown(text)
}
