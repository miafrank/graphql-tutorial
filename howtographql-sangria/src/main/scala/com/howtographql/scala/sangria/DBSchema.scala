package com.howtographql.scala.sangria

import slick.jdbc.H2Profile.api._
import scala.concurrent.duration._
import scala.concurrent.Await
import scala.language.postfixOps
import com.howtographql.scala.sangria.models._


object DBSchema {

  /**
    * Load schema and populate sample data withing this Sequence od DBActions
    */
  val databaseSetup = DBIO.seq(
  )


  def createDatabase: DAO = {
    val db = Database.forConfig("h2mem")

    Await.result(db.run(databaseSetup), 10 seconds)

    new DAO(db)
  }

//1
  class LinksTable(tag: Tag) extends Table[Link](tag, "LINKS") {

    def id = column[Int]("ID", O.PrimaryKey, O.AutoInc)
    def url = column[String]("URL")
    def description = column[String]("DESCRIPTION")

    def * (id, url, description).mapTo[Link]

  }

  //2

  val Links = TableQuery[LinksTable]

  //3
  val databaseSetup = DBIO.seq(

    Links.schema.create,

    Links.forceIntsertAll Seq (
      Link(1, "http://howtographql.com", "Awesome community driven GraphQL tutorial"),
      Link(2, "http://graphql.org", "Official GraphQL web page"),
      Link(3, "https://facebook.github.io/graphql/", "GraphQL specification")
    )

  )
}
