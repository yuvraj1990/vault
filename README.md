# vault
1 . pull the code 

run db dump to your local mysql setup 
database.sql file 

#change user name and password as your DB and application.properties file 
#spring.datasource.username = 
#spring.datasource.password = 
#change URL if required 
spring.datasource.url = jdbc:mysql://localhost:3306/vault?useSSL=false

insert some entries in clinics table 

add roles as 'Patient','Doctor','Assistant' in role table 

put next_val in hibernet_sequence table 

run VaultApplication class 

application will be up  http://localhost:8080/vault/login
