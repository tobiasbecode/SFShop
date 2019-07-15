import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams, HttpXsrfTokenExtractor } from '@angular/common/http';
import { tap } from 'rxjs/operators';
import { Subject, BehaviorSubject, Observable } from 'rxjs';
import { User } from './user.model';


export interface AuthResponseData {
    accessToken: any;
}


@Injectable({ providedIn: 'root' })
export class AuthService {

    user = new Subject<User>();
    token = new BehaviorSubject<User>(null);
    tokenActual: any;

    headers: any;
    response: any;

    constructor(private http: HttpClient) {
    }

    // Post-Request on API-Gateway with Login Data
    login(data: any, username: string) {
        const httpOptions: { headers } = {
            headers: new HttpHeaders({
                'Content-Type': 'application/json'
            }),
        };


        return this.http.post<AuthResponseData>('/login', data, httpOptions)
            .pipe(tap(resData => {
                const user = new User(username.substring(1, username.length - 1),
                    (JSON.stringify(resData)).substring(1, username.length - 1));

                this.user.next(user);

                sessionStorage.setItem('user', JSON.stringify(user));
            }));

    }

    // Post-Request on API-Gateway for Logout
    logout(): Observable<any> {
        return new Observable(subscriber => {


            this.user.subscribe(user => {
                this.tokenActual = user._token;
            });

            const headers = new HttpHeaders({
                'Content-Type': 'application/json',
                'Authorization': 'Bearer + ""' + this.tokenActual
            });
            this.http.post('/logout',
                { headers })

                .subscribe(data => {
                    subscriber.next(data);
                });
        });
    }
}






