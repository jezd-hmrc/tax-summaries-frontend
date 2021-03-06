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

package views

import config.ApplicationConfig
import org.scalatestplus.play.OneAppPerSuite
import play.api.i18n.{Lang, Messages, MessagesApi, MessagesImpl}
import play.api.test.Injecting
import uk.gov.hmrc.play.partials.FormPartialRetriever
import uk.gov.hmrc.play.test.UnitSpec

trait ViewSpecBase extends UnitSpec with OneAppPerSuite with Injecting {

  implicit val messagesApi: MessagesApi = inject[MessagesApi]
  implicit val messages: Messages = MessagesImpl(Lang("en"), messagesApi)
  implicit val formPartialRetriever: FormPartialRetriever = inject[FormPartialRetriever]
  implicit lazy val appConfig = inject[ApplicationConfig]
  implicit val lang : Lang = messages.lang

}
