import { Injectable } from '@angular/core';
import { delay, Observable, Subject, tap} from 'rxjs';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Book } from '../interface/book';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class BookService {
  
  private restUrl = environment.restUrl;

  constructor(private http: HttpClient) { }

  private refresh = new Subject<void>();

  get Refresh(){
    return this.refresh;
  }

  public getAllBook(): Observable<Book[]> {
    return this.http.get<Book[]>(`${this.restUrl}/bookapp/v1/getallbook`).pipe(delay(2000))
  }

  public getBook(isbn: number): Observable<Book> {
    return this.http.get<Book>(`${this.restUrl}/bookapp/v1/getbook/${isbn}`)
  }

  public addBook(book: Book): Observable<Book>{
    const httpOptions = {
      headers: new HttpHeaders({
        'admintoken': ''+sessionStorage.getItem('admintoken')
      })
    };
    return this.http.post<Book>(`${this.restUrl}/bookapp/v1/addbook`, book, httpOptions)
    .pipe(tap(() => {
      this.Refresh.next();
    }))
  }

  public assignStore(bookISBN: Number, storeName: String): Observable<any>{
    const httpOptions = {
      headers: new HttpHeaders({
        'admintoken': ''+sessionStorage.getItem('admintoken')
      })
    };
    return this.http.post<any>(`${this.restUrl}/bookapp/v1/assignstore`, {bookISBN, storeName}, httpOptions)
  }

  public deleteBook(isbn: Number): Observable<any> {
    const httpOptions = {
      headers: new HttpHeaders({
        'admintoken': ''+sessionStorage.getItem('admintoken')
      })
    };
    return this.http.delete<any>(`${this.restUrl}/bookapp/v1/delete/${isbn}`, httpOptions)
  }
  public uploadImage(file: FormData): Observable<File> {
    const httpOptions = {
      headers: new HttpHeaders({
        'admintoken': ''+sessionStorage.getItem('admintoken')
      })
    };
    return this.http.post<File>(`${this.restUrl}/bookapp/v1/uploadimage`, file, httpOptions)
  }
}
