import { Component, EventEmitter, Input, Output } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { CommentInfo } from '../../../models/CommentInfo';
import { Comments } from '../../../models/Comments';
import { Connection } from '../../../models/Connection';
import { Like } from '../../../models/Like';
import { User } from '../../../models/User';
import { CommentService } from '../../../services/comment.service';
import { FollowService } from '../../../services/follow.service';
import { LikeService } from '../../../services/like.service';
import { LocalStorageService } from '../../../services/local-storage.service';
import { PostActivityService } from '../../../services/post-activity.service';
import { PostService } from '../../../services/post.service';

@Component({
  selector: 'app-profile-post',
  templateUrl: './profile-post.component.html',
  styleUrl: './profile-post.component.css'
})
export class ProfilePostComponent {
  @Input()
  post !: any;

  @Output()
  deletePostEvent = new EventEmitter<number>();

  daysAgo !: number;
  loggedInUser !: User;
  userInfo !: User;
  likeCount !: number;
  commentCount !: number;
  didLikedPost !: boolean;
  doesFollows !: boolean;
  comments !: CommentInfo[];

  commentForm !: FormGroup;

  constructor(private localSS: LocalStorageService, private postActivityService: PostActivityService,
    private likeService: LikeService, private commentService: CommentService,
    private followService: FollowService, private formBuilder: FormBuilder,
    private postService:PostService) { }

  ngOnInit(): void {
    this.loggedInUser = this.localSS.getItem('user');
    this.userInfo = this.post.userInfoDTO;
    this.daysAgo = this.calculateDaysAgo(this.post.postDTO.createdAt);
    this.getPostAndCommentCount();
    this.checkWhetherUserLikedPostOrNot();
    this.loadComments();
    this.doesAlreadyFollows();

    this.commentForm = this.formBuilder.group({
      comment: ['',[Validators.required]]
    })
  }

  calculateDaysAgo(oldDate: any) {
    var today = new Date();
    var createdOn = new Date(oldDate);
    var msInDay = 24 * 60 * 60 * 1000;
    createdOn.setHours(0, 0, 0, 0);
    today.setHours(0, 0, 0, 0)
    var diff = (+today - +createdOn) / msInDay
    return diff;
  }

  getPostAndCommentCount() {
    this.postActivityService.getLikeAndCommentCount(this.post.postDTO.postId)
      .subscribe(
        (res) => {
          this.commentCount = res.commentCount;
          this.likeCount = res.likeCount;
        },
        (err) => console.error('Error while fetching like and comment count')
      )
  }

  checkWhetherUserLikedPostOrNot() {
    if (!this.loggedInUser.userId) return;
    const postId = this.post.postDTO.postId;
    const userId = this.loggedInUser.userId;

    this.likeService.checkLike(postId, userId)
      .subscribe(
        (res) => {
          this.didLikedPost = res.didLiked
        },
        (err) => console.error('Error while fetching info whether liked post or not')
      )
  }

  setLikeUnlikePost() {
    if (!this.loggedInUser.userId) return;

    const like: Like = {
      postId: this.post.postDTO.postId,
      userId: this.loggedInUser.userId
    }
    if (this.didLikedPost)
      this.unlikePost(like);
    else
      this.likePost(like);
  }

  likePost(like: Like) {
    this.likeService.handleLike(like)
      .subscribe(
        (res) => {
          this.commentCount = res.commentCount;
          this.likeCount = res.likeCount;
          this.checkWhetherUserLikedPostOrNot();
        },
        (err) => console.error('Error while liking post')
      )
  }

  unlikePost(like: Like) {
    this.likeService.handleUnLike(like)
      .subscribe(
        (res) => {
          this.commentCount = res.commentCount;
          this.likeCount = res.likeCount;
          this.checkWhetherUserLikedPostOrNot();
        },
        (err) => console.error('Error while unliking post')
      )
  }

  doesAlreadyFollows() {
    let connection: Connection = {
      followerName: this.loggedInUser.username,
      followeeName: this.userInfo.username
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
      followeeName: this.userInfo.username
    }
    this.followService.followRequest(connection)
      .subscribe(
        (res) => {
          this.doesFollows = res.isConnected;
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
          this.doesFollows = res.isConnected;
        }
      )
  }

  loadComments() {
    this.commentService.getAllCommentsOfAPost(this.post.postDTO.postId)
      .subscribe(
        (comments) => {
          this.comments = comments;
        }
      )
  }

  addComment() {
    if (!this.loggedInUser.userId) return;

    const commentData: Comments = {
      postId: this.post.postDTO.postId,
      userId: this.loggedInUser.userId,
      comment: this.commentForm.value.comment
    }

    this.commentService.addComment(commentData)
      .subscribe(
        (res) => {
          this.loadComments();
          this.getPostAndCommentCount();
          this.commentForm.reset();
        },
        (err) => {
          console.error('Error posting comment to post');
        }
      )

  }

  deleteComment(commentId: any) {
    this.commentService.deleteComment(commentId)
      .subscribe(
        (res) => {
          this.loadComments();
          this.getPostAndCommentCount();
        },
        (err) => {
          console.error('Error while deleting comment');
        }
      )
  }

  deletePost(){
    console.log(this.post.postDTO.postId);
    this.postService.deletePost(this.post.postDTO.postId)
      .subscribe(
        (res) => {
          console.log('Deleted post successfully');
          this.deletePostEvent.emit(this.post.postDTO.postId);
        },
        (err) => {
          console.error('Error while deleting post');
        }
      )
  }
}
