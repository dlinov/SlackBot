package serverSlack

import cats.effect._
import cats.syntax.all._

import org.http4s.UrlForm
import org.http4s.HttpRoutes
import org.http4s.dsl.Http4sDsl

case class Routes[F[_]: Sync](messageService: MessageService[F]) extends Http4sDsl[F] {

  val routes: HttpRoutes[F] = HttpRoutes.of[F] {

    case request @ POST -> Root =>
      for {
        urlForm  <- request.as[UrlForm]
        _        <- messageService.reply(urlForm)
        response <- Ok()
      } yield response

  }
}
