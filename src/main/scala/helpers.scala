package ru.ciridiri

import ru.circumflex.md.Markdown

object Helpers {
  def prettify(text : String) = Markdown(text)
}
