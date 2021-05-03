# ORM_Framework_Steven

## Project Description

An Object Relational Mapping Framework, created to strip away all of the JDBC logic from the end user. A user can use this framework and know very little about the JDBC api within Java and can still persist all of their data to a postgresSQL database.

## Technologies Used

* Java 8
* JDBC API
* PostgresSQL
* Maven
* AWS RDS
* DBeaver
* JUnit

## Features of ORM

* ORM supports all basic CRUD operations for an end user with little to no JDBC knowledge
* Multi-threaded application for a smoothe experience
* HikariCP was used for connection pooling, so there is always a speedy connection to the database

To-do list:
* Fully implement caching with the help of HikariCP
* To fully support reltaionships between tables (currently there are no relationships supported)
* To add in support for other databases other than just PostgresSql

## Getting Started
   
git clone https://github.com/StevenCeglarek/ORM_Framework_Steven.git

## Usage

> open application in your favorite IDE (IntelliJ was used in creating this ORM)
> create a db.properties file in resources folder
> db.properties needs jdbcUrl, dataSource.user, dataSource.password to be able to communicate with your database.
> If you wish to customize your connection pooling (The values in these fields what were used in creating the ORM, you can customize to your liking) dataSource.maximumPoolSize=5, dataSource.cachePrepStmts=true dataSource.prepStmtCacheSize=250 dataSource.prepStmtCacheSqlLimit=1000
> Only thing needed for persisting to the database once connected is a Schema already built out. ORM will create tables if none exist
