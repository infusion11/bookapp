import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {

  constructor(private router: Router) { }
  
  ngOnInit(): void {
  }

  respNavbar: boolean = false;
  showNav(){
    this.respNavbar =! this.respNavbar;
  }
  checkLiked() {
    if(localStorage.getItem('likedItems') == null) {
      return false;
    }else {
      return true;
    }
  }
  isAdminurl(){
    return this.router.url === '/admin';
  }

  Logout(){
    sessionStorage.removeItem('admintoken');
    this.router.navigate(['login']);
  }
}
