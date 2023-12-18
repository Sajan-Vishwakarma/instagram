import { Component } from '@angular/core';
import { PostInfo } from '../../../models/PostInfo';
import { User } from '../../../models/User';
import { LocalStorageService } from '../../../services/local-storage.service';
import { PostService } from '../../../services/post.service';

@Component({
  selector: 'app-explore',
  templateUrl: './explore.component.html',
  styleUrl: './explore.component.css'
})
export class ExploreComponent {
  feedPosts !: PostInfo[];
  loggedInUser !: User;

  constructor(private localSS:LocalStorageService, private postService:PostService) { }

  ngOnInit(): void {
    
    this.loggedInUser = this.localSS.getItem('user');

    this.postService.getAllPublicPosts()
      .subscribe(
        (posts) => {
          this.feedPosts = posts;
        },
        (err) => console.error('Error fetching posts for feed')
      )
  }
}
