##API

Spring Sequrity

user admin
password admin

###Customers endpoints

- get http://localhost:9000/customers/{id}
- get - http://localhost:9000/customers?page=1
- get - http://localhost:9000/customers?page=2&size=5
- delete - http://localhost:9000/customers/{id}
- post - http://localhost:9000/customers
- put - http://localhost:9000/customers

###Accounts endpoints

- get - http://localhost:9000/accounts
- get - http://localhost:9000/accounts/{id}
- get - http://localhost:9000/accounts/customer/{customerid}
- delete - http://localhost:9000/accounts/{id}
- post - http://localhost:9000/accounts/{customerid}
- post - http://localhost:9000/accounts/addmoney?accountnumber={accountnumber}&value={value}
- post - http://localhost:9000/accounts/withdraw?accountnumber={accountnumber}&value={value}
- post -http://localhost:9000/accounts/transfer?fromaccountnumber={fromaccountnumber}&toaccountnumber={toaccountnumber}&value={value}

###Employer endpoints

- get - http://localhost:9000/employers
- get - http://localhost:9000/employers/{id}
- delete - http://localhost:9000/employers/{id}
- post - http://localhost:9000/employers
