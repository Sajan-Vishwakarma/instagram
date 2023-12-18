import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Image } from '../models/Image';


@Injectable({
  providedIn: 'root'
})
export class ImageService {

  IMAGE_SERVICE_URL = 'http://localhost:4001/';

  constructor(private httpClient: HttpClient) { }

  getImage(imgId: number): Observable<Image> {
    return <Observable<Image>>this.httpClient.get(this.IMAGE_SERVICE_URL + 'images/image/' + imgId);
  }

  updateImage(imgId:number, imgFile: FormData): Observable<Image> {
    return <Observable<Image>> this.httpClient.put(this.IMAGE_SERVICE_URL + 'images/image/' + imgId, imgFile);
  }

  uploadImage(imgFile: FormData): Observable<number> {
    return <Observable<number>> this.httpClient.post(this.IMAGE_SERVICE_URL + 'images/image', imgFile);
  }

  uploadImages(imgFiles: FormData): Observable<number[]>{
    return <Observable<number[]>> this.httpClient.post(this.IMAGE_SERVICE_URL + 'images/uploads', imgFiles);
  }
}