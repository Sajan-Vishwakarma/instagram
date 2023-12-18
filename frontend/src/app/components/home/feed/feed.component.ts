import { Component } from '@angular/core';
import { User } from '../../../models/User';
import { LocalStorageService } from '../../../services/local-storage.service';
import { PostService } from '../../../services/post.service';

@Component({
  selector: 'app-feed',
  templateUrl: './feed.component.html',
  styleUrl: './feed.component.css'
})
export class FeedComponent {
  feedPosts !: any[];
  loggedInUser !: User;
  feedLength !: number;
  
  constructor(private localSS:LocalStorageService, private postService:PostService) { }

  ngOnInit(): void {
    
    this.loggedInUser = this.localSS.getItem('user');

    this.postService.getFeedForUserByFollowees(this.loggedInUser.username)
      .subscribe(
        (posts) => {
          this.feedPosts = posts;
          this.feedLength = this.feedPosts.length;
        },
        (err) => console.error('Error fetching posts for feed')
      )
  }
}
