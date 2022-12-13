import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AdmindashboardComponent } from './components/admindashboard/admindashboard.component';
import { AllBooksComponent } from './components/all-books/all-books.component';
import { HomeComponent } from './components/home/home.component';
import { LoginComponent } from './components/login/login.component';
import { MyBooksComponent } from './components/my-books/my-books.component';
import { AuthGuard } from './security/auth.guard';

const routes: Routes = [
    {path: '', component: HomeComponent, title: "Book app | Home"},
    {path: 'books', component: AllBooksComponent, title: "Book app | All book"},
    {path: 'admin',canActivate:[AuthGuard], component: AdmindashboardComponent, title: "Book app | Admin dashboard"},
    {path: 'login', component: LoginComponent, title: "Book app | Login"},
    {path: 'my-books', component: MyBooksComponent, title: "Book app | My-books"},
    {path: '**', redirectTo: ''}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
