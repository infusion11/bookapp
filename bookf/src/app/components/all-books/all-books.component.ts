import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { Book } from 'src/app/interface/book';
import { BookService } from 'src/app/services/book.service';

@Component({
  selector: 'app-all-books',
  templateUrl: './all-books.component.html',
  styleUrls: ['./all-books.component.css']
})
export class AllBooksComponent implements OnInit {

  public books:  Book[] = [];
  public isLoading: Boolean = true;

  constructor(private bookService: BookService) { }

  ngOnInit(): void {
    this.getBooks();
  }
  public getBooks(): void {
    this.bookService.getAllBook().subscribe({
      next: (response) => this.books = response,
      error: (error) => {
        if(error.status !== 0){
          console.log(error)
        }
        console.log('Api is offline.')},
      complete: () => {console.log('Successful',this.books),this.isLoading = false}
    })
  }
  
  like(isbn: number) {
    const likedbooks = JSON.parse(localStorage.getItem('likedItems') || '[]');
    for(let n = 0; n < likedbooks.length; n++){
      if(isbn == likedbooks[n] ){
        alert('Book already in your liked books collection!');
        return;
        }
      }
      likedbooks.push(isbn);
      localStorage.setItem('likedItems', JSON.stringify(likedbooks));
      alert('Book added to your liked books collection!');
  }
}
