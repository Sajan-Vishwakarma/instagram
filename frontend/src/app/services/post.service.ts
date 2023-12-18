import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Post } from '../models/Post';
import { PostInfo } from '../models/PostInfo';
import { SuccessInfo } from '../models/SuccessInfo';

@Injectable({
  providedIn: 'root'
})
export class PostService {

  POST_SERVICE_URL = 'http://localhost:4003/posts/';

  constructor(private httpClient:HttpClient) { }

  public createPost(post:Post): Observable<number>{
    return <Observable<number>> this.httpClient.post(this.POST_SERVICE_URL + 'create', post);
  }

  public getPostDetails(postId:number): Observable<PostInfo> {
    return <Observable<PostInfo>> this.httpClient.get( this.POST_SERVICE_URL + 'post/' + postId); 
  }

  public getAllPostsOfUser(userId:number): Observable<PostInfo[]>{
    return <Observable<PostInfo[]>> this.httpClient.get(this.POST_SERVICE_URL + 'get/user/'+userId);
  }

  public getAllPublicPosts(): Observable<PostInfo[]>{
    return <Observable<PostInfo[]>> this.httpClient.get(this.POST_SERVICE_URL + 'public/all');
  }

  public getAllPublicPostsOfUser(userId:number): Observable<PostInfo[]>{
    return <Observable<PostInfo[]>> this.httpClient.get(this.POST_SERVICE_URL + 'public/users/' + userId);
  }

  public getFeedForUserByFollowees(username:string): Observable<PostInfo[]>{
    return <Observable<PostInfo[]>> this.httpClient.get(this.POST_SERVICE_URL + 'user/followee/' + username);
  }

  public deletePost(postId:number): Observable<SuccessInfo>{
    return <Observable<SuccessInfo>> this.httpClient.delete(this.POST_SERVICE_URL + 'delete/' + postId);
  }
}