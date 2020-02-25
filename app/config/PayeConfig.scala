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

import com.google.inject.ImplementedBy
import com.typesafe.config.{Config, ConfigFactory}

import scala.collection.JavaConversions._

class PayeConfigImpl extends PayeConfig {
  override val payeYear: Int = ApplicationConfig.payeYear
  override protected val configPath = "paye.conf"
}

@ImplementedBy(classOf[PayeConfigImpl])
trait PayeConfig {
  val payeYear: Int
  protected val configPath: String

  lazy val spendCategories: List[String] = {
    val config: Config = ConfigFactory.load(configPath)
    val categories = Option(config.getStringList(s"categoryOrder.$payeYear")).map(_.toList)
    categories.getOrElse(throw new RuntimeException(s"No spend categories specified for $payeYear"))
  }
}