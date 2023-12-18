import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Like } from '../models/Like';
import { LikeInfo } from '../models/LikeInfo';
import { PostActivity } from '../models/PostActivity';


@Injectable({
  providedIn: 'root'
})
export class LikeService {

  LIKE_SERVICE_URL = 'http://localhost:4003/likes/';

  constructor(private httpClient:HttpClient) { }

  public handleLike(like:Like) : Observable<PostActivity> {
    return <Observable<PostActivity>> this.httpClient.post(this.LIKE_SERVICE_URL + 'handle-like',like);
  }

  public handleUnLike(like:Like) : Observable<PostActivity> {
    return <Observable<PostActivity>> this.httpClient.post(this.LIKE_SERVICE_URL + 'handle-unlike',like);
  }

  public checkLike(postId:number,userId:number) : Observable<LikeInfo> {
    return <Observable<LikeInfo>> <unknown>this.httpClient.get(this.LIKE_SERVICE_URL + 'check-like/'+postId+'/'+userId);
  }
}