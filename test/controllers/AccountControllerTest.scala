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

package controllers

import config.ApplicationConfig
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.mockito.MockitoSugar
import org.scalatestplus.play.guice.GuiceOneAppPerSuite
import play.api.test.FakeRequest
import play.api.test.Helpers._
import uk.gov.hmrc.play.test.UnitSpec

class AccountControllerTest extends UnitSpec with GuiceOneAppPerSuite with MockitoSugar with ScalaFutures {

  implicit val appConfig: ApplicationConfig = fakeApplication.injector.instanceOf[ApplicationConfig]

  val feedbackUrl = "http://localhost:9514/feedback/ATS/personal"
  val controller: AccountController = new AccountController

  "signOut" should {

    "redirect user to feedback url" in {

      val result = controller.signOut(FakeRequest())
      redirectLocation(result) shouldBe Some(feedbackUrl)
    }

    "clear user session after redirect" in {

      val result = controller.signOut(FakeRequest().withSession("test" -> "session"))
      val expected = result.futureValue

      expected shouldBe expected.withNewSession
    }
  }
}
