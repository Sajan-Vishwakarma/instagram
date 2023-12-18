import { Component, EventEmitter, Input, Output } from '@angular/core';
import { Connection } from '../../../models/Connection';
import { User } from '../../../models/User';
import { FollowService } from '../../../services/follow.service';
import { ImageService } from '../../../services/image.service';
import { LocalStorageService } from '../../../services/local-storage.service';

@Component({
  selector: 'app-user-list',
  templateUrl: './user-list.component.html',
  styleUrl: './user-list.component.css'
})
export class UserListComponent {
  @Input()
  user !: User;
  loggedInUser !: User;
  doesFollows !: boolean
  profileImageURL !: string;
  followersCount !: number;

  @Output()
  followActivityEvent = new EventEmitter<number>();

  constructor(private localSS: LocalStorageService, private followService: FollowService,
    private imageService:ImageService) { }

  ngOnInit(): void {
    this.loggedInUser = this.localSS.getItem('user');
    this.doesAlreadyFollows();
    if( this.user.profileImgId)
      this.fetchProfileImage(this.user.profileImgId);
    this.fetchFollowersCount();
  }

  doesAlreadyFollows() {
    let connection: Connection = {
      followerName: this.loggedInUser.username,
      followeeName: this.user.username
    }

    this.followService.doesFollows(connection)
      .subscribe(
        (followRes) => {
          this.doesFollows = followRes.isConnected;
        },
        (err) => {
          console.error('Error checking does follows')
        }
      )
  }

  followUser() {
    let connection: Connection = {
      followerName: this.loggedInUser.username,
      followeeName: this.user.username
    }
    this.followService.followRequest(connection)
      .subscribe(
        (res) => {
          this.doesFollows = res.isConnected;
          this.fetchFollowersCount();
          this.followActivityEvent.emit(1);
        }
      )
  }

  unfollowUser() {
    let connection: Connection = {
      followerName: this.loggedInUser.username,
      followeeName: this.user.username
    }
    this.followService.unfollowRequest(connection)
      .subscribe(
        (res) => {
          this.doesFollows = res.isConnected;
          this.fetchFollowersCount();
          this.followActivityEvent.emit(0);
        }
      )
  }

  isOtherUser(): boolean {
    if (this.loggedInUser.username !== this.user.username)
      return true;
    return false;
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

  fetchFollowersCount(){
    this.followService.getAllFollowers(this.user.username)
      .subscribe(
        (res) => {
          this.followersCount = res.length ? res.length : 0
        }
      )
  }
}
