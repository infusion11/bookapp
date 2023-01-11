IMPORTANT NOTE: This is an ongoing project, for which I used: Angular and Spring framework, MySQL. Since this is kind of a "presentation", I tried to use various techniques and resourceful ideas. Some of them would not fit in production environment, these are for the sake of simplicity and could be easily replaced. The goal of this project was to demonstrate resourcefulness rather than to showcase the best practices.


My application would be suitable for a book antiquary. The books are stored in the database. The user can see if the books are available in any of the antiquary's stores. There is an option to "like" a book, which saves the item for later, making it easy to keep track of its availability.
I also created options to manipulate the data: an admin can add, update(currently only backend implementation) and delete books, or assign a store to them.

BACKEND

A Rest API with the following responsibilities:

GET /login

    The user sends credentials, if those are valid, then a token is generated. The username, password and token combination is required to create, update or delete.

GET /getallbook

    Lists every book from the database.
    
GET /getbook/{isbn}

    It shows one book by its ISBN number.
    
POST /addbook

    Adds a new book to the database.
    
PUT /updatebook

    Updates the details of a book (except ISBN and ID).
    
DELETE /delete/{isbn}

    Deletes a book from the database.
    
POST /assignstore

    Changes the availability of a book.
    
POST /uploadimage

    Uploads an image to a book.
    

I tested the API with two methods: first I wrote a couple of unit tests, then I used an API client to make sure that everything is working as intended. Custom exceptions are responsible for helping to create a user-friendly and useful message, which is more than "something went wrong". Furthermore I created a very simple token system to expand security.


FRONTEND

/home

    A welcome page, in the future it could function to show "trending" books.
    
/books

    List of every book from the database.
    
/my-books

    List of books liked by the user. These are stored in the LocalStorage.
    
/login

    Username and password must be provided by the user, essential to get access to the admin dashboard.
    
/admin

    Admin dashboard.
    
