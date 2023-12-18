import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { PostInfo } from '../models/PostInfo';


@Injectable({
  providedIn: 'root'
})
export class TrendingService {

  TRENDING_SERVICE_URL = 'http://localhost:4004/trending/search/';
  
  constructor(private httpClient:HttpClient) { }

  public searchByTagName(tagname:string): Observable<PostInfo[]>{
    tagname = tagname.substring(1);
    return <Observable<PostInfo[]>> this.httpClient.get(this.TRENDING_SERVICE_URL + 'posts/' + tagname);
  }

}
