import { Component, OnInit } from '@angular/core';
import { Book } from 'src/app/interface/book';
import { BookService } from 'src/app/services/book.service';

@Component({
  selector: 'app-my-books',
  templateUrl: './my-books.component.html',
  styleUrls: ['./my-books.component.css']
})
export class MyBooksComponent implements OnInit {
  
  book!: Book;
  public allbook: Book[] = [];

  constructor(private bookService: BookService) { }

  ngOnInit(): void {
    this.getBook();
  }
  

  public getBook(): void {
    const likedbooks = JSON.parse(localStorage.getItem("likedItems") || "[]");
    for(let n = 0; n < likedbooks.length; n++){
      this.bookService.getBook(likedbooks[n]).subscribe({
        next: (response) => this.book= response,
        error: (error) =>{
          if(error.status !== 0){
          alert('Since your last visit sadly we deleted a book with ISBN '+ likedbooks[n] +' from our database.'),
          likedbooks.splice(n,1),
          localStorage.setItem("likedItems", JSON.stringify(likedbooks)),
          console.log(error)}
          console.log("Api is offline.")},
        complete: () => this.allbook.push(this.book)
      })
    }
  }
}
