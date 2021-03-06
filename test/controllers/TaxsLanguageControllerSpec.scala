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

import play.api.test.FakeRequest
import play.api.test.Helpers._

class TaxsLanguageControllerSpec extends ControllerBaseSpec {


  def sut = new TaxsLanguageController(mcc)

  "switchLanguage" should {

    "switch the language to english and redirect to the home page" in {

      val result = sut.switchLanguage("en")(FakeRequest())

      status(result) shouldBe 303
      cookies(result).get("PLAY_LANG").get.value shouldBe "en"
      redirectLocation(result) shouldBe Some("/annual-tax-summary")
    }

    "switch the language to welsh and redirect to the home page" in {

      val result = sut.switchLanguage("cy")(FakeRequest())

      status(result) shouldBe 303
      cookies(result).get("PLAY_LANG").get.value shouldBe "cy"
      redirectLocation(result) shouldBe Some("/annual-tax-summary")
    }

    "switch the language to welsh and redirect to the referring page" in {

      val request = FakeRequest().withHeaders(REFERER -> "foo")
      val result = sut.switchLanguage("cy")(request)

      status(result) shouldBe 303
      cookies(result).get("PLAY_LANG").get.value shouldBe "cy"
      redirectLocation(result) shouldBe Some("foo")
    }
  }
}
