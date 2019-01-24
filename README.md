# Money Distributor 

## Database
* Create mysql database:
 
         docker run --name mt-mysql --publish 3306:3306 -e MYSQL_ROOT_PASSWORD=root -d mysql:latest
* Connect to database:
    + URL: jdbc:mysql://localhost:3306/
        + username: root
        + password: root
* Run in database console:

        create database moneydistributor;    
* Connect to new database:    
  + URL: jdbc:mysql://localhost:3306/moneydistributor
    + username: root
    + password: root

**Tables in database will be  created on first application startup**
## Cli
* MoneyDistributorCLI : run main
* Commands: 
        
       NAME: SAVE_USER
       DESCRIPTION: Save user.
       SAVE_USER first_name,last_name, username, email
       
       NAME: EXIT
       DESCRIPTION: Exit money distributor client.
       EXIT 
       
       NAME: GET_USERS
       DESCRIPTION: Return all users.
       GET_USERS
       
       NAME: TO_WHOM_I_OWE
       DESCRIPTION: Returns all lenders of given user.
       TO_WHOM_I_OWE user_id
       
       NAME: WHO_OWE_ME
       DESCRIPTION: Return all borrowers of given user.
       WHO_OWE_ME user_id
       
       NAME: SAVE_TRANSACTIONS
       DESCRIPTION: Save transaction between two users.
       SAVE_TRANSACTIONS lender_id, description, (borrower_id=amount)+ 
       
       NAME: GET_USER
       DESCRIPTION: Get user by id
       GET_USER user_id
       
       NAME: GROUP_BALANCE
       DESCRIPTION: Returns group balance.
       GROUP_BALANCE user_id_1, user_id_2 (, user_id)+



## Rest
+ MoneyDistributorApplication : run main

## Api documentation

    http://localhost:8080/swagger-ui.html#/
## TODO
+ tests
+ deleting row in balance_tbl when amount == 0 :
    - maybe trigger after update (problem with batch insert)