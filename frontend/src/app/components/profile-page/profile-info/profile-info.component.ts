import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ENVIRONMENT } from '../../../environments/environment';
import { Connection } from '../../../models/Connection';
import { PostInfo } from '../../../models/PostInfo';
import { User } from '../../../models/User';
import { FollowService } from '../../../services/follow.service';
import { ImageService } from '../../../services/image.service';
import { LocalStorageService } from '../../../services/local-storage.service';
import { PostService } from '../../../services/post.service';
import { UsersService } from '../../../services/users.service';

@Component({
  selector: 'app-profile-info',
  templateUrl: './profile-info.component.html',
  styleUrl: './profile-info.component.css'
})
export class ProfileInfoComponent {
  userInfo !: User;
  profileImageURL !: string;
  followersList !: User[];
  followeesList !: User[];
  loggedInUser !: User;
  followersCount !: number;
  followeesCount !: number;
  postsCount !: number;
  doesFollows !: boolean;

  postsOfUser !: PostInfo[]

  constructor(private router: ActivatedRoute, private usersService: UsersService,
    private localSS: LocalStorageService, private imageService: ImageService,
    private followService: FollowService, private postService: PostService) {
    this.profileImageURL = ENVIRONMENT.DEF_PROFILE_PIC
    this.loggedInUser = this.localSS.getItem('user');
  }

  ngOnInit(): void {

    this.router.params
      .subscribe(
        (params) => {
          const username = params['username'];
          // fetching and populating user details to this.userInfo
          this.usersService.getUser(username)
            .subscribe(
              (fetchedUser) => {
                this.userInfo = fetchedUser;
                let imgId: number | undefined = fetchedUser.profileImgId;
                if (imgId != undefined) this.fetchProfileImage(imgId);
                this.fetchFollowees(this.userInfo.username);
                this.fetchFollowers(this.userInfo.username);
                this.alreadyFollows();
              },
              (err) => console.log(err)
            );
        },
        (err) => {
          console.log(err)
        }
      )
  }

  fetchProfileImage(imgID: number) {
    this.imageService.getImage(imgID)
      .subscribe(
        (image) => {
          this.profileImageURL = image.img_url;
        },
        (err) => {
          console.log("Error fetching profile picture")
        },
        () => console.log('Fetched profile picture')
      )
  }

  fetchFollowers(username: string) {
    this.followService.getAllFollowers(username)
      .subscribe(
        (followers) => {
          this.followersList = followers
        },
        (err) => {
          console.log("Error fetching followers list")
        },
        () => {
          this.followersCount = this.followersList.length ? this.followersList.length : 0
          console.log('Fetched followers list')
        }
      )
  }

  fetchFollowees(username: string) {
    this.followService.getAllFollowees(username)
      .subscribe(
        (followees) => {
          this.followeesList = followees
        },
        (err) => {
          console.log("Error fetching followees list")
        },
        () => {
          this.followeesCount = this.followeesList.length ? this.followeesList.length : 0
          console.log('Fetched followees list')
        }
      )
  }

  isOtherUser(): boolean {
    if (this.loggedInUser.username !== this.userInfo.username)
      return true;
    return false;
  }

  alreadyFollows() {
    let connection: Connection = {
      followerName: this.loggedInUser.username,
      followeeName: this.userInfo.username
    }

    this.followService.doesFollows(connection)
      .subscribe(
        (followRes) => {
          this.doesFollows = followRes.isConnected;
          this.fetchPostsOfUser(this.doesFollows);
        },
        (err) => {
          console.log('Error checking does follows')
        },
        () => {
          console.log('does follows value: ' + this.doesFollows);
        }
      )
  }

  followUser() {
    let connection: Connection = {
      followerName: this.loggedInUser.username,
      followeeName: this.userInfo.username
    }
    this.followService.followRequest(connection)
      .subscribe(
        (res) => {
          this.fetchFollowers(connection.followeeName);
          this.doesFollows = res.isConnected;
          this.fetchPostsOfUser(this.doesFollows);
        }
      )
  }

  unfollowUser() {
    let connection: Connection = {
      followerName: this.loggedInUser.username,
      followeeName: this.userInfo.username
    }
    this.followService.unfollowRequest(connection)
      .subscribe(
        (res) => {
          this.fetchFollowers(connection.followeeName);
          this.doesFollows = res.isConnected;
          this.fetchPostsOfUser(this.doesFollows);
        }
      )
  }

  fetchPostsOfUser(showsAllPost: boolean) {
    // viewing same profile
    if (!this.isOtherUser() && this.loggedInUser.userId) {
      this.postService.getAllPostsOfUser(this.loggedInUser.userId)
        .subscribe(
          (posts) => {
            this.postsOfUser = posts;
            this.postsCount = this.postsOfUser.length;
          },
          (err) => console.error('Error fetching posts for same user')
        )
      return;
    }

    // viewing some other people's profile
    if (showsAllPost && this.userInfo.userId) {
      this.postService.getAllPostsOfUser(this.userInfo.userId)
        .subscribe(
          (posts) => {
            this.postsOfUser = posts;
            this.postsCount = this.postsOfUser.length;
          },
          (err) => console.error('Error fetching posts for followee user')
        )
      return;
    }

    if (!this.userInfo.userId) return;
    this.postService.getAllPublicPostsOfUser(this.userInfo.userId)
      .subscribe(
        (posts) => {
          this.postsOfUser = posts;
          this.postsCount = this.postsOfUser.length;
        },
        (err) => console.error('Error fetching public posts of user')
      )
  }

  handleChild(event: any) {
    if( this.loggedInUser.username != this.userInfo.username)
      return;
    
    if( event.value == 1) // follow
      this.fetchFollowees(this.userInfo.username);
  }
  
  callfetchfollowees() {
    this.fetchFollowees(this.userInfo.username);
  }

  deltePostHandleFromChild(event: any) {
    this.fetchPostsOfUser(true);
  }

}
