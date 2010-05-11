package ru.ciridiri

import ru.circumflex.core.RequestRouter

trait AuthHelper extends RequestRouter {
  def authorized_? = session("arni") != None

  def protected_! = {
    if(!authorized_?) {
      if(Page.password != param("password").get) {
        error(403, "Forbidden: password mismatch")
      } else {
        session("arni") = true
      }
    }
  }

  def logout_! = session("arni") = None

}
