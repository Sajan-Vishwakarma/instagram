import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
import { User } from '../models/User';
import { LocalStorageService } from './local-storage.service';

@Injectable({
  providedIn: 'root'
})
export class SharedImageUserService {

  constructor(private localSS: LocalStorageService) { }

  userSubject$: BehaviorSubject<User> = new BehaviorSubject<User>(this.localSS.getItem('user'));
  profileImageSubject$: BehaviorSubject<string> =
    new BehaviorSubject<string>(this.localSS.getItem('PROFILE_IMG'))
}