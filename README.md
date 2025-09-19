POC Spring Boot project for using multi-tenant databases beside the master database

- It uses one Oracle DB with different schemas and Liquibase for maintain database objects in master schema.
- It has two transaction managers. One for the main datasource and one for the current branch we switch onto
- In TestController there are some queries and persist to demonstrate how it works

Usage: 
 
   http://localhost:8000/api/v1/test?schema1=test1&schema2=test2

Hope this helps others who have similar use cases.