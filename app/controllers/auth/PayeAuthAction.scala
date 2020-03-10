/*
 * Copyright 2020 HM Revenue & Customs
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package controllers.auth

import com.google.inject.{ImplementedBy, Inject}
import config.ApplicationConfig
import play.api.mvc.Results.Redirect
import play.api.mvc._
import play.api.{Configuration, Logger}
import uk.gov.hmrc.auth.core.retrieve.v2.Retrievals
import uk.gov.hmrc.auth.core.{AuthorisedFunctions, ConfidenceLevel, NoActiveSession, Nino => AuthNino}
import uk.gov.hmrc.domain.Nino
import uk.gov.hmrc.http.HeaderCarrier
import uk.gov.hmrc.play.HeaderCarrierConverter

import scala.concurrent.{ExecutionContext, Future}

class PayeAuthActionImpl @Inject()(override val authConnector: AuthConnector,
                                   configuration: Configuration)(implicit ec: ExecutionContext)
  extends PayeAuthAction with AuthorisedFunctions {

  override def invokeBlock[A](request: Request[A], block: PayeAuthenticatedRequest[A] => Future[Result]): Future[Result] = {

    implicit val hc: HeaderCarrier =
      HeaderCarrierConverter.fromHeadersAndSession(request.headers, Some(request.session))

    authorised(ConfidenceLevel.L200 and AuthNino(hasNino = true))
      .retrieve(Retrievals.nino) {
        case Some(nino) => {
          block {
            PayeAuthenticatedRequest(
              Nino(nino),
              request
            )
          }
        }
        case _ => throw new RuntimeException("Auth retrieval failed for user")
      }
  } recover {
    case _: NoActiveSession => {
      Redirect(
        ApplicationConfig.payeLoginUrl,
        Map(
          "continue" -> Seq(ApplicationConfig.loginCallback),
          "origin" -> Seq(ApplicationConfig.appName)
        )
      )
    }

    case e: Exception => {
      Logger.error(s"Exception in PayeAuthAction: $e", e)
      Redirect(controllers.routes.ErrorController.notAuthorised())
    }
  }
}

@ImplementedBy(classOf[PayeAuthActionImpl])
trait PayeAuthAction extends ActionBuilder[PayeAuthenticatedRequest] with ActionFunction[Request, PayeAuthenticatedRequest]

