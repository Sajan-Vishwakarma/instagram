import { Component } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { User } from '../../models/User';
import { FollowService } from '../../services/follow.service';
import { LocalStorageService } from '../../services/local-storage.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrl: './home.component.css'
})
export class HomeComponent {
  loggedInUser !: User;
  topFollowedUsers !: User[]
  explorePage !: false;

  constructor(private localSS:LocalStorageService, private router:Router,
    private followService:FollowService, private route:ActivatedRoute) { }

  ngOnInit() {
    this.loggedInUser = this.localSS.getItem('user');
    this.fetchTopUsers();
  }

  fetchTopUsers(){
    this.followService.getTopFollowedUsers()
      .subscribe(
        (users) => this.topFollowedUsers = users,
        (err) => console.error('Error fetching top followed users')
      )
  }
}
