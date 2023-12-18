import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Media } from '../models/Media';
import { SuccessInfo } from '../models/SuccessInfo';


@Injectable({
  providedIn: 'root'
})
export class MediaService {

  MEDIA_SERVICE_URL = 'http://localhost:4003/media/';

  constructor(private httpClient: HttpClient) { }

  addMedia(postId:number, imgIds: number[]): Observable<SuccessInfo> {
    return <Observable<SuccessInfo>>this.httpClient.post(this.MEDIA_SERVICE_URL + 'add/' + postId, imgIds);
  }

  getMediaOfPost(postId:number): Observable<Media[]> {
    return <Observable<Media[]>> this.httpClient.get(this.MEDIA_SERVICE_URL + 'get/' + postId);
  }

  deleteMediaOfPost(postId:number): Observable<SuccessInfo> {
    return <Observable<SuccessInfo>> this.httpClient.delete(this.MEDIA_SERVICE_URL + 'delete/' + postId);
  }

}