import { Component, OnInit } from '@angular/core';
import { Book } from 'src/app/interface/book';
import { BookService } from 'src/app/services/book.service';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Observable } from 'rxjs';

@Component({
  selector: 'app-admindashboard',
  templateUrl: './admindashboard.component.html',
  styleUrls: ['./admindashboard.component.css']
})
export class AdmindashboardComponent implements OnInit{

  book!: Book;
  public books:  Book[] = [];
  img!: File;
  public isLoading: Boolean = true;
  public books$!: Observable<Book[]>;

  constructor(private bookService: BookService) { }

  ngOnInit(): void {
    this.books$ = this.bookService.getAllBook();
    
    this.bookService.Refresh.subscribe({
      next: (response) => this.books$ = this.bookService.getAllBook(),
      error: (error) => {
        if(error.status !== 0){
          console.log(error)
        }
        console.log('Api is offline.')}
    });
  }

  addForm = new FormGroup({
    isbn: new FormControl('', [
      Validators.required,
      Validators.minLength(10),
      Validators.maxLength(13),
      Validators.pattern('^[0-9]*$')]),
    title: new FormControl('', [Validators.required]),
    author: new FormControl('', [Validators.required]),
    about: new FormControl('', [Validators.required]),
    image: new FormControl(null),
  });
  storeForm = new FormGroup({
    isbntoassign: new FormControl('', [Validators.required]),
    store1: new FormControl(''),
    store2: new FormControl(''),
  });
  delForm = new FormGroup({
    bookisbn: new FormControl('', [Validators.required]),
  });

  fileSelected(event: any) {
    console.log(event);
    if(event.target.files[0].type == 'image/jpeg' || event.target.files[0].type == 'image/png'){
      this.img = <File>event.target.files[0];
    }
    else {
      alert('Bad file extension.')
      this.addForm.controls['image'].reset();
    }
  }

  public onSubmit(){
    this.book = {
      id: null,
      isbn: this.isbn.value,
      title: this.title.value,
      author: this.author.value,
      about: this.about.value,
      image: null,
      stores: [],
      }
      if(this.addForm.valid){
        if(this.image.value){
          if(this.img.type == 'image/jpeg'){
          this.book.image = this.book.isbn+'.jpeg';
          this.addBook(this.book);
          const formData = new FormData();
          formData.append('file', this.img, this.isbn.value+'.jpeg');
          this.uploadImage(formData);
          }
          else{
            this.book.image = this.book.isbn+'.png';
            this.addBook(this.book);
            const formData = new FormData();
            formData.append('file', this.img, this.isbn.value+'.png');
            this.uploadImage(formData);
          }
        }
        else{
          this.addBook(this.book);
        }
    }
    this.addForm.reset();
  }
  assignOnSubmit(){
    if(this.store1.value){
      this.assignStore(this.isbntoassign.value, 'ImaginedBook');
    }
    if(this.store2.value){
      this.assignStore(this.isbntoassign.value, 'ThisIsntReal');
    }
    this.storeForm.reset();
  }
  delOnSubmit(){
    this.delBook(this.bookisbn.value);
    for(let i=0;i<this.books.length;i++){
      if(this.bookisbn.value == this.books[i].isbn){
        this.books.splice(i,1);
      }
    }
    this.delForm.reset();
  }

  public uploadImage(image: FormData): void{
    this.bookService.uploadImage(image)
    .subscribe({
      next: (response) => console.log(response),
      error: (error) => console.log(error),
      complete: () => console.log('Success(image)')
    })
  }

  public assignStore(isbn: Number, store: String): void{
    this.bookService.assignStore(isbn, store)
    .subscribe({
      next: (response) => console.log(response),
      error: (error) => {console.log(error),
        console.log(error.error.message)},
      complete: () => {
        console.log('Successful(store)'),
        alert("Store successfully assigned.")}
    })
  }

  public addBook(newbook: Book): void {
      this.bookService.addBook(newbook)
      .subscribe({
        next: (response) => console.log(response),
        error: (error) =>  console.log(error),
        complete: () => {
          console.log('Successful(addBook)'),
          alert("Book successfully added.")}
      })
  }

  public delBook(book: Number): void {
    this.bookService.deleteBook(book).subscribe({
      next: (response) => console.log(response),
      error: (error) => console.log(error),
      complete: () => {
        console.log('Successful(deleteBook)'),
        alert("Book with "+ book +" isbn deleted.")}
    })
  }
  
  get isbn(): FormControl{
    return this.addForm.get('isbn') as FormControl;
  }
  get title(): FormControl {
    return this.addForm.get('title') as FormControl;
  }
  get author(): FormControl {
    return this.addForm.get('author') as FormControl;
  }
  get about(): FormControl {
    return this.addForm.get('about') as FormControl;
  }
  get store1(): FormControl {
    return this.storeForm.get('store1') as FormControl;
  }
  get store2(): FormControl {
    return this.storeForm.get('store2') as FormControl;
  }
  get isbntoassign(): FormControl{
    return this.storeForm.get('isbntoassign') as FormControl;
  }
  get bookisbn(): FormControl{
    return this.delForm.get('bookisbn') as FormControl;
  }
  get image(): FormControl{
    return this.addForm.get('image') as FormControl;
  }
}
