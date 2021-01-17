# ****`Library Management System`****

#`SET UP THE APP`
    - Java version : 15
    - Install Apache ActiveMQ, and create a queue with the name 'library_queue'
    - Install MySQL(it is okey if a client) and create an user with username: root & password: 482403.
      and create a database with name 'library'.
    - Install ModHeader : Browser extension to add Authorization to HTTP request headers.

### `DATABASE DIAGRAM:`
![db](https://user-images.githubusercontent.com/3110131/104819865-e0e96a00-5830-11eb-9745-6c78bd4b79b7.jpg)

### `ENDPOINTS:`

![#1589F0](https://via.placeholder.com/15/1589F0/000000?text=+) `USER :`
      
   - POST   Since the Login id can not be null because of relationship between tables so: 
            The endpoint: `/user/new` and Json body for creating will be like that:
            `{
                 "fullName": "Halim Dakir",
                 "birthDate": "2000-02-11",
                 "address": "Maroc",
                 "loginDomain": {
                                 "email": "halim.dk@test.tr",
                                 "password": "halim121",
                                 "roles": "USER"
                                 }
            }`
     Roles: {USER, ADMIN} (it does not matter if upper or lower case).

     
   - GET one: /user/id/{id}
   - GET all: /user/all
   - DELETE : /user/id/{id}
   - PUT    : /user/id/{id}

     `{"fullName":"Halim Dakir",
       "birthDate": "20/11/1970",
       "address":"Rabat, Maroc"}`
     
![#1589F0](https://via.placeholder.com/15/1589F0/000000?text=+) `LOGIN :`
    
    - POST   : see above in `USER's POST` endpoint.


    - GET One : /login/id/{id}
    - DELETE  : /login/id/{id}
    - PUT     : /login/id/{id}

     {"email": "halim.dakir@test.tr",
        "password": "123456",
        "roles": "USER"}

    - PUT     : /login/activate/id/{id} to enable log in, send empty body
    - GET authenticated user's email: /login/authenticated

Other mapping request are existed in service which we implemented them in the **beans**.

![#1589F0](https://via.placeholder.com/15/1589F0/000000?text=+) `ITEM :`

- POST   : /item/new
  
  `{barCode":"123ABC55",
  "title":"La vie est belle", 
  "description":"This item handel about..."}`
  
- GET one: /item/id/{id}
- GET all: /item/all
- DELETE : /item/id/{id}
- PUT    : /item/id/{id}
  `{barCode":"123ABC55",
  "title":"La vie est belle",
  "description":"This item handel about..."}`

![#1589F0](https://via.placeholder.com/15/1589F0/000000?text=+) `AUTHOR :`

- POST   : /author/new
    `{"fullName":"Alex Andersson",
      "birthDate":"15/01/1960"}`
  
- GET one: /author/id/{id}
- GET all: /author/all
- DELETE : /author/id/{id}
- PUT    : /author/id/{id}
  `{"fullName":"Alex Andersson",
  "birthDate":"15/01/1960"}`
![#1589F0](https://via.placeholder.com/15/1589F0/000000?text=+) `AUTHENTICATION :`
- Json body :`{"username": "halim@gmail.com", "password": "123456"}` 
- POST   : /auth

![#1589F0](https://via.placeholder.com/15/1589F0/000000?text=+) `MESSAGE JMS :`
- Json body :`{"emailFrom": "halim@gmail.com","emailTo": "salim@gmail.com","msgObject": "reserve","message": "Hello! I'd like to borrow a book"}`
- POST   : /publishMessage

![#1589F0](https://via.placeholder.com/15/1589F0/000000?text=+) `STOCK :`

- PUT update stock     : /stock/id/{id}
           `{"quantity": 22}`
  
- GET all item's stock : /stock/all
- GET stock by item's id : /stock/id/{id}

![#1589F0](https://via.placeholder.com/15/1589F0/000000?text=+) `Item Lending :`

- POST return borrowed     : /borrowed/id/{id}        
  with an empty body.


![#1589F0](https://via.placeholder.com/15/1589F0/000000?text=+) `LOG OUT :`
- POST   : /logout

