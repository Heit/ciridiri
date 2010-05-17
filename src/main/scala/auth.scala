package ru.ciridiri

import ru.circumflex.core._

trait AuthHelper extends RequestRouter {
  def authorized_? = session.get('arni) != None

  def protected_! = {
    if(!authorized_?) {
      if(Page.password != param('password)) {
        error(403, "Forbidden: password mismatch")
      } else if(Page.rememberMe) {
        session('arni) = true
      }
    }
  }

  def logout_! = session('arni) = None

}
