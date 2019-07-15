import { Component } from '@angular/core';
import { NgForm } from '@angular/forms';
import { AuthService } from './auth.service';
import { HttpClient, HttpHeaders, HttpXsrfTokenExtractor } from '@angular/common/http';
import { Router } from '@angular/router';


@Component
    ({
        selector: 'app-auth',
        templateUrl: './auth.component.html'
    })

export class AuthComponent {

    isLoggedIn = true;
    isLoading = false;

    tokenExtract: string;


    constructor(private router: Router, private authService: AuthService,
                private http: HttpClient) {
    }

    // Login Form - passes Credentials to PostRequest on API-Gateway

    onSubmit(authForm: NgForm) {
        const data = JSON.stringify({ username: authForm.value.username, password: authForm.value.password });

        const username = JSON.stringify(authForm.value.username);

        this.isLoading = true;
        this.authService.login(data, username).subscribe(
            res => {
                this.isLoading = false;
                this.router.navigate(['/home']);
            }, error => {
                this.isLoading = false;
            }
        );

        authForm.reset();
    }
}
