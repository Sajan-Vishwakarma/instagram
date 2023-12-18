import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Login } from '../models/Login';
import { PasswordReset } from '../models/PasswordReset';
import { SuccessInfo } from '../models/SuccessInfo';
import { User } from '../models/User';


@Injectable({
  providedIn: 'root'
})
export class UsersService {

  USER_SERVICE_URL = 'http://localhost:4001/';

  constructor(private httpClient: HttpClient) { }

  getAllUsers(): Observable<User[]> {
    return <Observable<User[]>>this.httpClient.get(this.USER_SERVICE_URL + 'users/allusers');
  }

  searchUsernameMatches(username:string) : Observable<User[]> {
    username = username.substring(1);
    return <Observable<User[]>> this.httpClient.get(this.USER_SERVICE_URL + 'users/match/' + username);
  }

  getUser(username: string): Observable<User> {
    return <Observable<User>>this.httpClient.get(this.USER_SERVICE_URL + 'users/user/' + username);
  }

  updateUser(userData: User): Observable<SuccessInfo> {
    return this.httpClient.put(this.USER_SERVICE_URL + 'users/update/' + userData.userId, userData);
  }

  registerUser(userData: User): Observable<SuccessInfo> {
    return this.httpClient.post(this.USER_SERVICE_URL + 'users/register', userData);
  }

  loginUser(login: Login): Observable<User> {
    return <Observable<User>>this.httpClient.post(this.USER_SERVICE_URL + 'users/loginuser', login);
  }

  resetPassword(pwdResetData:PasswordReset): Observable<SuccessInfo> {
    return <Observable<SuccessInfo>> this.httpClient
      .post(this.USER_SERVICE_URL + 'users/reset-password', pwdResetData); 
  }
}