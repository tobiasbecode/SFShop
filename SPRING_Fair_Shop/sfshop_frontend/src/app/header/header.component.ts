import { Component, OnInit, OnDestroy } from '@angular/core';
import { Router } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { AuthService } from '../auth/auth.service';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {

  isAuthenticated = false;
  cartSymbol: string;
  logo: string;
  private userSub: Subscription;
  username: string;
  isAdmin = false;

  constructor(private authService: AuthService, private http: HttpClient, private router: Router) {
    this.cartSymbol = 'assets/images/home/cart/cart.png';
    this.logo = 'assets/images/logo/logo.png';

  }


  // Login-Check for Navigation
  ngOnInit() {
    this.userSub = this.authService.user.subscribe(user => {
      this.isAuthenticated = !!user;
      this.isAdmin = !!user && user.username === 'admin';
      this.username = user.username;

    });

    this.checkLogin();
  }

  checkLogin() {
    if ((JSON.parse(sessionStorage.getItem('user')) !== null)) {
    const userSession = JSON.parse(sessionStorage.getItem('user'));

    if (userSession !== null) {
    this.username = userSession.username;
    this.isAuthenticated = !!userSession;
    this.isAdmin = !!userSession && userSession.username === 'admin';
    }
  }
  }

  ngOnDestroy() {
    this.userSub.unsubscribe();
  }

  onLogout() {
    this.authService.logout().subscribe(msg => {
      this.isAuthenticated = false;
      this.isAdmin = false;
      sessionStorage.setItem('user', null);
      this.router.navigate(['']);

    });
  }

}
