# ****`Library Management System`****

#`SET UP THE APP`
    - Java version : 15
    - Install Apache ActiveMQ, and create a queue with the name 'library_queue'
    - Install MySQL(it is okey if a client) and create an user with username: root & password: 482403.
      and create a database with name 'library'.

### `DATABASE DIAGRAM:`
![db](https://user-images.githubusercontent.com/3110131/104819865-e0e96a00-5830-11eb-9745-6c78bd4b79b7.jpg)

### `ENDPOINTS:`

![#1589F0](https://via.placeholder.com/15/1589F0/000000?text=+) `USER :`
   - Json body :`{"fullName":"Halim Dakir","birthDate": "20/11/1970","address":"Rabat, Maroc"}`
   - POST   : /user/new
   - GET one: /user/id/{id}
   - GET all: /user/all
   - DELETE : /user/id/{id}
   - PUT    : /user/id/{id}
     
![#1589F0](https://via.placeholder.com/15/1589F0/000000?text=+) `LOGIN :`
- Json body :`{"email": "halim.dakir@test.tr","password": "123456","roles": "USER"}`
- POST   : /login/new
- GET authenticated user's email: /login/authenticated
Other mapping request are exist in service which we implemented them in the **beans** .

![#1589F0](https://via.placeholder.com/15/1589F0/000000?text=+) `ITEM :`
- Json body :`{barCode":"123ABC55","title":"La vie est belle", "description":"This item handel about..."}`
- POST   : /item/new
- GET one: /item/id/{id}
- GET all: /item/all
- DELETE : /item/id/{id}
- PUT    : /item/id/{id}

![#1589F0](https://via.placeholder.com/15/1589F0/000000?text=+) `AUTHOR :`
- Json body :`{"fullName":"Alex Andersson","birthDate":"15/01/1960"}`
- POST   : /author/new
- GET one: /author/id/{id}
- GET all: /author/all
- DELETE : /author/id/{id}
- PUT    : /author/id/{id}

![#1589F0](https://via.placeholder.com/15/1589F0/000000?text=+) `AUTHENTICATION :`
- Json body :`{"username": "halim@gmail.com", "password": "123456"}` 
- POST   : /auth

![#1589F0](https://via.placeholder.com/15/1589F0/000000?text=+) `MESSAGE JMS :`
- Json body :`{"emailFrom": "halim@gmail.com","emailTo": "salim@gmail.com","msgObject": "reserve","message": "Hello! I'd like to borrow a book"}`
- POST   : /publishMessage

![#1589F0](https://via.placeholder.com/15/1589F0/000000?text=+) `LOG OUT :`
- POST   : /logout