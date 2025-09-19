POC Spring Boot project for using multi-tenant databases beside the master database

- It uses Oracle DB with different schemas and liquibase for maintain database objects in master schema.
- It has two transaction managers. One for the main datasource and one for the current branch we switch onto

Hope this helps others who have similar use cases.