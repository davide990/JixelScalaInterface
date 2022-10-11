package JixelAPIInterface.Login

/**
 * Login credential provided by Jixel after succesful login
 *
 * @author Davide A. Guastella (davide.guastella@icar.cnr.it)
 */
case class JixelCredential(token: String, user: JixelUser);
