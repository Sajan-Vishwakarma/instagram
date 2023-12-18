import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { PostActivity } from '../models/PostActivity';


@Injectable({
  providedIn: 'root'
})
export class PostActivityService {

  POST_ACTIVITY_SERVICE_URL = 'http://localhost:4003/posts-activity/'

  constructor(private httpClient:HttpClient) { }

  public getLikeAndCommentCount(postId:number) : Observable<PostActivity> {
    return <Observable<PostActivity>> this.httpClient.get(this.POST_ACTIVITY_SERVICE_URL + 'get/' + postId);
  }

  public likePost(postId:number) : Observable<PostActivity> {
    return <Observable<PostActivity>> this.httpClient.post(this.POST_ACTIVITY_SERVICE_URL + 'like/' + postId, null);
  }

  public unlikePost(postId:number) : Observable<PostActivity> {
    return <Observable<PostActivity>> this.httpClient.post(this.POST_ACTIVITY_SERVICE_URL + 'unlike/' + postId, null);
  }
}