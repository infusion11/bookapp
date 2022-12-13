import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { LoginService } from 'src/app/services/login.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  admintoken: any;

  constructor(private loginService: LoginService, private router: Router) { }

  ngOnInit(): void {
  }
  loginOnSubmit(){
    this.getToken(this.un.value, this.pw.value);
  }

  loginForm = new FormGroup({
    un: new FormControl('', [Validators.required]),
    pw: new FormControl('', [Validators.required]),
  });



  public getToken(un: any, pw: any): void {
    this.loginService.login(un, pw).subscribe({
      next: (response) => {
        this.admintoken = response.admintoken;
        sessionStorage.setItem('admintoken', this.admintoken);
        
        const auth = JSON.parse(localStorage.getItem('Auth') || '[]');
        auth.push(window.btoa(un));
        auth.push(window.btoa(pw));
        sessionStorage.setItem('Auth', JSON.stringify(auth));
        this.router.navigate(['admin']);},
      error: (error) => {
        this.loginForm.reset(),
        console.log(error),
        console.log('Failed attempt.')
      },
      complete: () => console.log('Successful(login)')
    })
  }

  get un(): FormControl{
    return this.loginForm.get('un') as FormControl;
  }
  get pw(): FormControl {
    return this.loginForm.get('pw') as FormControl;
  }
}
