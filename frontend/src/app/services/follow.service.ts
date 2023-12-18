import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Connection } from '../models/Connection';
import { Follow } from '../models/Follow';
import { User } from '../models/User';


@Injectable({
  providedIn: 'root'
})
export class FollowService {

  FOLLOW_SERVICE_URL = 'http://localhost:4002/';

  constructor(private httpClient: HttpClient) { }

  getAllFollowers(username: string): Observable<User[]> {
    return <Observable<User[]>>this.httpClient.get(this.FOLLOW_SERVICE_URL + 'follows/followers/' + username);
  }

  getAllFollowees(username: string): Observable<User[]> {
    return <Observable<User[]>>this.httpClient.get(this.FOLLOW_SERVICE_URL + 'follows/followees/' + username);
  }

  doesFollows(connection: Connection): Observable<Follow> {
    return <Observable<Follow>>this.httpClient.post(this.FOLLOW_SERVICE_URL + 'follows/check-connection', connection);
  }

  followRequest(connection: Connection): Observable<Follow> {
    return <Observable<Follow>>this.httpClient.post(this.FOLLOW_SERVICE_URL + 'follows/handle-follow', connection);
  }

  unfollowRequest(connection: Connection): Observable<Follow> {
    return <Observable<Follow>>this.httpClient.post(this.FOLLOW_SERVICE_URL + 'follows/handle-unfollow', connection);
  }

  getTopFollowedUsers(): Observable<User[]> {
    return <Observable<User[]>> this.httpClient.get(this.FOLLOW_SERVICE_URL + 'follows/top-followed');
  }
}