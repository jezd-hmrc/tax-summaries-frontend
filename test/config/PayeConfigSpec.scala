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

package config

import org.scalatest.mockito.MockitoSugar
import uk.gov.hmrc.play.test.UnitSpec

class PayeConfigSpec extends UnitSpec with MockitoSugar {

  "PayeConfig" should {
    "retrieve spend categories in order for a valid year" in {
      val config = new PayeConfig {
        override protected val configPath: String = "paye_config.conf"
        override val payeYear: Int = 2019
      }

      val expected = List(
        "welfare",
        "health",
        "pension",
        "education",
        "defence",
        "national_debt_interest",
        "transport",
        "criminal_justice",
        "business_and_industry",
        "government_administration",
        "housing_and_utilities",
        "environment",
        "culture",
        "overseas_aid",
        "uk_contribution_to_eu_budget"
      )

      config.spendCategories shouldBe expected
    }

    "throw an exception for an invalid year" in {
      val config = new PayeConfig {
        override protected val configPath: String = "paye_config.conf"
        override val payeYear: Int = 2018
      }

      assertThrows[RuntimeException]{config.spendCategories}
    }
  }

}