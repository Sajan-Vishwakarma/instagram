import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { CommentInfo } from '../models/CommentInfo';
import { Comments } from '../models/Comments';
import { PostActivity } from '../models/PostActivity';
import { SuccessInfo } from '../models/SuccessInfo';


@Injectable({
  providedIn: 'root'
})
export class CommentService {

  COMMENT_SERVICE_URL = 'http://localhost:4003/comments/'

  constructor(private httpClient: HttpClient) { }

  public addComment(comment: Comments): Observable<PostActivity> {
    return <Observable<PostActivity>>this.httpClient.post(this.COMMENT_SERVICE_URL + 'add', comment);
  }

  public getAllCommentsOfAPost(postId: number): Observable<CommentInfo[]> {
    return <Observable<CommentInfo[]>>this.httpClient.get(this.COMMENT_SERVICE_URL + 'get/' + postId);
  }

  public deleteComment(commentId: number): Observable<SuccessInfo> {
    return <Observable<SuccessInfo>>this.httpClient.delete(this.COMMENT_SERVICE_URL + 'delete/comment/' + commentId);
  }
}
