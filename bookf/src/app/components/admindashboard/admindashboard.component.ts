import { Component, OnInit } from '@angular/core';
import { Book } from 'src/app/interface/book';
import { BookService } from 'src/app/services/book.service';
import { FormControl, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-admindashboard',
  templateUrl: './admindashboard.component.html',
  styleUrls: ['./admindashboard.component.css']
})
export class AdmindashboardComponent implements OnInit{

  book!: Book;
  private auth = JSON.parse(sessionStorage.getItem('Auth') || '[]');
  private uname = window.atob(this.auth[0]);
  private pw = window.atob(this.auth[1]);
  //private uname = environment.adminuser;
  //private pw = environment.adminpw;
  private uandp: String[] = [this.uname, this.pw];
  public books:  Book[] = [];
  img!: File;

  constructor(private bookService: BookService) { }

  ngOnInit(): void {
    this.getBooks();
    /*
    this.bookService.Refresh.subscribe(
      response => {
      this.getBooks();
    });
    */
    
    this.bookService.Refresh.subscribe({
      next: (response) => this.getBooks(),
      error: (error) => {
        if(error.status !== 0){
          console.log(error)
        }
        console.log('Api is offline.')},
      complete: () => console.log('Success(update)')
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
    this.bookService.uploadImage(image, this.uandp)
    .subscribe({
      next: (response) => console.log(response),
      error: (error) => console.log(error),
      complete: () => console.log('Success(image)')
    })
  }

  public assignStore(isbn: Number, store: String): void{
    this.bookService.assignStore(isbn, store, this.uandp)
    .subscribe({
      next: (response) => console.log(response),
      error: (error) => {console.log(error),
        console.log(error.error.message)},
      complete: () => console.log('Successful(store)')
    })
  }

  public addBook(newbook: Book): void {
      this.bookService.addBook(newbook, this.uname, this.pw)
      .subscribe({
        next: (response) => console.log(response),
        error: (error) =>  console.log(error),
        complete: () => console.log('Successful(addBook)')
      })
  }

  public getBooks(): void {
    this.bookService.getAllBook().subscribe({
      next: (response) => this.books = response,
      error: (error) =>{
        if(error.status !== 0){
          console.log(error)
        }
        console.log('Api is offline.')},
      complete: () => console.log('Successful(getBooks)')
    })
  }

  public delBook(book: Number): void {
    this.bookService.deleteBook(book, this.uandp).subscribe({
      next: (response) => console.log(response),
      error: (error) => console.log(error),
      complete: () => console.log('Successful(deleteBook)')
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
