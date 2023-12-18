import { Component } from '@angular/core';
import { PostInfo } from '../../models/PostInfo';
import { User } from '../../models/User';
import { TrendingService } from '../../services/trending.service';
import { UsersService } from '../../services/users.service';

@Component({
  selector: 'app-search',
  templateUrl: './search.component.html',
  styleUrl: './search.component.css'
})
export class SearchComponent {
  searchtext !: string
  searchUsersResults !: User[]
  searchPostsResults !: PostInfo[]

  constructor(private usersService:UsersService, private trendingService:TrendingService) { }

  ngOnInit(): void {
  }

  
  onSearch(){
    this.searchPostsResults = []
    this.searchUsersResults = []
    const text = this.searchtext;
    if( text[0] == '#') this.fetchPosts(text);
    if( text[0] == '@') this.fetchUser(text);
  }

  fetchUser(text:string){
    this.usersService.searchUsernameMatches(text)
      .subscribe(
        (users) => this.searchUsersResults = users,
        (err) => console.error('Error while fetching users while searching')
      )
  }

  fetchPosts(text:string){
    this.trendingService.searchByTagName(text)
      .subscribe(
        (posts) => this.searchPostsResults = posts,
        (err) => console.error('Error while fetching posts while searching')
      )
  }
}
